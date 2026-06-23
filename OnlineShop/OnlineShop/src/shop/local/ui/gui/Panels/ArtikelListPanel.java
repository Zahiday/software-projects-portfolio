package shop.local.ui.gui.Panels;

import java.util.Collections;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import shop.local.valueObjects.Artikel;

public class ArtikelListPanel extends JList<Artikel> {


	private static final long serialVersionUID = -1858082261425605453L;

	public ArtikelListPanel(java.util.List<Artikel> artikeln) {
		super();

		// ListModel erzeugen ...
		DefaultListModel<Artikel> listModel = new DefaultListModel<>();
		// ... bei JList "anmelden" und ...
		setModel(listModel);
		// ... Daten an Model übergeben
		updateArtikel(artikeln);
	}

	public void updateArtikel(java.util.List<Artikel> artikeln) {

		Collections.sort(artikeln, (b1, b2) -> b1.getArtNr() - b2.getArtNr());
		DefaultListModel<Artikel> listModel = (DefaultListModel<Artikel>) getModel();
		// ... Inhalt aktualisieren
		listModel.clear();
	}

}
