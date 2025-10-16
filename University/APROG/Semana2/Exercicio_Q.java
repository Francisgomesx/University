import java.util.Scanner;

public class Exercicio_Q {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int finalResult = 0;
        int allDigits = 0; // bitmask global para dígitos 1-9

        while (true) {
            int num = sc.nextInt();
            if (num == 0) break;

            int temp = num;
            int pow = 1;
            while (temp >= 10) { temp /= 10; pow *= 10; }

            int localDigits = 0; // bitmask local para o funcionário
            temp = num;
            while (pow > 0) {
                int digit = temp / pow;
                temp %= pow;
                pow /= 10;

                if (digit != 0) {
                    int mask = 1 << digit;
                    if ((localDigits & mask) == 0) { // evita repetição no mesmo número
                        localDigits |= mask;
                        if ((allDigits & mask) == 0) { // só adiciona se não globalmente presente
                            allDigits |= mask;
                            finalResult = finalResult * 10 + digit;
                        }
                    }
                }
            }
        }

        if (finalResult != 0) {
            System.out.println(finalResult);
        }
        sc.close();
    }
}
