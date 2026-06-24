package shop.local.ui.gui;

import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import shop.local.domain.EShop;
import shop.local.domain.exceptions.MitarbeiterExistiertBereitsException;

public class MitarbeiterEinfuegenFrame extends JFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = -7323322692697792691L;

	private EShop shop;

	private JTextField benutzerName;
	private JTextField vorName;
	private JTextField nachName;
	private JTextField passwort;

	public MitarbeiterEinfuegenFrame(EShop shop) {
		this.shop = shop;
		initialize();

	}

	/**
	 * Ertstellt den Frame "Mitarbeiter Einfügen" mit den ganzen Komponenten
	 */
	private void initialize() {

		JFrame einfuegenMitarbeiter = new JFrame("Mitarbeiter Einfügen");
		
		
		//----------------------------------Textfelder----------------------------------

		// Textfeld von Vorname
		vorName = new JTextField();
		einfuegenMitarbeiter.add(vorName);
		vorName.setBounds(110, 20, 100, 30);

		// Textfeld von Nachname
		nachName = new JTextField();
		einfuegenMitarbeiter.add(nachName);
		nachName.setBounds(110, 75, 100, 30);

		// Textfeld von Benutzername
		benutzerName = new JTextField();
		einfuegenMitarbeiter.add(benutzerName);
		benutzerName.setBounds(110, 125, 100, 30);

		// Textfeld von Passwort
		passwort = new JTextField();
		einfuegenMitarbeiter.add(passwort);
		passwort.setBounds(110, 175, 100, 30);

		//----------------------------------Labels----------------------------------

		// Label "Loginname"
		JLabel l1 = new JLabel("Vorname:");
		einfuegenMitarbeiter.add(l1);
		l1.setBounds(20, 20, 80, 30);

		// Label "Passwort"
		JLabel l2 = new JLabel("Nachname:");
		einfuegenMitarbeiter.add(l2);
		l2.setBounds(20, 70, 80, 30);

		// Label "Passwort"
		JLabel l3 = new JLabel("Benutzername:");
		einfuegenMitarbeiter.add(l3);
		l3.setBounds(20, 120, 80, 30);

		// Label "Passwort"
		JLabel l4 = new JLabel("Passwort:");
		einfuegenMitarbeiter.add(l4);
		l4.setBounds(20, 170, 80, 30);

		// Einfügen Button
		JButton b = new JButton("Einfügen");
		einfuegenMitarbeiter.add(b);
		b.setBounds(100, 245, 120, 30);

		b.addActionListener(e -> {

			verarbeiteMEinfuegenKlick();

		});

		einfuegenMitarbeiter.setSize(300, 350);
		einfuegenMitarbeiter.setLayout(null); // keinen LayoutManager benutzen
		einfuegenMitarbeiter.setVisible(true);// setzt den Frame auf sichtbar
		einfuegenMitarbeiter.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Damit wird der Frame Beendet
		einfuegenMitarbeiter.setLocationRelativeTo(null);
	}

	/**
	 * verarbeitet den Klick auf den Einfüge Button
	 */
	private void verarbeiteMEinfuegenKlick() {

		String loginName = benutzerName.getText();
		String vorname = vorName.getText();
		String nachname = nachName.getText();
		String pw = passwort.getText();

		try {
			shop.fuegeMitarbeiterEin(loginName, vorname, nachname, pw);
		} catch (MitarbeiterExistiertBereitsException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (IOException e) {
			// TODO Automatisch generierter Erfassungsblock
			e.printStackTrace();
		}

	}

}
