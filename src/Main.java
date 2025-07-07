import gui.MainScreen;
import model.*;

import javax.swing.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.EnumSet;

public class Main {
    public static void main(String[] args) {
        try {
            ObjectExtent.loadExtent();
            System.out.println("Extent wczytany.");
        } catch (Exception e) {
            System.out.println("Nie udało się wczytać extentu: " + e.getMessage());
        }
        //populateWithData();

        SwingUtilities.invokeLater(MainScreen::new);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                ObjectExtent.saveExtent();
                System.out.println("Extent zapisany przed zamknięciem programu.");
            } catch (IOException ex) {
                System.out.println("Błąd przy zapisie extentu: " + ex.getMessage());
            }
        }));
    }

    private static void populateWithData(){
        Musician musician = new Musician("Adam","kamilpanka@gmail.com","Adamek", EnumSet.of(MusicianType.GUITARIST));
        Musician musician1 = new Musician("Dawid","kamilpanka@gmail.com","Dadziu", EnumSet.of(MusicianType.GUITARIST));
        Musician musician2 = new Musician("Ewa","kamiln@kagmail.com","Ewas", EnumSet.of(MusicianType.DRUMMER));
        Musician musician3 = new Musician("Ola","kam@pankagmail.com","Alek", EnumSet.of(MusicianType.VOCALIST, MusicianType.BASSIST));

        Band band = new PopularBand("Thunder");
        MusicianScout scout = new MusicianScout("Darek","cipa@gmail.com", LocalDate.of(2012,12,2) ,2234,"scout");

        Genre genre = new Genre("ProgRock","British Genre");
        Album album = new Album("The Overview",2025,true, genre, 10000 );
        Album album1 = new Album("The Overview2",2025,true, genre, 9900 );

        band.addAlbum(album);
        band.addAlbum(album1);

        Genre genre1 = new Genre("Pop","USA");

        Band band1 = new PopularBand("Porcupine Tree");
    }
}