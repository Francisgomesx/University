package Tributaveis.src.test;

import Tributaveis.src.main.org.dei.tributaveis.Cores;
import Tributaveis.src.main.org.dei.tributaveis.Moradia;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MoradiaTest {

    @Test
    void testMoradiaCreation() {
        Moradia m = new Moradia("Rua do Bocage", 90, Cores.CINZENTO);
        assertNotNull(m, "A moradia não deve ser nula após criação");
    }

    @Test
    void testCalcularImposto() {
        Moradia m = new Moradia("Rua do Bocage", 90, Cores.CINZENTO);
        double imposto = m.calcularImposto();
        assertEquals(180, imposto, "O imposto deve ser 180 para área 90");
    }


}
