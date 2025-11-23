package view;

import java.awt.Color;
import java.awt.Font;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import dao.ProdutoDAO;
import dao.MovimentacaoDAO;

/**
 * Painel de interface gráfica para consulta de saldo e estoque de produtos.
 *
 * <p>Esta classe é responsável por apresentar a visão geral do estoque, atendendo
 * a dois requisitos principais:</p>
 * <ul>
 * <li><b>Saldo Atual (Requisito 4):</b> Exibe a quantidade e valor total dos produtos
 * conforme constam no arquivo de cadastro.</li>
 * <li><b>Saldo por Período (Requisito 5):</b> Calcula a movimentação (Entradas - Saídas)
 * dentro de um intervalo de datas específico.</li>
 * </ul>
 *
 * <p>O painel inclui filtros por nome e data, e exibe o valor total do patrimônio
 * em estoque no rodapé.</p>
 *
 * @author Pedro Jose
 * @version 1.0
 */
public class TelaConsultaSaldo extends JPanel {

    private static final long serialVersionUID = 1L;

    /** Tabela para exibição dos dados */
    private JTable tabelaSaldo;

    /** Modelo da tabela */
    private DefaultTableModel tableModel;

    /** Campo de texto para filtro por nome */
    private JTextField txtPesquisa;

    /** Label que exibe o valor total do patrimônio */
    private JLabel lblValorTotalEstoque;

    private JButton btnBuscar;
    private JButton btnRecarregar;

    /** Campos formatados para data de início e fim */
    private JFormattedTextField txtDataInicio;
    private JFormattedTextField txtDataFim;

