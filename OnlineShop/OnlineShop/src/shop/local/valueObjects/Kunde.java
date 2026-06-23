package shop.local.valueObjects;

public class Kunde extends Benutzer {

	protected Adresse adresse;
	private Warenkorb warenkorb;
	private String login;

	public Kunde(String vorname, String nachname, String loginName, String pw, Adresse adresse) {
		super(loginName, vorname, nachname, pw);
		this.adresse = adresse;
		warenkorb = new Warenkorb();
		warenkorb.setKunde(this);
	}

	public Warenkorb getWarenkorb() {
		return warenkorb;
	}

	public void setWarenkorb(Warenkorb warenkorb) {
		this.warenkorb = warenkorb;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	public String getLogin() {
		return login;
	}

	@Override
	public String toString() {
		return "Login Name:\t\t" + loginName + "\n" + "Vorname:\t\t" + vorname + "\n" + "Nachname:\t\t" + nachname
				+ "\n" + adresse + "\n";
	}
}
