#include <iostream>
#include <cmath>
#include <vector>
#include <random>
#include <ctime>

using namespace std;

// Функция для генерации случайного числа в заданном диапазоне
int Random(int min, int max)
{
    return rand() % (max - min + 1) + min;
}

// Функция для возведения числа в степень по модулю
int PowByMod(int a, int x, int p)
{
    int result = 1;
    a = a % p;
    while (x > 0)
    {
        if (x % 2 == 1)
        {
            result = (result * a) % p;
        }
        a = (a * a) % p;
        x /= 2;
    }
    return result;
}

// Алгоритм решета Эратосфена для поиска простых чисел до 500
void Eratosphen(vector<int>& PrimeNumbs)
{
    for (int i = 2; i <= 500; i++)
    {
        PrimeNumbs.push_back(i);
    }
    for (int i = 0; i <= sqrt(PrimeNumbs.size()); i++)
    {
        int j = i + 1;
        while (j < PrimeNumbs.size())
        {
            if (PrimeNumbs[j] % PrimeNumbs[i] == 0)
            {
                for (int k = j; k < PrimeNumbs.size() - 1; k++)
                {
                    PrimeNumbs[k] = PrimeNumbs[k++];
                }
                PrimeNumbs.pop_back();
            }
            else {
                j++;
            }
        }
    }
}

// Функция для нахождения наибольшего общего делителя двух чисел
int NOD(int a, int b)
{
    if (b == 0)
    {
        return a;
    }
    return NOD(b, a % b);
}

// Функция Эйлера для вычисления функции Эйлера от простого числа
int Eiler(int p)
{
    int result = p;
    for (int i = 2; i * i <= p; i++)
    {
        if (p % i == 0)
        {
            while (p % i == 0)
            {
                p /= i;
            }
            result -= result / i;
        }
    }
    if (p > 1)
    {
        result -= result / p;
    }
    return result;
}

// Функция для нахождения уникальных простых множителей и вычисления m/2 для теста Миллера
int MillerRealize(int m, int n1, const vector<int>& PrimeNumbs, vector<int>& PrimeMnUnic, vector<int>& PrimeMn)
{
    for (size_t i = 0; i < PrimeNumbs.size(); i++)
    {
        int degree = 0;
        if (n1 % PrimeNumbs[i] == 0)
        {
            while (n1 % PrimeNumbs[i] == 0)
            {
                n1 /= PrimeNumbs[i];
                degree += 1;
                PrimeMn.push_back(PrimeNumbs[i]);
            }
            PrimeMnUnic.push_back(PrimeNumbs[i]);
            m *= pow(PrimeNumbs[i], degree);
        }
    }
    return m / 2;
}

// Тест простоты Миллера-Рабина
bool Miller(int n, const vector<int>& PrimeMnUnic, int t)
{
    if (n == 2)
    {
        return true;
    }
    if (n < 2 || n % 2 == 0)
    {
        return false;
    }
    bool MillerCheckFrst = false;
    bool MillerCheckScnd = false;
    for (int j = 0; j < t; j++)
    {
        int a = Random(2, n - 1);
        if (PowByMod(a, n - 1, n) != 1)
        {
            MillerCheckFrst = false;
        }
        else
        {
            MillerCheckFrst = true;
        }
    }
    for (size_t i = 0; i < PrimeMnUnic.size(); i++)
    {
        for (int j = 0; j < t; j++)
        {
            int a = Random(2, n - 1);
            if (PowByMod(a, (n - 1) / PrimeMnUnic[i], n) != 1)
            {
                continue;
            }
            else
            {
                MillerCheckScnd = true;
            }
        }
        if (MillerCheckFrst == true && MillerCheckScnd == true)
        {
            return true;
        }
        return false;
    }
}

