package org.example;

import java.util.Scanner;

public class MinimoMultiploComum {
    public static int mdc(int a, int b) {
        while (b != 0) {
            int resto =  a % b;
            a = b;
            b = resto;
        }
        return a;
    }


    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int a = input.nextInt();
        int b = input.nextInt();

        int mmc = (a * b) / mdc(a, b);


        System.out.println(mmc);
        input.close();
    }
}
