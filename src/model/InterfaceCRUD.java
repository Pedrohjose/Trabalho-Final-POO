package model;

import java.io.IOException;
import java.util.List;

/**
 * Interface genérica que define as operações CRUD (Create, Read, Update, Delete)
 * para objetos de um tipo {@code T}.
 *
 * <p>Classes que implementam esta interface devem fornecer a implementação
 * para inserir, ler, atualizar e deletar objetos persistidos, geralmente
 * em arquivos CSV ou banco de dados.</p>
 *
 * @param <T> Tipo do objeto que será gerenciado pelo CRUD
 * @author Carlos e Pedro Jose
 */
public interface InterfaceCRUD<T> {

    /**
     * Insere um objeto persistente.
     *
     * @param objeto Objeto a ser inserido
     * @throws IOException Caso ocorra erro de escrita ou persistência
     */
    void inserir(T objeto) throws IOException;

    /**
     * Lê todos os objetos persistentes.
     *
     * @return Lista de strings representando os objetos lidos
     * @throws IOException Caso ocorra erro de leitura ou persistência
     */
    List<String> ler() throws IOException;

    /**
     * Atualiza um objeto persistente existente.
     *
     * @param objeto Objeto com dados atualizados
     * @throws IOException Caso ocorra erro de escrita ou persistência
     */
    void atualizar(T objeto) throws IOException;

    /**
     * Deleta um objeto persistente.
     *
     * @param objeto Objeto a ser deletado
     * @throws IOException Caso ocorra erro de escrita ou persistência
     */
    void deletar(T objeto) throws IOException;
}
