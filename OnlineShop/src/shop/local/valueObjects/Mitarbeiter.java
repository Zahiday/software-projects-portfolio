package shop.local.valueObjects;

public class Mitarbeiter extends Benutzer {

	public Mitarbeiter(String loginName, String vorname, String nachname, String pw) {
		super(loginName, vorname, nachname, pw);

	}

	@Override
	public String toString() {
		return "Login Name: 		" + loginName + "\n" + "Vorname: 			" + vorname + "\n"
				+ "Nachname: 			" + nachname + "\n";
	}
}
