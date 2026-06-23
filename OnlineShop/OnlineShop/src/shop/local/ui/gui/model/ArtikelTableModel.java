package shop.local.ui.gui.model;

import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import shop.local.valueObjects.Artikel;
import shop.local.valueObjects.Massengutartikel;

public class ArtikelTableModel extends AbstractTableModel {

	/**
	 *
	 */
	private static final long serialVersionUID = -4971275536884878805L;
	private List<Artikel> artikeln;
	private String[] spaltenNamen = { "Nummer", "Titel", "Preis", "Bestand", "Einheit" };

	public ArtikelTableModel(List<Artikel> aktuelleArtikel) {
		super();
		// Ich erstelle eine Kopie der Bücherliste,
		// damit beim Aktualisieren (siehe Methode setBooks())
		// keine unerwarteten Seiteneffekte entstehen.
		artikeln = new Vector<Artikel>();
		artikeln.addAll(aktuelleArtikel);
	}

	public void setArtikel(List<Artikel> aktuelleArtikel) {
		artikeln.clear();
		artikeln.addAll(aktuelleArtikel);
		fireTableDataChanged();
	}

	/*
	 * Ab hier überschriebene Methoden mit Informationen, die eine JTable von jedem
	 * TableModel erwartet: - Anzahl der Zeilen - Anzahl der Spalten - Benennung der
	 * Spalten - Wert einer Zelle in Zeile / Spalte
	 */

	@Override
	public int getRowCount() {
		return artikeln.size();
	}

	@Override
	public int getColumnCount() {
		return spaltenNamen.length;
	}

	@Override
	public String getColumnName(int col) {
		return spaltenNamen[col];
	}

	@Override
	public Object getValueAt(int row, int col) {

		Artikel gewaehltesArtikel = artikeln.get(row);
		switch (col) {
		case 0:
			return gewaehltesArtikel.getArtNr();
		case 1:
			return gewaehltesArtikel.getBezeichnung();
		case 2:
			return gewaehltesArtikel.getPreis();
		case 3:
			return gewaehltesArtikel.getBestand();
		case 4:
			if (gewaehltesArtikel instanceof Massengutartikel) {
				return ((Massengutartikel) gewaehltesArtikel).getEinheit();
			}
		default:
			return null;
		}
	}
}
