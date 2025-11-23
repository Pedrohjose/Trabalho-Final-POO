package model;

import java.time.LocalDate;
import model.CategoriasProdutos;


/**
 * Representa um produto no sistema de inventário.
 *
 * <p>Implementa {@link GerenciaCSV} para permitir a conversão
 * para CSV e reconstrução a partir de linhas CSV.</p>
 *
 * <p>Possui atributos como código, nome, preço, quantidade e categoria.</p>
 *
 * <p>O código do produto é gerenciado automaticamente pelo atributo
 * estático {@link #control}, que é incrementado a cada novo produto criado.</p>
 *
 * @author Carlos e Pedro Jose
 */
public class Produto implements GerenciaCSV {
    // Método principal que inicia a aplicação.

    /** Código único do produto */
    private int codigo;

    /** Nome do produto */
    private String nome;

    /** Preço do produto */
    private double preco;

    /** Quantidade em estoque */
    private int quantidade;

    /** Categoria do produto */
    private CategoriasProdutos categoria;

    /** Controlador estático para geração automática de códigos */
    private static int control = 0;

    /**
     * Construtor de produto com código automático.
     *
     * @param nome Nome do produto
     * @param preco Preço do produto
     * @param categoria Categoria do produto
     */
    public Produto(String nome, double preco, CategoriasProdutos categoria) {
        this.codigo = control++;
        this.nome = nome;
        this.preco = preco;
        this.quantidade = 0;
        this.categoria = categoria;
    }

    /**
     * Construtor de produto com todos os atributos definidos.
     *
     * @param codigo Código do produto
     * @param nome Nome do produto
     * @param preco Preço do produto
     * @param quantidade Quantidade em estoque
     * @param categoria Categoria do produto
     */
    public Produto(int codigo, String nome, double preco, int quantidade, CategoriasProdutos categoria) {
        this.codigo = codigo;
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.categoria = categoria;
    }

    /** Construtor padrão. */
    public Produto() {}

    /**
     * Converte o produto em uma linha CSV.
     *
     * @return String CSV representando o produto
     */
    @Override
    public String toCSV() {
        StringBuilder sb = new StringBuilder();
        sb.append(codigo).append(";");
        sb.append(nome).append(";");
        sb.append(LocalDate.now()).append(";");
        sb.append(quantidade).append(";");
        sb.append(preco).append(";");
        sb.append(categoria).append("");
        return sb.toString();
    }

    /**
     * Cria um produto a partir de uma linha CSV.
     *
     * @param linha Linha CSV com os dados do produto
     * @return Instância de {@link Produto} correspondente à linha CSV
     * @throws IllegalArgumentException Se a linha estiver mal formatada
     */
    public static Produto fromCSV(String linha) {
        try {
            String[] dados = linha.split(";");

            return new Produto(
                    Integer.parseInt(dados[0]),
                    dados[1],
                    Double.parseDouble(dados[4]),
                    Integer.parseInt(dados[3]),
                    CategoriasProdutos.valueOf(dados[5])
            );

        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Erro ao ler linha CSV: " + linha + " | Erro: " + e.getMessage()
            );
        }
    }

    /**
     * Compara dois produtos pelo código.
     *
     * @param obj Objeto a ser comparado
     * @return true se os códigos forem iguais, false caso contrário
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Produto produto = (Produto) obj;
        return codigo == produto.codigo;
    }

    /** Getter Codigo
     * @return Codigo do produto */
    public int getCodigo() { return codigo; }

    /**Setter Codigo
     * Define o código do produto
     * @param codigo
     */
    public void setCodigo(int codigo) { this.codigo = codigo; }

    /** Getter Nome
     * @return Nome do produto
     */
    public String getNome() { return nome; }

    /**Setter nome
     * Define o nome do produto
     * @param nome
     */
    public void setNome(String nome) { this.nome = nome; }

    /**Getter Preco
     * @return Preço do produto */
    public double getPreco() { return preco; }

    /**Setter Preco
     * Define o preço do produto
     * @param preco
     * */
    public void setPreco(double preco) { this.preco = preco; }

    /**Getter Quantidade
     * @return Quantidade em estoque */
    public int getQuantidade() { return quantidade; }

    /**Setter Quantidade
     * Define a quantidade em estoque
     * @param quantidade
     * */
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    /**Getter Categoria
     * @return Categoria do produto */
    public CategoriasProdutos getCategoria() { return categoria; }

    /**Setter Categoria
     * Define a categoria do produto
     * @param categoria
     * */
    public void setCategoria(CategoriasProdutos categoria) { this.categoria = categoria; }

    /** Setter da variavel de controle
     * Define um valor para a variavel de controle
     * @param control
     */
    public static void setControl(int control) {
        Produto.control = control;
    }

    public static int getControl() { return control; }

}