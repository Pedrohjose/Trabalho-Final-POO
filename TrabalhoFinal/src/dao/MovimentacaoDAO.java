package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import model.Entrada;
import model.InterfaceCRUD;
import model.Movimentacao;
import model.Saida;

public class MovimentacaoDAO implements InterfaceCRUD<Movimentacao> {

    private static final String NOME_ARQUIVO = "db/movimentacoes.csv";
    private static final String DIRETORIO_DB = "db";
    // O separador que define o fim de um objeto
    private static final String SEPARADOR = "============"; 

    public MovimentacaoDAO() {
        File diretorio = new File(DIRETORIO_DB);
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }

        File arquivo = new File(NOME_ARQUIVO);
        if (!arquivo.exists()) {
            try {
                arquivo.createNewFile();
            } catch (IOException e) {
                System.err.println("Erro ao criar arquivo: " + e.getMessage());
            }
        }
    }

    @Override
    public void inserir(Movimentacao movimento) throws IOException {
        try (FileWriter fileWriter = new FileWriter(NOME_ARQUIVO, true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            
            printWriter.println(movimento.toCSV()); // Imprime o bloco
            printWriter.println(SEPARADOR); // Imprime o separador
        }
    }

    @Override
    public List<Movimentacao> ler() throws IOException {
        List<Movimentacao> movimentos = new ArrayList<>();
        List<String> linhasObjetoAtual = new ArrayList<>(); // Acumulador de linhas
        
        try (FileReader fileReader = new FileReader(NOME_ARQUIVO);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            
            String linha;
            while ((linha = bufferedReader.readLine()) != null) {
                
                // Se for o separador, processa o bloco
                if (linha.trim().equals(SEPARADOR)) {
                    if (!linhasObjetoAtual.isEmpty()) {
                        try {
                            // Pega a primeira linha para descobrir o tipo
                            String tipo = linhasObjetoAtual.get(0); 
                            Movimentacao movimento;
                            
                            if (tipo.contains("ENTRADA")) {
                                movimento = Entrada.fromCSV(linhasObjetoAtual);
                            } else if (tipo.contains("SAIDA")) {
                                movimento = Saida.fromCSV(linhasObjetoAtual);
                            } else {
                                throw new IllegalArgumentException("Tipo de movimento desconhecido.");
                            }
                            movimentos.add(movimento);
                            
                        } catch (Exception e) {
                            System.err.println("Erro ao processar objeto (Movimentacao): " + e.getMessage());
                        }
                        linhasObjetoAtual.clear(); // Limpa para o próximo bloco
                    }
                } else if (!linha.trim().isEmpty()) {
                    // Acumula a linha no bloco
                    linhasObjetoAtual.add(linha.trim());
                }
            }
        } catch (FileNotFoundException e) {
            File file = new File(NOME_ARQUIVO);
            file.createNewFile();
        }
        
        return movimentos;
    }

    // Método para reescrever o arquivo (usado por atualizar e deletar)
    private void reescreverArquivo(List<Movimentacao> movimentos) throws IOException {
        try (FileWriter fileWriter = new FileWriter(NOME_ARQUIVO, false);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            
            for (Movimentacao m : movimentos) {
                printWriter.println(m.toCSV());
                printWriter.println(SEPARADOR);
            }
        }
    }

    @Override
    public void atualizar(Movimentacao movimentoAtualizado) throws IOException {
        List<Movimentacao> movimentos = ler();
        boolean encontrado = false;

        for (int i = 0; i < movimentos.size(); i++) {
            if (movimentos.get(i).equals(movimentoAtualizado)) {
                movimentos.set(i, movimentoAtualizado);
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            throw new IOException("Movimento não encontrado para atualização.");
        }

        reescreverArquivo(movimentos);
    }

    @Override
    public void deletar(Movimentacao movimentoParaDeletar) throws IOException {
        List<Movimentacao> movimentos = ler();
        
        boolean removido = movimentos.removeIf(movimento -> movimento.equals(movimentoParaDeletar));

        if (!removido) {
            throw new IOException("Movimento não encontrado para deleção.");
        }

        reescreverArquivo(movimentos);
    }
}