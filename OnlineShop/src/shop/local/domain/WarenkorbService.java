package shop.local.domain;

import java.util.Map;

import shop.local.domain.exceptions.ArtikelBestandReichtNichtAusException;
import shop.local.domain.exceptions.MassengutartikelException;
import shop.local.valueObjects.Artikel;
import shop.local.valueObjects.Kunde;
import shop.local.valueObjects.Massengutartikel;
import shop.local.valueObjects.Warenkorb;

public class WarenkorbService {

	private ArtikelVerwaltung meineArtikel;


	public WarenkorbService(ArtikelVerwaltung meineArtikel, KundenVerwaltung meineKunden,
			EreignisVerwaltung meineEreinisVerwaltung) {
		this.meineArtikel = meineArtikel;
	}

	/**
	 * Artikel wird dem Warenkorb hinzugefügt
	 * Zuerst wird geprüft ob der Bestand ausreicht, dann ob die Packungsgröße
	 * stimmt
	 * @param artikel
	 * @param kunde
	 * @param menge
	 * @return
	 * @throws MassengutartikelException
	 * @throws ArtikelBestandReichtNichtAusException
	 */
	public Warenkorb hinzufuegen(Artikel artikel, Kunde kunde, int menge)
			throws MassengutartikelException, ArtikelBestandReichtNichtAusException {
		if (artikel.getBestand() < menge) { // Bestand wird geprüft
			throw new ArtikelBestandReichtNichtAusException(artikel);
		} else if (artikel instanceof Massengutartikel) {
			Massengutartikel mArtikel = (Massengutartikel) artikel;
			if (menge % mArtikel.getEinheit() != 0) {
				throw new MassengutartikelException((Massengutartikel) artikel);
			}
		}
		Warenkorb neuerWarenkorb = kunde.getWarenkorb();
		neuerWarenkorb.artikelHinzufuegen(artikel, menge); // Artikel wird hinzugefügt
		meineArtikel.aendereBestandVonArtikel(artikel.getArtNr(), -menge); // Artikel Bestand wird geändert
		return neuerWarenkorb;
	}

	/**
	 * Gesammter Warenkorb wird gelöscht
	 * @param kunde
	 */
	public void loescheWarenkorb(Kunde kunde) {
		Warenkorb warenkorb = kunde.getWarenkorb();
		for (Map.Entry<Artikel, Integer> entry : warenkorb.getInhalt().entrySet()) {
			int menge = entry.getValue();
			Artikel artikel = entry.getKey();
			entfernen(artikel, kunde, menge);
		}
		warenkorbLeeren(warenkorb);
	}

	/**
	 * Artikel wird aus Warenkorb entfernt
	 * @param artikel
	 * @param kunde
	 * @param menge
	 * @return
	 */
	public Warenkorb entfernen(Artikel artikel, Kunde kunde, int menge) {
		Warenkorb warenkorb = kunde.getWarenkorb();
		warenkorb.artikelLoeschen(artikel, menge);
		meineArtikel.aendereBestandVonArtikel(artikel.getArtNr(), +menge);

		return warenkorb;
	}

	/**
	 * Getter für Warenkorb
	 * @param kunde
	 * @return
	 */
	public Warenkorb getWarenkorb(Kunde kunde) {
		return kunde.getWarenkorb();
	}

	/**
	 * Warenkorb fuer kaufAbschließen funktion leeren
	 * @param warenkorb
	 */
	public void warenkorbLeeren(Warenkorb warenkorb) {
		warenkorb.warenkorbLeeren();
	}
}
