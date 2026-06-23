package shop.local.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import shop.local.domain.exceptions.ArtikelNrSchonVorhandenException;
import shop.local.domain.exceptions.ArtikelSchonVorhandenException;
import shop.local.persistence.FilePersistenceManager;
import shop.local.persistence.PersistenceManager;
import shop.local.valueObjects.Artikel;
import shop.local.valueObjects.Massengutartikel;

public class ArtikelVerwaltung {

	private List<Artikel> artikelBestand = new ArrayList<>();
	private PersistenceManager pm = new FilePersistenceManager();

	/**
	 * Artikel werden aus einer externen Datei gelesen
	 * @param datei
	 * @throws IOException
	 */
	public void liesDaten(String datei) throws IOException {
		// PersistenzManager für Lesevorgänge öffnen
		pm.openForReading(datei);

		Artikel einArtikel;
		do {
			// Artikel-Objekt einlesen
			einArtikel = pm.ladeArtikel();
			if (einArtikel != null) {
				artikelBestand.add(einArtikel);
			}
		} while (einArtikel != null);

		// Persistenz-Schnittstelle wieder schließen
		pm.close();
	}

	/**
	 * Artikel werden in einer externen Datei gespeichert 
	 * @param datei
	 * @throws IOException
	 */
	public void schreibeDaten(String datei) throws IOException {
		
		pm.openForWriting(datei);

		for (Artikel b : artikelBestand) {
			pm.speichereArtikel(b);
		}
		pm.close();
	}

	/**
	 * Hier wird ein Artikel aus dem Artikelbestand gelöscht
	 * @param einArtikel Artikel der gelöscht werden soll
	 */
	public void loeschen(Artikel einArtikel) {
		artikelBestand.remove(einArtikel);
	}
	
	/**
	 * Hier wird die ganze Liste des Artikel Bestandes zurück gegeben
	 * @return
	 */
	public List<Artikel> getArtikelBestand() {
		return new ArrayList<>(artikelBestand);
	}

	/**
	 * Hier wird ein Artikelobjekt erstellt und den Artikelbestand hinzugefügt
	 * Wenn die Einheit größer als eins ist wird ein Massengutartikel erzeugt, 
	 * ist sie eins wird ein normaler Artikel erstellt 
	 * 
	 * @param titel
	 * @param artNr
	 * @param preis
	 * @param bestand
	 * @param einheit
	 * @return
	 * @throws ArtikelSchonVorhandenException
	 */
	public Artikel einfuegen(String titel, int artNr, double preis, int bestand, int einheit)
			throws ArtikelNrSchonVorhandenException {

		// Diese For-Schleife durchläuft einmal den gesamten Artikelbestand,
		// ob ein Artikel schon mit der eingegeben Artikelnummer vorhanden ist
		// ist dies der Fall, wird eine Exception geworfen
		// Wenn es die eingegebene Artikelnummer nicht gibt, geht es weiter 
		for (Artikel artikel : artikelBestand) {
			if (artikel.getArtNr() == artNr) {
				throw new ArtikelNrSchonVorhandenException(artNr);
			}
		}

		// Hier wird überprüft ob die Einheit größer 1 ist oder kleiner 1 ist
		// Ist der Wert 1 ist es ein normaler Artikel und man kann den einzeln kaufen
		// Ist der Wert größer 1 kann man das Artikel nur in der vorgegebenen Einheit
		// kaufen
		Artikel a;
		if (einheit > 1) {
			a = new Massengutartikel(titel, artNr, preis, bestand, einheit);

		} else {
			a = new Artikel(titel, artNr, preis, bestand);

		}
		artikelBestand.add(a);
		return a;
	}

	/**
	 * Ertstellt eine Liste von Artikeln mit dem gleichen Titel des gesuchten Artikels
	 * @param titel
	 * @return
	 */
	public List<Artikel> sucheArtikel(String titel) {

		List<Artikel> suchErg = new ArrayList<>();

		Iterator<Artikel> iter = artikelBestand.iterator();
		while (iter.hasNext()) {

			Artikel b = iter.next();
			if (b.getBezeichnung().equals(titel))
				suchErg.add(b);

		}
		return suchErg;
	}

	/**
	 * Mit dieser Methode kann man den Bestand eines Artikels ändern/erhöhen
	 * 
	 * @param artikelNr
	 * @param bestand
	 */
	public void aendereBestandVonArtikel(int artikelNr, int bestand) {

		for (Artikel a : artikelBestand) {
			if (a.getArtNr() == artikelNr) {
				a.setBestand(a.getBestand() + bestand);
			}
		}
	}

	/**
	 * Gibt eine sortierte Liste des aktuellen Bestandes aus
	 * 
	 * @param eingabe
	 */
	public List<Artikel> gibArtikelAus(int eingabe) {
		ArrayList<Artikel> sortierteListe = (ArrayList<Artikel>) artikelBestand;
		switch (eingabe) {
		case 1:
			Collections.sort(sortierteListe, (a, b) -> a.getBezeichnung().compareToIgnoreCase(b.getBezeichnung()));
			break;
		case 2:
			Collections.sort(sortierteListe, (a, b) -> a.getArtNr() - b.getArtNr());
			break;
		}
		;
		return sortierteListe;
	}
}
