package shop.local.domain.exceptions;

import shop.local.valueObjects.Mitarbeiter;

public class MitarbeiterExistiertBereitsException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public MitarbeiterExistiertBereitsException(Mitarbeiter mitarbeiter) {
		super("Mitarbeiter: " + mitarbeiter.getLoginName() + " existiert bereits!");
	}
}
