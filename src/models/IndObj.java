package models;

import java.util.HashMap;

public class IndObj {
    public enum CLASSIFICATION {
        NONE, CORRECT, INCORRECT
    }

    private PositionObj positionObj;
    private String startClassif;
    private HashMap<Integer, String> knnClassif = new HashMap<>();
    private boolean trainingData;
    private boolean withStart;

    public IndObj(PositionObj positionObj, String startClassif, boolean trainingData) {
        this.positionObj = positionObj;
        this.startClassif = startClassif;
        this.trainingData = trainingData;
        withStart = true;
    }

    public IndObj(PositionObj positionObj) {
        this.positionObj = positionObj;
        trainingData = false;
        withStart = false;
    }

    public CLASSIFICATION isCorrect(int k) {
        if (!withStart) return CLASSIFICATION.NONE;
        if (trainingData) return CLASSIFICATION.NONE;
        return startClassif.equals(knnClassif.get(k)) ? CLASSIFICATION.CORRECT : CLASSIFICATION.INCORRECT;
    }

    public void setClassif(int k, String data) {
        knnClassif.put(k, data);
    }

    public String getClassif(int k) {
        if(trainingData) return startClassif;
        return knnClassif.get(k);
    }

    public PositionObj getPositionObj() {
        return positionObj;
    }
}
