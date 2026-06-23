package shop.local.ui.gui.Panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import shop.local.domain.EShop;
import shop.local.domain.exceptions.ArtikelMitBezeichnungExistiertNichtException;
import shop.local.valueObjects.Artikel;

public class SuchArtikelPanel extends JPanel {


	private static final long serialVersionUID = -1863723316288708994L;


	public interface SearchResultListener {
		public void onSearchResult(List<Artikel> artikelList);
	}

	private EShop shop = null;
	private SearchResultListener searchListener = null;

	private JTextField searchTextField;
	private JButton searchButton = null;

	public SuchArtikelPanel(EShop eshop, SearchResultListener listener) {
		shop = eshop;
		searchListener = listener;

		setupUI();

		setupEvents();
	}

	private void setupUI() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		setLayout(gridBagLayout);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = 0; // Zeile 0

		JLabel suchLabel = new JLabel("Suchbegriff:");
		c.gridx = 0; // Spalte 0
		c.weightx = 0.2; // 20% der gesamten Breite
		gridBagLayout.setConstraints(suchLabel, c);
		add(suchLabel);

		searchTextField = new JTextField();
		searchTextField.setToolTipText("Suchbegriff eingeben.");
		c.gridx = 1; // Spalte 1
		c.weightx = 0.6; // 60% der gesamten Breite
		gridBagLayout.setConstraints(searchTextField, c);
		add(searchTextField);

		searchButton = new JButton("Such!");

		c.gridx = 2; // Spalte 2
		c.weightx = 0.2; // 20% der gesamten Breite
		gridBagLayout.setConstraints(searchButton, c);
		add(searchButton);

		// Rahmen definieren und Name setzen
		setBorder(BorderFactory.createTitledBorder("Suche"));
	}

	private void setupEvents() {
		searchButton.addActionListener(new SuchListener());
	}

	// Lokale Klasse für Reaktion auf Such-Klick
	class SuchListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource().equals(searchButton)) {
				String suchbegriff = searchTextField.getText();
				List<Artikel> suchErgebnis = null;
				if (suchbegriff.isEmpty()) {
					suchErgebnis = shop.gibAlleArtikel();
				} else {
					try {
						suchErgebnis = shop.sucheNachTitel(suchbegriff);
					} catch (ArtikelMitBezeichnungExistiertNichtException e) {
						JOptionPane.showMessageDialog(null, e);
					}
				}

				// Listener benachrichtigen, damit er die Ausgabe aktualisieren kann
				searchListener.onSearchResult(suchErgebnis);

			}
		}
	}
}
