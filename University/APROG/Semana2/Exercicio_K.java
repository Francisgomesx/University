import java.util.Scanner;

public class Exercicio_K {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int N = input.nextInt();

        // Se N for negativo, termina imediatamente
        if (N < 0) {
            input.close();
            return;
        }

        double maiorPercentagem = 0.0;

        // Ler e processar N números
        for (int i = 0; i < N; i++) {
            int numero = input.nextInt();
            String numeroStr = Integer.toString(numero);

            int totalDigitos = numeroStr.length();
            int divisores = 0;

            // Verificar cada dígito
            for (int j = 0; j < numeroStr.length(); j++) {
                int digito = numeroStr.charAt(j) - '0';
                if (digito != 0 && numero % digito == 0) {
                    divisores++;
                }
            }

            // Calcular percentagem
            double percentagem = ((double) divisores / totalDigitos) * 100;

            // Atualizar maior percentagem
            if (percentagem > maiorPercentagem) {
                maiorPercentagem = percentagem;
            }

            // Mostrar percentagem com duas casas decimais e símbolo %
            System.out.printf("%.2f%%%n", percentagem);
        }

        // Mostrar maior percentagem entre parêntesis com %
        System.out.printf("(%.2f%%)%n", maiorPercentagem);

        input.close();
    }
}
