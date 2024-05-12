import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {

    static double firstFrag(double x) {
        return 1;
    }

    static double secFrag(double x) {
        return 0.5 * x - 2;
    }

    static double thirdFrag(double x) {
        double y = Math.sqrt(Math.abs(4 - Math.pow(x, 2)));
        if (Math.abs(y) < 0.000001) {
            y = 0.0;
        }
        return y;
    }

    static double fourthFrag(double x) {
        double y = -1 * (Math.sqrt(Math.abs(1 - Math.pow(x, 2))));
        if (Math.abs(y) < 0.000001) {
            y = 0.0;
        }
        return y;
    }

    static double fifthFrag(double x) {
        double y = -1 * x + 2;
        if (Math.abs(y) < 0.000001) {
            y = 0.0;
        }
        return y;
    }

    public static void main(String[] args) {
        double xstart = -7;
        double xfinish = 3;
        double dx = 0.1;
        try {
            PrintWriter outfile = new PrintWriter(new FileWriter("1Task_15Variant.txt"));
            outfile.printf("%10s%10s\n", "X", "Y");
            outfile.println("".format("%20s", "-").replace(' ', '-'));
            for (double x = xstart; x <= xfinish; x += dx) {
                if (Math.abs(x) < 0.000001) {
                    x = 0.0;
                }
                if (x >= -7 && x < -6) {
                    outfile.printf("%10.3f%10.3f\n", x, firstFrag(x));
                }
                if (x >= -6 && x < -4) {
                    outfile.printf("%10.3f%10.3f\n", x, secFrag(x));
                }
                if (x >= -4 && x < 0) {
                    outfile.printf("%10.3f%10.3f\n", x, thirdFrag(x));
                }
                if (x >= 0 && x < 2) {
                    outfile.printf("%10.3f%10.3f\n", x, fourthFrag(x));
                }
                if (x >= 2 && x <= 3) {
                    outfile.printf("%10.3f%10.3f\n", x, fifthFrag(x));
                }
            }
            outfile.close();
        } catch (IOException e) {
            System.out.println("При открытии файла произошла ошибка");
            e.printStackTrace();
        }
    }
}
