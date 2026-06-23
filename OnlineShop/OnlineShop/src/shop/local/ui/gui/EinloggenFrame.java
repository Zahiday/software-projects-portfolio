package shop.local.ui.gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import shop.local.domain.EShop;
import shop.local.domain.exceptions.LoginUngueltigException;
import shop.local.valueObjects.Benutzer;
import shop.local.valueObjects.Kunde;
import shop.local.valueObjects.Mitarbeiter;

public class EinloggenFrame extends JFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private EShop shop;

	private JTextField text1;
	private JTextField text2;
	private Benutzer aktuellerBenutzer;
	private JFrame einloggen;

	public EinloggenFrame(EShop shop) {
		this.shop = shop;
		initialize();

	}

	/**
	 * Ertstellt den Frame "Einloggen" mit den ganzen Komponenten
	 */
	private void initialize() {

		einloggen = new JFrame("Einloggen");

		// Textfeld von Loginname
		text1 = new JTextField();
		einloggen.add(text1);
		text1.setBounds(110, 20, 100, 30);

		// Textfeld von Passwort
		text2 = new JPasswordField();
		einloggen.add(text2);
		text2.setBounds(110, 75, 100, 30);

		// Label "Loginname"
		JLabel l1 = new JLabel("Loginname:");
		einloggen.add(l1);
		l1.setBounds(20, 20, 80, 30);

		// Label "Passwort"
		JLabel l2 = new JLabel("Passwort:");
		einloggen.add(l2);
		l2.setBounds(20, 75, 80, 30);

		// Login Button Mitarbeiter
		JButton b = new JButton("Login M");
		einloggen.add(b);
		b.setBounds(50, 140, 80, 30);
		b.addActionListener(e6 -> verarbeiteLoginMKlick());

		// Login Button Kunde
		JButton c = new JButton("Login K");
		einloggen.add(c);
		c.setBounds(150, 140, 80, 30);
		c.addActionListener(e6 -> verarbeiteLoginKKlick());

		// Login Button Kunde
		JButton a = new JButton("Registrieren");
		einloggen.add(a);
		a.setBounds(90, 180, 110, 30);
		a.addActionListener(e -> verarbeiteRegistrierenKlick());

		einloggen.setSize(300, 300);
		einloggen.setLayout(null); // keinen LayoutManager benutzen
		einloggen.setVisible(true);// setzt den Frame auf sichtbar
		einloggen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Damit wird der Frame Beendet
		einloggen.setLocationRelativeTo(null);

	}

	private void verarbeiteRegistrierenKlick() {

		einloggen.dispose();
		new RegistrierenFrame(shop);

	}

	private void verarbeiteLoginMKlick() {
		String login = text1.getText();
		String passwort = text2.getText();

		try {

			aktuellerBenutzer = shop.mEinloggen(login, passwort);
			JOptionPane.showMessageDialog(null, aktuellerBenutzer.getVorname() + " " + aktuellerBenutzer.getNachname());
			if (aktuellerBenutzer instanceof Mitarbeiter) {
				new AngemeldetAlsMitarbeiter(shop, aktuellerBenutzer);
				einloggen.dispose();
				if (aktuellerBenutzer == null) {
					new EinloggenFrame(shop);

				}
			}
		} catch (LoginUngueltigException e2) {
			JOptionPane.showMessageDialog(null, e2.getMessage());
		}

	}

	private void verarbeiteLoginKKlick() {
		String login = text1.getText();
		String passwort = text2.getText();

		try {

			aktuellerBenutzer = shop.kEinloggen(login, passwort);
			JOptionPane.showMessageDialog(null, aktuellerBenutzer.getVorname() + " " + aktuellerBenutzer.getNachname());
			if (aktuellerBenutzer instanceof Kunde) {
				new AngemeldetAlsKunde(shop, (Kunde) aktuellerBenutzer);

				einloggen.dispose();
				if (aktuellerBenutzer == null) {
					new EinloggenFrame(shop);

				}
			}
		} catch (LoginUngueltigException e2) {
			JOptionPane.showMessageDialog(null, e2.getMessage());
		}

	}
}
