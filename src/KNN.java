import models.TwoDim;
import models.IndObj;
import models.PositionObj;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class KNN {
    private int dim;
    private int kmin;
    private int kmax;
    private List<IndObj> objs = new ArrayList<>();

    public KNN(int kmin, int kmax, int dim) {
        this.kmin = kmin;
        this.kmax = kmax;
        this.dim = dim;
    }

    public void training(IndObj indObj) {
        if (indObj.getPositionObj().size() != dim) return;

        objs.add(indObj);
    }

    public void testing(IndObj indObj) {
        if (indObj.getPositionObj().size() != dim) return;

        var distAr = new ArrayList<Double>();
        for (int i = 0; i < objs.size(); i++) {
            distAr.add(PositionObj.getDist(objs.get(i).getPositionObj(), indObj.getPositionObj()));
        }
        var distObjAr = new ArrayList<TwoDim>();
        for (int i = 0; i < distAr.size(); i++) {
            distObjAr.add(new TwoDim(i, distAr.get(i)));
        }
        distObjAr.sort(Comparator.comparing(TwoDim::getD));

        for (int k = kmin; k <= kmax; k++) {
            var distCounterMap = new HashMap<String, Integer>();
            for (int i = 0; i < k; i++) {
                String tmp = objs.get(distObjAr.get(i).getI()).getClassif(k);
                if (!distCounterMap.containsKey(tmp)) {
                    distCounterMap.put(tmp, 1);
                } else {
                    distCounterMap.put(tmp, distCounterMap.get(tmp) + 1);
                }
            }
            AtomicInteger tmpv = new AtomicInteger(-1);
            AtomicReference<String> tmpn = new AtomicReference<>("");
            distCounterMap.forEach((key, vol) -> {
                if (vol > tmpv.get()) {
                    tmpn.set(key);
                    tmpv.set(vol);
                }
            });
            indObj.setClassif(k, tmpn.get());
        }
        objs.add(indObj);
    }

    public Double getStats(int k) {
        var cor = 0;
        var incor = 0;

        for (int i = 0; i < objs.size(); i++) {
            switch (objs.get(i).isCorrect(k)) {
                case CORRECT -> cor++;
                case INCORRECT -> incor++;
            }
        }
        if ((cor + incor) == 0) return -1.0;
        return (double) cor / (double) (cor + incor);
    }

    public List<TwoDim> getStats() {
        var res = new ArrayList<TwoDim>();

        for (int i = kmin; i <= kmax; i++) {
            res.add(new TwoDim(i, getStats(i)));
        }

        return res;
    }
}
