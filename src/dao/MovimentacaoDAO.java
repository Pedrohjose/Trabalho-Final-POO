package dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.InterfaceCRUD;
import model.Movimentacao;

/**
 * Classe responsável pelo acesso a dados de {@link Movimentacao}.
 * Implementa operações CRUD (Create, Read, Update, Delete) para
 * armazenar e manipular movimentações em arquivo CSV.
 *
 * <p>O arquivo padrão é <code>db/movimentacoes.csv</code>.</p>
 *
 * <p>Também possui métodos auxiliares para verificar diretórios e
 * ajustar o contador de IDs das movimentações.</p>
 *
 * @author Carlos e Pedro Jose
 */
public class MovimentacaoDAO implements InterfaceCRUD<Movimentacao> {

    /** Caminho do arquivo CSV usado para armazenar as movimentações */
    private static final String NOME_ARQUIVO = "db/movimentacoes.csv";

    /**
     * Construtor que inicializa o DAO.
     * Verifica se o diretório do arquivo existe e cria se necessário.
     */
    public MovimentacaoDAO() {
        verificarDiretorio();
    }

    /**
     * Insere uma nova movimentação no arquivo CSV.
     *
     * @param movimento Movimentação a ser inserida
     * @throws IOException Caso ocorra erro de escrita no arquivo
     */
    public void inserir(Movimentacao movimento) throws IOException {
        try (FileWriter fw = new FileWriter(NOME_ARQUIVO, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(movimento.toCSV());
            bw.newLine();
        }
    }

    /**
     * Lê todas as linhas do arquivo CSV e retorna como lista de Strings.
     *
     * @return Lista de linhas do arquivo
     * @throws IOException Caso ocorra erro de leitura do arquivo
     */
    public List<String> ler() throws IOException {
        List<String> linhas = new ArrayList<>();

        try (Scanner sc = new Scanner(new File(NOME_ARQUIVO))) {
            while (sc.hasNextLine()) {
                linhas.add(sc.nextLine());
            }
        }

        return linhas;
    }

    /**
     * Atualiza uma movimentação existente no arquivo CSV.
     * Se o código da movimentação não for encontrado, exibe uma mensagem.
     *
     * @param movimentoAtualizado Movimentação com dados atualizados
     * @throws IOException Caso ocorra erro de escrita no arquivo
     */
    public void atualizar(Movimentacao movimentoAtualizado) throws IOException {
        List<String> linhas = ler();
        boolean encontrado = false;

        for (int i = 0; i < linhas.size(); i++) {
            String linha = linhas.get(i);
            String[] partes = linha.split(";");

            if (Integer.parseInt(partes[0]) == movimentoAtualizado.getCodigo()) {
                linhas.set(i, linha);
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            System.out.println("Objeto não encontrado");
        }

        reescreverArquivo(linhas);
    }

    /**
     * Deleta uma movimentação do arquivo CSV.
     * Se a movimentação não for encontrada, exibe uma mensagem.
     *
     * @param movimentoParaDeletar Movimentação a ser deletada
     * @throws IOException Caso ocorra erro de escrita no arquivo
     */
    public void deletar(Movimentacao movimentoParaDeletar) throws IOException {
        List<String> movimentos = ler();

        boolean removido = movimentos.removeIf(
                movimento -> movimento.equals(movimentoParaDeletar.toCSV())
        );

        if (!removido) {
            System.out.println("Movimento não encontrado para deletar.");
        }

        reescreverArquivo(movimentos);
    }

    /**
     * Reescreve todo o arquivo CSV com a lista de movimentações fornecida.
     *
     * @param movimentos Lista de linhas que irão substituir o arquivo atual
     * @throws IOException Caso ocorra erro de escrita
     */
    public void reescreverArquivo(List<String> movimentos) throws IOException {
        try (FileWriter fw = new FileWriter(NOME_ARQUIVO)) {
            for (String linha : movimentos) {
                fw.write(linha + System.lineSeparator());
            }
        }
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
     * Ajusta o contador de IDs da classe {@link Movimentacao}
     * com base na maior movimentação existente no arquivo CSV.
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
            Movimentacao.setControl(maiorId + 1);
        } catch (IOException e) {
            Movimentacao.setControl(0);
        }
    }
}
