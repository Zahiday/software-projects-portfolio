package shop.local.ui.cui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import shop.local.domain.EShop;
import shop.local.domain.exceptions.ArtikelBestandReichtNichtAusException;
import shop.local.domain.exceptions.ArtikelNrSchonVorhandenException;
import shop.local.domain.exceptions.ArtikelSchonVorhandenException;
import shop.local.domain.exceptions.KundeBereitsVorhandenException;
import shop.local.domain.exceptions.LoginUngueltigException;
import shop.local.domain.exceptions.MassengutartikelException;
import shop.local.domain.exceptions.MitarbeiterExistiertBereitsException;
import shop.local.valueObjects.Adresse;
import shop.local.valueObjects.Artikel;
import shop.local.valueObjects.Benutzer;
import shop.local.valueObjects.Ereignis;
import shop.local.valueObjects.Kunde;
import shop.local.valueObjects.Mitarbeiter;
import shop.local.valueObjects.Warenkorb;

/**
 * @author Zahid Aryobi
 *
 */

public class ShopCuiClient {

	private BufferedReader in;
	private EShop shop;
	private Benutzer aktuellerBenutzer;
	private Kunde aktuellerKunde;

	public ShopCuiClient(String daten) throws IOException, Exception, ArtikelSchonVorhandenException {
		// TODO Automatisch generierter Konstruktorstub
		shop = new EShop(daten);
		in = new BufferedReader(new InputStreamReader(System.in));
	}

	// TODO: Mitarbeitemenue

	private void gibArbeiterMenueAus() {
		System.out.print("  \n  ARBEITERNMENUE:                 \n");
		System.out.print("  \n ------------------------------------ \n");
		System.out.print("  \n  Waren anzeigen:                'a'");
		System.out.print("  \n  Artikel hinzufügen:            'e'");
		System.out.print(" 	\n  Artikel löschen:               'l'");
		System.out.print("  \n  Artikel suchen:                'f'");
		System.out.print("  \n  Artikel Bestand ändern:        'z'");
		System.out.print("  \n  Kunden anzeigen:               'b'");
		System.out.print("  \n  Mitarbeiter hinzufügen:        'm'");
		System.out.print("  \n  ausloggen:                     'd'");
		System.out.print("  \n  Zeige alle Ereignissse:        't'");
		System.out.print("  \n\n ------------------------------------ \n");
		System.out.println("\n Beenden:                      'q'\n");
		System.out.print("> ");
		System.out.flush();
	}

	private void gibKundenMenueAus() {
		System.out.print("  \n  KUNDENMENUE: ");
		System.out.print("  \n ------------------------------------ \n");
		System.out.print("  \n  Waren anzeigen:                 'a'");
		System.out.print("  \n  Artikel suchen:                 'f'");
		System.out.print("  \n  ausloggen:                      'd'");
		System.out.print("  \n  Artikel kaufen:                 '+'");
		System.out.print("  \n  Artikel aus Warenkorb l�schen:  '-'");
		System.out.print("  \n  Warenkorb leeren:               'l'");
		System.out.print("  \n  Warenkorb anzeigen:             'w'");
		System.out.print("  \n  Kauf abschließen:               'x' ");
		System.out.print("  \n\n ------------------------------------ \n");
		System.out.println("\n Beenden:                         'q'\n");
		System.out.print("> ");
		System.out.flush();

	}

	private void gibUneingeloggtesMenueAus() {
		System.out.print("  \n LOGIN: ");
		System.out.print("  \n ------------------------------------ \n");
		System.out.print("  \n Kunde anmelden:                'y'");
		System.out.print("  \n Kunde registrieren:            'r'");
		System.out.print("  \n Mitarbeiter anmelden:          'm'");
		System.out.print("  \n\n ------------------------------------ \n");
		System.out.println("\n Beenden:                       'q'\n");
		System.out.print("> ");
		System.out.flush();
	}

	private void verarbeiteEingabe(String input) throws IOException, ArtikelSchonVorhandenException,
			LoginUngueltigException, ArtikelNrSchonVorhandenException, KundeBereitsVorhandenException {

		// Unterscheidung fuer eingaben ob Kunde eingeloggt ist.
		if (aktuellerBenutzer instanceof Kunde) {
			verarbeiteKundenEingabe(input);
		} else if (aktuellerBenutzer instanceof Mitarbeiter) {
			verarbeiteMitarbeiterEingabe(input);
		} else {
			verarbeiteUneingeloggteEingabe(input);
		}
	}

