import java.util.Scanner;

public class Exercicio_M {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int N = input.nextInt();

        if (N >= 1)
            System.out.println(0);
        if (N >= 2)
            System.out.println(1);

        int anterior = 0;
        int atual = 1;

        for (int i = 3; i <= N; i++) {
            int proximo = anterior + atual;
            System.out.println(proximo);
            anterior = atual;
            atual = proximo;
        }

        input.close();
    }
}
