package shop.local.valueObjects;

public class Benutzer {
	
	/**
	 * Dies ist die Super Klasse der Benutzer
	 */
	
	protected String loginName;
	protected String vorname;
	protected String nachname;
	protected String passwort;

	public Benutzer(String loginName, String vorname, String nachname, String passwort) {
		this.loginName = loginName;
		this.vorname = vorname;
		this.nachname = nachname;
		this.passwort = passwort;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPw() {
		return passwort;
	}

	public void setPw(String passwort) {
		this.passwort = passwort;
	}
}
