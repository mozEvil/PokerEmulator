package ru.mozevil.poker.model.table.dealer;

public class Blinds {

    private int ante;
    private int sb;
    private int bb;

    public Blinds() {
        ante = 0;
        sb = 10;
        bb = 20;
    }

    public Blinds(int ante, int sb, int bb) {
        this.ante = ante;
        this.sb = sb;
        this.bb = bb;
    }

    public int getAnte() {
        return ante;
    }

    public void setAnte(int ante) {
        this.ante = ante;
    }

    public int getSb() {
        return sb;
    }

    public void setSb(int sb) {
        this.sb = sb;
    }

    public int getBb() {
        return bb;
    }

    public void setBb(int bb) {
        this.bb = bb;
    }

    public void setBlinds(Blinds blinds) {
        ante = blinds.getAnte();
        sb = blinds.getSb();
        bb = blinds.getBb();
    }
}
