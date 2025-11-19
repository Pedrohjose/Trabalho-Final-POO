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


public class MovimentacaoDAO implements InterfaceCRUD<Movimentacao> {

	private static final String NOME_ARQUIVO = "db/movimentacoes.csv";

	public MovimentacaoDAO() {
		verificarDiretorio();
	}

	public void inserir(Movimentacao movimento) throws IOException {
		try (FileWriter fw = new FileWriter(NOME_ARQUIVO, true); BufferedWriter bw = new BufferedWriter(fw)) {
			bw.write(movimento.toCSV());
			bw.newLine();
		}
	}

	public List<String> ler() throws IOException {
		List<String> linhas = new ArrayList<>();

		try (Scanner sc = new Scanner(new File(NOME_ARQUIVO))) {
			while (sc.hasNextLine()) {
				linhas.add(sc.nextLine());
			}
		}

		return linhas;
	}

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

	public void deletar(Movimentacao movimentoParaDeletar) throws IOException {
		List<String> movimentos = ler();

		boolean removido = movimentos.removeIf(movimento -> movimento.equals(movimentoParaDeletar.toCSV()));

		if (!removido) {
			System.out.println("Movimento não encontrado para deletar.");
		}

		reescreverArquivo(movimentos);
	}

	public void reescreverArquivo(List<String> movimentos) throws IOException {
		try (FileWriter fw = new FileWriter(NOME_ARQUIVO)) {
			for (String linha : movimentos) {
				fw.write(linha + System.lineSeparator());
			}
		}
	}

	private void verificarDiretorio() {
		File arquivo = new File(NOME_ARQUIVO);
		File pasta = arquivo.getParentFile();
		if (pasta != null && !pasta.exists()) {
			pasta.mkdirs();
		}
	}

	public void ajustarContadorId() {
		try {
			List<String> lista = ler();
			int maiorId = -1;
			for (String linha : lista) {
				String[] partes = linha.split(";");
				if (partes.length > 0 && partes[0].matches("\\d+"))//Verifica se o texto é formado apenas por dígitos(Regex) 
				{
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