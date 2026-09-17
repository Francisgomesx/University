package Semana7.src.main.java;

import Utiliarios.src.main.java.utiliarios.Data;
import Utiliarios.src.main.java.utiliarios.Tempo;

import java.util.ArrayList;

public class MainEmpregado {
    static void main() {
        Data hoje = new Data(16, 4, 2026);
        System.out.println(hoje);

        Tempo entrada = new Tempo(9, 0);
        Tempo saida = new Tempo(17, 0);

        System.out.println(entrada);
        System.out.println(saida);

        Empregado e1 = new Empregado("Ana", "Silva", hoje, entrada, saida);
        Empregado e2 = new Empregado("Joao", "Costa", hoje, entrada, saida);


        System.out.println(e1.getDataContrato() == e2.getDataContrato());

        System.out.println(e1.getHoraEntrada() == e2.getHoraEntrada());
        System.out.println(e1.getHoraSaida() == e2.getHoraSaida());

        hoje.setAno(2030);
        entrada.setHora(10);
        saida.setHora(18);

        System.out.println(hoje);
        System.out.println(entrada);
        System.out.println(saida);

        System.out.println(e1);
        System.out.println(e2);

        e2 = new Empregado("Joao", "Costa",
                new Data(1,1,2020),
                new Tempo(8,0),
                new Tempo(16,0));

        ArrayList<Empregado> lista = new ArrayList<>();

        for (Empregado e : lista) {
            System.out.println(e);
        }

        for (Empregado e : lista) {
            System.out.println(
                    e.getPrimeiroNome() + " " +
                            e.getUltimoNome() + " | Horas: " +
                            e.calcularHorasSemana() + " | Antiguidade: " +
                            e.calcularAntiguidade()
            );
        }
    }
}



