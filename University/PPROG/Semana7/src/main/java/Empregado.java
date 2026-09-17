package Semana7.src.main.java;

import Utiliarios.src.main.java.utiliarios.Data;
import Utiliarios.src.main.java.utiliarios.Tempo;

public class Empregado {

    private final String primeiroNome;
    private final String ultimoNome;
    private final Data dataContrato;
    private Tempo horaEntrada;
    private final Tempo horaSaida;

    public Empregado(String ultimoNome, String primeiroNome, Data dataContrato, Tempo horaEntrada, Tempo horaSaida) {
        this.ultimoNome = ultimoNome;
        this.primeiroNome = primeiroNome;
        this.dataContrato = dataContrato;
        this.horaEntrada = horaEntrada;
        this.horaSaida = horaSaida;
    }

    public void setHoraEntrada(Tempo horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public String getPrimeiroNome() {
        return primeiroNome;
    }

    public String getUltimoNome() {
        return ultimoNome;
    }

    public Tempo getHoraEntrada() {
        return horaEntrada;
    }

    public Tempo getHoraSaida() {
        return horaSaida;
    }

    public Data getDataContrato() {
        return dataContrato;
    }

    @Override
    public String toString() {
        return primeiroNome + " " + ultimoNome;
    }


}
