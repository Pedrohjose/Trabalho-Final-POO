package dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.InterfaceCRUD;
import model.Produto;

/**
 * Classe responsável pelo acesso a dados de {@link Produto}.
 * Implementa operações CRUD (Create, Read, Update, Delete) para
 * armazenar e manipular produtos em arquivo CSV.
 *
 * <p>O arquivo padrão é <code>db/produtos.csv</code>.</p>
 *
 * <p>Também possui métodos auxiliares para verificar diretórios e
 * ajustar o contador de IDs dos produtos.</p>
 *
 * @author Carlos e Pedro Jose
 */
public class ProdutoDAO implements InterfaceCRUD<Produto> {

    /** Caminho do arquivo CSV usado para armazenar os produtos */
    private static final String NOME_ARQUIVO = "db/produtos.csv";

    /**
     * Construtor que inicializa o DAO.
     * Verifica se o diretório do arquivo existe e cria se necessário.
     */
    public ProdutoDAO() {
        verificarDiretorio();
    }

    /**
     * Verifica se o diretório do arquivo CSV existe.
     * Se não existir, cria o diretório.
     */
    private void verificarDiretorio() {
        File arquivo = new File(NOME_ARQUIVO);
        File pasta = arquivo.getParentFile();
        if (pasta != null && !pasta.exists()) {
            pasta.mkdirs();
        }
    }

    /**
     * Insere um novo produto no arquivo CSV.
     *
     * @param produto Produto a ser inserido
     * @throws IOException Caso ocorra erro de escrita no arquivo
     */
    @Override
    public void inserir(Produto produto) throws IOException {
        verificarDiretorio();
        try (FileWriter fw = new FileWriter(NOME_ARQUIVO, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            String linha = produto.toCSV();
            bw.write(linha);
            bw.newLine();
        }
    }

    /**
     * Lê todas as linhas do arquivo CSV e retorna como lista de Strings.
     *
     * @return Lista de linhas do arquivo
     * @throws IOException Caso ocorra erro de leitura do arquivo
     */
    @Override
    public List<String> ler() throws IOException {
        List<String> produtos = new ArrayList<>();
        File arquivo = new File(NOME_ARQUIVO);

        if (!arquivo.exists()) {
            return produtos;
        }

        try (Scanner sc = new Scanner(arquivo)) {
            while (sc.hasNextLine()) {
                String linha = sc.nextLine();
                if (linha != null && !linha.trim().isEmpty()) {
                    produtos.add(linha);
                }
            }
        }

        return produtos;
    }

    /**
     * Atualiza um produto existente no arquivo CSV.
     * Se o código do produto não for encontrado, exibe uma mensagem.
     *
     * @param produtoAtualizado Produto com dados atualizados
     * @throws IOException Caso ocorra erro de escrita no arquivo
     */
    @Override
    public void atualizar(Produto produtoAtualizado) throws IOException {
        List<String> produtos = ler();
        boolean encontrado = false;

        for (int i = 0; i < produtos.size(); i++) {
            String linha = produtos.get(i);
            String[] partes = linha.split(";");

            if (partes.length > 0 && Integer.parseInt(partes[0]) == produtoAtualizado.getCodigo()) {
                produtos.set(i, produtoAtualizado.toCSV());
                encontrado = true;
                break;
            }
        }

        if (encontrado) {
            reescreverArquivo(produtos);
        } else {
            System.out.println("Produto não encontrado para atualizar.");
        }
    }

    /**
     * Deleta um produto do arquivo CSV.
     * Se o produto não for encontrado, não faz nada.
     *
     * @param produtoParaDeletar Produto a ser deletado
     * @throws IOException Caso ocorra erro de escrita no arquivo
     */
    @Override
    public void deletar(Produto produtoParaDeletar) throws IOException {
        List<String> produtos = ler();

        boolean removido = produtos.removeIf(linha -> {
            String[] partes = linha.split(";");
            return partes.length > 0 && Integer.parseInt(partes[0]) == produtoParaDeletar.getCodigo();
        });

        if (removido) {
            reescreverArquivo(produtos);
        }
    }

    /**
     * Reescreve todo o arquivo CSV com a lista de produtos fornecida.
     *
     * @param produtos Lista de linhas que irão substituir o arquivo atual
     * @throws IOException Caso ocorra erro de escrita
     */
    public void reescreverArquivo(List<String> produtos) throws IOException {
        verificarDiretorio();
        try (FileWriter fw = new FileWriter(NOME_ARQUIVO);
             BufferedWriter bw = new BufferedWriter(fw)) {
            for (String linha : produtos) {
                bw.write(linha);
                bw.newLine();
            }
        }
    }

    /**
     * Ajusta o contador de IDs da classe {@link Produto}
     * com base na maior ID existente no arquivo CSV.
     * Se ocorrer erro de leitura, o contador é zerado.
     */
    public void ajustarContadorId() {
        try {
            List<String> lista = ler();
            int maiorId = -1;
            for (String linha : lista) {
                String[] partes = linha.split(";");
                if (partes.length > 0 && partes[0].matches("\\d+")) { // Verifica se é número
                    int idAtual = Integer.parseInt(partes[0]);
                    maiorId = Math.max(maiorId, idAtual);
                }
            }
            Produto.setControl(maiorId + 1);
        } catch (IOException e) {
            Produto.setControl(0);
        }
    }
}
