package model;

public class Performance extends ObjectExtent {
    private Event event;
    private Band band;
    private boolean isMainBand;

    public Performance(Event event, Band band, boolean isMainBand) {
        try {
            setEvent(event);
            setBand(band);
            this.isMainBand = isMainBand;

            event.addPerformance(this);
            band.addPerformance(this);
        }catch (Exception e) {
            e.printStackTrace();
            removeFromExtent();
        }
    }


    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        if(event == null) {
            throw new NullPointerException("Event cannot be null");
        }
        this.event = event;
    }

    public Band getBand() {
        return band;
    }

    public void setBand(Band band) {
        if(band == null || band.equals(this.band)) {
            throw new NullPointerException("band is null");
        }
        this.band = band;
    }

    protected void updateBand(Band band) {
        this.band = band;
    }

    public boolean isMainBand() {
        return isMainBand;
    }

    public void setMainBand(boolean mainBand) {
        isMainBand = mainBand;
    }

    public void removePerformance() {
        event.removePerformance(this);
        removeFromExtent();
    }

    @Override
    public String toString() {
        return "Performance{" +
                "event=" + event +
                ", band=" + band +
                ", isMainBand=" + isMainBand +
                '}';
    }
}
