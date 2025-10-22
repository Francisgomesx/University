import java.util.Scanner;

public class Main {

    public static int contarAlgarismos(int num){
        if (num == 0) return 1;
        int cont = 1;
        int n = Math.abs(num);
        while (n > 0) {
            cont++;
            n = n / 10;
        }
        return cont;
    }
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        final int K = 5;
        int N = input.nextInt();

        int soma = 0;
        int cont = 0;

        while (cont < K) {
            int num = input.nextInt();

            if (contarAlgarismos(num) > N) {
                System.out.print(0.00);
            }
            soma += num;
            cont++;
        }

        if (cont > 0){
            double media = (double) soma / cont;
            System.out.printf("%.2f%n", media);
        }

        input.close();
    }
}
