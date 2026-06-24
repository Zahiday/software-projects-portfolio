package shop.local.domain.exceptions;

public class ArtikelMitBezeichnungExistiertNichtException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public ArtikelMitBezeichnungExistiertNichtException(String falscheBezeichnung) {
		super("Artikel mit dieser Bezeichnung " + falscheBezeichnung + " existiert nicht!");
	}
}
