package model;

public class PopularBand extends Band {

    private Contract contract;
    private double earnedMoney;

    public PopularBand(String name) {
        super(name);
    }

    public PopularBand(UnpopularBand old) {
        super(old.getName());
        for (Musician m : old.getMembers()){
            m.updateBand(this);
            this.members.add(m);
        }
        for(Album album : old.getAlbums()){
            album.updateBand(this);
            this.albums.add(album);
        }
        for(Performance p : old.getPerformances()){
            p.updateBand(this);
            this.performances.add(p);
        }
        if(contract != null){
            contract.removeContract();
        }
        old.removeFromExtent();
    }

    public void addContract(Contract contract) {
        if (this.contract == null) {
            this.contract = contract;
        }
    }

    protected Contract getContract() {
        return contract;
    }

    public void removeContract() {
        this.contract = null;
    }
}
