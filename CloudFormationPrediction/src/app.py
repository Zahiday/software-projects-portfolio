import time
import threading
import json
import urllib.request
import ssl
from flask import Flask, render_template, make_response

from src.stations import DK_STATIONS

app = Flask(__name__, template_folder="../templates")

STATION_CACHE = {}
DEBUG_LOG = []
THREAD_STARTED = False

LAST_UPDATE = 0


def normalize_name(name):
    return name.strip()


def fetch_open_meteo_native(station):
    global STATION_CACHE, DEBUG_LOG

    name = normalize_name(station["name"])
    lat = float(station["lat"])
    lon = float(station["lon"])

    url = (
        "https://api.open-meteo.com/v1/forecast"
        f"?latitude={lat}&longitude={lon}"
        "&current_weather=true"
        "&hourly=relative_humidity_2m,surface_pressure,cloud_cover"
        "&forecast_days=1"
    )

    try:
        ctx = ssl.create_default_context()

        req = urllib.request.Request(
            url,
            headers={
                "User-Agent": "Mozilla/5.0",
                "Accept": "application/json"
            }
        )

        with urllib.request.urlopen(req, context=ctx, timeout=8) as response:
            data = json.loads(response.read().decode("utf-8"))

        if "current_weather" not in data:
            return

        curr = data["current_weather"]
        hourly = data.get("hourly", {})

        temp = curr.get("temperature")
        wind_kmh = curr.get("windspeed")

        hum = hourly.get("relative_humidity_2m", [None])[0]
        press = hourly.get("surface_pressure", [None])[0]
        clouds = hourly.get("cloud_cover", [None])[0]

        if temp is None:
            return

        wind_ms = round(float(wind_kmh) / 3.6, 1) if wind_kmh else None

        STATION_CACHE[name] = {
            "temp": round(float(temp), 1),
            "wind": wind_ms,
            "hum": hum,
            "press": press,
            "clouds": clouds
        }

        DEBUG_LOG.append({
            "station": name,
            "temp": temp,
            "wind": wind_kmh,
            "status": "REAL"
        })

        print(f"[OK] {name} → temp={temp}, wind={wind_kmh}")

    except Exception as e:
        print(f"[ERROR] {name}: {e}")


def background_worker_loop():
    global LAST_UPDATE

    while True:
        print("\n[THREAD] SYNC START\n")

        for s in DK_STATIONS:
            fetch_open_meteo_native(s)

        LAST_UPDATE = int(time.time())

        print("\n[THREAD] SYNC DONE\n")

        time.sleep(600)


@app.route("/")
def index():
    global THREAD_STARTED

    if not THREAD_STARTED:
        for s in DK_STATIONS:
            STATION_CACHE[normalize_name(s["name"])] = {
                "temp": None,
                "wind": None,
                "hum": None,
                "press": None,
                "clouds": None
            }

        t = threading.Thread(target=background_worker_loop, daemon=True)
        t.start()
        THREAD_STARTED = True

    live_data = []

    for s in DK_STATIONS:
        name = normalize_name(s["name"])
        cache = STATION_CACHE.get(name, {})

        temp = cache.get("temp")
        wind = cache.get("wind")
        hum = cache.get("hum")
        press = cache.get("press")
        clouds = cache.get("clouds")

        if temp is None or wind is None:
            continue

        base = int(
            (hum * 0.3 if hum else 0) +
            (abs(1013 - press) * 1.2 if press else 0) +
            (temp * 0.7) +
            (wind * 2 if wind else 0)
        )

        if clouds is not None:
            if clouds > 75:
                base -= 10
            elif 35 <= clouds <= 75:
                base += 10

        prob = max(5, min(base, 98))

        status = (
            "Formed" if prob >= 75 else
            "Potential" if prob >= 45 else
            "Stable"
        )

        live_data.append({
            "name": name,
            "region": s["region"],
            "lat": s["lat"],
            "lon": s["lon"],
            "temp": temp,
            "wind": wind,
            "hum": hum,
            "press": press,
            "clouds": clouds,
            "prob": prob,
            "status": status
        })

    return make_response(render_template(
        "index.html",
        data=live_data,
        last_update=LAST_UPDATE
    ))


@app.route("/debug")
def debug():
    return {
        "cache": STATION_CACHE,
        "log": DEBUG_LOG[-30:],
        "last_update": LAST_UPDATE
    }


if __name__ == "__main__":
    app.run(debug=True, use_reloader=False, port=5000)