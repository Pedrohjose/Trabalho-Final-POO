package model;

import java.time.LocalDate;
import java.util.Objects;

public abstract class Movimentacao implements GerenciaCSV {
    
    protected int codigoProduto;
    protected LocalDate data;
    protected int quantidade;
    protected double valorUnitario;

    public Movimentacao(int codigoProduto, LocalDate data, int quantidade, double valorUnitario) {
        this.codigoProduto = codigoProduto;
        this.data = data;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
    }

    public Movimentacao() {
    }

    @Override
    public abstract String toCSV();

    // Método auxiliar estático para as subclasses usarem
    protected static String parseValor(String linha) {
        try {
            String valor = linha.split(" : ")[1];
            return valor.substring(0, valor.length() - 1);
        } catch (Exception e) {
            throw new IllegalArgumentException("Linha mal formatada: " + linha, e);
        }
    }

    // --- Getters, Setters, Equals e HashCode ---

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Movimentacao that = (Movimentacao) obj;
        return codigoProduto == that.codigoProduto &&
               quantidade == that.quantidade &&
               Double.compare(that.valorUnitario, valorUnitario) == 0 &&
               Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigoProduto, data, quantidade, valorUnitario);
    }

    public int getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(int codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }
}