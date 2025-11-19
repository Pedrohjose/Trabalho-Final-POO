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

public class ProdutoDAO implements InterfaceCRUD<Produto> {

	private static final String NOME_ARQUIVO = "db/produtos.csv";

	public ProdutoDAO() {
		verificarDiretorio();
	}

	private void verificarDiretorio() {
		File arquivo = new File(NOME_ARQUIVO);
		File pasta = arquivo.getParentFile();
		if (pasta != null && !pasta.exists()) {
			pasta.mkdirs();
		}
	}

	@Override
	public void inserir(Produto produto) throws IOException {
		verificarDiretorio();
		try (FileWriter fw = new FileWriter(NOME_ARQUIVO, true); BufferedWriter bw = new BufferedWriter(fw)) {
			String linha = produto.toCSV();
			bw.write(linha);
			bw.newLine();
		}
	}

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

	public void reescreverArquivo(List<String> produtos) throws IOException {
		verificarDiretorio();
		try (FileWriter fw = new FileWriter(NOME_ARQUIVO); BufferedWriter bw = new BufferedWriter(fw)) {
			for (String linha : produtos) {
				bw.write(linha);
				bw.newLine();
			}
		}
	}

	public void ajustarContadorId() {
		try {
			List<String> lista = ler();
			int maiorId = -1;
			for (String linha : lista) {
				String[] partes = linha.split(";");
				if (partes.length > 0 && partes[0].matches("\\d+")) {
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