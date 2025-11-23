package test;

import model.CategoriasProdutos;
import model.Produto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de testes unitários para a classe {@link Produto}.
 *
 * <p>Testa os métodos {@link Produto#toCSV()}, {@link Produto#fromCSV(String)}
 * e {@link Produto#equals(Object)} para garantir que a serialização,
 * desserialização e comparação de produtos funcionem corretamente.</p>
 *
 * <p>Utiliza JUnit 5 para configuração e assertions.</p>
 *
 * @author Carlos
 */
class ProdutoTest {

    /** Instância de {@link Produto} utilizada nos testes */
    static Produto produto;

    /**
     * Configura a instância de {@link Produto} antes de todos os testes.
     */
    @BeforeAll
    static void setup() {
        produto = new Produto("Pc Gamer Pichau", 10.50, CategoriasProdutos.HARDWARE);
    }

    /**
     * Testa o método {@link Produto#toCSV()}.
     *
     * <p>Verifica se a representação CSV do produto está correta.</p>
     */
    @Test
    void PT_01() {
        String retorno = String.format("0;Pc Gamer Pichau;%s;0;10.5;HARDWARE", LocalDate.now());

        assertEquals(retorno, produto.toCSV());
    }

    /**
     * Testa o método {@link Produto#fromCSV(String)}.
     *
     * <p>Verifica se a desserialização de CSV e a serialização subsequente
     * retornam o mesmo valor esperado.</p>
     */
    @Test
    void PT_02() {
        String data = String.format("0;Pc Gamer Pichau;%s;0;10.5;HARDWARE", LocalDate.now());
        Produto produto1 = Produto.fromCSV(data);

        assertEquals(data, produto1.toCSV());
    }

    /**
     * Testa o método {@link Produto#equals(Object)}.
     *
     * <p>Verifica se dois produtos com o mesmo código são considerados iguais
     * e se produtos diferentes não são iguais.</p>
     */
    @Test
    void PT_03() {
        Produto produto1 = new Produto("Teclado Gamer", 100, CategoriasProdutos.PERIFERICOS);
        Produto produto2 = new Produto("Teclado Gamer", 100, CategoriasProdutos.PERIFERICOS);
        Produto produto3 = new Produto("Fone Gamer", 50, CategoriasProdutos.PERIFERICOS);

        assertTrue(produto1.equals(produto1));
        assertFalse(produto2.equals(produto3));
    }
}
