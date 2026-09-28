import java.util.Scanner;

public class Exercicio_L {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int N = input.nextInt();

        int contagem = 0;
        int num = 2;

        while (contagem < N) {
            int sum = 0;

            for (int i = 1; i < num; i++) {
                if (num % i == 0) {
                    sum += i;
                }
            }

            if (sum == num) {
                System.out.println(num);
                contagem++;
            }

            num++;
        }

        input.close();
    }
}
