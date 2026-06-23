package shop.local.ui.gui;

import java.io.IOException;



import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import shop.local.domain.EShop;
import shop.local.domain.exceptions.KundeBereitsVorhandenException;



public class ShopGuiClient extends JFrame {

	private EShop shop;

	private static final long serialVersionUID = 1L;

	public ShopGuiClient(String titel, String datei) throws IOException, KundeBereitsVorhandenException {
		super(titel);
		shop = new EShop(datei);

		new EinloggenFrame(shop);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					new ShopGuiClient("OnlineShop", "SHOP");
				} catch (IOException | KundeBereitsVorhandenException  e) {
					JOptionPane.showMessageDialog(null, e);
				} 
			}
		});
	}
}
