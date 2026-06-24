package shop.local.valueObjects;

public class Artikel {

	protected String bezeichnung;
	protected int artNr;
	protected int bestand;
	protected double preis;

	public Artikel(String bezeichnung, int artNr, double preis, int bestand) {
		this.bezeichnung = bezeichnung;
		this.artNr = artNr;
		this.preis = preis;
		this.bestand = bestand;
	}

	public double getPreis() {
		return preis;
	}

	public void setPreis(double preis) {
		this.preis = preis;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public int getArtNr() {
		return artNr;
	}

	public void setArtNr(int artNr) {
		this.artNr = artNr;
	}

	public int getBestand() {
		return bestand;
	}

	public void setBestand(int bestand) {
		this.bestand = bestand;
	}

	@Override
	public String toString() {

		return ("\n\n" + "Artikel Nummer:\t" + artNr + "\n" + "Bezeichnung:\t" + bezeichnung + "\n" + "Preis:\t\t"
				+ preis + "\n");
	}
}
