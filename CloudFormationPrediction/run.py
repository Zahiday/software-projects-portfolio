import os
import sys

# Setzt den Suchpfad korrekt auf das Hauptverzeichnis fest
sys.path.insert(0, os.path.abspath(os.path.dirname(__file__)))

from src.app import app

if __name__ == "__main__":
    app.run(debug=True, port=5000)
