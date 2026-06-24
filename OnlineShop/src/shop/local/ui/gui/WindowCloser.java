package shop.local.ui.gui;

import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

public class WindowCloser extends WindowAdapter {

	@Override
	public void windowClosing(WindowEvent e) {
		Window window = e.getWindow();
		int result = JOptionPane.showConfirmDialog(window, "Wollen Sie die Anwendung wirklich beenden?");
		if (result == 0) {
			window.setVisible(false);
			window.dispose();
			System.exit(0);
		}
	}
}
