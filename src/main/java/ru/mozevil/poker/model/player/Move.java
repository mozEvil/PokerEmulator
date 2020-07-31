package ru.mozevil.poker.model.player;

public class Move {

    //todo remake decision
    public static final int UNKNOWN = -1;
    public static final int FOLD = 0;
    public static final int CHECK = 1;
    public static final int CALL = 2;
    public static final int RAISE = 3;

    private Decision decision;
    private int size;

    public Move(Decision decision, int size) {
        this.decision = decision;
        this.size = size;
    }

    public Move(Decision decision) {
        this.decision = decision;
        this.size = 0;
    }

    public Move(int size) {
        this.decision = Decision.UNKNOWN;
        this.size = size;
    }

    public Move() {
        this.decision = Decision.UNKNOWN;
        this.size = 0;
    }

    public Decision getDecision() {
        return decision;
    }

    public void setDecision(Decision decision) {
        this.decision = decision;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return decision.getName() + " " + size;
    }


    public enum Decision {
        UNKNOWN("Unknown"),
        FOLD("Fold"),
        CHECK("Check"),
        CALL("Call"),
        RAISE("Raise"),
        ALLIN("All In");

        private String name;

        Decision(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return getName();
        }
    }

}
