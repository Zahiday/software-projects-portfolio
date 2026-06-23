package shop.local.ui.gui;

import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import shop.local.domain.EShop;
import shop.local.valueObjects.Artikel;
import shop.local.valueObjects.Kunde;

public class AusWarenkorbFrame extends JFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = -6054064288209179035L;
	private JFrame ausWarenkorb;
	private EShop shop;
	private JTextField text1;
	private JTextField text2;
	private Kunde kunde;
	private JTable artikelTabelle;

	public AusWarenkorbFrame(EShop shop, Kunde kunde) {
		this.shop = shop;
		this.kunde = kunde;
		initialize();
	}

	/**
	 * Ertstellt den Frame "Artikel aus den Warenkorb" mit den ganzen Komponenten
	 */
	private void initialize() {

		ausWarenkorb = new JFrame("Artikel aus den Warenkorb");

		// Textfeld von Anzahl
		text1 = new JTextField();
		ausWarenkorb.add(text1);
		text1.setBounds(130, 20, 100, 30);

		// Textfeld von Titel
		text2 = new JTextField();
		ausWarenkorb.add(text2);
		text2.setBounds(130, 75, 100, 30);

		// -----------------------------------------------------------------------------------------------

		JLabel l1 = new JLabel("Anzahl: ");
		ausWarenkorb.add(l1);
		l1.setBounds(50, 20, 80, 30);

		JLabel l2 = new JLabel("Titel: ");
		ausWarenkorb.add(l2);
		l2.setBounds(50, 75, 80, 30);

		// Button Artikel in den Warenkorb
		JButton b = new JButton("aus Warenkorb");
		ausWarenkorb.add(b);
		b.setBounds(100, 140, 80, 30);
		b.addActionListener(e6 -> verarbeiteAusWarenkorbKlick());

		new JScrollPane(artikelTabelle);

		ausWarenkorb.setSize(320, 300);
		ausWarenkorb.setLayout(null); // keinen LayoutManager benutzen
		ausWarenkorb.setVisible(true);// setzt den Frame auf sichtbar
		ausWarenkorb.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Damit wird der Frame Beendet
		ausWarenkorb.setLocationRelativeTo(null);

	}

	/**
	 * verarbeitet den Klick auf AusWarenkorb Button
	 */
	private void verarbeiteAusWarenkorbKlick() {

		String titel = text2.getText();
		String anzahlString = text1.getText();
		int menge = Integer.parseInt(anzahlString);

		List<Artikel> liste1 = shop.sucheArtikelBezeichnung(titel);

		shop.artikelAusWarenkorbentfernen(liste1.get(0), kunde, menge);

		try {
			shop.schreibeArtikel();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e);
		}

	}

}
