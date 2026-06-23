package shop.local.persistence;

import java.io.IOException;

import shop.local.valueObjects.Artikel;
import shop.local.valueObjects.Kunde;
import shop.local.valueObjects.Mitarbeiter;

public interface PersistenceManager {

	public void openForReading(String datenquelle) throws IOException;

	public void openForWriting(String datenquelle) throws IOException;

	public boolean close();

	// ----------------------------------------Artikel--------------------------------------------------------
	public Artikel ladeArtikel() throws IOException;

	public boolean speichereArtikel(Artikel b) throws IOException;

	// ----------------------------------------Kunde--------------------------------------------------------
	public Kunde ladeKunde() throws IOException;

	public boolean speichereKunde(Kunde k) throws IOException;

	// ----------------------------------------Mitarbeiter--------------------------------------------------------
	public Mitarbeiter ladeMitarbeiter() throws Exception;

	public boolean speichereMitarbeiter(Mitarbeiter m);
}