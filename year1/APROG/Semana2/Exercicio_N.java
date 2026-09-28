import java.util.Scanner;

public class Exercicio_N {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int previous = -1;
        boolean keepReading = true;

        while (keepReading) {
            int number = sc.nextInt();

            if (number < 0) {
                keepReading = false;
            } else {
                if (previous != -1 && number > previous && digitsAscending(number)) {
                    System.out.println(number);
                }
                previous = number;
            }
        }

        sc.close();
    }

    public static boolean digitsAscending(int number) {
        String numStr = Integer.toString(number);
        for (int i = 0; i < numStr.length() - 1; i++) {
            if (numStr.charAt(i) >= numStr.charAt(i + 1)) {
                return false;
            }
        }
        return true;
    }
}
