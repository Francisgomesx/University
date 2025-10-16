import java.util.Scanner;

public class Exercicio_N {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int previous = -1; // no previous number initially
        boolean keepReading = true;

        while (keepReading) {
            int number = sc.nextInt();

            if (number < 0) { // stop reading on negative number
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

    // function to check if digits of a number are in ascending order
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