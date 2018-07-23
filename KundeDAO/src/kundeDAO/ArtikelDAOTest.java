package kundeDAO;

import java.sql.SQLException;

public class ArtikelDAOTest {
    
    public static void main(String[] args) throws SQLException {
    	//Zugriff auf DAO-Objekt bekommen
    	ArtikelDAO artikelDAO = ArtikelDAO.getInstance();
    	
        System.out.println("Erzeuge einen Artikel");
        Artikel derArtikel = new Artikel(4711l, "Kuchen", 1);
        System.out.println("Setze lokale Variable auf NULL und hole Artikel zurück");
        derArtikel = null;
        derArtikel = Artikel.read(4711l);
        System.out.println("Artikel ist " + derArtikel.getId() + " "
                + derArtikel.getName()+", "+derArtikel.getPreis());
        System.out.println("Aktualisiere den Artikel. Setze Preis auf 2");
        derArtikel.setPreis(2);
        derArtikel = null;
        derArtikel = Artikel.read(4711l);
        System.out.println("Artikel hat jetzt Preis " + derArtikel.getPreis());

        // Jetzt wird der Preis gelöscht
 
        artikelDAO.delete(derArtikel);
        derArtikel = null;
        System.out.println("Versuche den Preis nach Löschung erneut zu lesen:");
        derArtikel = Artikel.read(4711l);
        System.out.println(derArtikel);

    }
    
}
