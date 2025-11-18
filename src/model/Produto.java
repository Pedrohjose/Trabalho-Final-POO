package model;

import java.time.LocalDate;

public class Produto implements GerenciaCSV {

	private int codigo;
	private String nome;
	private double preco;
	private int quantidade;
	private CategoriasProdutos categoria;
	private static int control = 0;

	public Produto(String nome, double preco, CategoriasProdutos categoria) {
		this.codigo = control++;
		this.nome = nome;
		this.preco = preco;
		this.quantidade = 0;
		this.categoria = categoria;
	}

	public Produto(int codigo, String nome, double preco, int quantidade, CategoriasProdutos categoria) {
		this.codigo = codigo;
		this.nome = nome;
		this.preco = preco;
		this.quantidade = quantidade;
		this.categoria = categoria;
	}

	public Produto() {
	}

	@Override
	public String toCSV() {
        StringBuilder sb = new StringBuilder();

		sb.append(categoria).append(";");
		sb.append(codigo).append(";");
		sb.append(LocalDate.now()).append(";");
		sb.append(quantidade).append(";");
		sb.append(categoria).append(";");

		return sb.toString();
	}

	@Override
	public Produto fromCSV(String linha) {
		try {
			String[] dados = linha.split(";");

			return new Produto(Integer.parseInt(dados[0]), (String)dados[1], Double.parseDouble(dados[2]),Integer.parseInt(dados[3]), CategoriasProdutos.valueOf(dados[4]));

		} catch (Exception e) {
			throw new IllegalArgumentException(
					"Erro ao formatar dados do produto. Verifique o arquivo CSV. Erro: " + e.getMessage());
		}
	}

	@Override
    public boolean equals(Object obj) {
        //Se for o mesmíssimo objeto na memória, retorna true.
        if (this == obj)
            return true;
        
        //Se o outro for nulo ou de uma classe diferente (ex: Entrada), retorna false.
        if (obj == null || getClass() != obj.getClass())
            return false;
            
        //Converte o objeto genérico para Produto para podermos ler os dados.
        Produto produto = (Produto) obj;
        
        //São iguais se o código (ID) for igual.
        return codigo == produto.codigo;
    }

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public CategoriasProdutos getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriasProdutos categoria) {
		this.categoria = categoria;
	}
}