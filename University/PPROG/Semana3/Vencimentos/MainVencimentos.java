public class MainVencimentos {

    public static void main(String[] args) {

        TrabalhadorPeca t1 = new TrabalhadorPeca("Jorge Silva", 53, 62);
        TrabalhadorComissao t2 = new TrabalhadorComissao("Susana Ferreira", 650F, 2731.50F,4.25F);
        TrabalhadorHora t3 = new TrabalhadorHora("Carlos Miguel", 180, 4.50F);

        Trabalhador[] contentor = new Trabalhador[10];

        contentor[0] = t1;
        contentor[1] = t2;
        contentor[2] = t3;

        System.out.println("=== LISTAGEM DE TODOS OS TRABALHADORES ===");
        for (int i = 0; i < contentor.length; i++) {
            if (contentor[i] != null) {
                System.out.println(contentor[i]);
            }
        }

        System.out.println("=== LISTAGEM DE TRABALHADORES À HORA ===");
        for (int i = 0; i < contentor.length; i++) {
            if (contentor[i] != null && contentor[i] instanceof TrabalhadorHora) {
                System.out.println(contentor[i]);
            }
        }

        System.out.println("=== NOMES E VENCIMENTOS ===");
        for (int i = 0; i < contentor.length; i++) {
            if (contentor[i] != null) {
                System.out.printf("%s - %.2f€%n", contentor[i].getNome(), contentor[i].calcularVencimento());
            }
        }


    }
}
