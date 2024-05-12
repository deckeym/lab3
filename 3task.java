import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void coffee(double TemprNull, double TemprAverage, double CoolDownCoef, int t, ArrayList<Double> Coffee) {
        for (int i = 0; i <= t; i++) {
            Coffee.add(TemprNull);
            TemprNull -= CoolDownCoef * (TemprNull - TemprAverage);
        }
    }

    public static double aproxA(ArrayList<Double> Coffee) {
        double Ys = 0;
        double Xs = 0;
        double XYs = 0;
        double X2s = 0;
        int n = Coffee.size();

        for (int i = 0; i < n; i++) {
            Ys += Coffee.get(i);
            Xs += i;
            XYs += Coffee.get(i) * i;
            X2s += i * i;
        }
        return (n * XYs - (Xs * Ys)) / (n * X2s - Xs * Xs);
    }

    public static double aproxB(ArrayList<Double> Coffee) {
        double Ys = 0, Xs = 0;
        double a = aproxA(Coffee);
        int n = Coffee.size();

        for (int i = 0; i < n; i++) {
            Ys += Coffee.get(i);
            Xs += i;
        }
        return (Ys - a * Xs) / n;
    }

    public static double correl(ArrayList<Double> Coffee) {
        int n = Coffee.size();
        double sumT = 0.0;

        for (double T : Coffee) {
            sumT += T;
        }
        double SrT = sumT / n;
        double Srt = (n - 1) * n / 2;
        double XYs = 0.0;
        double Xs2 = 0.0;
        double Ys2 = 0.0;

        for (int i = 0; i < n; i++) {
            double tmX = i - Srt;
            double tmY = Coffee.get(i) - SrT;
            XYs += tmX * tmY;
            Xs2 += tmX * tmX;
            Ys2 += tmY * tmY;
        }
        double CoolDownCoef = XYs / Math.sqrt(Xs2 * Ys2);
        return CoolDownCoef;
    }

    public static void main(String[] args) {
        double TemprNull, TemprAverage, CoolDownCoef;
        int t;
        ArrayList<Double> Coffee = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Пожалуйста, введите начальную температуру кофе, среднюю температуру окружающей среды, коэффициент охлаждения и время охлаждения (время в минутах, разделенные пробелами):");
        TemprNull = scanner.nextDouble();
        TemprAverage = scanner.nextDouble();
        CoolDownCoef = scanner.nextDouble();
        t = scanner.nextInt();

        coffee(TemprNull, TemprAverage, CoolDownCoef, t, Coffee);
        double A = aproxA(Coffee);
        double B = aproxB(Coffee);
        double C = correl(Coffee);
        t = 0;
        System.out.printf("%19s%25s\n", "Время", "Температура");
        for (double T : Coffee) {
            System.out.printf("%10d%10.2f\n", t, T);
            t++;
        }
        System.out.println("Прямая аппроксимации: T = " + A + " * t + " + B);
        System.out.println("Коэффициент корреляции: " + C);
    }
}
