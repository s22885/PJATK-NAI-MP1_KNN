import models.TwoDim;

public class Main {
    public static void main(String[] args) {
        int kmin = 1;
        int kmax = 100;
        int dim = 4;

        KNN knn = new KNN(kmin, kmax, dim);
        FileLoader fltrening = new FileLoader("trening.csv", knn, true, ";");
        FileLoader fltesting = new FileLoader("test.csv", knn, false, ";");
        UserInterface userInterface=new UserInterface(dim,knn);

    }
}
