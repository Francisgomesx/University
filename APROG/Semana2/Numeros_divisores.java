import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int N = input.nextInt();

        if (N < 0) {
            input.close();
            return;
        }

        double maiorPercentagem = 0.0;

        for (int i = 0; i < N; i++) {
            int numero = input.nextInt();
            String numeroStr = Integer.toString(numero);

            int totalDigitos = numeroStr.length();
            int divisores = 0;

            for (int j = 0; j < numeroStr.length(); j++) {
                int digito = numeroStr.charAt(j) - '0';
                if (digito != 0 && numero % digito == 0) {
                    divisores++;
                }
            }

            double percentagem = ((double) divisores / totalDigitos) * 100;

            if (percentagem > maiorPercentagem) {
                maiorPercentagem = percentagem;
            }

            // Percentagem com 2 casas decimais e símbolo %
            System.out.printf("%.2f%%%n", percentagem);
        }

        // Maior percentagem entre parêntesis, com 2 casas decimais e %
        System.out.printf("(%.2f%%)%n", maiorPercentagem);

        input.close();
    }
}
