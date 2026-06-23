package shop.local.domain.exceptions;

public class LoginUngueltigException extends Exception {

	/**
	* 
	*/
	private static final long serialVersionUID = -8356136987833096711L;

	public LoginUngueltigException(String login) {
		super("Login oder Passwort ist falsch!");

	}
}