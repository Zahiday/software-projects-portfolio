package shop.local.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import shop.local.valueObjects.Artikel;
import shop.local.valueObjects.Ereignis;
import shop.local.valueObjects.Warenkorb;

public class EreignisVerwaltung {

	private List<Ereignis> ereignisse = new ArrayList<>();

	/**
	 * Mit dieser Methode wird ein erstelltes Ereignis in den Ereignis Bestand
	 * hinzugefügt
	 *
	 * @param einEreignis - Ein Erstelltes Ereignis
	 */
	public void einfuegen(Ereignis einEreignis) {
		ereignisse.add(einEreignis);
	}

	/**
	 * @return die aktuellen Ereignisse die gespeichert sind.
	 */
	public List<Ereignis> getEreignisse() {
		return ereignisse;
	}

	public void erstelleEreignis(Warenkorb warenkorb) {
		for (Map.Entry<Artikel, Integer> entry : warenkorb.getInhalt().entrySet()) {
			Ereignis einEreignis = new Ereignis(entry.getKey(), entry.getValue(), warenkorb.getKunde());
			einfuegen(einEreignis);
		}
	}
}
