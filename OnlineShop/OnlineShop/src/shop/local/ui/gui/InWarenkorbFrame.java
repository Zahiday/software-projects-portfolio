package shop.local.ui.gui;

import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import shop.local.domain.EShop;
import shop.local.domain.exceptions.ArtikelBestandReichtNichtAusException;
import shop.local.domain.exceptions.MassengutartikelException;
import shop.local.valueObjects.Artikel;
import shop.local.valueObjects.Kunde;

public class InWarenkorbFrame extends JFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = -6505622778816608652L;
	private JFrame inWarenkorb;
	private EShop shop;
	private JTextField text1;
	private JTextField text2;
	private Kunde kunde;

	public InWarenkorbFrame(EShop shop, Kunde kunde) {
		this.shop = shop;
		this.kunde = kunde;
		initialize();
	}

	/**
	 * Ertstellt den Frame "Artikel in den Warenkorb" mit den ganzen Komponenten
	 */
	private void initialize() {

		inWarenkorb = new JFrame("Artikel in den Warenkorb");
		
		
		//----------------------------------------Textfelder----------------------------------------

		// Textfeld von Anzahl
		text1 = new JTextField();
		inWarenkorb.add(text1);
		text1.setBounds(130, 20, 100, 30);

		// Textfeld von Titel
		text2 = new JTextField();
		inWarenkorb.add(text2);
		text2.setBounds(130, 75, 100, 30);

		//----------------------------------------Labels----------------------------------------

		JLabel l1 = new JLabel("Anzahl: ");
		inWarenkorb.add(l1);
		l1.setBounds(50, 20, 80, 30);

		JLabel l2 = new JLabel("Titel: ");
		inWarenkorb.add(l2);
		l2.setBounds(50, 75, 80, 30);

		// Button, was Artikel in den Warenkorb tut
		JButton b = new JButton("In Warenkorb");
		inWarenkorb.add(b);
		b.setBounds(100, 140, 150, 30);
		b.addActionListener(e6 -> verarbeiteInWarenkorbKlick());

		inWarenkorb.setSize(320, 300);
		inWarenkorb.setLayout(null); // keinen LayoutManager benutzen
		inWarenkorb.setVisible(true);// setzt den Frame auf sichtbar
		inWarenkorb.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Damit wird der Frame Beendet
		inWarenkorb.setLocationRelativeTo(null);

	}

	/**
	 * verarbeitet den Klick auf den InWarenkorb Button
	 */
	private void verarbeiteInWarenkorbKlick() {

		String titel = text2.getText();
		String anzahlString = text1.getText();
		int menge = Integer.parseInt(anzahlString);

		List<Artikel> liste1 = shop.sucheArtikelBezeichnung(titel);

		try {
			try {
				shop.warenHinzufuegen(liste1.get(0), kunde, menge);
			} catch (ArtikelBestandReichtNichtAusException e) {
				// TODO Automatisch generierter Erfassungsblock
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		} catch (MassengutartikelException e1) {

			JOptionPane.showMessageDialog(null, e1.getMessage());
		}
		try {
			shop.schreibeArtikel();
		} catch (IOException e) {
			// TODO Automatisch generierter Erfassungsblock
			e.printStackTrace();
		}
	}
}
