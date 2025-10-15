import java.util.Scanner;

public class Impares {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int numero = input.nextInt();


        int produtoImpares = 1;
        boolean temimpar = false;

        while (numero > 0) {
            int digito = numero % 10;
            if (digito % 2 != 0) {
                produtoImpares *= digito;
                temimpar = true;
            }
            numero /= 10;
        }

        if (temimpar) {
            System.out.println(produtoImpares);
        } else {
            System.out.println("no odd digits");
        }

        input.close();
    }
}


