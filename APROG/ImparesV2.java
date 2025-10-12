import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        while(true){
            int numero = input.nextInt();
            if(numero<= 0){
                break;
            }
            int produtoImpares = 1;
            boolean temImpar = false;
            int n = numero;

            while(n > 0){
                int digito = n % 10;
                if (digito % 2 != 0){
                    produtoImpares *= digito;
                    temImpar = true;
                }
                n /= 10;
            }
            if (temImpar){
                System.out.println(produtoImpares);
            } else {
                System.out.println("no odd digits");
            }
        }
        input.close();
    }
}
