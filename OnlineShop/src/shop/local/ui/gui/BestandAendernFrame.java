package shop.local.ui.gui;

import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import shop.local.domain.EShop;

public class BestandAendernFrame extends JFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = -6529870746797887782L;

	private EShop shop;

	private JTextField text1;
	private JTextField text2;
	private JFrame bestandAendern;
	
	public BestandAendernFrame(EShop shop) {
		this.shop = shop;
		initialize();

	}

	/**
	 * Ertstellt den Frame "Bestand ändern" mit den ganzen Komponenten
	 */
	private void initialize() {
		
		bestandAendern = new JFrame("Bestand ändern");
		
		
		// Textfeld für Anzahl der Artikel
		text1 = new JTextField();
		bestandAendern.add(text1);
		text1.setBounds(110, 20, 100, 30);

		// Textfeld für den Namen/Titel des Artikels
		text2 = new JTextField();
		bestandAendern.add(text2);
		text2.setBounds(110, 75, 100, 30);

		// Label "Anzahl"
		JLabel l1 = new JLabel("Artikel Nr.: ");
		bestandAendern.add(l1);
		l1.setBounds(20, 20, 80, 30);

		// Label "Titel"
		JLabel l2 = new JLabel("Anzahl: ");
		bestandAendern.add(l2);
		l2.setBounds(20, 75, 80, 30);

		// Bestand Ä Button
		JButton a = new JButton("Ändern");
		bestandAendern.add(a);
		a.setBounds(110, 120, 100, 30);
		a.addActionListener(e -> verarbeiteBestandKlick());

		bestandAendern.setSize(300, 300);
		bestandAendern.setLayout(null); // keinen LayoutManager benutzen
		bestandAendern.setVisible(true);// setzt den Frame auf sichtbar
		bestandAendern.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Damit wird der Frame Beendet
		bestandAendern.setLocationRelativeTo(null);

	}

	private void verarbeiteBestandKlick() {

		String nrAlsString = text1.getText();
		int zuAendernderArtikel = Integer.parseInt(nrAlsString);

		String bestandAlsString = text2.getText();
		int zuErhoenderBestand = Integer.parseInt(bestandAlsString);

		shop.aendereBestandVonArtikel(zuAendernderArtikel, zuErhoenderBestand);

		try {
			shop.schreibeArtikel();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

}
