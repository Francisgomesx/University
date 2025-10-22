import java.util.Scanner;

public class Main {

    public static void imprimir(String nome, int aprovados, int total) {
        System.out.println("Subject: " + nome);
        System.out.print("- Approved: ");
        for (int i = 0; i < aprovados; i++) System.out.print("*");
        System.out.println();
        System.out.print("- Failed: ");
        for (int i = 0; i < total - aprovados; i++) System.out.print("*");
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int alunos = sc.nextInt();
        int disciplinas = sc.nextInt();

        for (int i = 0; i < disciplinas; i++) {
            String nome = sc.next();
            int aprovados = sc.nextInt();
            imprimir(nome, aprovados, alunos);
        }

        sc.close();
    }
}
