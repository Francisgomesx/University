package org.example;

public class ContadorEletricidadeSimples extends ContadorEletricidade {
    private double potenciaContratada;
    private static final double POTENCIA_DE_REFERENCIA = 6.9;
    private static double custoUnitario1 = 0.13;
    private static double custoUnitario2 = 0.16;


    public ContadorEletricidadeSimples(String cliente, double consumo, double potenciaContratada) {
        super(cliente, consumo);
        this.potenciaContratada = potenciaContratada;
    }

    @Override
    public double calcularConsumo(){
        if(potenciaContratada < POTENCIA_DE_REFERENCIA)
            return custoUnitario1 * getConsumo();
        else
            return custoUnitario2 * getConsumo();
    }
}
