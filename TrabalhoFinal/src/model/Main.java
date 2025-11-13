package model;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import dao.MovimentacaoDAO;
import dao.ProdutoDAO;

public class Main {

	public static void main(String[] args) {

		ProdutoDAO produtoDAO = new ProdutoDAO();
		MovimentacaoDAO movimentacaoDAO = new MovimentacaoDAO();
		Scanner scanner = new Scanner(System.in);

		int opcao = -1;

		while (opcao != 0) {
			System.out.println("\n--- MENU DE TESTE INTERATIVO ---");
			System.out.println("1. Inserir Produto");
			System.out.println("2. Listar Produtos");
			System.out.println("3. Atualizar Produto (pelo código)");
			System.out.println("4. Deletar Produto (pelo código)");
			System.out.println("5. Registrar Entrada de Estoque");
			System.out.println("6. Registrar Saída de Estoque");
			System.out.println("7. Listar Movimentações");
			System.out.println("99. LIMPAR TODO O BANCO DE DADOS"); // NOVA OPÇÃO
			System.out.println("0. Sair");
			System.out.print("Escolha uma opção: ");

			try {
				opcao = Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Opção inválida. Digite um número.");
				continue;
			}

			try {
				switch (opcao) {
				case 1:
					inserirProduto(scanner, produtoDAO);
					break;
				case 2:
					listarProdutos(produtoDAO);
					break;
				case 3:
					atualizarProduto(scanner, produtoDAO);
					break;
				case 4:
					deletarProduto(scanner, produtoDAO);
					break;
				case 5:
					registrarEntrada(scanner, movimentacaoDAO, produtoDAO);
					break;
				case 6:
					registrarSaida(scanner, movimentacaoDAO, produtoDAO);
					break;
				case 7:
					listarMovimentacoes(movimentacaoDAO);
					break;
				case 99:
					System.out.println("TEM CERTEZA QUE DESEJA APAGAR TUDO? (S/N)");
					String confirmacao = scanner.nextLine();
					if (confirmacao.equalsIgnoreCase("S")) {
						limparBancoDeDados();
					} else {
						System.out.println("Operação cancelada.");
					}
					break;
				case 0:
					System.out.println("Saindo...");
					break;
				default:
					System.out.println("Opção inválida.");
				}
			} catch (IOException e) {
				System.err.println("ERRO: " + e.getMessage());
			} catch (Exception e) {
				System.err.println("ERRO INESPERADO: " + e.getMessage());
			}
		}

		scanner.close();
	}

	private static void inserirProduto(Scanner scanner, ProdutoDAO dao) throws IOException {
		System.out.println("--- Inserir Produto ---");
		System.out.print("Código: ");
		int codigo = Integer.parseInt(scanner.nextLine());
		System.out.print("Nome: ");
		String nome = scanner.nextLine();
		System.out.print("Preço (Venda): ");
		double preco = Double.parseDouble(scanner.nextLine());

		CategoriasProdutos categoria = selecionarCategoria(scanner); // Chama o novo menu

		Produto p = new Produto(codigo, nome, preco, 0, categoria); // Quantidade inicial é 0
		dao.inserir(p);
		System.out.println("Produto inserido com sucesso! Estoque atual: 0");
	}

	private static CategoriasProdutos selecionarCategoria(Scanner scanner) {
		System.out.println("Selecione a Categoria:");
		System.out.println("1. Hardware");
		System.out.println("2. Periféricos");
		System.out.println("3. Acessórios");
		System.out.println("4. Outros");
		System.out.print("Opção: ");

		try {
			int opcao = Integer.parseInt(scanner.nextLine());

			switch (opcao) {
			case 1:
				return CategoriasProdutos.HARDWARE;
			case 2:
				return CategoriasProdutos.PERIFERICOS;
			case 3:
				return CategoriasProdutos.ACESSORIOS;
			case 4:
				return CategoriasProdutos.OUTROS;
			default:
				throw new IllegalArgumentException("Opção inválida.");
			}
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Entrada inválida. Digite um número.");
		}
	}

	private static void listarProdutos(ProdutoDAO dao) throws IOException {
		System.out.println("--- Lista de Produtos ---");
		List<Produto> produtos = dao.ler();
		if (produtos.isEmpty()) {
			System.out.println("Nenhum produto cadastrado.");
			return;
		}
		for (Produto p : produtos) {
			System.out.println(p.toCSV());
			System.out.println("------------");
		}
	}

	private static void atualizarProduto(Scanner scanner, ProdutoDAO dao) throws IOException {
		System.out.println("--- Atualizar Produto ---");
		System.out.print("Digite o código do produto que deseja atualizar: ");
		int codigo = Integer.parseInt(scanner.nextLine());

		Produto pExistente = dao.buscarPorCodigo(codigo);
		System.out.println("Produto encontrado: " + pExistente.getNome() + " | Estoque: " + pExistente.getQuantidade());

		System.out.print("Novo Nome: ");
		String nome = scanner.nextLine();
		System.out.print("Novo Preço: ");
		double preco = Double.parseDouble(scanner.nextLine());
		System.out.print("Nova Categoria (HARDWARE, PERIFERICOS, ACESSORIOS, OUTROS): ");
		CategoriasProdutos categoria = CategoriasProdutos.valueOf(scanner.nextLine().toUpperCase());

		Produto pAtualizado = new Produto(codigo, nome, preco, pExistente.getQuantidade(), categoria);
		dao.atualizar(pAtualizado);
		System.out.println("Produto atualizado com sucesso!");
	}

