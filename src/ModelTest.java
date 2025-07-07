import model.*;

import java.time.LocalDate;
import java.util.EnumSet;

public class ModelTest {

    public static void main(String[] args) {
        System.out.println("===== TEST: Tworzenie muzyków i zespołu =====");
        new Musician("Adam", "kamilpanka@gmail.com", "Adamek", EnumSet.of(MusicianType.GUITARIST));
        new Musician("Dawid", "kamilpanka@gmail.com", "Dadziu", EnumSet.of(MusicianType.GUITARIST));
        new Musician("Ewa", "kamiln@kagmail.com", "Ewas", EnumSet.of(MusicianType.DRUMMER));
        new Musician("Ola", "kam@pankagmail.com", "Alek", EnumSet.of(MusicianType.VOCALIST, MusicianType.BASSIST));

        PopularBand band = new PopularBand("Thunder");

        System.out.println("Muzycy: " + ObjectExtent.getExtentFromClass(Musician.class));

        System.out.println("\n===== TEST: Scout przypisuje muzyków do zespołu =====");
        MusicianScout scout = new MusicianScout("Darek", "pa@gmail.com", LocalDate.of(2012, 12, 2), 2234, "scout");
        scout.findAndAssignMusicianToBand(band, MusicianType.DRUMMER);
        scout.findAndAssignMusicianToBand(band, MusicianType.GUITARIST);
        scout.findAndAssignMusicianToBand(band, MusicianType.GUITARIST);

        System.out.println("Członkowie zespołu " + band.getName() + ":");
        for (Musician m : band.getMembers()) {
            System.out.println("- " + m.getName() + " (" + m.getTypes() + ")");
        }

        System.out.println("\n===== TEST: Dodanie albumu i gatunku =====");
        Genre progrock = new Genre("ProgRock", "British");
        Band band3 = new UnpopularBand("Siema", "Arka Gdynia");
        Album court = new Album("Court", 1969, true, progrock, 9000);
        band.addAlbum(court);

        System.out.println("Albumy zespołu " + band.getName() + ": " + band.getAlbums());
        System.out.println("Gatunek 'ProgRock' zawiera: " + progrock.getAllAlbums());

        System.out.println("\n===== TEST: Menedżer podpisuje kontrakt =====");
        BandManager bandManager = new BandManager("Grzegorz", "gfloryda@gmail.com",
                LocalDate.of(2012, 12, 2), 2500, "Europe", "manager");

        bandManager.displayPopularBands();
        bandManager.signContract(band, 5, 0.22, 300);

        System.out.println("Lista kontraktów: " + ObjectExtent.getExtentFromClass(Contract.class));

        System.out.println("\n===== TEST: Występy zespołów =====");
        Event event = new Event("Open Air", LocalDate.of(2025, 8, 12));
        Band band1 = new PopularBand("Arctic Monkeys");
        Band band2 = new PopularBand("Muse");

        Musician musician = new Musician("Grzegorz", "k@g.pl", "Floryda",
                EnumSet.of(MusicianType.DRUMMER, MusicianType.GUITARIST));

        band1.addMusician(musician);
        band2.addMusician(musician); // nie powinno się udać

        event.addBandToPerformance(band1, true);
        event.addBandToPerformance(band2, false);

        System.out.println("Główne zespoły: " + event.getMainBands());
        System.out.println("Wszystkie zespoły: " + event.getBands());
        System.out.println("Występy: " + ObjectExtent.getExtentFromClass(Performance.class));

        System.out.println("\n===== TEST: Promocja zespołu =====");
        band1.addAlbum(court);  // album 9000
        Album album = new Album("Art", 1969, true, progrock, 11000);
        band3.addAlbum(album);  // razem 11000, powinien przejść na PopularBand

        System.out.println("Zespół 'band3' ma albumy: " + band3.getAlbums());
        System.out.println("PopularBands: " + ObjectExtent.getExtentFromClass(PopularBand.class));
        System.out.println("UnpopularBands: " + ObjectExtent.getExtentFromClass(UnpopularBand.class));

        System.out.println("\n===== TEST: Pełny cykl z nowym zespołem =====");
        Musician m1 = new Musician("John", "j@i.pl", "Johnny", EnumSet.of(MusicianType.GUITARIST));
        Band b1 = new UnpopularBand("The Dreamers", "siem");
        b1.addMusician(m1);
        System.out.println("Członkowie: " + b1.getMembers());

        Album a1 = new Album("ss", 11, true, progrock, 12000);
        b1.addAlbum(a1);
        b1 = b1.evaluatePromotionAndReturn();  // powinien stać się PopularBand
        System.out.println("Typ zespołu po promocji: " + b1.getClass().getSimpleName());

        Event e1 = new Event("Rock Night", LocalDate.of(2026, 6, 1));
        b1.addPerformanceToEvent(e1, true);
        System.out.println("Występy zespołu: " + b1.getPerformances());

        System.out.println("\n===== EKSTENSJE NA KONIEC =====");
        System.out.println("PopularBands: " + ObjectExtent.getExtentFromClass(PopularBand.class));
        System.out.println("UnpopularBands: " + ObjectExtent.getExtentFromClass(UnpopularBand.class));
        System.out.println("Wszystkie Band: " + ObjectExtent.getExtentFromClass(Band.class));
        System.out.println("Kontrakty: " + ObjectExtent.getExtentFromClass(Contract.class));
        System.out.println("Występy: " + ObjectExtent.getExtentFromClass(Performance.class));
        System.out.println("Albumy: " + ObjectExtent.getExtentFromClass(Album.class));
    }

}
