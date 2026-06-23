package shop.local.domain.exceptions;

import shop.local.valueObjects.Kunde;

public class KundeBereitsVorhandenException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Kunde kunde;

	public KundeBereitsVorhandenException(String vorname, String nachname) {
		super("Kunde " + vorname + " " + nachname + " existiert bereits!");
	}

	public KundeBereitsVorhandenException(Kunde einKunde) {
		// TODO Automatisch generierter Konstruktorstub
	}

	public Kunde getKunde() {
		return kunde;
	}

}
