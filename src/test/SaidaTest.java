package test;

import model.Saida;
import model.TipoSaida;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class SaidaTest {

    static Saida saida;

    @BeforeAll
    static void setup() {
        saida = new Saida(0,10,150.75, TipoSaida.VENDA_CLIENTE);
    }

    @Test
    void toCSV() {
        String retorno = String.format("0;0;150.75;%s;10;VENDA_CLIENTE", LocalDate.now());

        assertEquals(retorno, saida.toCSV());
    }

    @Test
    void fromCSV() {
        String dado = String.format("0;0;150.75;%s;10;VENDA_CLIENTE", LocalDate.now());
        Saida saida1 = Saida.fromCSV(dado);

        assertEquals(dado, saida1.toCSV());
    }

    @Test
    void testEquals() {
        Saida saida1 = new Saida(0, 10, 150.75,TipoSaida.VENDA_CLIENTE);
        Saida saida2 = new Saida(0, 10, 150.75,TipoSaida.VENDA_CLIENTE);
        Saida saida3 = new Saida(10, 100, 1500.75,TipoSaida.VENDA_CLIENTE);

        assertTrue(saida1.equals(saida1));
        assertFalse(saida2.equals(saida3));
    }
}