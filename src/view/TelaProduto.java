package view;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import dao.ProdutoDAO;
import model.CategoriasProdutos;
import model.Produto;

public class TelaProduto extends JFrame {

	private static final long serialVersionUID = 1L;

	private javax.swing.JTextField sku;
	private javax.swing.JTextField nome;
	private javax.swing.JTextField preco;
	private JComboBox<CategoriasProdutos> CBcategoria;

	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private JPanel painelDireito;

	private JTable tabelaProdutos;
	private DefaultTableModel tableModel;

	private TelaEntrada telaEntrada;
	private CardLayout cardLayout;

	private static final java.util.logging.Logger logger = java.util.logging.Logger
			.getLogger(TelaProduto.class.getName());

	public TelaProduto() {
		ProdutoDAO dao = new ProdutoDAO();
		dao.ajustarContadorId();
		initComponents();

		cardLayout.show(painelDireito, "TELA_PRODUTOS");
		atualizarListaProdutos();
	}

	private void initComponents() {

		jPanel1 = new javax.swing.JPanel();
		jButton1 = new javax.swing.JButton();
		jButton2 = new javax.swing.JButton();
		jButton3 = new javax.swing.JButton();
		jButton4 = new javax.swing.JButton();
		jButton6 = new javax.swing.JButton();

		jButton1.setText("Produtos");
		jButton1.addActionListener(this::jButton1ActionPerformed);
		jButton2.setText("Registrar Entrada");
		jButton2.addActionListener(this::jButton2ActionPerformed);
		jButton3.setText("Registrar Saída");
		jButton3.addActionListener(this::jButton3ActionPerformed);
		jButton4.setText("Consultar Saldo");
		jButton6.setText("Listar Movimetos");

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup()
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
								.addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jButton6, javax.swing.GroupLayout.Alignment.LEADING,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(jButton4, javax.swing.GroupLayout.Alignment.LEADING,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
								.addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addGap(27, 27, 27).addComponent(jButton1)
						.addGap(18, 18, 18).addComponent(jButton2).addGap(18, 18, 18).addComponent(jButton3)
						.addGap(18, 18, 18).addComponent(jButton4).addGap(18, 18, 18).addComponent(jButton6)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jPanel2 = new javax.swing.JPanel();
		jPanel2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		sku = new javax.swing.JTextField();
		jLabel3 = new javax.swing.JLabel();
		nome = new javax.swing.JTextField();
		jButton5 = new javax.swing.JButton("Excluir");
		jLabel4 = new javax.swing.JLabel();
		CBcategoria = new JComboBox<CategoriasProdutos>();
		jLabel5 = new javax.swing.JLabel();
		preco = new javax.swing.JTextField();
		jButton7 = new javax.swing.JButton();
		jButton8 = new javax.swing.JButton();
		jScrollPane1 = new javax.swing.JScrollPane();

		jLabel1.setFont(new java.awt.Font("Noto Sans", 0, 18));
		jLabel1.setText("Cadastro de Produtos");

		jLabel2.setText("SKU (Cód)");
		sku.setText("");
		sku.setEditable(false);

		jLabel3.setText("Nome");
		nome.setText("");

		jLabel4.setText("Categoria");
		try {
			for (CategoriasProdutos cat : CategoriasProdutos.values()) {
				CBcategoria.addItem(cat);
			}
		} catch (Exception e) {
		}

		jLabel5.setText("Preço unitário padrão");
		preco.setText("");

		jButton8.setText("Salvar");
		jButton8.addActionListener(this::jButton8ActionPerformed);
		jButton7.setText("Novo");
		jButton7.addActionListener(this::jButton7ActionPerformed);
		jButton5.setText("Excluir");
		jButton5.addActionListener(this::jButton5ActionPerformed);

		String[] colunas = { "Código", "Nome", "Categoria", "Preço", "Qtd" };
		tableModel = new DefaultTableModel(colunas, 0) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tabelaProdutos = new JTable(tableModel);
		tabelaProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jScrollPane1.setViewportView(tabelaProdutos);

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addGroup(jPanel2Layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jPanel2Layout.createSequentialGroup()
								.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jLabel4).addComponent(jLabel5))
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
								.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
										.addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(javax.swing.GroupLayout.Alignment.LEADING,
												jPanel2Layout.createSequentialGroup().addGroup(jPanel2Layout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING,
																false)
														.addComponent(jButton8)
														.addComponent(preco, javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(CBcategoria,
																javax.swing.GroupLayout.Alignment.LEADING, 0,
																javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(jLabel1,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(jLabel2,
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(sku, javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(jLabel3,
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(nome, javax.swing.GroupLayout.Alignment.LEADING))
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED, 214,
																Short.MAX_VALUE)
														.addGroup(jPanel2Layout
																.createParallelGroup(
																		javax.swing.GroupLayout.Alignment.LEADING)
																.addComponent(jButton7,
																		javax.swing.GroupLayout.Alignment.TRAILING,
																		javax.swing.GroupLayout.PREFERRED_SIZE, 80,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addComponent(jButton5,
																		javax.swing.GroupLayout.Alignment.TRAILING,
																		javax.swing.GroupLayout.PREFERRED_SIZE, 80,
																		javax.swing.GroupLayout.PREFERRED_SIZE))))
								.addGap(16, 16, 16)))));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel2Layout.createSequentialGroup().addContainerGap()
						.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jButton7))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jButton5).addComponent(jLabel2))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(sku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(18, 18, 18).addComponent(jLabel3)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(nome, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jLabel4)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(CBcategoria, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(18, 18, 18).addComponent(jLabel5)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(preco, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(18, 18, 18).addComponent(jButton8).addGap(18, 18, 18).addComponent(jScrollPane1,
								javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(26, Short.MAX_VALUE)));

		cardLayout = new CardLayout();
		painelDireito = new JPanel(cardLayout);

		telaEntrada = new TelaEntrada();

		painelDireito.add(jPanel2, "TELA_PRODUTOS");
		painelDireito.add(telaEntrada, "TELA_ENTRADA");

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(painelDireito, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)
				.addGroup(layout.createSequentialGroup()
						.addComponent(painelDireito, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(0, 0, Short.MAX_VALUE)));

		pack();
	}

	private void jButton1ActionPerformed(ActionEvent e) {
		cardLayout.show(painelDireito, "TELA_PRODUTOS");
		atualizarListaProdutos();
	}

	private void jButton2ActionPerformed(ActionEvent e) {
		cardLayout.show(painelDireito, "TELA_ENTRADA");
		telaEntrada.carregarDados();
	}

	private void jButton3ActionPerformed(ActionEvent e) {
	}

	private Produto obterProdutoDoFormulario() {
		try {
			String nomeTxt = this.nome.getText().trim();
			if (nomeTxt.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Preencha o nome do produto.", "Campo Obrigatório",
						JOptionPane.WARNING_MESSAGE);
				this.nome.requestFocus();
				return null;
			}

			String precoStr = preco.getText().trim().replace(",", ".");
			if (precoStr.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Preencha o preço do produto.", "Campo Obrigatório",
						JOptionPane.WARNING_MESSAGE);
				preco.requestFocus();
				return null;
			}
			double precoVal = Double.parseDouble(precoStr);

			CategoriasProdutos categoria = (CategoriasProdutos) CBcategoria.getSelectedItem();

			return new Produto(nomeTxt, precoVal, categoria);

		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Preço inválido. Digite apenas números.", "Erro de Formato",
					JOptionPane.ERROR_MESSAGE);
			preco.requestFocus();
			return null;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Erro ao ler dados do formulário: " + e.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}

	private void atualizarListaProdutos() {
		ProdutoDAO dao = new ProdutoDAO();
		tableModel.setRowCount(0);

		try {
			List<String> linhas = dao.ler();
			for (String linha : linhas) {
				String[] dados = linha.split(";");
				if (dados.length >= 6) {
					Object[] row = { dados[0], dados[1], dados[5], dados[4], dados[3] };
					tableModel.addRow(row);
				}
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Erro ao ler arquivo de dados: " + e.getMessage());
		}
	}

	private void limparCampos() {
		sku.setText("");
		nome.setText("");
		preco.setText("");
		if (CBcategoria.getItemCount() > 0)
			CBcategoria.setSelectedIndex(0);
		nome.requestFocus();
	}

	private void jButton8ActionPerformed(ActionEvent evnt) {
		Produto produto = obterProdutoDoFormulario();
		if (produto != null) {
			try {
				ProdutoDAO dao = new ProdutoDAO();
				dao.inserir(produto);
				JOptionPane.showMessageDialog(this, "Produto salvo com sucesso!");
				limparCampos();
				atualizarListaProdutos();
			} catch (Exception e) {
				logger.log(java.util.logging.Level.SEVERE, "Erro ao salvar produto", e);
				JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage(), "Erro de Persistência",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void jButton5ActionPerformed(ActionEvent e) {
		int linhaSelecionada = tabelaProdutos.getSelectedRow();
		if (linhaSelecionada == -1) {
			JOptionPane.showMessageDialog(this, "Selecione um produto na tabela para excluir.");
			return;
		}
		String codigoStr = (String) tableModel.getValueAt(linhaSelecionada, 0);
		int codigo = Integer.parseInt(codigoStr);
		int confirm = JOptionPane.showConfirmDialog(this, "Deseja excluir o produto ID " + codigo + "?",
				"Confirmar Exclusão", JOptionPane.YES_NO_OPTION);

		if (confirm == JOptionPane.YES_OPTION) {
			try {
				ProdutoDAO dao = new ProdutoDAO();
				Produto produtoParaDeletar = new Produto(codigo, "", 0.0, 0, null);
				dao.deletar(produtoParaDeletar);
				atualizarListaProdutos();
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(this, "Erro ao excluir: " + ex.getMessage());
			}
		}
	}

	private void jButton7ActionPerformed(ActionEvent e) {
		limparCampos();
	}

	public static void main(String args[]) {
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception ex) {
			logger.log(java.util.logging.Level.SEVERE, null, ex);
		}

		java.awt.EventQueue.invokeLater(() -> new TelaProduto().setVisible(true));
	}

	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton2;
	private javax.swing.JButton jButton3;
	private javax.swing.JButton jButton4;
	private javax.swing.JButton jButton5;
	private javax.swing.JButton jButton6;
	private javax.swing.JButton jButton7;
	private javax.swing.JButton jButton8;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JScrollPane jScrollPane1;
}