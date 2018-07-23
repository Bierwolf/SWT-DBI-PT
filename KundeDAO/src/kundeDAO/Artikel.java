package kundeDAO;

import java.sql.SQLException;

import framework.Identifizierbar;

public class Artikel implements Identifizierbar{
    
    private Long artikelnr;
    private String bezeichnung;
    private int preis;
    //für Zugriff ArtikelDAO
    private ArtikelDAO artikelDAO = ArtikelDAO.getInstance();
    
    public Artikel(Long atnr, String nme, int preis) throws SQLException {
        this.artikelnr = atnr;
        this.bezeichnung = nme;
        this.preis = preis;
        artikelDAO.create(this);
    }

    public void setArtikelnummer(Long artikelnummer) throws SQLException {
        this.artikelnr = artikelnummer;
        artikelDAO.update(this);
    }

    public String getName() {
        return bezeichnung;
    }

    public void setName(String name) throws SQLException {
        this.bezeichnung = name;
        artikelDAO.update(this);
    }

    public int getPreis() {
        return preis;
    }

    public void setPreis(int preis) throws SQLException {
        this.preis = preis;
        artikelDAO.update(this);
    }
	
	/** Holen eines vorhandenen Artikel aus der Datenhaltungsschicht
	*   über den Preis
	 * @throws SQLException 
	**/
	public static Artikel read(Long artikelnummer) throws SQLException {
		return ArtikelDAO.getInstance().read(artikelnummer);
	}

	@Override
	public long getId() {
		return artikelnr;
	}
}
