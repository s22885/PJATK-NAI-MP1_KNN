import models.IndObj;
import models.PositionObj;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class FileLoader {
    private String pathName;
    private boolean training;
    private KNN knn;
    private String separator;

    public FileLoader(String pathName, KNN knn, boolean training, String separator) {
        this.pathName = pathName;
        this.training = training;
        this.knn = knn;
        this.separator = separator;
        exec();
    }

    private void exec() {
        try (var bf = new BufferedReader(new FileReader(pathName))) {
            var stAr = new ArrayList<String>();
            String line;
            while ((line = bf.readLine()) != null) {
                stAr.add(line);
            }
            for (String st : stAr) {
                var tmpStAr = st.split(separator);
                var tmpDoAr = new ArrayList<Double>();
                for (int i = 0; i < tmpStAr.length - 1; i++) {
                    try {
                        tmpDoAr.add(Double.parseDouble(tmpStAr[i]));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        break;
                    }
                }
                PositionObj po = new PositionObj(tmpDoAr);
                IndObj ino = new IndObj(po, tmpStAr[tmpStAr.length - 1], training);
                if (training) {
                    knn.training(ino);
                } else {
                    knn.testing(ino);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
