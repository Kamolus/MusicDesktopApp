package model;

import java.util.EnumSet;

public class Musician extends User  {
    private EnumSet<MusicianType> types;
    private String stageName;
    private Band currentBand;
    private String voiceRange;
    private String bass;
    private int guitarCount;
    private boolean hasDrum;

    public Musician(String name, String email, String stageName, EnumSet<MusicianType> types) {
        super(name, email);
        try {
            setStageName(stageName);
            this.types = types;
        }catch(Exception e){
            e.printStackTrace();
            removeFromExtent();
        }
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        if (stageName == null || stageName.isBlank()) {
            throw new IllegalArgumentException("Stage name cannot be null or empty");
        }
        this.stageName = stageName;
    }

    public boolean isAvailable() {
        return currentBand == null;
    }

    public boolean hasType(MusicianType type) {
        return types.contains(type);
    }

    public void assignToBand(Band band) {
        if (isAvailable() && band != null) {
            this.currentBand = band;
            band.addMusician(this);
        }
    }

    public void removeBand(){
        if (currentBand != null) {
            currentBand.removeMusician(this);
            this.currentBand = null;
        }
    }

    public String getVoiceRange() {
        if(!types.contains(MusicianType.VOCALIST)){
            throw new UnsupportedOperationException("Musician is not a vocalist");
        }
        return voiceRange;
    }

    public void setVoiceRange(String voiceRange) {
        if(!types.contains(MusicianType.VOCALIST)){
            throw new UnsupportedOperationException("Musician is not a vocalist");
        }
        this.voiceRange = voiceRange;
    }

    public String getBass() {
        if(!types.contains(MusicianType.BASSIST)){
            throw new UnsupportedOperationException("Musician is not a bassist");
        }
        return bass;
    }

    public void setBass(String bass) {
        if(!types.contains(MusicianType.BASSIST)){
            throw new UnsupportedOperationException("Musician is not a bassist");
        }
        this.bass = bass;
    }

    public int getGuitarCount() {
        if(!types.contains(MusicianType.GUITARIST)){
            throw new UnsupportedOperationException("Musician is not a guitarist");
        }
        return guitarCount;
    }

    public void setGuitarCount(int guitarCount) {
        if(!types.contains(MusicianType.GUITARIST)){
            throw new UnsupportedOperationException("Musician is not a guitarist");
        }
        this.guitarCount = guitarCount;
    }

    public boolean isHasDrum() {
        if(!types.contains(MusicianType.DRUMMER)){
            throw new UnsupportedOperationException("Musician is not a drummer");
        }
        return hasDrum;
    }

    public void setHasDrum(boolean hasDrum) {
        if(!types.contains(MusicianType.DRUMMER)){
            throw new UnsupportedOperationException("Musician is not a drummer");
        }
        this.hasDrum = hasDrum;
    }

    protected void updateBand(Band band) {
        this.currentBand = band;
    }

    public EnumSet<MusicianType> getTypes() {
        return types;
    }

    @Override
    public String toString() {
        return "Musician{" +
                "stageName='" + getStageName() + '\'' +
                ", types=" + getTypes() + '\'' +
                '}';
    }
}