	private void verarbeiteUneingeloggteEingabe(String input) throws IOException, KundeBereitsVorhandenException {

		String login;
		String passwort;
		String vorname;
		String nachname;
		Adresse adresse;
		String strasse;
		String ort;
		String hausNr;
		String plz;

		switch (input) {
		case "y":
			System.out.print("Benutzername  > ");
			login = liesEingabe();
			System.out.print("Passwort  > ");
			passwort = liesEingabe();
			try {
				aktuellerBenutzer = shop.kEinloggen(login, passwort);
				aktuellerKunde = (Kunde) aktuellerBenutzer;
				System.out.println(aktuellerBenutzer.getVorname() + " " + aktuellerBenutzer.getNachname());
			} catch (LoginUngueltigException e) {

				System.err.println(e.getMessage());
				// System.out.println("Login oder Passwort ist falsch!");
			}
			break;
		case "r":
			System.out.print("Vorname  > ");
			vorname = liesEingabe();
			System.out.print("Nachname  > ");
			nachname = liesEingabe();
			System.out.print("Benutzername  > ");
			login = liesEingabe();
			System.out.print("Passwort  > ");
			passwort = liesEingabe();
			System.out.print("Strasse  > ");
			strasse = liesEingabe();
			System.out.print("Hausnummer  > ");
			hausNr = liesEingabe();
			System.out.print("Poistleitzahl  > ");
			plz = liesEingabe();
			System.out.print("Ort  > ");
			ort = liesEingabe();
			adresse = new Adresse(strasse, hausNr, plz, ort);
			try {
				shop.registrieren(vorname, nachname, login, passwort, adresse);

			} catch (KundeBereitsVorhandenException e) {
				System.err.println(e.getMessage());
			}
			break;
		case "m":
			System.out.print("Benutzername  > ");
			login = liesEingabe();
			System.out.print("Passwort  > ");
			passwort = liesEingabe();

			try {
				aktuellerBenutzer = shop.mEinloggen(login, passwort);
				System.out.println(aktuellerBenutzer.getVorname() + " " + aktuellerBenutzer.getNachname());
			} catch (LoginUngueltigException e) {
				System.err.println(e.getMessage());
				// System.out.println("Login oder Passwort ist falsch!");
			}
			break;
		default:
			System.out.println("Eingabe ung�ltig!");
		}
	}

	private void verarbeiteKundenEingabe(String input) throws IOException, ArtikelSchonVorhandenException {
		List<Artikel> liste1;
		String artikelName;
		String anzahl;
		int menge;

		switch (input) {
		case "x":
			shop.kaufAbschliessen(aktuellerKunde);
			aktuellerBenutzer = null;
			break;
		case "w":
			Warenkorb warenkorb = shop.gibWarenImKorb(aktuellerBenutzer);
			gibWarenkorbAus(warenkorb);
			break;
		case "a":
			System.out.println("1. Artikel nach Bezeichnung sortieren");
			System.out.println("2. Artikel nach Artikelnummer sortieren");
			String eingabe = liesEingabe();
			int eingabeInt = Integer.parseInt(eingabe);
			try {
				shop.gibArtikelListeAusKunde(eingabeInt);
			} catch (NumberFormatException e) {
				System.out.println("Falsche Eingabe. Bitte wiederholen.");
			}
			break;
		case "f":
			System.out.print("Artikelbezeichnung  > ");
			artikelName = liesEingabe();
			liste1 = shop.sucheArtikelBezeichnung(artikelName);
			gibArtikellisteAus(liste1);
			break;
		case "d":
			aktuellerBenutzer = null;
			break;
		case "+":
			System.out.print("Artikelbezeichnung  > ");
			artikelName = liesEingabe();
			liste1 = shop.sucheArtikelBezeichnung(artikelName);
			System.out.print("Wie viele m�chten Sie kaufen? > ");
			anzahl = liesEingabe();
			menge = Integer.parseInt(anzahl);
			try {
				try {
					warenkorb = shop.warenHinzufuegen(liste1.get(0), aktuellerKunde, menge);
				} catch (ArtikelBestandReichtNichtAusException e) {
					// TODO Automatisch generierter Erfassungsblock
					e.printStackTrace();
				}
			} catch (MassengutartikelException e) {
				// TODO Automatisch generierter Erfassungsblock
				e.printStackTrace();
			}
			shop.schreibeArtikel();
			break;
		case "-":
			System.out.print("Artikelbezeichnung  > ");
			artikelName = liesEingabe();
			System.out.print("Wie viele m�chten Sie entfernen? > ");
			anzahl = liesEingabe();
			menge = Integer.parseInt(anzahl);
			liste1 = shop.sucheArtikelBezeichnung(artikelName);
			shop.artikelAusWarenkorbentfernen(liste1.get(0), aktuellerKunde, menge);
			shop.schreibeArtikel();
			break;
		case "l":
			shop.warenkorbLeeren(aktuellerKunde);
			System.out.println("Warenkorb wurde gel�scht");
			break;
		default:
			System.out.println("Eingabe ung�ltig!");
		}
	}

