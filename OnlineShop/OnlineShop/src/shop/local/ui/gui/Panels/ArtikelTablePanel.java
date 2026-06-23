package shop.local.ui.gui.Panels;

import java.util.Collections;

import javax.swing.JTable;

import shop.local.valueObjects.Artikel;


public class ArtikelTablePanel extends JTable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public ArtikelTablePanel(java.util.List<Artikel> artikel) {
		super();

		updateBooks(artikel);
	}

	public void updateBooks(java.util.List<Artikel> artikel) {


		Collections.sort(artikel, (b1, b2) -> b1.getArtNr() - b2.getArtNr());

	}

}
