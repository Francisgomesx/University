import java.util.Scanner;

public class Exercicio_P {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            int num = sc.nextInt();
            if (num < 0)
                break;

            String s = String.valueOf(num);
            boolean found = false;

            // verificar da direita para a esquerda
            for (int i = s.length() - 1; i >= 0 && !found; i--) {
                char d1 = s.charAt(i);
                int pos1 = s.length() - i; // posição da direita para a esquerda

                for (int j = i - 1; j >= 0; j--) {
                    if (s.charAt(j) == d1) {
                        int pos2 = s.length() - j;
                        System.out.println(num + " : digit (" + d1 + ") repeated in positions (" + pos1 + ") and (" + pos2 + ")");
                        found = true;
                        break;
                    }
                }
            }
        }

        sc.close();
    }
}
