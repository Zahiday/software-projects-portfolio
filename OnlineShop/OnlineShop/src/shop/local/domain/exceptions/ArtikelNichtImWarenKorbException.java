package shop.local.domain.exceptions;

import shop.local.valueObjects.Artikel;

public class ArtikelNichtImWarenKorbException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 4912963133822367098L;
	Artikel artikel;

	public ArtikelNichtImWarenKorbException(Artikel artikel) {

		super(artikel.getBezeichnung() + "ist nicht in Ihrem Warenkorb.");
		this.artikel = artikel;
	}
}
