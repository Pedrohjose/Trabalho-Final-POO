package view;

import java.awt.Color;
import java.awt.Font;
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

import dao.ProdutoDAO;

/**
 * Painel de interface gráfica para consulta de saldo e estoque de produtos.
 *
 * <p>Exibe uma tabela com os produtos cadastrados, permitindo filtrar por nome
 * e calcular o valor total do estoque. A tabela mostra as colunas:</p>
 * <ul>
 *     <li>Cód: Código do produto</li>
 *     <li>Nome: Nome do produto</li>
 *     <li>Categoria: Categoria do produto</li>
 *     <li>Preço Unit.: Preço unitário do produto</li>
 *     <li>Qntd: Quantidade em estoque</li>
 *     <li>Total Item: Valor total de cada item (preço * quantidade)</li>
 * </ul>
 *
 * <p>O painel também exibe o valor total do patrimônio em estoque e possui
 * botões para buscar e limpar filtros.</p>
 *
 * @author Pedro Jose
 */
public class TelaConsultaSaldo extends JPanel {

    private static final long serialVersionUID = 1L;

    /** Botao da tabela saldo */
    private JTable tabelaSaldo;

    /** Instancia da Tabela */
    private DefaultTableModel tableModel;

    /** Campo para pesquisa no filtro */
    private JTextField txtPesquisa;

    /** Campo que retorna a valor total do estoque */
    private JLabel lblValorTotalEstoque;

    /** Botao de busca */
    private JButton btnBuscar;

    /** Botao de recarregar a tabela */
    private JButton btnRecarregar;

    /**
     * Construtor da tela de consulta de saldo.
     * <p>Inicializa todos os componentes visuais e carrega os dados do estoque.</p>
     */
    public TelaConsultaSaldo() {
        setLayout(null);

        JLabel lblTitulo = new JLabel("Consulta de Saldo e Estoque");
        lblTitulo.setBounds(10, 10, 300, 30);
        lblTitulo.setFont(new Font("Noto Sans", Font.BOLD, 18));
        add(lblTitulo);

        JLabel lblPesquisa = new JLabel("Filtrar por Nome:");
        lblPesquisa.setBounds(10, 50, 120, 20);
        add(lblPesquisa);

        txtPesquisa = new JTextField();
        txtPesquisa.setBounds(10, 70, 200, 30);
        add(txtPesquisa);

        btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(239, 70, 100, 30);
        btnBuscar.addActionListener(e -> carregarDados(txtPesquisa.getText()));
        add(btnBuscar);

        btnRecarregar = new JButton("Limpar Filtro");
        btnRecarregar.setBounds(355, 70, 130, 30);
        btnRecarregar.addActionListener(e -> {
            txtPesquisa.setText("");
            carregarDados("");
        });
        add(btnRecarregar);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 110, 472, 200);
        add(scrollPane);

        String[] colunas = { "Cód", "Nome", "Categoria", "Preço Unit.", "Qntd", "Total Item" };
        tableModel = new DefaultTableModel(colunas, 0) {
            private static final long serialVersionUID = 1L;
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        tabelaSaldo = new JTable(tableModel);
        tabelaSaldo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tabelaSaldo.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabelaSaldo.getColumnModel().getColumn(1).setPreferredWidth(100);
        tabelaSaldo.getColumnModel().getColumn(2).setPreferredWidth(120);
        tabelaSaldo.getColumnModel().getColumn(4).setPreferredWidth(40);

        scrollPane.setViewportView(tabelaSaldo);

        JLabel lblTextoTotal = new JLabel("Valor Total do Patrimônio em Estoque:");
        lblTextoTotal.setBounds(10, 320, 250, 20);
        lblTextoTotal.setFont(new Font("Noto Sans", Font.BOLD, 12));
        add(lblTextoTotal);

        lblValorTotalEstoque = new JLabel("R$ 0,00");
        lblValorTotalEstoque.setBounds(260, 320, 200, 20);
        lblValorTotalEstoque.setFont(new Font("Noto Sans", Font.BOLD, 14));
        lblValorTotalEstoque.setForeground(new Color(0, 100, 0));
        add(lblValorTotalEstoque);

        carregarDados("");
    }

    /**
     * Carrega os dados do estoque na tabela, aplicando um filtro opcional pelo nome.
     *
     * @param filtro Texto para filtrar produtos pelo nome (case-insensitive). Use "" para não filtrar.
     */
    public void carregarDados(String filtro) {
        ProdutoDAO dao = new ProdutoDAO();
        tableModel.setRowCount(0);

        double somaTotalEstoque = 0.0;

        try {
            List<String> linhas = dao.ler();

            for (String linha : linhas) {
                String[] dados = linha.split(";");

                if (dados.length >= 6) {
                    String nome = dados[1];

                    if (!filtro.isEmpty() && !nome.toLowerCase().contains(filtro.toLowerCase())) {
                        continue;
                    }

                    int qtd = Integer.parseInt(dados[3]);
                    double preco = Double.parseDouble(dados[4]);
                    double totalItem = qtd * preco;

                    somaTotalEstoque += totalItem;

                    tableModel.addRow(new Object[] {
                            dados[0],
                            nome,
                            dados[5],
                            String.format("R$ %.2f", preco),
                            qtd,
                            String.format("R$ %.2f", totalItem)
                    });
                }
            }

            lblValorTotalEstoque.setText(String.format("R$ %.2f", somaTotalEstoque));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao calcular saldo: " + e.getMessage());
        }
    }

    /**
     * Atualiza a tabela e limpa qualquer filtro aplicado.
     */
    public void atualizarTela() {
        txtPesquisa.setText("");
        carregarDados("");
    }
}
