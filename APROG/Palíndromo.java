import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int numero =  input.nextInt();
        int original = numero;
        int invertido = 0;

        while (numero > 0){
            int digito = numero % 10;
            invertido = invertido * 10 + digito;
            numero /= 10;
        }

        if (invertido == original){
            System.out.println("palindrome");
        } else {
            System.out.println("not palindrome");
        }
        input.close();
    }
}
