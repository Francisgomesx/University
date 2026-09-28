package org.example;

public class ContadorGas extends Contador {

    private static double custoUnitario = 0.8;
    private static int totalContadoresGas = 0;

    public ContadorGas(String cliente, double consumo) {
        super("GAS-" + cliente, consumo);
        totalContadoresGas++;
    }

    public static double getCustoUnitario() {
        return custoUnitario;
    }

    public static void setCustoUnitario(double custoUnitario) {
        ContadorGas.custoUnitario = custoUnitario;
    }

    @Override
    public double calcularConsumo(){
        return custoUnitario*getConsumo();
    }
}
