package shop.local.domain.exceptions;

import shop.local.valueObjects.Massengutartikel;

public class MassengutartikelException extends Exception {

	private static final long serialVersionUID = 1L;

	public MassengutartikelException(Massengutartikel masssengutartikel) {
		super("Der Artikel: " + masssengutartikel.getBezeichnung() + " kann nur in der Einheitengröße von "
				+ masssengutartikel.getEinheit() + " gekauft werden.");
	}

}
