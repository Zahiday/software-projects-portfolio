package shop.local.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import shop.local.domain.exceptions.LoginUngueltigException;
import shop.local.domain.exceptions.MitarbeiterExistiertBereitsException;
import shop.local.persistence.FilePersistenceManager;
import shop.local.persistence.PersistenceManager;
import shop.local.valueObjects.Mitarbeiter;

public class MitarbeiterVerwaltung {

	private PersistenceManager pm = new FilePersistenceManager();
	private List<Mitarbeiter> mitarbeiterBestand = new ArrayList<>();
	
	/**
	 * In dieser Methode wird ein Mitarbeite den man hinzuf�gen will erstmal
	 * �berpr�ft, ob er schon im Bestand vorhanden ist wenn nicht wird er angelegt
	 * wenn er schon vorhanden ist wird eine Exception geworfen
	 * 
	 * @param einMitarbeiter
	 * @throws MitarbeiterExistiertBereitsException
	 */
	public void einfuegen(Mitarbeiter einMitarbeiter) throws MitarbeiterExistiertBereitsException {
		for (Mitarbeiter b : mitarbeiterBestand) {
			if (b.getLoginName().equals(einMitarbeiter.getLoginName())) {
				throw new MitarbeiterExistiertBereitsException(einMitarbeiter);
			}
		}
		mitarbeiterBestand.add(einMitarbeiter);
	}

	/**
	 * Hier wird der Mitarbeiter zun�chst gesucht, wenn dies Erfolgreich war, 
	 * wird gepr�ft, ob der Login �bereinstimmt 
	 * Wenn dies nicht der Fall ist, wird eine Exception geworfen
	 * @param login
	 * @param passwort
	 * @return
	 * @throws LoginUngueltigException
	 */
	public Mitarbeiter einloggen(String login, String passwort) throws LoginUngueltigException {

		Mitarbeiter mitarbeiter = sucheNachLogin(login, passwort);
		return mitarbeiter;
	}

	/**
	 * Interne Methode die pr�ft, ob Mitarbeiter im Mitarbeiterbestand vorhanden ist 
	 * @param login
	 * @param passwort
	 * @return
	 * @throws LoginUngueltigException
	 */
	private Mitarbeiter sucheNachLogin(String login, String passwort) throws LoginUngueltigException {

		for (Mitarbeiter mitarbeiter : mitarbeiterBestand) {
			if (mitarbeiter.getLoginName().equals(login) && mitarbeiter.getPw().equals(passwort)) {
				return mitarbeiter;
			}
		}
		throw new LoginUngueltigException(login);
	}

	/**
	 * Mitarbeiter werden aus einer externen Datei gelesen
	 * @param datei
	 * @throws Exception
	 */
	public void liesDaten(String datei) throws Exception {
		pm.openForReading(datei);

		Mitarbeiter einMitarbeiter;
		do {

			einMitarbeiter = pm.ladeMitarbeiter();
			if (einMitarbeiter != null) {
				einfuegen(einMitarbeiter);
			}
		} while (einMitarbeiter != null);
		pm.close();
	}

	/**
	 * Mit dieser Methode wird der Mitarbeiter gespeichert in der Datei
	 * 
	 * @param datei
	 * @throws IOException
	 */
	public void schreibeDaten(String datei) throws IOException {
		pm.openForWriting(datei);

		Iterator<Mitarbeiter> iter = mitarbeiterBestand.iterator();
		while (iter.hasNext()) {
			Mitarbeiter m = iter.next();
			pm.speichereMitarbeiter(m);
		}
		pm.close();
	}
}
