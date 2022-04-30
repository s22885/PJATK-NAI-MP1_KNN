package models;

public class TwoDim {
    private int i;
    private Double d;

    public TwoDim(int i, Double d) {
        this.i = i;
        this.d = d;
    }

    public Double getD() {
        return d;
    }

    public int getI() {
        return i;
    }

    @Override
    public String toString() {
        return "k = (" + i + ") accuracy = (" + d + ")";
    }
}
