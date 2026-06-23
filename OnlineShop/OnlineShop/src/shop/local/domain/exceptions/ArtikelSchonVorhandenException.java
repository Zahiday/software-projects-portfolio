package shop.local.domain.exceptions;

import shop.local.valueObjects.Artikel;

public class ArtikelSchonVorhandenException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = -4943473552277166201L;


	public ArtikelSchonVorhandenException(Artikel artikel) {
		super("Artikel " + artikel.getBezeichnung() + " und Nummer " + artikel.getArtNr() + " ist schon Vorhanden");
	}

}
