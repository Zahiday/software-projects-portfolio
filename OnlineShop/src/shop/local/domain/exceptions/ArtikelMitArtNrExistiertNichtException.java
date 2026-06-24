package shop.local.domain.exceptions;

public class ArtikelMitArtNrExistiertNichtException extends Exception {

	/**
	* 
	*/
	private static final long serialVersionUID = 7696893400550993098L;

	public ArtikelMitArtNrExistiertNichtException(int falscheArtNr) {
		super("Artikel mit Artikelnummer " + falscheArtNr + " existiert nicht");
	}
}
