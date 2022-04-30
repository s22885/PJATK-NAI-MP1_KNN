package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PositionObj extends ArrayList<Double> {
    public PositionObj(ArrayList<Double> al) {
        this.addAll(al);
    }

    public PositionObj(double[] posval) {
        for (double v : posval) {
            this.add(v);
        }
    }


    public static double getDist(PositionObj posA, PositionObj posB) {
        if (posA.size() != posB.size()) return -1;
        double res = 0;
        for (int i = 0; i < posA.size(); i++) {
            res += Math.pow((posA.get(i) - posB.get(i)), 2);
        }
        return res;
    }
}
