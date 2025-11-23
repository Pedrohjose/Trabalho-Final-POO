package test;

import dao.MovimentacaoDAO;
import model.Entrada;
import model.Movimentacao;
import model.Saida;
import model.TipoSaida;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Classe de testes unitários para a classe {@link MovimentacaoDAO}.
 *
 * <p>Testa todos os metodos CRUD da classe e alguns metodos auxiliares
 * <ul>
 * <li>{@link MovimentacaoDAO#ler()}</li>
 * <li>{@link MovimentacaoDAO#inserir(Movimentacao)}</li>
 * <li>{@link MovimentacaoDAO#atualizar(Movimentacao)}</li>
 * <li>{@link MovimentacaoDAO#deletar(Movimentacao)}</li>
 * </ul>
 * <p>Utiliza JUnit 5 e JUnit 5 para configuração e assertions.</p>
 *
 * @author Carlos
 */
class MovimentacaoDAOTest {

    /**
     * Instância da classe DAO a ser testada.
     */
    MovimentacaoDAO movimentacaoDAO;

    /**
     * Objeto de entrada utilizado nos testes.
     */
    Movimentacao entrada;

    /**
     * Inicializa o ambiente de teste antes da execução de cada caso de teste.
     * Cria uma instância de {@link MovimentacaoDAO} e objetos de movimentação para uso posterior.
     */
    @BeforeEach
    public void setUp() {
        movimentacaoDAO = new MovimentacaoDAO();
        entrada = new Entrada(1, 1, 2.5);
    }

    /**
     * Testa o método {@link MovimentacaoDAO#inserir(Movimentacao)} seguido de {@link MovimentacaoDAO#ler()}.
     * <p>Verifica se a última linha escrita no arquivo corresponde ao objeto inserido.</p>
     */
    @Order(1)
    @Test
    void MD_01() {
        try {
            movimentacaoDAO.inserir(entrada);

            List<String> dado = movimentacaoDAO.ler();
            int totalLinhas = dado.size();

            assertEquals(dado.get(totalLinhas - 1), entrada.toCSV());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Testa os métodos {@link MovimentacaoDAO#inserir(Movimentacao)} e {@link MovimentacaoDAO#atualizar(Movimentacao)}.
     * <p>Após atualizar a movimentação, verifica se o conteúdo regravado no arquivo reflete as alterações realizadas.</p>
     */
    @Order(2)
    @Test
    void MD_02() {
        try {
            entrada.setQuantidade(100);
            movimentacaoDAO.inserir(entrada);
            movimentacaoDAO.atualizar(entrada);

            List<String> dado = movimentacaoDAO.ler();
            int totalLinhas = dado.size();

            assertEquals(dado.get(totalLinhas - 1), entrada.toCSV());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Testa o método {@link MovimentacaoDAO#ajustarContadorId()}.
     * <p>Lê o arquivo após o ajuste e verifica se o ID de controle da classe
     * {@link Movimentacao} foi atualizado corretamente com base no maior ID existente.</p>
     */
    @Order(3)
    @Test
    void MD_03() {
        try {
            movimentacaoDAO.inserir(entrada);
            movimentacaoDAO.ajustarContadorId();

            List<String> dado = movimentacaoDAO.ler();
            int totalLinhas = dado.size();

            int id = Integer.parseInt(dado.get(totalLinhas - 1).split(";")[0]);
            movimentacaoDAO.deletar(entrada);

            assertEquals(id + 1, Movimentacao.getControl());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Testa o método {@link MovimentacaoDAO#deletar(Movimentacao)}.
     * <p>Após a remoção da movimentação, garante que a última linha do arquivo
     * não corresponde mais ao objeto anteriormente excluído.</p>
     */
    @Order(4)
    @Test
    void MD_04() {
        try {
            movimentacaoDAO.inserir(entrada);
            movimentacaoDAO.deletar(entrada);

            List<String> dado = movimentacaoDAO.ler();
            int totalLinhas = dado.size();

            assertNotEquals(dado.get(totalLinhas - 1), entrada.toCSV());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}