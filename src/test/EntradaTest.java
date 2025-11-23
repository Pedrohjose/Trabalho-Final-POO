package test;

import org.junit.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import model.*;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

/** Classe de testes unitários para a classe {@link Entrada}.
 *
 * <p>Testa os métodos {@link Entrada#toCSV()} e
 * {@link Entrada#fromCSV(String)} para garantir que a serialização
 * e desserialização para CSV funcionem corretamente.</p>
 *
 * <p>Utiliza JUnit 4 e JUnit 5 para configuração e assertions.</p>
 *
 * @author Carlos*/
public class EntradaTest {
    // Instância de {@link Entrada} utilizada nos testes
    static Entrada entrada;

     /**
     * Construtor Padrao
     */
    public EntradaTest() {}
        //Construtor Padrao

    /**
     * Configura a instância de {@link Entrada} antes de todos os testes.
     */
    @BeforeAll
    static void setup() {
        entrada = new Entrada(1, 1, 2.5);
    }

    /**
     * Testa o método {@link Entrada#toCSV()}.
     *
     * <p>Verifica se a representação CSV está correta com base nos dados da instância.</p>
     */
    @Test
    public void ET_01() {
        LocalDate data = LocalDate.now();
        String retorno = String.format("0;1;2.5;%s;1;ENTRADA", data.toString());

        assertEquals(retorno, entrada.toCSV());
    }

    /**
     * Testa o método {@link Entrada#fromCSV(String)}.
     *
     * <p>Verifica se a desserialização e serialização subsequente resultam no mesmo CSV.</p>
     */
    @Test
    public void ET_02() {
        String dado = String.format("0;1;2.5;%s;1;ENTRADA", LocalDate.now().toString());
        Entrada entrada1 = Entrada.fromCSV(dado);

        assertEquals(dado, entrada1.toCSV());
    }
}
