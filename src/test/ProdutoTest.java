package test;

import model.CategoriasProdutos;
import model.Produto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoTest {

    static Produto produto;

    @BeforeAll
    static void setup() {
        produto = new Produto("Pc Gamer Pichau", 10.50, CategoriasProdutos.HARDWARE);
    }

    @Test
    void testToCSV() {
        String retorno = String.format("0;Pc Gamer Pichau;%s;0;10.5;HARDWARE", LocalDate.now());

        assertEquals(retorno, produto.toCSV());
    }

    @Test
    void testFromCSV() {
        String data = String.format("0;Pc Gamer Pichau;%s;0;10.5;HARDWARE", LocalDate.now());
        Produto produto1 = Produto.fromCSV(data);

        assertEquals(data, produto1.toCSV());
    }

    @Test
    void testEquals() {
        Produto produto1 = new Produto("Teclado Gamer",100,CategoriasProdutos.PERIFERICOS);
        Produto produto2 = new Produto("Teclado Gamer",100,CategoriasProdutos.PERIFERICOS);
        Produto produto3 = new Produto("Fone Gamer",50,CategoriasProdutos.PERIFERICOS);

        assertTrue(produto1.equals(produto1));
        assertFalse(produto2.equals(produto3));
    }
}