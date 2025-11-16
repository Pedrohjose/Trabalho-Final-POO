package dao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.InterfaceCRUD;
import model.Produto;

public class ProdutoDAO implements InterfaceCRUD<Produto> {

	private static final String NOME_ARQUIVO = "db/produtos.csv";

	public void inserir(Produto produto) throws IOException {
        try (FileWriter fw = new FileWriter(NOME_ARQUIVO)) {
            fw.write(produto.toCSV());
        }
    }

    public List<String> ler() throws IOException {
        List<String> produtos = new ArrayList<>();

        try(Scanner sc = new Scanner(new File(NOME_ARQUIVO))) {
            while (sc.hasNextLine()) {
                produtos.add(sc.nextLine());
            }
        }

        return produtos;
    }
    
    public void atualizar(Produto produtoAtualizado) throws IOException {
        List<String> produtos = ler();
        boolean encontrado = false;

        for (int i = 0; i < produtos.size(); i++) {
            String linha = produtos.get(i);
            String[] partes = linha.split(";");
            
            if(Integer.parseInt(partes[0]) == produtoAtualizado.getCodigo()) {
                produtos.set(i, linha);
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            System.out.println("Objeto não encontrado");
        }

        reescreverArquivo(produtos);
    }

    
    public void deletar(Produto produtoParaDeletar) throws IOException {
        List<String> produtos = ler();
        
        boolean removido = produtos.removeIf(produto -> produto.equals(produtoParaDeletar.toCSV()));

        if (!removido) {
            System.out.println("Movimento não encontrado para deletar.");
        }

        reescreverArquivo(produtos);
    }

    public void reescreverArquivo(List<String> produtos) throws IOException {
        try(FileWriter fw = new FileWriter(NOME_ARQUIVO)) {
            for (String linha : produtos) {
                fw.write(linha + System.lineSeparator());
            }
        }
    }
}