import models.TwoDim;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileExport {
    public static void export(List<TwoDim> list) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("export.txt"));

            bw.write("k;accuracy");
            bw.newLine();
            for (TwoDim td:
                 list) {
                bw.write(td.getI()+";"+ td.getD());
                bw.newLine();
            }
            System.out.println("export finished");

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
