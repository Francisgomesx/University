package org.example;

public abstract class Contador {

    private String id;
    private String cliente;
    private double consumo;

    public Contador(String id, String cliente, double consumo) {
        this.id = id;
        this.cliente = cliente;
        this.consumo = consumo;
    }

    public String getId() {
        return id;
    }

    public String getCliente() {
        return cliente;
    }

    public double getConsumo() {
        return consumo;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public void setConsumo(double consumo) {
        this.consumo = consumo;
    }

    @Override
    public String toString() {
        return "Contador{" +
                "id=" + id +
                ", cliente='" + cliente + '\'' +
                ", consumo=" + consumo +
                '}';
    }

    public abstract double calcularConsumo();
}