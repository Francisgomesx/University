public class Main {
    public static void main(String[] args) {
        System.out.println(reverse("marco"));

    }
    public static String reverse(String word){
            if (word.isEmpty())
                return word;
            char letra = word.charAt(0);
            String resto = word.substring(1);
            return reverse(resto) + letra;
    }
    public static int product(int m, int n){
        if(n == 0)
            return 0;
        if(n > 0)
            return product(m, n) = m + n;
    }
}
