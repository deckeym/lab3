#include <iostream>
#include <cmath>
#include <iomanip>
#include <fstream>

using namespace std;

double FirstFrag(double x)
{
    double y = 1;
    return y;
}

double SecFrag(double x)
{
    double y = 0.5 * x - 2;
    return y;
}

double ThirdFrag(double x)
{
    double y = sqrt(abs(4 - pow(x, 2)));
    if (abs(y) < 0.000001) {
        y = 0.0;
        }
    return y;
}

double FourthFrag(double x)
{   
    double y = -1 * (sqrt(abs(1 - pow(x, 2))));
    if (abs(y) < 0.000001) {
        y = 0.0;
        }
    return y;
}

double FifthFrag(double x)
{
    double y = -1 * x + 2;
    if (abs(y) < 0.000001) {
        y = 0.0;
        }
    return y;
}

int main()
{
    double xstart = -7;
    double xfinish = 3;
    double dx = 0.1;
    ofstream outfile("1Task_15Variant.txt");
    if (!outfile.is_open()) {
        cout << "При открытии файла произошла ошибка" << endl;
        return 1;
    }

    outfile << setw(10) << "X" << setw(10) << "Y" << endl;
    outfile << string (20, '-') << endl;
    for (auto x = xstart; x <= xfinish; x += dx) {
        if (abs(x) < 0.000001) {
        x = 0.0;
        }
        if (x >= -7 && x < -6) {
            outfile << setprecision(3) << setw(10)  << x << setw(10) << FirstFrag(x) << endl;
        }
        if (x >= -6 && x < -4) {
            outfile << setprecision(3) << setw(10) << x << setw(10) << SecFrag(x) << endl;
        }
        if (x >= -4 && x < 0){
            outfile << setprecision(3) << setw(10) << x << setw(10) << ThirdFrag(x) << endl;
        }
         if (x >= 0 && x < 2){
            outfile << setprecision(3) << setw(10) << x << setw(10) << FourthFrag(x) << endl;
        }
         if (x >= 2 && x <= 3){
            outfile << setprecision(3) << setw(10) << x << setw(10) << FifthFrag(x) << endl;
        }
    }
}