	private static void deletarProduto(Scanner scanner, ProdutoDAO dao) throws IOException {
		System.out.println("--- Deletar Produto ---");
		System.out.print("Digite o código do produto que deseja deletar: ");
		int codigo = Integer.parseInt(scanner.nextLine());

		Produto pDelete = new Produto();
		pDelete.setCodigo(codigo);

		dao.deletar(pDelete);
		System.out.println("Produto deletado com sucesso!");
	}

	private static void registrarEntrada(Scanner scanner, MovimentacaoDAO mDao, ProdutoDAO pDao) throws IOException {
		System.out.println("--- Registrar Entrada ---");
		System.out.print("Código do Produto: ");
		int codigo = Integer.parseInt(scanner.nextLine());

		Produto p = pDao.buscarPorCodigo(codigo);
		System.out.println("Produto: " + p.getNome() + " | Estoque Atual: " + p.getQuantidade());

		System.out.print("Quantidade de Entrada: ");
		int qtd = Integer.parseInt(scanner.nextLine());
		System.out.print("Valor Unitário (Custo): ");
		double valor = Double.parseDouble(scanner.nextLine());

		Entrada e = new Entrada(codigo, LocalDate.now(), qtd, valor);
		mDao.inserir(e);

		p.setQuantidade(p.getQuantidade() + qtd);
		pDao.atualizar(p);

		System.out.println("Entrada registrada! Novo estoque: " + p.getQuantidade());
	}

	private static void registrarSaida(Scanner scanner, MovimentacaoDAO mDao, ProdutoDAO pDao) throws IOException {
		System.out.println("--- Registrar Saída ---");
		System.out.print("Código do Produto: ");
		int codigo = Integer.parseInt(scanner.nextLine());

		Produto p = pDao.buscarPorCodigo(codigo);
		System.out.println("Produto: " + p.getNome() + " | Estoque Atual: " + p.getQuantidade());

		System.out.print("Quantidade de Saída: ");
		int qtd = Integer.parseInt(scanner.nextLine());

		// Validação de estoque
		if (qtd > p.getQuantidade()) {
			System.err.println("Erro: Estoque insuficiente. (Disponível: " + p.getQuantidade() + ")");
			return;
		}

		System.out.print("Valor Unitário (Venda): ");
		double valor = Double.parseDouble(scanner.nextLine());

		TipoSaida tipo = selecionarTipoSaida(scanner); // Chama o novo menu

		Saida s = new Saida(codigo, LocalDate.now(), qtd, valor, tipo);
		mDao.inserir(s);

		p.setQuantidade(p.getQuantidade() - qtd);
		pDao.atualizar(p);

		System.out.println("Saída registrada! Novo estoque: " + p.getQuantidade());
	}

	private static TipoSaida selecionarTipoSaida(Scanner scanner) {
		System.out.println("Selecione o Tipo de Saída:");
		System.out.println("1. Venda ao Cliente");
		System.out.println("2. Uso Interno");
		System.out.println("3. Devolução ao Fornecedor");
		System.out.println("4. Outras Saídas");
		System.out.print("Opção: ");

		try {
			int opcao = Integer.parseInt(scanner.nextLine());

			switch (opcao) {
			case 1:
				return TipoSaida.VENDA_CLIENTE;
			case 2:
				return TipoSaida.USO_INTERNO;
			case 3:
				return TipoSaida.DEVOLUCAO_FORNECEDOR;
			case 4:
				return TipoSaida.OUTRAS_SAIDAS;
			default:
				throw new IllegalArgumentException("Opção inválida.");
			}
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Entrada inválida. Digite um número.");
		}
	}

	private static void listarMovimentacoes(MovimentacaoDAO dao) throws IOException {
		System.out.println("--- Lista de Movimentações ---");
		List<Movimentacao> movimentos = dao.ler();
		if (movimentos.isEmpty()) {
			System.out.println("Nenhuma movimentação registrada.");
			return;
		}
		for (Movimentacao m : movimentos) {
			System.out.println(m.toCSV());
			System.out.println("------------");
		}
	}

	private static void limparBancoDeDados() {
		System.out.println("Limpando arquivos (db/produtos.csv, db/movimentacoes.csv)...");
		try {
			File p = new File("db/produtos.csv");
			if (p.exists()) {
				p.delete();
			}
			p.createNewFile();

			File m = new File("db/movimentacoes.csv");
			if (m.exists()) {
				m.delete();
			}
			m.createNewFile();

			System.out.println("Banco de dados limpo com sucesso.");

		} catch (Exception e) {
			System.err.println("Erro ao limpar arquivos: " + e.getMessage());
		}
	}
}