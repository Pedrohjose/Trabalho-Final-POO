package view;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import dao.ProdutoDAO;
import model.CategoriasProdutos;
import model.Produto;

public class TelaProduto extends JFrame {

	private static final long serialVersionUID = 1L;

	private javax.swing.JTextField sku;
	private javax.swing.JTextField nome;
	private javax.swing.JTextField preco;
	private JComboBox<CategoriasProdutos> CBcategoria;
	private JPanel contentPane;

	private static final java.util.logging.Logger logger = java.util.logging.Logger
			.getLogger(TelaProduto.class.getName());

	public TelaProduto() {
		initComponents();
		atualizarListaProdutos();
	}

	private void initComponents() {

		jPanel1 = new javax.swing.JPanel();
		jButton1 = new javax.swing.JButton();
		jButton2 = new javax.swing.JButton();
		jButton3 = new javax.swing.JButton();
		jButton4 = new javax.swing.JButton();
		jButton6 = new javax.swing.JButton();
		jPanel2 = new javax.swing.JPanel();
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
		jTextArea1 = new javax.swing.JTextArea();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

		jPanel2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

		jLabel1.setFont(new java.awt.Font("Noto Sans", 0, 18));
		jLabel1.setText("Cadastro de Produtos");
		jLabel1.setToolTipText("");

		jLabel2.setText("SKU");
		sku.setText("");

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

		jTextArea1.setColumns(20);
		jTextArea1.setRows(5);
		jTextArea1.setText("");

		jTextArea1.setEditable(false);
		jScrollPane1.setViewportView(jTextArea1);

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

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)
				.addGroup(layout.createSequentialGroup()
						.addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(0, 0, Short.MAX_VALUE)));

		pack();
	}

	private Produto obterProdutoDoFormulario() {
		try {
			String nome = this.nome.getText().trim();
			if (nome.isEmpty()) {
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
			double preco = Double.parseDouble(precoStr);

			CategoriasProdutos categoria = (CategoriasProdutos) CBcategoria.getSelectedItem();

			return new Produto(nome, preco, categoria);

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
		StringBuilder sb = new StringBuilder("CÓDIGO; NOME; DATA; PREÇO; CATEGORIA):\n");

		try {
			List<String> linhas = dao.ler();
			if (linhas.isEmpty()) {
				sb.append("Nenhum produto cadastrado.");
			} else {
				for (String linha : linhas) {
					sb.append(linha).append("\n");
				}
			}
		} catch (IOException e) {
			sb.append("Erro ao ler arquivo de dados.");
		}

		jTextArea1.setText(sb.toString());
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

			} catch (Exception e) {
				logger.log(java.util.logging.Level.SEVERE, "Erro ao salvar produto", e);
				JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage(), "Erro de Persistência",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void jButton1ActionPerformed(ActionEvent e) {

		contentPane.setVisible(true);
		contentPane.setVisible(false);

	}

	private void jButton5ActionPerformed(ActionEvent e) {
		// TODO add your handling code here:
	}

	private void jButton2ActionPerformed(ActionEvent e) {
		// TODO add your handling code here:
	}

	private void jButton3ActionPerformed(ActionEvent e) {
		// TODO add your handling code here:
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
		} catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
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
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JTextArea jTextArea1;
}