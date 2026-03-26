package org.example;

public class ContadorEletricidadeBihorario extends ContadorEletricidade{
    private double consumo2;
    private static double custoVazio = 0.066;
    private static double custoForaVazio = 0.14;

    public ContadorEletricidadeBihorario(String cliente, double consumo, double consumo2) {
        super(cliente, consumo)
        this.consumo2 = consumo2;
    }

    public double getConsumo2() {
        return consumo2;
    }

    public void setConsumo2(double consumo2) {
        this.consumo2 = consumo2;
    }

    public static double getCustoVazio() {
        return custoVazio;
    }

    public static void setCustoVazio(double custoVazio) {
        ContadorEletricidadeBihorario.custoVazio = custoVazio;
    }

    public static double getCustoForaVazio() {
        return custoForaVazio;
    }

    public static void setCustoForaVazio(double custoForaVazio) {
        ContadorEletricidadeBihorario.custoForaVazio = custoForaVazio;
    }

    @Override
    public double calcularConsumo(){
        return custoVazio * getConsumo2() + custoForaVazio * getConsumo();
    }
}
