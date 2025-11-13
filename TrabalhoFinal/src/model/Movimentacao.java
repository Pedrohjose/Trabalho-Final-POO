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

    
    protected static String extrairValor(String linha) {
        try {
            String valor = linha.split(" : ")[1];
            return valor.substring(0, valor.length() - 1);
        } catch (Exception e) {
            throw new IllegalArgumentException("Linha mal formatada: " + linha, e);
        }
    }

    
    @Override
    public boolean equals(Object obj) {
        //Verificação de Identidade (Mesmo endereço de memória?)
        if (this == obj) return true;
        
        //Verificação de Nulidade e Tipo (É a mesma classe?)
        if (obj == null || getClass() != obj.getClass()) return false;
        
        //Casting (Conversão segura para o tipo Movimentacao)
        Movimentacao other = (Movimentacao) obj;
        
        // Comparação profunda de todos os atributos
        return codigoProduto == other.codigoProduto &&
               quantidade == other.quantidade &&
               // Double.compare é usado para evitar erros de precisão com decimais
               Double.compare(other.valorUnitario, valorUnitario) == 0 &&
               // Objects.equals evita erro se a data for nula (NullPointerException)
               Objects.equals(data, other.data);
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