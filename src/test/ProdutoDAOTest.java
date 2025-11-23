package test;

import dao.ProdutoDAO;
import model.CategoriasProdutos;
import model.Produto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Classe de testes unitários para a classe {@link ProdutoDAO}.
 *
 * <p>Verifica o funcionamento dos principais métodos de manipulação de produtos (CRUD)
 * e métodos auxiliares relacionados ao controle de IDs.</p>
 *
 * <p>Métodos testados:</p>
 * <ul>
 *     <li>{@link ProdutoDAO#ler()}</li>
 *     <li>{@link ProdutoDAO#inserir(Produto)}</li>
 *     <li>{@link ProdutoDAO#atualizar(Produto)}</li>
 *     <li>{@link ProdutoDAO#deletar(Produto)}</li>
 *     <li>{@link ProdutoDAO#ajustarContadorId()}</li>
 * </ul>
 *
 * <p>Utiliza JUnit 5 para execução dos testes.</p>
 *
 * <p><b>Autor:</b> Carlos</p>
 */
class ProdutoDAOTest {

    /** Instância da classe DAO utilizada nos testes. */
    ProdutoDAO produtoDAO = new ProdutoDAO();


    /** Produto base utilizado para os testes. */
    Produto produto = new Produto("Pc Gamer Pichau", 10.50, CategoriasProdutos.HARDWARE);

    /**
     * Testa o método {@link ProdutoDAO#inserir(Produto)} seguido de {@link ProdutoDAO#ler()}.
     * <p>Verifica se o último registro do arquivo corresponde ao produto inserido.</p>
     */
    @Order(1)
    @Test
    void PD_01() {
        try {
            produtoDAO.inserir(produto);

            List<String> dado = produtoDAO.ler();
            int totalLinhas = dado.size();

            assertEquals(dado.get(totalLinhas - 1), produto.toCSV());

            produtoDAO.deletar(produto);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Testa o método {@link ProdutoDAO#atualizar(Produto)}.
     * <p>Após modificar e atualizar o produto, verifica se a última linha regravada
     * no arquivo corresponde ao novo estado do objeto.</p>
     */
    @Order(2)
    @Test
    void PD_02() {
        try {
            produto.setNome("Teste");

            produtoDAO.inserir(produto);

            produtoDAO.atualizar(produto);

            List<String> dado = produtoDAO.ler();
            int totalLinhas = dado.size();

            assertEquals(dado.get(totalLinhas - 1), produto.toCSV());

            produtoDAO.deletar(produto);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Testa o método {@link ProdutoDAO#ajustarContadorId()}.
     * <p>Garante que o controle estático de IDs da classe {@link Produto}
     * seja atualizado corretamente com base no maior ID presente no arquivo.</p>
     */
    @Order(3)
    @Test
    void PD_03() {
        try {
            produtoDAO.inserir(produto);
            produtoDAO.ajustarContadorId();

            List<String> dado = produtoDAO.ler();
            int totalLinhas = dado.size();

            int id = Integer.parseInt(dado.get(totalLinhas - 1).split(";")[0]);

            assertEquals(id + 1, Produto.getControl());

            produtoDAO.deletar(produto);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Testa o método {@link ProdutoDAO#deletar(Produto)}.
     * <p>Após excluir o produto, verifica que a última linha do arquivo
     * não corresponde mais ao objeto removido.</p>
     */
    @Order(4)
    @Test
    void PD_04() {
        try {
            produtoDAO.inserir(produto);
            produtoDAO.deletar(produto);

            List<String> dado = produtoDAO.ler();
            int totalLinhas = dado.size();

            assertEquals(0, dado.size());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
