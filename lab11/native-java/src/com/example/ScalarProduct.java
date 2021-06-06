package com.example;

public class ScalarProduct {

    public Double[] a;
    public Double[] b;
    public Double c;

    // zakładamy, że po stronie kodu natywnego wyliczony zostanie iloczyn skalarny dwóch wektorów
    public native Double multi01(Double[] a, Double[] b);

    // zakładamy, że drugi atrybut będzie pobrany z obiektu przekazanego do metody natywnej
    public native Double multi02(Double[] a);

    // zakładamy, że po stronie natywnej utworzone zostanie okienko na atrybuty,
    // a po ich wczytaniu i przepisaniu do a,b obliczony zostanie wynik.
    // Wynik powinna wyliczać metoda Javy multi04
    // (korzystająca z parametrów a,b i wpisująca wynik do c).
    public native void multi03();

    // mnoży a i b, wynik wpisuje do c
    public void multi04(){
        double result = 0.0;

        for (int i = 0; i < a.length; i++)
            result += a[i] * b[i];

        c = result;
    }

}
