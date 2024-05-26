import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    // Функция для генерации случайного числа в заданном диапазоне
    static int random(int min, int max) {
        Random rand = new Random();
        return rand.nextInt(max - min + 1) + min;
    }

    // Функция для возведения числа в степень по модулю
    static int powByMod(int a, int x, int p) {
        int result = 1;
        a = a % p;
        while (x > 0) {
            if (x % 2 == 1) {
                result = (result * a) % p;
            }
            a = (a * a) % p;
            x /= 2;
        }
        return result;
    }

    // Алгоритм решета Эратосфена для поиска простых чисел до 500
    static void eratosphen(List<Integer> primeNumbs) {
        for (int i = 2; i <= 500; i++) {
            primeNumbs.add(i);
        }
        for (int i = 0; i <= Math.sqrt(primeNumbs.size()); i++) {
            int j = i + 1;
            while (j < primeNumbs.size()) {
                if (primeNumbs.get(j) % primeNumbs.get(i) == 0) {
                    for (int k = j; k < primeNumbs.size() - 1; k++) {
                        primeNumbs.set(k, primeNumbs.get(k + 1));
                    }
                    primeNumbs.remove(primeNumbs.size() - 1);
                } else {
                    j++;
                }
            }
        }
    }

    // Функция для нахождения наибольшего общего делителя двух чисел
    static int nod(int a, int b) {
        if (b == 0) {
            return a;
        }
        return nod(b, a % b);
    }

    // Функция Эйлера для вычисления функции Эйлера от простого числа
    static int eiler(int p) {
        int result = p;
        for (int i = 2; i * i <= p; i++) {
            if (p % i == 0) {
                while (p % i == 0) {
                    p /= i;
                }
                result -= result / i;
            }
        }
        if (p > 1) {
            result -= result / p;
        }
        return result;
    }

    // Функция для нахождения уникальных простых множителей и вычисления m/2 для теста Миллера
    static int millerRealize(int m, int n1, List<Integer> primeNumbs, List<Integer> primeMnUnic, List<Integer> primeMn) {
        for (int primeNumb : primeNumbs) {
            int degree = 0;
            if (n1 % primeNumb == 0) {
                while (n1 % primeNumb == 0) {
                    n1 /= primeNumb;
                    degree += 1;
                    primeMn.add(primeNumb);
                }
                primeMnUnic.add(primeNumb);
                m *= Math.pow(primeNumb, degree);
            }
        }
        return m / 2;
    }

    // Тест простоты Миллера-Рабина
    static boolean miller(int n, List<Integer> primeMnUnic, int t) {
        if (n == 2) {
            return true;
        }
        if (n < 2 || n % 2 == 0) {
            return false;
        }
        boolean millerCheckFrst = false;
        boolean millerCheckScnd = false;
        for (int j = 0; j < t; j++) {
            int a = random(2, n - 1);
            if (powByMod(a, n - 1, n) != 1) {
                millerCheckFrst = false;
            } else {
                millerCheckFrst = true;
            }
        }
        for (int prime : primeMnUnic) {
            for (int j = 0; j < t; j++) {
                int a = random(2, n - 1);
                if (powByMod(a, (n - 1) / prime, n) != 1) {
                    continue;
                } else {
                    millerCheckScnd = true;
                }
            }
            if (millerCheckFrst && millerCheckScnd) {
                return true;
            }
            return false;
        }
        return false;
    }

    // Генерация простого числа p в соответствии с ГОСТ Р 34.10-94
    static boolean gost(int t, int q1, int[] p) {
        p[0] = 0;
        while (true) {
            int N1 = (int) Math.ceil(Math.pow(2, t - 1) / q1);
            int N2 = (int) Math.ceil(Math.pow(2, t - 1) * 0 / q1);

            double N = N1 + N2;
            if ((int) Math.round(N) % 2 != 0) {
                N++;
            }

            for (int u = 0; Math.pow(2, t) >= (N + u) * q1 + 1; u += 2) {
                p[0] = (int) ((N + u) * q1 + 1);
                if ((powByMod(2, (int)(p[0] - 1), p[0]) == 1) && (powByMod(2, (int)(N + u), p[0]) != 1)) {
                    return true;
                }
            }
        }
    }

    // Функция для реализации теста Поклингтона
    static void poklingtonRealize(int n, int[] F, int[] R, List<Integer> primeMn) {
        for (int i = primeMn.size() - 1; i >= 0; i--) {
            if (F[0] <= Math.sqrt(n) - 1) {
                F[0] *= primeMn.get(i);
            } else {
                R[0] *= primeMn.get(i);
            }
        }
    }

    // Тест простоты Поклингтона
    static boolean poklington(int n, int F, int R, List<Integer> primeMnUnic, int t) {
        if (n == 2 || n == 5) {
            return true;
        }
        if (n < 2 || n % 2 == 0) {
            return false;
        }
        boolean poklingCheckFrst = false;
        boolean poklingCheckScnd = false;
        for (int j = 0; j < t; j++) {
            int a = random(2, n - 1);
            if (powByMod(a, n - 1, n) != 1) {
                poklingCheckFrst = false;
            } else {
                poklingCheckScnd = true;
            }
            for (int prime : primeMnUnic) {
                if (powByMod(a, (n - 1) / prime, n) == 1) {
                    poklingCheckScnd = false;
                    break;
                } else {
                    poklingCheckScnd = true;
                }
            }
            if (poklingCheckFrst && poklingCheckScnd) {
                return true;
            }
        }
        return false;
    }

    // Верификация теста простоты
    static boolean verTest(int n, int t, int R, int F) {
        if (nod(R, F) == 1) {
            double verMiller = ((double) eiler(n - 1) / (n - 1)) * t;
            double verPoklington = ((double) eiler(n) / n) * t;
            double result = 1 - verMiller - verPoklington;
            return (result <= 0.1);
        } else {
            double verMiller = ((double) eiler(n - 1) / (n - 1)) * t;
        }
        return false;
    }

    // Функция ввода результатов теста
    static void input(int n, boolean verTest, int k) {
        if (verTest && k <= 6) {
            System.out.println(n + "\t\t" + "+" + "\t\t" + k);
        } else {
            System.out.println(n + "\t\t" + "-" + "\t\t" + k);
        }
    }

    public static void main(String[] args) {
        List<Integer> primeNumbs = new ArrayList<>();
        eratosphen(primeNumbs);
        int t = 5;
        int t1 = 6;
        int q1 = 5;
        int k = 0;
        System.out.println("Число | Результат проверки| Количество отвергнутых чисел");
        System.out.println("______________________________");
        for (int i = 0; i < 10; i++) {
            List<Integer> primeMnUnic = new ArrayList<>();
            List<Integer> primeMn = new ArrayList<>();
            int n = random(2, 500 - 2);
            int n1 = n - 1;
            int m = 1;
            m = millerRealize(m, n1, primeNumbs, primeMnUnic, primeMn);
            int[] F = {1};
            int[] R = {1};
            poklingtonRealize(n, F, R, primeMn);

            if (m * 2 + 1 != n || F[0] * R[0] + 1 != n || !miller(n, primeMnUnic, t) || !poklington(n, F[0], R[0], primeMnUnic, t)) {
                k++;
                i--;
                continue;
            }
            input(n, verTest(n, t, R[0], F[0]), k);

            if (miller(n, primeMnUnic, t) && poklington(n, F[0], R[0], primeMnUnic, t)) {
                k = 0;
            }
        }
        int[] p = {0};
        boolean gostResult = gost(t1, q1, p);
        System.out.println("Значение переменной теста госта: " + p[0]);
    }
}
