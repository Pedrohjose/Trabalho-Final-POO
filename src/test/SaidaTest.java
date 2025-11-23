package test;

import model.Saida;
import model.TipoSaida;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de testes unitários para a classe {@link Saida}.
 *
 * <p>Testa os métodos {@link Saida#toCSV()}, {@link Saida#fromCSV(String)}
 * e {@link Saida#equals(Object)} para garantir que a serialização,
 * desserialização e comparação de saídas funcionem corretamente.</p>
 *
 * <p>Utiliza JUnit 5 para configuração e assertions.</p>
 *
 * @author Carlos
 */
class SaidaTest {

    /** Instância de {@link Saida} utilizada nos testes */
    static Saida saida;

    /**
     * Configura a instância de {@link Saida} antes de todos os testes.
     */
    @BeforeAll
    static void setup() {
        saida = new Saida(0, 10, 150.75, TipoSaida.VENDA_CLIENTE);
    }

    /**
     * Testa o método {@link Saida#toCSV()}.
     *
     * <p>Verifica se a representação CSV da saída está correta.</p>
     */
    @Test
    void ST_01() {
        String retorno = String.format("0;0;150.75;%s;10;VENDA_CLIENTE", LocalDate.now());
        assertEquals(retorno, saida.toCSV());
    }

    /**
     * Testa o método {@link Saida#fromCSV(String)}.
     *
     * <p>Verifica se a desserialização de CSV e a serialização subsequente
     * retornam o mesmo valor esperado.</p>
     */
    @Test
    void ST_02() {
        String dado = String.format("0;0;150.75;%s;10;VENDA_CLIENTE", LocalDate.now());
        Saida saida1 = Saida.fromCSV(dado);
        assertEquals(dado, saida1.toCSV());
    }

    /**
     * Testa o método {@link Saida#equals(Object)}.
     *
     * <p>Verifica se duas saídas com os mesmos dados são consideradas iguais
     * e se saídas diferentes não são iguais.</p>
     */
    @Test
    void ST_03() {
        Saida saida1 = new Saida(0, 10, 150.75, TipoSaida.VENDA_CLIENTE);
        Saida saida2 = new Saida(0, 10, 150.75, TipoSaida.VENDA_CLIENTE);
        Saida saida3 = new Saida(10, 100, 1500.75, TipoSaida.VENDA_CLIENTE);

        assertTrue(saida1.equals(saida1));
        assertFalse(saida2.equals(saida3));
    }
}
