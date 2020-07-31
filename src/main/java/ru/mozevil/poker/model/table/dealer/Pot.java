package ru.mozevil.poker.model.table.dealer;

public class Pot {

    private int size;

    public Pot() {
    }

    public Pot(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void deposit(int value) {
        size += value;
    }

    public void withdrawal(int value) {
        size -= value;
    }

    public void clear() {
        size = 0;
    }
}
