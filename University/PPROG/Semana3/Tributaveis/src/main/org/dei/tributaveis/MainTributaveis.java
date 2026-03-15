package Tributaveis.src.main.org.dei.tributaveis;

public class MainTributaveis {

    public static void imprimirImposto(Tributavel t) {
        System.out.println(t + " -> Imposto: " + t.calcularImposto());
    }

    public static void main(String[] args) {

        Veiculo v1 = new Veiculo("22-33-CC", 1000, "encarnado");
        System.out.println(v1.toString());

        Veiculo v2 = new Veiculo("44-55-DD", 2500, Cores.AZUL);
        System.out.println(v2.toString());

        Veiculo v3 = new Veiculo("11-22-BB", 1400, Veiculo.VERDE);
        System.out.println(v3.toString());

        Moradia c1 = new Moradia("Rua do Bocage", 90, Cores.CINZENTO);
        System.out.println(c1.toString());

        Tributavel[] tributaveis = new Tributavel[4];

        tributaveis[0] = v1;
        tributaveis[1] = v2;
        tributaveis[2] = v3;
        tributaveis[3] = c1;

        double totalImposto = 0;

        for (Tributavel t : tributaveis) {
            imprimirImposto(t);
            totalImposto += t.calcularImposto();
        }
        System.out.println("Total imposto: " + totalImposto);
    }
}
