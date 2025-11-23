package view;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import dao.ProdutoDAO;
import model.CategoriasProdutos;
import model.Produto;

/**
 * Janela principal da aplicação de Gerenciamento de Estoque.
 * <p>
 * Esta classe estende {@link JFrame} e atua como o container central do sistema.
 * Utiliza um layout de cartões ({@link CardLayout}) para alternar entre os diferentes
 * módulos funcionais (Cadastro de Produtos, Entradas, Saídas, Saldo e Histórico).
 * </p>
 * <p>
 * Além da navegação, esta classe implementa diretamente a lógica de CRUD
 * (Cadastro, Leitura, Atualização e Exclusão) para a entidade {@link Produto}.
 * </p>
 *
 * @author Pedro Henrique Jose
 * @version 1.0
 */
public class TelaPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;

    // Componentes de Cadastro
    private javax.swing.JTextField sku;
    private javax.swing.JTextField nome;
    private javax.swing.JTextField preco;
    private JComboBox<CategoriasProdutos> CBcategoria;

    // Painéis
    private javax.swing.JPanel jPanel1; // Menu Lateral
    private javax.swing.JPanel jPanel2; // Tela de Cadastro de Produtos
    private JPanel painelDireito;       // Container das telas (CardLayout)

    // Tabela
    private JTable tabelaProdutos;
    private DefaultTableModel tableModel;

    // Referências para as outras telas
    private TelaEntrada telaEntrada;
    private TelaSaida telaSaida;
    private TelaConsultaSaldo telaConsultaSaldo;
    private TelaListaMovimentos telaMovimentos;
    private CardLayout cardLayout; // Gerenciador de navegação

    private static final java.util.logging.Logger logger = java.util.logging.Logger
            .getLogger(TelaPrincipal.class.getName());

    /**
     * Construtor da Tela Principal.
     * <p>
     * Inicializa os componentes gráficos, ajusta o contador global de IDs baseado
     * no arquivo existente e define a tela de "Produtos" como a visão inicial.
     * </p>
     */
    public TelaPrincipal() {
        // --- 1. Inicialização do Sistema ---

        // Ajusta o contador de IDs lendo o arquivo para evitar duplicação ao reiniciar
        ProdutoDAO dao = new ProdutoDAO();
        dao.ajustarContadorId();

        initComponents();

        // Define a tela inicial como "Produtos"
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

        // --- Configuração dos Botões do Menu ---
        jButton1.setText("Produtos");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        jButton2.setText("Registrar Entrada");
        jButton2.addActionListener(this::jButton2ActionPerformed);

        jButton3.setText("Registrar Saída");
        jButton3.addActionListener(this::jButton3ActionPerformed);

        jButton4.setText("Consultar Saldo");
        jButton4.addActionListener(this::jButton4ActionPerformed);

        jButton6.setText("Listar Movimentos");
        jButton6.addActionListener(this::jButton6ActionPerformed);

        // Layout do Menu Lateral
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

        // --- Painel de Cadastro (CRUD Produtos) ---
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

        jLabel2.setText("SKU");
        sku.setText("");
        sku.setEditable(false); // Bloqueado: ID é automático

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

        // Botões de Ação do CRUD
        jButton8.setText("Salvar");
        jButton8.addActionListener(this::jButton8ActionPerformed);

        jButton7.setText("Novo");
        jButton7.addActionListener(this::jButton7ActionPerformed);

        jButton5.setText("Excluir");
        jButton5.addActionListener(this::jButton5ActionPerformed);

        // --- Configuração da Tabela ---
        String[] colunas = { "Código", "Nome", "Categoria", "Preço", "Qtd" };
        tableModel = new DefaultTableModel(colunas, 0) {
            private static final long serialVersionUID = 1L;
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaProdutos = new JTable(tableModel);
        tabelaProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tabelaProdutos.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabelaProdutos.getColumnModel().getColumn(1).setPreferredWidth(100);
        tabelaProdutos.getColumnModel().getColumn(2).setPreferredWidth(120);
        tabelaProdutos.getColumnModel().getColumn(4).setPreferredWidth(40);

        jScrollPane1.setViewportView(tabelaProdutos);

        // --- Botão Editar (Carrega dados para o form) ---
        JButton btnEditar = new JButton("Editar");
        btnEditar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int linhaSelecionada = tabelaProdutos.getSelectedRow();
                if (linhaSelecionada == -1) {
                    JOptionPane.showMessageDialog(null, "Selecione um produto na tabela para editar.");
                    return;
                }

                // Pega os dados da linha selecionada
                String codigoStr = String.valueOf(tableModel.getValueAt(linhaSelecionada, 0));
                String nomeStr = String.valueOf(tableModel.getValueAt(linhaSelecionada, 1));
                String categoriaStr = String.valueOf(tableModel.getValueAt(linhaSelecionada, 2));
                String precoStr = String.valueOf(tableModel.getValueAt(linhaSelecionada, 3));

                // Preenche os campos
                sku.setText(codigoStr);
                sku.setEnabled(false); // ID não pode mudar na edição

                nome.setText(nomeStr);
                String precoLimpo = precoStr.replace("R$", "").replace(" ", "").replace(".", ",");
                preco.setText(precoLimpo);

                try {
                    CategoriasProdutos catEnum = CategoriasProdutos.valueOf(categoriaStr);
                    CBcategoria.setSelectedItem(catEnum);
                } catch (Exception ex) {
                    CBcategoria.setSelectedIndex(0);
                }

                nome.requestFocus();
            }
        });

        // Layout do Painel de Produtos
        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(Alignment.TRAILING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING)
                                                        .addComponent(jLabel4)
                                                        .addComponent(jLabel5))
                                                .addContainerGap(382, Short.MAX_VALUE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING)
                                                        .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addGroup(jPanel2Layout.createParallelGroup(Alignment.TRAILING, false)
                                                                        .addComponent(jButton8)
                                                                        .addComponent(preco, Alignment.LEADING)
                                                                        .addComponent(CBcategoria, Alignment.LEADING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addComponent(jLabel1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addComponent(jLabel2, Alignment.LEADING)
                                                                        .addComponent(sku, Alignment.LEADING)
                                                                        .addComponent(jLabel3, Alignment.LEADING)
                                                                        .addComponent(nome, Alignment.LEADING))
                                                                .addPreferredGap(ComponentPlacement.RELATED, 214, Short.MAX_VALUE)
                                                                .addGroup(jPanel2Layout.createParallelGroup(Alignment.TRAILING)
                                                                        .addComponent(jButton7, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(jButton5, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(btnEditar, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))))
                                                .addGap(16))))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton7))
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING)
                                        .addComponent(jButton5)
                                        .addComponent(jLabel2))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(sku, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(18)
                                                .addComponent(jLabel3)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(nome, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel4)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(CBcategoria, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(18)
                                                .addComponent(jLabel5)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(preco, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(18)
                                                .addComponent(jButton8))
                                        .addComponent(btnEditar))
                                .addGap(18)
                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel2.setLayout(jPanel2Layout);

        // --- Configuração da Navegação (CardLayout) ---
        cardLayout = new CardLayout();
        painelDireito = new JPanel(cardLayout);

        // Cria as telas secundárias
        telaEntrada = new TelaEntrada();
        telaSaida = new TelaSaida();
        telaConsultaSaldo = new TelaConsultaSaldo();
        telaMovimentos = new TelaListaMovimentos();

        // Adiciona ao container principal
        painelDireito.add(jPanel2, "TELA_PRODUTOS");
        painelDireito.add(telaEntrada, "TELA_ENTRADA");
        painelDireito.add(telaSaida, "TELA_SAIDA");
        painelDireito.add(telaConsultaSaldo, "TELA_CONSULTA_SALDO");
        painelDireito.add(telaMovimentos, "TELA_MOVIMENTOS");

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

    // --- Métodos de Navegação ---

    private void jButton1ActionPerformed(ActionEvent e) {
        cardLayout.show(painelDireito, "TELA_PRODUTOS");
        atualizarListaProdutos(); // Recarrega lista
    }

    private void jButton2ActionPerformed(ActionEvent e) {
        cardLayout.show(painelDireito, "TELA_ENTRADA");
        telaEntrada.carregarDados(); // Carrega combo box
    }

    private void jButton3ActionPerformed(ActionEvent e) {
        cardLayout.show(painelDireito, "TELA_SAIDA");
        telaSaida.carregarDados(); // Carrega combo box
    }

    private void jButton4ActionPerformed(ActionEvent e) {
        cardLayout.show(painelDireito, "TELA_CONSULTA_SALDO");
        // Carrega o saldo atual (padrão)
        telaConsultaSaldo.carregarDados("");
    }

    private void jButton6ActionPerformed(ActionEvent e) {
        cardLayout.show(painelDireito, "TELA_MOVIMENTOS");
        telaMovimentos.carregarDados(); // Recarrega lista de histórico
    }

    // --- Métodos de CRUD (Lógica de Produtos) ---

    /**
     * Lê o arquivo de produtos via DAO e preenche a tabela visual.
     */
    private void atualizarListaProdutos() {
        ProdutoDAO dao = new ProdutoDAO();
        tableModel.setRowCount(0);

        try {
            List<String> linhas = dao.ler();

            for (String linha : linhas) {
                Produto p = new Produto().fromCSV(linha);
                Object[] row = { p.getCodigo(), p.getNome(), p.getCategoria(), p.getPreco(), p.getQuantidade() };
                tableModel.addRow(row);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao ler arquivo: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ignorando linha inválida: " + e.getMessage());
        }
    }

    /**
     * Limpa o formulário para permitir um novo cadastro.
     */
    private void limparCampos() {
        sku.setText("");
        sku.setEnabled(true); // Libera o campo (embora ele seja auto-gerado)
        nome.setText("");
        preco.setText("");
        if (CBcategoria.getItemCount() > 0)
            CBcategoria.setSelectedIndex(0);
        nome.requestFocus();
    }

    /**
     * Valida os campos e cria o objeto Produto.
     */
    private Produto obterProdutoDoFormulario() {
        try {
            String nomeTxt = this.nome.getText().trim();
            if (nomeTxt.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha o nome do produto.", "Campo Obrigatório",
                        JOptionPane.WARNING_MESSAGE);
                this.nome.requestFocus();
                return null;
            }

            // Tratamento do preço (virgula para ponto, remove R$)
            String precoStr = preco.getText().trim().replace("R$", "").replace(" ", "").replace(".", "").replace(",", ".");
            if (precoStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha o preço.", "Campo Obrigatório",
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

    /**
     * Botão SALVAR:
     * - Se SKU vazio: Insere novo.
     * - Se SKU preenchido: Atualiza existente (mantendo o estoque).
     */
    private void jButton8ActionPerformed(ActionEvent evnt) {

        Produto produtoBase = obterProdutoDoFormulario();
        if(produtoBase == null) return; // Parar se validação falhou

        try {
            ProdutoDAO dao = new ProdutoDAO();
            String skuTxt = sku.getText();

            // Lógica: Se SKU está vazio, é um produto NOVO
            if (skuTxt.isEmpty()) {
                dao.inserir(produtoBase);
                JOptionPane.showMessageDialog(this, "Produto cadastrado com sucesso!");

            } else {
                // Se tem SKU, é EDIÇÃO
                int idExistente = Integer.parseInt(skuTxt);

                // IMPORTANTE: Recupera a quantidade atual do estoque para não zerar na edição
                int qtdAtual = 0;
                List<String> linhas = dao.ler();
                for(String linha : linhas) {
                    String[] dados = linha.split(";");
                    if(dados.length > 0 && Integer.parseInt(dados[0]) == idExistente) {
                        qtdAtual = Integer.parseInt(dados[3]);
                        break;
                    }
                }

                produtoBase.setCodigo(idExistente);
                produtoBase.setQuantidade(qtdAtual); // Mantém estoque

                dao.atualizar(produtoBase);
                JOptionPane.showMessageDialog(this, "Produto atualizado com sucesso!");
            }

            limparCampos();
            atualizarListaProdutos();

        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Erro ao salvar produto", e);
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage());
        }
    }

    /**
     * Botão EXCLUIR: Remove o produto selecionado.
     */
    private void jButton5ActionPerformed(ActionEvent e) {
        int linhaSelecionada = tabelaProdutos.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um produto na tabela para excluir.");
            return;
        }

        Object valorCelula = tableModel.getValueAt(linhaSelecionada, 0);
        String codigoStr = String.valueOf(valorCelula);
        int codigo = Integer.parseInt(codigoStr);

        // Confirmação de segurança
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

    /**
     * Botão NOVO: Limpa formulário.
     */
    private void jButton7ActionPerformed(ActionEvent e) {
        limparCampos();
    }

    // Declaração de variáveis dos componentes Swing
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