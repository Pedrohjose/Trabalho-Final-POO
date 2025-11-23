package view;

import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import dao.MovimentacaoDAO;

/**
 * Painel de interface gráfica que exibe o histórico de movimentações de estoque.
 * <p>
 * Atende aos requisitos:
 * <ul>
 * <li><b>6. Listar entradas:</b> Filtro por tipo "ENTRADA".</li>
 * <li><b>7. Listar saídas:</b> Filtro por tipo "SAÍDA".</li>
 * <li><b>8. Extrato ordenado:</b> Lista movimentos ordenados por data, mostrando o impacto no saldo.</li>
 * </ul>
 * </p>
 *
 * @author Pedro Jose
 */
public class TelaListaMovimentos extends JPanel {

    private static final long serialVersionUID = 1L;

    private JTable tabelaMovimentos;
    private DefaultTableModel tableModel;
    private JButton btnRecarregar;
    private JComboBox<String> cbFiltroTipo;

    private SimpleDateFormat sdfCsv = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat sdfTela = new SimpleDateFormat("dd/MM/yyyy");

    public TelaListaMovimentos() {
        setLayout(null);

        JLabel lblTitulo = new JLabel("Histórico / Extrato");
        lblTitulo.setBounds(10, 10, 300, 30);
        lblTitulo.setFont(new Font("Noto Sans", Font.BOLD, 18));
        add(lblTitulo);

        // --- Filtro de Tipo ---
        JLabel lblFiltrar = new JLabel("Exibir:");
        lblFiltrar.setBounds(10, 50, 50, 25);
        add(lblFiltrar);

        cbFiltroTipo = new JComboBox<>(new String[] { "TODOS", "ENTRADA", "SAÍDA" });
        cbFiltroTipo.setBounds(60, 50, 150, 25);
        add(cbFiltroTipo);

        // Botão Atualizar
        btnRecarregar = new JButton("Filtrar / Atualizar");
        btnRecarregar.setBounds(220, 50, 150, 25);
        btnRecarregar.addActionListener(e -> carregarDados());
        add(btnRecarregar);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 90, 580, 260);
        add(scrollPane);

        String[] colunas = { "ID", "Prod ID", "Data", "Impacto Qtd", "Valor Total", "Tipo / Motivo" };

        tableModel = new DefaultTableModel(colunas, 0) {
            private static final long serialVersionUID = 1L;
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        tabelaMovimentos = new JTable(tableModel);
        tabelaMovimentos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tabelaMovimentos.getColumnModel().getColumn(0).setPreferredWidth(30);
        tabelaMovimentos.getColumnModel().getColumn(1).setPreferredWidth(50);
        tabelaMovimentos.getColumnModel().getColumn(2).setPreferredWidth(80);
        tabelaMovimentos.getColumnModel().getColumn(3).setPreferredWidth(75); // Ajustado para exibir cabeçalho
        tabelaMovimentos.getColumnModel().getColumn(4).setPreferredWidth(90);
        tabelaMovimentos.getColumnModel().getColumn(5).setPreferredWidth(150);

        scrollPane.setViewportView(tabelaMovimentos);

        carregarDados();
    }

    /**
     * Carrega as movimentações do arquivo, aplica ordenação e filtros.
     * <p>
     * Os dados são ordenados cronologicamente (Requisito 8).
     * O impacto na quantidade é exibido com sinais (+/-).
     * </p>
     */
    public void carregarDados() {
        MovimentacaoDAO dao = new MovimentacaoDAO();
        tableModel.setRowCount(0);

        String filtroSelecionado = (String) cbFiltroTipo.getSelectedItem();

        try {
            List<String> linhas = dao.ler();

            // Ordenação por data (Requisito 8)
            Collections.sort(linhas, new Comparator<String>() {
                @Override
                public int compare(String s1, String s2) {
                    try {
                        String data1 = s1.split(";")[3];
                        String data2 = s2.split(";")[3];
                        return data1.compareTo(data2);
                    } catch (Exception e) { return 0; }
                }
            });

            for (String linha : linhas) {
                String[] dados = linha.split(";", -1);

                if (dados.length >= 5) {
                    try {
                        double valorTotal = Double.parseDouble(dados[2]);
                        int qtd = Integer.parseInt(dados[4]);

                        String tipo = "ENTRADA";
                        if (dados.length >= 6 && !dados[5].trim().isEmpty()) {
                            tipo = dados[5];
                        }

                        // Filtro de seleção (Requisitos 6 e 7)
                        if (!filtroSelecionado.equals("TODOS")) {
                            if (filtroSelecionado.equals("ENTRADA") && !tipo.equals("ENTRADA")) continue;
                            if (filtroSelecionado.equals("SAÍDA") && tipo.equals("ENTRADA")) continue;
                        }

                        // Formatação de data visual
                        String dataVisual = dados[3];
                        try {
                            Date dataObj = sdfCsv.parse(dados[3]);
                            dataVisual = sdfTela.format(dataObj);
                        } catch (Exception e) {}

                        // Definição do sinal de impacto (+/-)
                        String impactoQtd;
                        if (tipo.equalsIgnoreCase("ENTRADA")) {
                            impactoQtd = "+ " + qtd;
                        } else {
                            impactoQtd = "- " + qtd;
                        }

                        tableModel.addRow(new Object[] {
                                dados[0],
                                dados[1],
                                dataVisual,
                                impactoQtd,
                                String.format("R$ %.2f", valorTotal),
                                tipo
                        });
                    } catch (NumberFormatException e) {
                        // Ignora erro
                    }
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar histórico: " + e.getMessage());
        }
    }
}