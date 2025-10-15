import java.util.Scanner;

public class HexaParaDecimal {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        while(true){
            int octal = input.nextInt();
            if (octal <= 0) break;
            int decimal = 0;
            int multiplicador = 1;
            int n = octal;

            while( n > 0 ) {
                int digito = n % 10;
                decimal += digito * multiplicador;
                multiplicador *= 8;
                n /= 10;
            }
            System.out.println(decimal);
        }
        input.close();
    }
}


