import org.junit.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import model.*;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class EntradaTest {
    static Entrada entrada;

    @BeforeAll
    static void setup() {
        entrada = new Entrada(1, 1, 2.5);
    }

    @Test
    public void testToCSV() {
        LocalDate data = LocalDate.now();
        String retorno = String.format("0;1;2.5;%s;1;ENTRADA", data.toString());

        assertEquals(retorno, entrada.toCSV());
    }

    @Test
    public void testFromCSV() {
        String dado = String.format("0;1;2.5;%s;1;ENTRADA", LocalDate.now().toString());
        Entrada entrada1 = Entrada.fromCSV(dado);

        assertEquals(dado, entrada1.toCSV());
    }
}