    // Formatadores de data para exibição (Tela) e persistência (CSV)
    private SimpleDateFormat sdfTela = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat sdfCsv = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Construtor da tela de Consulta de Saldo.
     * <p>Inicializa todos os componentes gráficos, configura a tabela, os filtros de data
     * e carrega o estado inicial (Saldo Atual).</p>
     */
    public TelaConsultaSaldo() {
        setLayout(null);

        JLabel lblTitulo = new JLabel("Consulta de Saldo e Estoque");
        lblTitulo.setBounds(10, 10, 300, 30);
        lblTitulo.setFont(new Font("Noto Sans", Font.BOLD, 18));
        add(lblTitulo);

        // --- Configuração dos Filtros ---
        JLabel lblPesquisa = new JLabel("Nome:");
        lblPesquisa.setBounds(10, 50, 50, 20);
        add(lblPesquisa);

        txtPesquisa = new JTextField();
        txtPesquisa.setBounds(10, 70, 140, 30);
        add(txtPesquisa);

        try {
            MaskFormatter maskData = new MaskFormatter("##/##/####");
            maskData.setPlaceholderCharacter('_');

            JLabel lblDataIni = new JLabel("Data Início:");
            lblDataIni.setBounds(160, 50, 80, 20);
            add(lblDataIni);

            txtDataInicio = new JFormattedTextField(maskData);
            txtDataInicio.setBounds(160, 70, 80, 30);
            add(txtDataInicio);

            JLabel lblDataFim = new JLabel("Data Fim:");
            lblDataFim.setBounds(250, 50, 80, 20);
            add(lblDataFim);

            txtDataFim = new JFormattedTextField(maskData);
            txtDataFim.setBounds(250, 70, 80, 30);
            add(txtDataFim);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        // --- Configuração dos Botões ---
        btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(340, 70, 80, 30);
        btnBuscar.addActionListener(e -> executarBusca());
        add(btnBuscar);

        btnRecarregar = new JButton("Limpar");
        btnRecarregar.setBounds(430, 70, 80, 30);
        btnRecarregar.addActionListener(e -> {
            txtPesquisa.setText("");
            txtDataInicio.setValue(null);
            txtDataInicio.setText("");
            txtDataFim.setValue(null);
            txtDataFim.setText("");
            carregarDados(""); // Retorna ao estado padrão (Saldo Atual)
        });
        add(btnRecarregar);

        // --- Configuração da Tabela ---
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 110, 580, 200);
        add(scrollPane);

        String[] colunas = { "Cód", "Nome", "Data Ref.", "Categoria", "Preço Unit.", "Qntd Liq.", "Total Item" };

        tableModel = new DefaultTableModel(colunas, 0) {
            private static final long serialVersionUID = 1L;
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        tabelaSaldo = new JTable(tableModel);
        tabelaSaldo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Ajuste das larguras das colunas
        tabelaSaldo.getColumnModel().getColumn(0).setPreferredWidth(10);
        tabelaSaldo.getColumnModel().getColumn(1).setPreferredWidth(40);
        tabelaSaldo.getColumnModel().getColumn(2).setPreferredWidth(40);
        tabelaSaldo.getColumnModel().getColumn(3).setPreferredWidth(40);
        tabelaSaldo.getColumnModel().getColumn(4).setPreferredWidth(40);
        tabelaSaldo.getColumnModel().getColumn(5).setPreferredWidth(40);

        scrollPane.setViewportView(tabelaSaldo);

        // --- Configuração do Rodapé (Valor Total) ---
        JLabel lblTextoTotal = new JLabel("Valor Total do Patrimônio em Estoque:");
        lblTextoTotal.setBounds(10, 320, 250, 20);
        lblTextoTotal.setFont(new Font("Noto Sans", Font.BOLD, 12));
        add(lblTextoTotal);

        lblValorTotalEstoque = new JLabel("R$ 0,00");
        lblValorTotalEstoque.setBounds(260, 320, 200, 20);
        lblValorTotalEstoque.setFont(new Font("Noto Sans", Font.BOLD, 14));
        lblValorTotalEstoque.setForeground(new Color(0, 100, 0));
        add(lblValorTotalEstoque);

        // Carrega o estado inicial
        carregarDados("");
    }

    /**
     * Determina qual método de busca executar.
     * <p>Se os campos de data estiverem preenchidos, executa a busca por período (Requisito 5).
     * Caso contrário, executa a busca de saldo atual (Requisito 4).</p>
     */
    private void executarBusca() {
        String dtIni = txtDataInicio.getText();
        String dtFim = txtDataFim.getText();
        String nome = txtPesquisa.getText();

        boolean temData = dtIni != null && !dtIni.equals("__/__/____")
                && dtFim != null && !dtFim.equals("__/__/____");

        if (temData) {
            carregarDadosPorPeriodo(nome, dtIni, dtFim);
        } else {
            carregarDados(nome);
        }
    }

    /**
     * Carrega os dados do estoque atual (Requisito 4).
     * <p>Lê diretamente do arquivo de produtos ({@link ProdutoDAO}), que representa o estado atual.</p>
     *
     * @param filtro Texto para filtrar produtos pelo nome.
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

                    // Aplica filtro por nome
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
                            dados[2],
                            dados[5],
                            String.format("R$ %.2f", preco),
                            qtd,
                            String.format("R$ %.2f", totalItem)
                    });
                }
            }
            lblValorTotalEstoque.setText(String.format("R$ %.2f", somaTotalEstoque));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao calcular saldo atual: " + e.getMessage());
        }
    }

    /**
     * Carrega o saldo calculado por período (Requisito 5).
     * <p>Lê o arquivo de movimentações ({@link MovimentacaoDAO}) e calcula o saldo
     * (Entradas - Saídas) para cada produto dentro das datas informadas.</p>
     *
     * @param filtroNome Filtro por nome do produto.
     * @param dataInicioStr Data inicial do período.
     * @param dataFimStr Data final do período.
     */
    public void carregarDadosPorPeriodo(String filtroNome, String dataInicioStr, String dataFimStr) {
        tableModel.setRowCount(0);
        double somaTotalPeriodo = 0.0;

        try {
            Date dataInicio = sdfTela.parse(dataInicioStr);
            Date dataFim = sdfTela.parse(dataFimStr);

            String periodoTexto = dataInicioStr.substring(0, 5) + " à " + dataFimStr.substring(0, 5);

            // 1. Mapeia produtos base para obter nomes e preços
            Map<Integer, String[]> mapProdutos = new HashMap<>();
            ProdutoDAO pDao = new ProdutoDAO();
            for(String linha : pDao.ler()) {
                String[] d = linha.split(";");
                mapProdutos.put(Integer.parseInt(d[0]), new String[]{ d[1], d[4], d[5] });
            }

            // 2. Lê movimentações e filtra por data
            MovimentacaoDAO mDao = new MovimentacaoDAO();
            List<String> transacoes = mDao.ler();

            Map<Integer, Integer> saldoPeriodo = new HashMap<>();

            for (String linha : transacoes) {
                String[] t = linha.split(";");
                if (t.length < 6) continue;

                int idProd = Integer.parseInt(t[1]);
                if (!mapProdutos.containsKey(idProd)) continue;

                String nomeProd = mapProdutos.get(idProd)[0];
                if (!filtroNome.isEmpty() && !nomeProd.toLowerCase().contains(filtroNome.toLowerCase())) {
                    continue;
                }

                Date dataTransacao = sdfCsv.parse(t[3]);

                // Verifica intervalo de datas
                if (dataTransacao.compareTo(dataInicio) >= 0 && dataTransacao.compareTo(dataFim) <= 0) {

                    int qtd = Integer.parseInt(t[4]);
                    String tipo = t[5].trim();

                    saldoPeriodo.putIfAbsent(idProd, 0);
                    int saldoAtual = saldoPeriodo.get(idProd);

                    // Lógica de saldo: Entrada soma, Saída subtrai
                    if (tipo.equalsIgnoreCase("ENTRADA")) {
                        saldoPeriodo.put(idProd, saldoAtual + qtd);
                    } else if (tipo.contains("VENDA")) {
                        saldoPeriodo.put(idProd, saldoAtual - qtd);
                    }
                }
            }

            // 3. Preenche a tabela com os resultados calculados
            for (Map.Entry<Integer, Integer> entry : saldoPeriodo.entrySet()) {
                int id = entry.getKey();
                int qtdResultante = entry.getValue();

                if (mapProdutos.containsKey(id)) {
                    String[] dadosProd = mapProdutos.get(id);

                    double preco = Double.parseDouble(dadosProd[1]);
                    double total = qtdResultante * preco;

                    somaTotalPeriodo += total;

                    tableModel.addRow(new Object[] {
                            id,
                            dadosProd[0],
                            periodoTexto,
                            dadosProd[2],
                            String.format("R$ %.2f", preco),
                            qtdResultante,
                            String.format("R$ %.2f", total)
                    });
                }
            }

            lblValorTotalEstoque.setText(String.format("R$ %.2f", somaTotalPeriodo));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao processar período: " + e.getMessage());
        }
    }
}