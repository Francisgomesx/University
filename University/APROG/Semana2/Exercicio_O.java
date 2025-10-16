import java.util.Scanner;

public class Exercicio_O {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        String dislikedStr = input.next();    // ingredientes que o cliente não gosta (ex: "59")
        boolean[] disliked = new boolean[10];
        for (int i = 0; i < dislikedStr.length(); i++) {
            char c = dislikedStr.charAt(i);
            if (c >= '0' && c <= '9') {
                disliked[c - '0'] = true;
            }
        }

        int N = input.nextInt();
        int suggestionIndex = 1;

        for (int i = 0; i < N; i++) {
            String pizza = input.next(); // ler como string para verificar dígitos e preservar formato
            boolean ok = true;
            for (int j = 0; j < pizza.length(); j++) {
                char c = pizza.charAt(j);
                if (c >= '0' && c <= '9') {
                    if (disliked[c - '0']) {
                        ok = false;
                        break;
                    }
                }
            }
            if (ok) {
                System.out.println("Suggestion #" + suggestionIndex + ":" + pizza);
                suggestionIndex++;
            }
        }

        input.close();
    }
}