	private void verarbeiteMitarbeiterEingabe(String input) throws IOException {

		String nummer;
		String loginName;
		String pw;
		String vorname;
		String nachname;

		String artikelName;
		int nr;
		String artikel;
		double preis = 0;
		int bestand = 0;
		int einheit = 0;

		List<Artikel> liste1;
		List<Kunde> liste2;
		List<Ereignis> liste3;

		switch (input) {
		case "e":
			System.out.print("Artikelnummer> ");
			nummer = liesEingabe();
			nr = Integer.parseInt(nummer);
			System.out.print("Produkt  > ");
			artikel = liesEingabe();
			System.out.print("Preis  > ");
			nummer = liesEingabe();
			preis = Double.parseDouble(nummer);
			System.out.print("Bestand  > ");
			nummer = liesEingabe();
			bestand = Integer.parseInt(nummer);
			System.out.print("Einheitengr��e  > ");
			nummer = liesEingabe();
			einheit = Integer.parseInt(nummer);
			try {
				shop.fuegeArtikelEin(artikel, nr, preis, bestand, einheit);
				System.out.println("Einfügen ok");
			} catch (ArtikelNrSchonVorhandenException e) {
				// Hier Fehlerbehandlung...
				System.out.println("Fehler beim Einfügen");
				e.printStackTrace();
			}
			shop.schreibeArtikel();
			break;
		case "a":
			liste1 = shop.gibAlleArtikel();
			gibArtikellisteAus(liste1);
			break;
		case "b":
			liste2 = shop.gibAlleKunden();
			gibKundenListeAus(liste2);
			break;
		case "t":
			liste3 = shop.gibAlleEreignisse();
			gibEreignisListe(liste3);
			break;
		case "m":
			System.out.print("Login Name > ");
			loginName = liesEingabe();

			System.out.print("Vorname  > ");
			vorname = liesEingabe();

			System.out.print("Nachname > ");
			nachname = liesEingabe();

			System.out.print("Passwort > ");
			pw = liesEingabe();
			try {
				shop.fuegeMitarbeiterEin(loginName, vorname, nachname, pw);
				System.out.println("Einfuegen ok");
			} catch (MitarbeiterExistiertBereitsException e) {
				System.err.println(e.getMessage());
			}
			break;
		case "l":
			System.out.print("ArtNr > ");
			nummer = liesEingabe();
			nr = Integer.parseInt(nummer);
			System.out.print("Produkt  > ");
			artikel = liesEingabe();
			shop.loescheArtikel(artikel, nr, preis, bestand, einheit);
			shop.schreibeArtikel();
			break;
		case "f":
			System.out.print("Artikelbezeichnung  > ");
			artikelName = liesEingabe();
			liste1 = shop.sucheArtikelBezeichnung(artikelName);
			gibArtikellisteAus(liste1);
			break;
		case "z":
			System.out.println("Artikelnummer?  > ");
			nummer = liesEingabe();
			int zuAendernderArtikel = Integer.parseInt(nummer);
			System.out.println("Neuer Bestand?  > ");
			String zuErhoehenderBestandStr = liesEingabe();
			int zuErhoenderBestand = Integer.parseInt(zuErhoehenderBestandStr);
			shop.aendereBestandVonArtikel(zuAendernderArtikel, zuErhoenderBestand);
			shop.schreibeArtikel();
			break;
		case "d":
			ausloggen();
			break;
		case "s":
			shop.schreibeKunden();
			break;
		default:
			System.out.println("Eingabe ung�ltig!");
		}
	}

	private String liesEingabe() throws IOException {
		return in.readLine();
	}

	private void gibArtikellisteAus(List<Artikel> liste) {
		if (liste.isEmpty()) {
			System.out.println("Liste ist leer.");
		} else {
			for (Artikel artikel : liste) {
				System.out.println(artikel);
			}
		}
	}

	private void gibKundenListeAus(List<Kunde> liste) {
		if (liste.isEmpty()) {
			System.out.println("Liste ist leer.");
		} else {
			for (Kunde kunde : liste) {
				System.out.println(kunde);
			}
		}
	}

	private void gibWarenkorbAus(Warenkorb warenkorb) {
		System.out.println(warenkorb);
	}

	private void gibEreignisListe(List<Ereignis> liste) {
		if (liste.isEmpty()) {
			System.out.println("Liste ist leer.");
		} else {
			for (Ereignis ereignis : liste) {
				System.out.println(ereignis);
			}
		}
	}

	public Benutzer getAktuellerBenutzer() {
		return aktuellerBenutzer;
	}

	public void setAktuellerBenutzer(Benutzer aktuellerBenutzer) {
		this.aktuellerBenutzer = aktuellerBenutzer;
	}

	private void run()
			throws ArtikelSchonVorhandenException, ArtikelNrSchonVorhandenException, KundeBereitsVorhandenException {
		String input = "";

		// Hauptschleife der Benutzungsschnittstelle
		do {
			if (aktuellerBenutzer instanceof Kunde) {
				gibKundenMenueAus();

				try {
					input = liesEingabe();
					verarbeiteEingabe(input);
				} catch (IOException | LoginUngueltigException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (aktuellerBenutzer instanceof Mitarbeiter) {

				gibArbeiterMenueAus();
				try {
					input = liesEingabe();
					verarbeiteEingabe(input);
				} catch (IOException | LoginUngueltigException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				gibUneingeloggtesMenueAus();
				try {
					input = liesEingabe();
					verarbeiteEingabe(input);
				} catch (IOException | LoginUngueltigException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} while (!input.equals("q"));
	}

	public void ausloggen() {
		aktuellerBenutzer = null;
		aktuellerKunde = null;
	}

	public static void main(String[] args) throws IOException, Exception {
		ShopCuiClient cui;
		try {
			cui = new ShopCuiClient("shop");
			cui.run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}