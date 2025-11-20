package view;

import java.awt.Font;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import dao.MovimentacaoDAO;

public class TelaListaMovimentos extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JTable tabelaMovimentos;
	private DefaultTableModel tableModel;
	private JButton btnRecarregar;

	public TelaListaMovimentos() {
		setLayout(null);

		JLabel lblTitulo = new JLabel("Histórico de Movimentações");
		lblTitulo.setBounds(10, 10, 300, 30);
		lblTitulo.setFont(new Font("Noto Sans", Font.BOLD, 18));
		add(lblTitulo);

		btnRecarregar = new JButton("Atualizar Lista");
		btnRecarregar.setBounds(330, 15, 150, 25);
		btnRecarregar.addActionListener(e -> carregarDados());
		add(btnRecarregar);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 50, 472, 260);
		add(scrollPane);

		String[] colunas = { "ID", "Cód. Prod", "Data", "Qtd", "Valor Total", "Tipo / Motivo" };
		tableModel = new DefaultTableModel(colunas, 0) {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		
		tabelaMovimentos = new JTable(tableModel);
		tabelaMovimentos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		tabelaMovimentos.getColumnModel().getColumn(0).setPreferredWidth(30);  
		tabelaMovimentos.getColumnModel().getColumn(1).setPreferredWidth(60);  
		tabelaMovimentos.getColumnModel().getColumn(2).setPreferredWidth(80);  
		tabelaMovimentos.getColumnModel().getColumn(3).setPreferredWidth(40);  
		tabelaMovimentos.getColumnModel().getColumn(5).setPreferredWidth(150); 
		
		scrollPane.setViewportView(tabelaMovimentos);

		carregarDados();
	}

	public void carregarDados() {
		MovimentacaoDAO dao = new MovimentacaoDAO();
		tableModel.setRowCount(0);
		
		try {
			List<String> linhas = dao.ler();
			
			for (int i = linhas.size() - 1; i >= 0; i--) {
				String linha = linhas.get(i);
				
				String[] dados = linha.split(";", -1);
				
				if (dados.length >= 5) {
					try {
						double valorTotal = Double.parseDouble(dados[2]);
						
						String tipo = "ENTRADA";
						if (dados.length >= 6 && !dados[5].trim().isEmpty()) {
							tipo = dados[5];
						}
						
						String quantidadeFormatada = dados[4];
						
						tableModel.addRow(new Object[] { 
							dados[0],                        
							dados[1],                        
							dados[3],                        
							quantidadeFormatada,                        
							String.format("R$ %.2f", valorTotal), 
							tipo                        
						});
					} catch (NumberFormatException e) {
						
					}
				}
			}
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Erro ao carregar histórico: " + e.getMessage());
		}
	}
}