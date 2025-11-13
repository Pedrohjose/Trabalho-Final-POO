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

import model.InterfaceCRUD;
import model.Produto;

public class ProdutoDAO implements InterfaceCRUD<Produto> {

	private static final String NOME_ARQUIVO = "db/produtos.csv";
	private static final String DIRETORIO_DB = "db";
	private static final String SEPARADOR = "============";

	public ProdutoDAO() {
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

	// Método de validação de duplicidade (sem classe de exceção)
	private void validarProduto(Produto pValidar, List<Produto> todos) throws IOException {
		for (Produto p : todos) {
			if (p.getCodigo() == pValidar.getCodigo()) {
				throw new IOException("Erro: O código " + pValidar.getCodigo() + " já está cadastrado.");
			}
			if (p.getNome().equalsIgnoreCase(pValidar.getNome())) {
				throw new IOException("Erro: O nome '" + pValidar.getNome() + "' já está cadastrado.");
			}
		}
	}

	// Método de validação de duplicidade para atualização
	private void validarAtualizacao(Produto pAtualizado, List<Produto> todos) throws IOException {
		for (Produto p : todos) {
			boolean msmCodigo = p.getCodigo() == pAtualizado.getCodigo();
			boolean msmNome = p.getNome().equalsIgnoreCase(pAtualizado.getNome());
			if (msmNome && !msmCodigo) {
				throw new IOException("Erro: O nome '" + pAtualizado.getNome() + "' já pertence a outro produto.");
			}
		}
	}

	@Override
	public void inserir(Produto produto) throws IOException {
		List<Produto> produtos = ler();
		validarProduto(produto, produtos);

		try (FileWriter fileWriter = new FileWriter(NOME_ARQUIVO, true);
				PrintWriter printWriter = new PrintWriter(fileWriter)) {

			printWriter.println(produto.toCSV());
			printWriter.println(SEPARADOR);
		}
	}

	@Override
	public List<Produto> ler() throws IOException {
		List<Produto> produtos = new ArrayList<>();
		List<String> linhasObjetoAtual = new ArrayList<>();

		try (FileReader fileReader = new FileReader(NOME_ARQUIVO);
				BufferedReader bufferedReader = new BufferedReader(fileReader)) {

			String linha;
			while ((linha = bufferedReader.readLine()) != null) {

				if (linha.trim().equals(SEPARADOR)) {
					if (!linhasObjetoAtual.isEmpty()) {
						try {
							Produto produto = Produto.fromCSV(linhasObjetoAtual);
							produtos.add(produto);
						} catch (IllegalArgumentException e) {
							System.err.println("Erro ao processar objeto: " + e.getMessage());
						}
						linhasObjetoAtual.clear();
					}
				} else if (!linha.trim().isEmpty()) {
					linhasObjetoAtual.add(linha.trim());
				}
			}
		} catch (FileNotFoundException e) {
			File file = new File(NOME_ARQUIVO);
			file.createNewFile();
		}

		return produtos;
	}

	private void reescreverArquivo(List<Produto> produtos) throws IOException {
		try (FileWriter fileWriter = new FileWriter(NOME_ARQUIVO, false);
				PrintWriter printWriter = new PrintWriter(fileWriter)) {

			for (Produto p : produtos) {
				printWriter.println(p.toCSV());
				printWriter.println(SEPARADOR);
			}
		}
	}

	@Override
	public void atualizar(Produto produtoAtualizado) throws IOException {
		List<Produto> produtos = ler();
		validarAtualizacao(produtoAtualizado, produtos);

		boolean encontrado = false;

		for (int i = 0; i < produtos.size(); i++) {
			if (produtos.get(i).equals(produtoAtualizado)) {
				produtos.set(i, produtoAtualizado);
				encontrado = true;
				break;
			}
		}

		if (!encontrado) {
			throw new IOException(
					"Produto com código " + produtoAtualizado.getCodigo() + " não encontrado para atualização.");
		}

		reescreverArquivo(produtos);
	}

	@Override
	public void deletar(Produto produtoParaDeletar) throws IOException {
		List<Produto> produtos = ler();

		boolean removido = produtos.removeIf(produto -> produto.equals(produtoParaDeletar));

		if (!removido) {
			throw new IOException(
					"Produto com código " + produtoParaDeletar.getCodigo() + " não encontrado para deleção.");
		}

		reescreverArquivo(produtos);
	}

	public Produto buscarPorCodigo(int codigo) throws IOException {
		List<Produto> produtos = ler();
		for (Produto p : produtos) {
			if (p.getCodigo() == codigo) {
				return p;
			}
		}
		throw new IOException("Produto com código " + codigo + " não encontrado.");
	}
}