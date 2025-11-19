package model;

import java.time.LocalDate;

public abstract class Movimentacao implements GerenciaCSV {

	private int codigo;
	private int codigoProduto;
	private LocalDate data;
	private int quantidade;
	private double valorTotal;

	private static int control = 0;

	public Movimentacao(int codigoProduto, int quantidade, double valorTotal) {

		this.codigo = control;
		control++; 
		this.codigoProduto = codigoProduto;
		this.quantidade = quantidade;
		this.valorTotal = valorTotal;
		this.data = LocalDate.now();
	}

	public Movimentacao(int codigo, int codigoProduto, double valorTotal, LocalDate data, int quantidade) {
		this.codigo = codigo;
		this.codigoProduto = codigoProduto;
		this.valorTotal = valorTotal;
		this.data = data;
		this.quantidade = quantidade;
	}

	public Movimentacao() {
	}



	@Override
	public abstract String toCSV();

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Movimentacao other = (Movimentacao) obj;
		return codigo == other.codigo;
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

	public double getvalorTotal() {
		return valorTotal;
	}

	public void setvalorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}	
	public static void setControl(int valor) {
		control = valor;
	}

	public static int getControl() {
		return control;
	}
}