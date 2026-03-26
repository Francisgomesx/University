package main.java.vencimentos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainVencimentos {

    public static void main(String[] args) {

        TrabalhadorComissao tc = new TrabalhadorComissao("Susana Ferreira", 500.0f, 1500.0f, 6f);
        TrabalhadorPeca tp = new TrabalhadorPeca("Jorge Silva", 20, 30);
        TrabalhadorHora th = new TrabalhadorHora("Carlos Miguel", 160, 3.5f);

        List<Trabalhador> trabs = new ArrayList<>();
        trabs.add(tc);
        trabs.add(tp);
        trabs.add(th);

        System.out.println("### Trabalhadores (ordem original) ###");
        listar(trabs);

        System.out.println("\n### Trabalhadores por vencimento crescente ###");
        Collections.sort(trabs);
        listar(trabs);

        System.out.println("\n### Trabalhadores por vencimento decrescente ###");
        Collections.sort(trabs, Collections.reverseOrder());
        listar(trabs);
    }

    private static void listar(List<Trabalhador> trabs) {
        for (Trabalhador trab : trabs) {
            if (trab != null) {
                System.out.printf("%s, vencimento = %.2f€%n",
                        trab.getNome(),
                        trab.calcularVencimento());
            }
        }
    }
}