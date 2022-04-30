import models.IndObj;
import models.PositionObj;
import models.TwoDim;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserInterface {
    private int dim;
    private KNN knn;

    public UserInterface(int dim, KNN knn) {
        this.dim = dim;
        this.knn = knn;

        start();
    }

    public void start() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        double[] posval = new double[dim];
        String classif = "";
        String in;

        boolean con = true;
        while (con) {
            System.out.println("waiting for main command...");
            try {
                in = reader.readLine();

                switch (in) {
                    case "export":
                        FileExport.export(knn.getStats());
                        break;
                    case "check":
                        for (int i = 0; i < posval.length; i++) {
                            System.out.println("(" + (i+1) + ") = (" + posval[i] + ")");
                        }
                        System.out.println("(species) = (" + classif + ")");
                        break;
                    case "setval":
                        System.out.println("which dim? (" + 1 + "..." + posval.length + ")");
                        in = reader.readLine();
                        try {
                            int indimnum = Integer.parseInt(in);
                            System.out.println("waiting for dim value (double)");
                            in = reader.readLine();
                            double indimval = Double.parseDouble(in);
                            if (indimnum <= posval.length && 0 < indimnum) {
                                posval[indimnum - 1] = indimval;
                            } else {
                                System.out.println("arg!");
                            }

                        } catch (NumberFormatException eNFE) {
                            System.out.println("arg!");
                        }
                        break;
                    case "setspec":
                        System.out.println("provide species");
                        in = reader.readLine();
                        String tmpspec = in;
                        System.out.println("is it correct? (" + tmpspec + ")");
                        System.out.println("confirm (y/n)");
                        in = reader.readLine();
                        if (in.equals("y")) {
                            classif = tmpspec;
                        }
                        break;
                    case "getstats":
                        knn.getStats().forEach(System.out::println);
                        break;
                    case "push":
                        for (int i = 0; i < posval.length; i++) {
                            System.out.println("(" + i + ") = (" + posval[i] + ")");
                        }
                        System.out.println("(species) = (" + classif + ")");
                        System.out.println("confirm (y/n)");
                        in = reader.readLine();
                        if (in.equals("y")) {
                            knn.testing(new IndObj(new PositionObj(posval), classif, false));
                            for (Double d:
                                 posval) {
                                d=0.0;
                            }
                            classif = "";
                        }
                        break;

                    case "exit":
                        con = false;
                        break;
                    default:
                        System.out.println("arg!");

                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }
}
