package org.example;
import java.util.Scanner;

public class Divisores {
    public static void main(String[] arg){
        Scanner input = new Scanner(System.in);
        int numero = input.nextInt();
        int contador = 0;

        for (int i = 1; i <= numero; i++){
            if (numero % i == 0){
                System.out.println(i);
                contador++;
            }
        }

        System.out.println("(" + contador + ")");
        input.close();
    }

}


