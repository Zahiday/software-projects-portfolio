package shop.local.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import shop.local.domain.exceptions.KundeBereitsVorhandenException;
import shop.local.domain.exceptions.LoginUngueltigException;
import shop.local.persistence.FilePersistenceManager;
import shop.local.persistence.PersistenceManager;
import shop.local.valueObjects.Adresse;
import shop.local.valueObjects.Kunde;

public class KundenVerwaltung {

	private PersistenceManager pm = new FilePersistenceManager();
	private List<Kunde> kundenBestand = new ArrayList<>();

	/**
	 * Hier wird das Kundenobjekt erstellt 
	 * @param vorname
	 * @param nachname
	 * @param loginName
	 * @param passwort
	 * @param adresse
	 * @throws KundeBereitsVorhandenException
	 */
	public void registrieren(String vorname, String nachname, String loginName, String passwort, Adresse adresse)
			throws KundeBereitsVorhandenException {
		for (Kunde kunde : kundenBestand) {
			if (kunde.getLoginName().equals(loginName) && kunde.getVorname().equals(vorname)) {
				throw new KundeBereitsVorhandenException(vorname, nachname);
			}
		}
		kundenBestand.add(new Kunde(vorname, nachname, loginName, passwort, adresse));
	}

	/**
	 * Hier wird der Kunde zunächst gesucht, wenn dies Erfolgreich war, 
	 * wird geprüft, ob der Login übereinstimmt 
	 * Wenn dies nicht der Fall ist, wird eine Exception geworfen
	 * @param login
	 * @param passwort
	 * @return
	 * @throws LoginUngueltigException 
	 * 
	 */
	public Kunde einloggen(String login, String passwort) throws LoginUngueltigException {

		Kunde kunde = sucheNachLogin(login,passwort);

		return kunde;
	}

	/**
	 * Interne Methode die prüft, ob Kunde im Kundenbestand vorhanden ist 
	 * @param login
	 * @return
	 * @throws LoginUngueltigException 
	 */
	private Kunde sucheNachLogin(String login,String passwort) throws LoginUngueltigException {

		for (Kunde kunde : kundenBestand) {
			if (kunde.getLoginName().equals(login) && kunde.getPw().equals(passwort)) {
				return kunde;
			}
		}
		throw new LoginUngueltigException(login);
	}

	/**
	 * Kunden werden aus einer externen Datei gelesen
	 * @param datei
	 * @throws IOException
	 * @throws KundeBereitsVorhandenException
	 */
	public void liesDaten(String datei) throws IOException, KundeBereitsVorhandenException {
		pm.openForReading(datei);

		Kunde einKunde;
		do {
			einKunde = pm.ladeKunde();
			if (einKunde != null) {
				einfuegen(einKunde);
			}
		} while (einKunde != null);
		pm.close();
	}

	/**
	 * Kunden werden in eine externe Datei gespeichert
	 * @param datei
	 * @throws IOException
	 */
	public void schreibeDaten(String datei) throws IOException {
		pm.openForWriting(datei);

		Iterator<Kunde> iter = kundenBestand.iterator();
		while (iter.hasNext()) {
			Kunde k = iter.next();
			pm.speichereKunde(k);
		}
		pm.close();
	}

	/**
	 * Kundenobjekt wird erstellt
	 * @param einKunde
	 * @throws KundeBereitsVorhandenException
	 */
	private void einfuegen(Kunde einKunde) throws KundeBereitsVorhandenException {
		if (kundenBestand.contains(einKunde)) {
			throw new KundeBereitsVorhandenException(einKunde);
		}
		kundenBestand.add(einKunde);
	}

	/**
	 * Gibt eine Liste des Kundenbestandes zurück
	 * @return
	 */
	public List<Kunde> getKundenBestand() {
		return new ArrayList<>(kundenBestand);
	}
}
