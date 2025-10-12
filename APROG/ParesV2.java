import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        while (true) {
            if (!input.hasNextInt()) break; // segurança extra
            int numero = input.nextInt();
            if (numero <= 0) break;

            int somaPares = 0;
            int n = numero;

            while (n > 0) {
                int digito = n % 10;
                if (digito % 2 == 0) {
                    somaPares += digito;
                }
                n /= 10;
            }

            System.out.println(somaPares);
        }

        input.close();
    }
}
