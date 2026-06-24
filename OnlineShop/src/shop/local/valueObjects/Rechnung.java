package shop.local.valueObjects;

import java.time.LocalDateTime;

public class Rechnung {

	private final Kunde kunde;
	private LocalDateTime datum;

	public Rechnung(Kunde kunde) {
		this.kunde = kunde;
		kunde.getWarenkorb();
		datum = LocalDateTime.now();

	}

	public Kunde getKunde() {
		return kunde;
	}

	@Override
	public String toString() {

		return ("Rechnung:  " + datum + "\n" + kunde + "\n " + kunde.getWarenkorb());
	}

}
