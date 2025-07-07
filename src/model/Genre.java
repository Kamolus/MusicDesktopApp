package model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Genre extends ObjectExtent implements Serializable {

    private String name;
    private String nationality;
    private Map<String, Album> albumMap = new HashMap<>();

    public Genre(String name, String nationality) {
        try {
            setName(name);
            setNationality(nationality);
        }catch(Exception e) {
            e.printStackTrace();
            removeFromExtent();
        }
    }

    //Unique
    public void setName(String name) {
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name;
    }

    public void setNationality(String nationality) {
        if(nationality == null || nationality.isEmpty()){
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.nationality = nationality;
    }

    public void addAlbum(Album album) {
        if (album != null && album.getTitle() != null && !albumMap.containsKey(album.getTitle())) {
            albumMap.put(album.getTitle(), album);
        }
    }

    public void removeAlbum(Album album) {
        if (albumMap.containsKey(album.getTitle())) {
            albumMap.remove(album.getTitle());
            album.removeGenre(this);
        }
    }

    public Album getAlbumByTitle(String title) {
        return albumMap.get(title);
    }


    public Collection<Album> getAllAlbums() {
        return Collections.unmodifiableCollection(albumMap.values());
    }

    public String getGenreName() {
        return name;
    }

    public String getNationality() {
        return nationality;
    }

    @Override
    public String toString() {
        return "Genre{" +
                ",name=" + name + ' ' +
                ",nationality=" + nationality + ' ' +
                '}';
    }
}
