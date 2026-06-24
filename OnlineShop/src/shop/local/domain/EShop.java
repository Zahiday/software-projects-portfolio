package shop.local.domain;

import java.io.IOException;
import java.util.List;

import shop.local.domain.exceptions.ArtikelBestandReichtNichtAusException;
import shop.local.domain.exceptions.ArtikelMitBezeichnungExistiertNichtException;
import shop.local.domain.exceptions.ArtikelNrSchonVorhandenException;
import shop.local.domain.exceptions.KundeBereitsVorhandenException;
import shop.local.domain.exceptions.LoginUngueltigException;
import shop.local.domain.exceptions.MassengutartikelException;
import shop.local.domain.exceptions.MitarbeiterExistiertBereitsException;
import shop.local.valueObjects.Adresse;
import shop.local.valueObjects.Artikel;
import shop.local.valueObjects.Benutzer;
import shop.local.valueObjects.Ereignis;
import shop.local.valueObjects.Kunde;
import shop.local.valueObjects.Massengutartikel;
import shop.local.valueObjects.Mitarbeiter;
import shop.local.valueObjects.Warenkorb;

public class EShop {
	private String datei = "";
	private ArtikelVerwaltung meineArtikel;
	private KundenVerwaltung meineKunden;
	private WarenkorbService meineWarenkorbVerwaltung;
	private MitarbeiterVerwaltung meineMitarbeiter;
	private EreignisVerwaltung meineEreignisVerwaltung;

	public EShop(String datei) throws IOException, KundeBereitsVorhandenException {
		this.datei = datei;

		// Artikelbestand aus Datei einlesen
		meineArtikel = new ArtikelVerwaltung();
		meineArtikel.liesDaten(datei + "_P.txt");

		// Kundenkartei aus Datei einlesen
		meineKunden = new KundenVerwaltung();
		try {
			meineKunden.liesDaten(datei + "_K.txt");
		} catch (KundeBereitsVorhandenException e) {

			e.printStackTrace();
		}

		meineMitarbeiter = new MitarbeiterVerwaltung();

		try {
			meineMitarbeiter.liesDaten(datei + "_M.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}

		meineEreignisVerwaltung = new EreignisVerwaltung();
		meineWarenkorbVerwaltung = new WarenkorbService(meineArtikel, meineKunden, meineEreignisVerwaltung);

	}

	// ----------------------------------------------ArtikelVerwaltung-------------------------------------------------------

	public List<Artikel> gibAlleArtikel() {
		return meineArtikel.getArtikelBestand();
	}

	public List<Artikel> gibArtikelListeAusKunde(int eingabe) throws NumberFormatException {
		return meineArtikel.gibArtikelAus(eingabe);
	}

	public List<Artikel> sucheNachArtikelnummer(int artNr) {
		return meineArtikel.getArtikelBestand();
	}

	public List<Artikel> sucheNachTitel(String titel) throws ArtikelMitBezeichnungExistiertNichtException {
		return meineArtikel.sucheArtikel(titel);
	}

	public Artikel fuegeArtikelEin(String titel, int artNr, double preis, int bestand, int einheit)
			throws ArtikelNrSchonVorhandenException {
		return meineArtikel.einfuegen(titel, artNr, preis, bestand, einheit);

	}

	public void loescheArtikel(String bezeichnung, int artNr, double preis, int bestand, int einheit) {
		Artikel a = new Artikel(bezeichnung, artNr, preis, bestand);
		meineArtikel.loeschen(a);
	}

	public List<Artikel> sucheArtikelBezeichnung(String artikelBezeichnung) {
		return meineArtikel.sucheArtikel(artikelBezeichnung);
	}

	public void schreibeArtikel() throws IOException {
		meineArtikel.schreibeDaten(datei + "_P.txt");

	}

	public void aendereBestandVonArtikel(int artikelNr, int bestand) {
		meineArtikel.aendereBestandVonArtikel(artikelNr, bestand);
	}

	// ----------------------------------------------EreignisVerwaltung-------------------------------------------------------

	public List<Ereignis> gibAlleEreignisse() {
		return meineEreignisVerwaltung.getEreignisse();
	}
	// ----------------------------------------------KundenVerwaltung-------------------------------------------------------

	public Kunde kEinloggen(String login, String passwort) throws LoginUngueltigException {
		return meineKunden.einloggen(login, passwort);
	}

	public void registrieren(String vorname, String nachname, String loginName, String passwort, Adresse adresse)
			throws KundeBereitsVorhandenException, IOException {
		meineKunden.registrieren(vorname, nachname, loginName, passwort, adresse);
		schreibeKunden();
	}

	public <Kunden> List<Kunde> gibAlleKunden() {
		return meineKunden.getKundenBestand();
	}

	public void schreibeKunden() throws IOException {
		meineKunden.schreibeDaten(datei + "_K.txt");
	}
	// ----------------------------------------------MitarbeiterVerwaltung-------------------------------------------------------

	public Mitarbeiter mEinloggen(String login, String passwort) throws LoginUngueltigException {
		return meineMitarbeiter.einloggen(login, passwort);
	}

	public void fuegeMitarbeiterEin(String loginName, String vorname, String nachname, String pw)
			throws IOException, MitarbeiterExistiertBereitsException {
		meineMitarbeiter.einfuegen(new Mitarbeiter(loginName, vorname, nachname, pw));
		meineMitarbeiter.schreibeDaten(datei + "_M.txt");
	}

	// ----------------------------------------------WarenkorbVerwaltung-------------------------------------------------------

	public Warenkorb warenHinzufuegen(Artikel artikel, Kunde kunde, int menge)
			throws MassengutartikelException, ArtikelBestandReichtNichtAusException {
		return meineWarenkorbVerwaltung.hinzufuegen(artikel, kunde, menge);
	}

	public Warenkorb warenHinzufuegen(Massengutartikel artikel, Kunde kunde, int menge)
			throws MassengutartikelException, ArtikelBestandReichtNichtAusException {
		return meineWarenkorbVerwaltung.hinzufuegen(artikel, kunde, menge);
	}

	public void artikelAusWarenkorbentfernen(Artikel artikel, Kunde kunde, int menge) {
		meineWarenkorbVerwaltung.entfernen(artikel, kunde, menge);
	}

	public Warenkorb gibWarenImKorb(Benutzer aktuellerBenutzer) {
		return meineWarenkorbVerwaltung.getWarenkorb((Kunde) aktuellerBenutzer);
	}

	public void warenkorbLeeren(Kunde kunde) {
		meineWarenkorbVerwaltung.loescheWarenkorb(kunde);
	}

	public void kaufAbschliessen(Kunde kunde) {
		warenkorbLeeren(kunde);
	}
}
