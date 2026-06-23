package shop.local.domain.exceptions;

public class ArtikelNrSchonVorhandenException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 3867059691804029245L;

	public ArtikelNrSchonVorhandenException(int artNr) {
		super(artNr + " bereits vorhanden");

	}

}
