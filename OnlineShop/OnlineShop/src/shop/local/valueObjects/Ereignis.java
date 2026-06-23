package shop.local.valueObjects;

import java.time.LocalDateTime;

public class Ereignis {

	private LocalDateTime datum;
	private Artikel artikel;
	private int anzahl;
	private Benutzer benutzer;

	public Ereignis(Artikel artikel, int anzahl, Benutzer benutzer) {
		setBenutzer(benutzer);
		datum = LocalDateTime.now();
		this.artikel = artikel;
		this.anzahl = anzahl;
	}

	public LocalDateTime getDatum() {
		return datum;
	}

	public Artikel getArtikel() {
		return artikel;
	}

	public int getAnzahl() {
		return anzahl;
	}

	public Benutzer getBenutzer() {
		return benutzer;
	}

	public void setBenutzer(Benutzer benutzer) {
		this.benutzer = benutzer;
	}

	@Override
	public String toString() {
		return ("\n" + "Datum:\t" + datum + "\n" + "Benutzer:\t" + benutzer.getLoginName() + "\n" + "Artikel:\t"
				+ artikel.getBezeichnung() + "\n" + "Bestandsänderung:\t" + " " + "\n");
	}
}
