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
    void testCalcularVencimentoTrabalhadorComissao() {
        TrabalhadorComissao t = new TrabalhadorComissao("Miguel Oliveira", 1300.40f, 4.25f, 2731.50f);
        assertEquals(1416.48875, t.calcularVencimento(), 0.001);
    }
}
