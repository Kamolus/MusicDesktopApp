package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Klasa reprezentująca album muzyczny.
 * Obsługuje relacje z zespołem, gatunkiem i utworami.
 * Dziedziczy po ObjectExtent – dodawana jest automatycznie do ekstensji.
 */
public class Album extends ObjectExtent {
    private String title;
    private int releaseYear;
    private boolean isLimitedEdition;
    private int sells;
    private int price;
    private Genre genre;
    private Band band;
    private List<Song> songs = new ArrayList<>();

    /**
     * Konstruktor albumu – wymaga podstawowych danych.
     */
    public Album(String title, int releaseYear, boolean isLimitedEdition, Genre genre, int sells) {
        try {
            setTitle(title);
            setReleaseYear(releaseYear);
            setGenre(genre);
            setLimitedEdition(isLimitedEdition);
            setSells(sells);
        } catch (Exception e) {
            e.printStackTrace();
            removeFromExtent(); // W razie błędu usuwa obiekt z ekstensji
        }
    }

    /**
     * Przypisanie gatunku – zachowana spójność dwustronna.
     */
    public void setGenre(Genre genre) {
        if (this.genre == null || genre.equals(this.genre)) {
            this.genre = genre;
            genre.addAlbum(this);
        }
    }

    /**
     * Usunięcie relacji z gatunkiem.
     */
    public void removeGenre(Genre genre) {
        if (genre.equals(this.genre)) {
            this.genre = null;
            genre.removeAlbum(this);
        }
    }

    public int getSells() {
        return sells;
    }

    /**
     * Ustawienie sprzedaży – wywołuje ewaluację awansu zespołu.
     */
    private void setSells(int sells) {
        if (this.sells < 0) {
            throw new IllegalArgumentException("Sells cannot be negative");
        }
        this.sells = sells;
        if (band != null) {
            band.evaluatePromotionAndReturn(); // sprawdza awans zespołu
        }
    }

    /**
     * Uaktualnia sprzedaż tylko jeśli nowa wartość jest większa.
     */
    public void updateSells(int sells) {
        if (sells > this.sells) {
            this.sells = sells;
        }
    }

    public void setTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Album title cannot be empty");
        }
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setReleaseYear(int releaseYear) {
        if (releaseYear <= 0) {
            throw new IllegalArgumentException("Release year must be positive");
        }
        this.releaseYear = releaseYear;
    }

    /**
     * Ustawienie flagi limitowanej edycji oraz ceny.
     */
    public void setLimitedEdition(boolean limitedEdition) {
        this.price = limitedEdition ? 50 : 20;
        this.isLimitedEdition = limitedEdition;
    }

    /**
     * Przypisanie albumu do zespołu – zachowuje spójność.
     */
    public void setBand(Band band) {
        if (band != null) {
            this.band = band;
            band.addAlbum(this);
        }
    }

    /**
     * Usuwa przypisanie zespołu – zachowuje spójność.
     */
    public void removeBand(Band band) {
        if (band.equals(this.band)) {
            band.removeAlbum(this);
            this.band = null;
        }
    }

    /**
     * Pomocnicza metoda do nadpisania referencji zespołu (używana przez Band).
     */
    protected void updateBand(Band band) {
        this.band = band;
    }

    /**
     * Dodaje utwór do albumu.
     */
    public void addSong(Song song) {
        if (song != null && !songs.contains(song)) {
            songs.add(song);
        }
    }

    /**
     * Usuwa utwór z albumu i z ekstensji.
     */
    public void removeSong(Song song) {
        if (songs.contains(song)) {
            songs.remove(song);
            song.removeFromExtent();
        }
    }

    /**
     * Zwraca nazwę gatunku.
     */
    public String getGenreNamee() {
        return genre.getGenreName();
    }

    /**
     * Sprawdza czy album przypisano do zespołu.
     */
    public boolean hasAssignedBand() {
        return band != null;
    }

    /**
     * Usuwa wszystkie utwory z albumu przed jego usunięciem z ekstensji.
     */
    @Override
    protected void removeFromExtent() {
        while (!songs.isEmpty()) {
            removeSong(songs.getFirst());
        }
        super.removeFromExtent();
    }

    /**
     * Zwraca niemodyfikowalną listę utworów.
     */
    public List<Song> getSongs() {
        return Collections.unmodifiableList(songs);
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    /**
     * Reprezentacja tekstowa albumu.
     */
    @Override
    public String toString() {
        return "Album{" +
                "title='" + title + '\'' +
                ", releaseYear=" + releaseYear +
                ", price=" + price +
                ", songs=" + songs +
                ", genre=" + genre.getGenreName() +
                '}';
    }
}
