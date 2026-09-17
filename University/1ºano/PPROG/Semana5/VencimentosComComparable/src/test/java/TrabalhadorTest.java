package test.java;

import main.java.vencimentos.TrabalhadorComissao;
import main.java.vencimentos.TrabalhadorHora;
import main.java.vencimentos.TrabalhadorPeca;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TrabalhadorTest {

    @Test
    public void testCalcularVencimentoTrabalhadorHora() {
        TrabalhadorHora t = new TrabalhadorHora("Carlos Miguel", 168, 4.5f);
        assertEquals(756.00, t.calcularVencimento(), 0.001);
    }

    @Test
    public void testCalcularVencimentoTrabalhadorPeca() {
        TrabalhadorPeca t = new TrabalhadorPeca("João Matos", 24, 36);
        assertEquals(1092, t.calcularVencimento(), 0.001);
    }

    @Test
    public void testCalcularVencimentoTrabalhadorComissao() {
        TrabalhadorComissao t = new TrabalhadorComissao("Miguel Oliveira", 1300.40f, 4.25f, 2731.50f);
        assertEquals(1416.48875, t.calcularVencimento(), 0.001);
    }

    @Test
    public void testCompareToVencimentoMenor() {
        TrabalhadorHora t1 = new TrabalhadorHora("Carlos", 100, 4.50f);
        TrabalhadorHora t2 = new TrabalhadorHora("Ana", 100, 10.0f);
        assertTrue(t1.compareTo(t2) < 0);
    }

    @Test
    public void testCompareToVencimentoMaior() {
        TrabalhadorHora t1 = new TrabalhadorHora("Carlos", 100, 10.0f);   // 1000
        TrabalhadorHora t2 = new TrabalhadorHora("Ana", 100, 4.5f);       // 450
        assertTrue(t1.compareTo(t2) > 0);
    }

    @Test
    public void testCompareToVencimentoIgual() {
        TrabalhadorHora t1 = new TrabalhadorHora("Carlos", 100, 5.0f);    // 500
        TrabalhadorHora t2 = new TrabalhadorHora("Ana", 50, 10.0f);       // 500
        assertEquals(0, t1.compareTo(t2));
    }

}
