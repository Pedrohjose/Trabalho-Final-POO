package view;

import java.awt.Font;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import dao.MovimentacaoDAO;
import dao.ProdutoDAO;
import model.CategoriasProdutos;
import model.Entrada;
import model.Produto;

public class TelaEntrada extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JTable tabelaProdutos;
	private DefaultTableModel tableModel;
	private JTextField txtQuantidade;
	private JButton btnConfirmar;
	private JButton btnAtualizar;

	
	
	public TelaEntrada() {
		MovimentacaoDAO movDao = new MovimentacaoDAO();
		movDao.ajustarContadorId();
		
		setLayout(null);

		JLabel lblTitulo = new JLabel("Registrar Entrada de Estoque");
		lblTitulo.setBounds(10, 10, 300, 30);
		lblTitulo.setFont(new Font("Noto Sans", Font.BOLD, 18));
		add(lblTitulo);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 50, 452, 200);
		add(scrollPane);

		String[] colunas = { "Código", "Nome", "Preço", "Atual" };
		tableModel = new DefaultTableModel(colunas, 0) {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		
		tabelaProdutos = new JTable(tableModel);
		tabelaProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(tabelaProdutos);

		JLabel lblQtd = new JLabel("Quantidade a adicionar:");
		lblQtd.setBounds(10, 270, 150, 20);
		add(lblQtd);

		txtQuantidade = new JTextField();
		txtQuantidade.setBounds(10, 290, 100, 30);
		add(txtQuantidade);

		btnConfirmar = new JButton("Confirmar Entrada");
		btnConfirmar.setBounds(120, 290, 150, 30);
		btnConfirmar.addActionListener(e -> registrarEntrada());
		add(btnConfirmar);
		
		btnAtualizar = new JButton("Recarregar Lista");
		btnAtualizar.setBounds(332, 16, 130, 25);
		btnAtualizar.addActionListener(e -> carregarDados());
		add(btnAtualizar);

		carregarDados();
	}

	public void carregarDados() {
		ProdutoDAO dao = new ProdutoDAO();
		tableModel.setRowCount(0);
		try {
			List<String> linhas = dao.ler();
			for (String linha : linhas) {
				String[] dados = linha.split(";");
				if (dados.length >= 6) {
					tableModel.addRow(new Object[] { dados[0], dados[1], dados[4], dados[3] });
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Erro ao ler dados: " + e.getMessage());
		}
	}

	private void registrarEntrada() {
		int linhaSelecionada = tabelaProdutos.getSelectedRow();
		if (linhaSelecionada == -1) {
			JOptionPane.showMessageDialog(this, "Selecione um produto na tabela acima.");
			return;
		}

		String codigoStr = (String) tableModel.getValueAt(linhaSelecionada, 0);
		String nome = (String) tableModel.getValueAt(linhaSelecionada, 1);
		String precoStr = (String) tableModel.getValueAt(linhaSelecionada, 2);
		
		int codigoProduto = Integer.parseInt(codigoStr);
		double precoUnitario = Double.parseDouble(precoStr);
		
		try {
			String textoDigitado = txtQuantidade.getText().trim(); 
			int qtdEntrada = Integer.parseInt(textoDigitado);
			
			if(qtdEntrada <= 0) throw new NumberFormatException();
			
			double valorTotalMovimentacao = qtdEntrada * precoUnitario;
			
			int confirm = JOptionPane.showConfirmDialog(this, 
					"Produto: " + nome + "\n" +
					"Adicionar: " + qtdEntrada + " unidades\n" +
					"Valor Total da Entrada: R$ " + valorTotalMovimentacao,
					"Confirmar Entrada", JOptionPane.YES_NO_OPTION);
			
			if(confirm == JOptionPane.YES_OPTION) {
				
				ProdutoDAO produtoDao = new ProdutoDAO();
				List<String> linhas = produtoDao.ler();
				Produto produtoParaAtualizar = null;
				
				for (String linha : linhas) {
					String[] dados = linha.split(";");
					if(Integer.parseInt(dados[0]) == codigoProduto) {
						double preco = Double.parseDouble(dados[4]);
						int qtdAtual = Integer.parseInt(dados[3]);
						CategoriasProdutos cat = CategoriasProdutos.valueOf(dados[5]);
						produtoParaAtualizar = new Produto(codigoProduto, dados[1], preco, qtdAtual, cat);
						break;
					}
				}

				if (produtoParaAtualizar != null) {
					int novaQuantidade = produtoParaAtualizar.getQuantidade() + qtdEntrada;
					produtoParaAtualizar.setQuantidade(novaQuantidade);
					produtoDao.atualizar(produtoParaAtualizar);
					
					MovimentacaoDAO movDao = new MovimentacaoDAO();
					Entrada novaEntrada = new Entrada(codigoProduto, qtdEntrada, valorTotalMovimentacao);
					movDao.inserir(novaEntrada);
					
					JOptionPane.showMessageDialog(this, "Sucesso!\nEstoque atualizado e histórico salvo.");
					
					txtQuantidade.setText("");
					carregarDados();
				} else {
					JOptionPane.showMessageDialog(this, "Erro crítico: Produto não encontrado no arquivo.");
				}
			}
			
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Digite uma quantidade válida (número inteiro positivo sem letras).");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Erro de arquivo: " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Erro inesperado: " + e.getMessage());
		}
	}
}