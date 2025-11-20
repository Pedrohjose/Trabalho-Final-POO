package model;

import java.time.LocalDate;

/**
 * Classe abstrata que representa uma movimentação de produtos no sistema.
 * Pode ser uma entrada ou saída de produto.
 * Implementa a interface {@link GerenciaCSV} para conversão de objetos em CSV.
 *
 * <p>Possui atributos como código da movimentação, código do produto, data,
 * quantidade e valor total. Mantém também um contador estático para controle
 * de códigos automáticos.</p>
 *
 * @author Carlos e Pedro Jose
 */
public abstract class Movimentacao implements GerenciaCSV {

    /**
     * Código único da movimentação
     */
    private int codigo;

    /**
     * Código do produto associado
     */
    private int codigoProduto;

    /**
     * Data da movimentação
     */
    private LocalDate data;

    /**
     * Quantidade de produtos movimentados
     */
    private int quantidade;

    /**
     * Valor total da movimentação
     */
    private double valorTotal;

    /**
     * Contador estático para gerar códigos automáticos
     */
    private static int control = 0;

    /**
     * Construtor que cria uma movimentação com código automático.
     *
     * @param codigoProduto Código do produto
     * @param quantidade    Quantidade movimentada
     * @param valorTotal    Valor total da movimentação
     */
    public Movimentacao(int codigoProduto, int quantidade, double valorTotal) {
        this.codigo = control;
        control++;
        this.codigoProduto = codigoProduto;
        this.quantidade = quantidade;
        this.valorTotal = valorTotal;
        this.data = LocalDate.now();
    }

    /**
     * Construtor que cria uma movimentação com todos os atributos definidos.
     *
     * @param codigo        Código da movimentação
     * @param codigoProduto Código do produto
     * @param valorTotal    Valor total
     * @param data          Data da movimentação
     * @param quantidade    Quantidade movimentada
     */
    public Movimentacao(int codigo, int codigoProduto, double valorTotal, LocalDate data, int quantidade) {
        this.codigo = codigo;
        this.codigoProduto = codigoProduto;
        this.valorTotal = valorTotal;
        this.data = data;
        this.quantidade = quantidade;
    }

    /**
     * Construtor padrão sem parâmetros
     */
    public Movimentacao() {
    }

    /**
     * Converte a movimentação em formato CSV.
     * Implementação obrigatória nas subclasses.
     *
     * @return String representando a movimentação em CSV
     */
    @Override
    public abstract String toCSV();

    /**
     * Verifica se duas movimentações são iguais com base no código.
     *
     * @param obj Objeto a ser comparado
     * @return true se forem da mesma classe e tiverem o mesmo código
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Movimentacao other = (Movimentacao) obj;
        return codigo == other.codigo;
    }

    // ----- Getters e Setters -----

    /**
     * Retorna o código do produto.
     *
     * @return Código do produto
     */
    public int getCodigoProduto() {
        return codigoProduto;
    }

    /**
     * Define o código do produto.
     *
     * @param codigoProduto Código do produto
     */
    public void setCodigoProduto(int codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    /**
     * Retorna a data da movimentação.
     *
     * @return Data da movimentação
     */
    public LocalDate getData() {
        return data;
    }

    /**
     * Define a data da movimentação.
     *
     * @param data Data da movimentação
     */
    public void setData(LocalDate data) {
        this.data = data;
    }

    /**
     * Retorna a quantidade movimentada.
     *
     * @return Quantidade
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * Define a quantidade movimentada.
     *
     * @param quantidade Quantidade
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * Retorna o valor total da movimentação.
     *
     * @return Valor total
     */
    public double getvalorTotal() {
        return valorTotal;
    }

    /**
     * Define o valor total da movimentação.
     *
     * @param valorTotal Valor total
     */
    public void setvalorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    /**
     * Retorna o código da movimentação.
     *
     * @return Código da movimentação
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Define o código da movimentação.
     *
     * @param codigo Código da movimentação
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * Define o valor do contador estático.
     *
     * @param valor Novo valor do contador
     */
    public static void setControl(int valor) {
        control = valor;
    }

    /**
     * Retorna o valor atual do contador estático.
     *
     * @return Valor do contador
     */
    public static int getControl() {
        return control;
    }
}
