import java.util.Scanner;

public class Main {

    public static int contarAlgarismos(int num) {
        if (num == 0) return 1;
        int cont = 0;
        int n = Math.abs(num);
        while (n > 0) {
            cont++;
            n /= 10;
        }
        return cont;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        final int K = 5;
        if (!sc.hasNextInt()) {
            sc.close();
            return;
        }

        int N = sc.nextInt();
        int soma = 0;
        int cont = 0;

        while (cont < K && sc.hasNextInt()) {
            int num = sc.nextInt();
            if (contarAlgarismos(num) >= N) break;
            soma += num;
            cont++;
        }

        double media = 0;
        if (cont > 0) media = (double) soma / cont;
        System.out.printf("%.2f%n", media);

        sc.close();
    }
}