// Генерация простого числа p в соответствии с ГОСТ Р 34.10-94
bool GOST(int t, int q1, int& p)
{
    p = 0;

    while (true)
    {
        int N1 = ceil(pow(2, t - 1) / q1);
        int N2 = ceil(pow(2, t - 1) * 0 / (q1));

        double N = N1 + N2;
        if (static_cast<int>(round(N)) % 2 != 0)
        {
            N++;
        }

        for (int u = 0; pow(2, t) >= (N + u) * q1 + 1; u += 2)
        {
            p = (N + u) * q1 + 1;
            if ((PowByMod(2, p - 1, p) == 1) && (PowByMod(2, N + u, p) != 1))
            {
                return true;
            }
        }
    }
    return false;
}

// Функция для реализации теста Поклингтона
void PoklingtonRealize(int n, int& F, int& R, vector<int>& PrimeMn)
{
    for (size_t i = PrimeMn.size() - 1; i + 1 > 0; i--)
    {
        if (F <= sqrt(n) - 1)
        {
            F *= PrimeMn[i];
        }
        else
        {
            R *= PrimeMn[i];
        }
    }
}

// Тест простоты Поклингтона
bool Poklington(int n, int F, int R, const vector<int>& PrimeMnUnic, int t)
{
    if (n == 2 || n == 5)
    {
        return true;
    }
    if (n < 2 || n % 2 == 0)
    {
        return false;
    }
    bool PoklingCheckFrst = false;
    bool PoklingCheckScnd = false;
    for (int j = 0; j < t; j++)
    {
        int a = Random(2, n - 1);
        if (PowByMod(a, n - 1, n) != 1)
        {
            PoklingCheckFrst = false;
        }
        else
        {
            PoklingCheckScnd = true;
        }
        for (size_t i = 0; i < PrimeMnUnic.size(); i++)
        {
            if (PowByMod(a, (n - 1) / PrimeMnUnic[i], n) == 1)
            {
                PoklingCheckScnd = false;
                break;
            }
            else
            {
                PoklingCheckScnd = true;
            }
        }
        if (PoklingCheckFrst == true && PoklingCheckScnd == true)
        {
            return true;
        }
    }
    return false;
}

// Верификация теста простоты
bool VerTest(int n, int t, int R, int F)
{
    if (NOD(R, F) == 1)
    {
        double verMiller = (static_cast<double>(Eiler(n - 1)) / static_cast<double>(n - 1)) * t;
        double verPoklington = (static_cast<double>(Eiler(n)) / static_cast<double>(n)) * t;
        double result = 1 - verMiller - verPoklington;
        return (result <= 0.1);
    }
    else
    {
        double verMiller = (static_cast<double>(Eiler(n - 1)) / static_cast<double>(n - 1)) * t;
    }
}

// Функция ввода результатов теста
void Input(int n, bool VerTest, int k)
{
    if (VerTest && k <= 6)
    {
        cout << n << " \t\t" << "+" << " \t\t" << k << endl;
    }
    else
    {
        cout << n << " \t\t" << "-" << " \t\t" << k << endl;
    }
}

int main()
{
    srand(time(0));

    vector<int> PrimeNumbs;
    Eratosphen(PrimeNumbs);
    int t = 5;
    int t1 = 6;
    int q1 = 5;
    int k = 0;
    cout << "Число | Результат проверки| Количество отвергнутых чисел" << endl;
    cout << "______________________________" << endl;
    for (int i = 0; i < 10; i++)
    {
        vector<int> PrimeMnUnic;
        vector<int> PrimeMn;
        int n = Random(2, 500 - 2);
        int n1 = n - 1;
        int m = 1;
        m = MillerRealize(m, n1, PrimeNumbs, PrimeMnUnic, PrimeMn);
        int F = 1;
        int R = 1;
        PoklingtonRealize(n, F, R, PrimeMn);

        if (m * 2 + 1 != n || F * R + 1 != n || !Miller(n, PrimeMnUnic, t) || !Poklington(n, F, R, PrimeMnUnic, t))
        {
            k++;
            i--;
            continue;
        }
        Input(n, VerTest(n, t, R, F), k);

        if (Miller(n, PrimeMnUnic, t) && Poklington(n, F, R, PrimeMnUnic, t))
        {
            k = 0;
        }
    }
    int p;
    bool GOSTResult = GOST(t1, q1, p);
    cout << "Значение переменной теста госта: ";
    cout << p << endl;
}
