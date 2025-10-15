import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);


        while (true) {
            int num = input.nextInt();
            if (num < 0) {
                input.close();
                return;
            }

            System.out.println(num);

        }
    }
}
