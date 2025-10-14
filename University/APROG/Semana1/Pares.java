package org.example;

import java.util.Scanner;

public class Pares {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Introduza um número: ");
        long numero = input.nextLong();

        if (numero < 0) {
            System.out.println("O número deve ser positivo.");
            input.close();
            return;
        }

        long somaPares = 0;
        long digito;

        while (numero > 0) {
            digito = numero % 10;

            if (digito % 2 == 0) {
                somaPares += digito;
            }

            numero /= 10;
        }

        System.out.println(somaPares);

        input.close();
    }
}
