package shop.local.valueObjects;

public class Massengutartikel extends Artikel {

	private int einheit;

	public Massengutartikel(String bezeichnung, int artNr, double preis, int bestand, int einheit) {
		super(bezeichnung, artNr, preis, bestand);
		this.einheit = einheit;
	}

	public int getEinheit() {
		return einheit;
	}

	public void setEinheit(int einheit) {
		this.einheit = einheit;
	}

	@Override
	public String toString() {

		return ("\n\n" + "Massengutartikel Nummer:\t" + artNr + "\n" + "Bezeichnung:\t" + bezeichnung + "\n"
				+ "Preis:\t\t" + preis + "\n" + "Packungsgröße: " + einheit + "\n");
	}
}
