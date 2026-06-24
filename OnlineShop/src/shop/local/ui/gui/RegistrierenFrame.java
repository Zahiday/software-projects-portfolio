package shop.local.ui.gui;

import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import shop.local.domain.EShop;
import shop.local.domain.exceptions.KundeBereitsVorhandenException;
import shop.local.valueObjects.Adresse;

public class RegistrierenFrame extends JFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = 2280676096983238986L;

	private EShop shop;

	private JTextField text1;
	private JTextField text2;
	private JTextField text3;
	private JTextField text4;
	private JTextField text5;
	private JTextField text6;
	private JTextField text7;
	private JTextField text8;
	private JFrame registrieren;

	public RegistrierenFrame(EShop shop) {
		this.shop = shop;
		initialize();

	}

	/**
	 * Ertstellt den Frame "Registrieren" mit den ganzen Komponenten
	 */
	private void initialize() {

		registrieren = new JFrame("Registrieren");
		
		
		
		//---------------------------------Textfelder-----------------------------

		// Textfeld von Vorname
		text1 = new JTextField();
		registrieren.add(text1);
		text1.setBounds(110, 20, 100, 30);

		// Textfeld von Nachname
		text2 = new JTextField();
		registrieren.add(text2);
		text2.setBounds(110, 75, 100, 30);

		// Textfeld von Benutzername
		text3 = new JTextField();
		registrieren.add(text3);
		text3.setBounds(110, 125, 100, 30);

		// Textfeld von Passwort
		text4 = new JTextField();
		registrieren.add(text4);
		text4.setBounds(110, 175, 100, 30);

		// Textfeld von Strasse
		text5 = new JTextField();
		registrieren.add(text5);
		text5.setBounds(110, 225, 100, 30);

		// Textfeld von Hausnummer
		text6 = new JTextField();
		registrieren.add(text6);
		text6.setBounds(110, 275, 100, 30);

		// Textfeld von Poistleitzahl
		text7 = new JTextField();
		registrieren.add(text7);
		text7.setBounds(110, 325, 100, 30);

		// Textfeld von Ort
		text8 = new JTextField();
		registrieren.add(text8);
		text8.setBounds(110, 375, 100, 30);

		//--------------------------------- Labels -----------------------------

		// Label "Loginname"
		JLabel l1 = new JLabel("Vorname:");
		registrieren.add(l1);
		l1.setBounds(20, 20, 80, 30);

		// Label "Passwort"
		JLabel l2 = new JLabel("Nachname:");
		registrieren.add(l2);
		l2.setBounds(20, 70, 80, 30);

		// Label "Passwort"
		JLabel l3 = new JLabel("Benutzername:");
		registrieren.add(l3);
		l3.setBounds(20, 120, 80, 30);

		// Label "Passwort"
		JLabel l4 = new JLabel("Passwort:");
		registrieren.add(l4);
		l4.setBounds(20, 170, 80, 30);

		// Label "Passwort"
		JLabel l5 = new JLabel("Strasse:");
		registrieren.add(l5);
		l5.setBounds(20, 220, 80, 30);

		// Label "Passwort"
		JLabel l6 = new JLabel("Hausnummer:");
		registrieren.add(l6);
		l6.setBounds(20, 270, 80, 30);

		// Label "Passwort"
		JLabel l7 = new JLabel("Poistleitzahl:");
		registrieren.add(l7);
		l7.setBounds(20, 320, 80, 30);

		// Label "Passwort"
		JLabel l8 = new JLabel("Ort:");
		registrieren.add(l8);
		l8.setBounds(20, 370, 80, 30);

		// Registrieren Button
		JButton b = new JButton("Registrieren");
		registrieren.add(b);
		b.setBounds(100, 440, 120, 30);
		b.addActionListener(e -> {
			try {
				verarbeiteRegistrierenKlick();
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, e);
			}
		});

		registrieren.setSize(300, 600);
		registrieren.setLayout(null); // keinen LayoutManager benutzen
		registrieren.setVisible(true);// setzt den Frame auf sichtbar
		registrieren.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Damit wird der Frame Beendet
		registrieren.setLocationRelativeTo(null);
	}

	/**
	 * verarbeitet den Klick auf den Registrieren Button
	 * @throws IOException
	 */
	private void verarbeiteRegistrierenKlick() throws IOException {

		String vorname = text1.getText();
		String nachname = text2.getText();
		String login = text3.getText();
		String passwort = text4.getText();
		String strasse = text5.getText();
		String hausNr = text6.getText();
		String plz = text7.getText();
		String ort = text8.getText();

		Adresse adresse = new Adresse(strasse, hausNr, plz, ort);
		try {
			shop.registrieren(vorname, nachname, login, passwort, adresse);
			System.out.println("wurde ausgeführt!");
		} catch (KundeBereitsVorhandenException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		registrieren.dispose();
		new EinloggenFrame(shop);
	}

}
