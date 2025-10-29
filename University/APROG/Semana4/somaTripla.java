import java.util.Scanner;

public class somaTripla {

    public static int calcularTriplas(int N) {
        int count = 0;

        for (int a = N - 2; a >= 1; a--) {
            for (int b = a; b >= 1; b--) {
                for (int c = b; c >= 1; c--) {
                    if (a + b + c == N) {
                        System.out.println(a + " + " + b + " + " + c);
                        count++;
                    }
                }
            }
        }

        return count;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int N = input.nextInt();

        int total = calcularTriplas(N);
        System.out.println("triples=" + total);

        input.close();
    }
}
