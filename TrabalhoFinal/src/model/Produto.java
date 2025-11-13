package model;

import java.util.List;

public class Produto implements GerenciaCSV {

	private int codigo;
	private String nome;
	private double preco;
	private int quantidade;
	private CategoriasProdutos categoria;

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
		String EOL = System.lineSeparator();
		StringBuilder sb = new StringBuilder();

		sb.append("codigo : ").append(codigo).append(";").append(EOL);
		sb.append("nome : ").append(nome).append(";").append(EOL);
		sb.append("preco : ").append(preco).append(";").append(EOL);
		sb.append("quantidade : ").append(quantidade).append(";").append(EOL);
		sb.append("categoria : ").append(categoria.name()).append(";");

		return sb.toString();
	}

	public static Produto fromCSV(List<String> linhasDoObjeto) {
		if (linhasDoObjeto.size() < 5) {
			throw new IllegalArgumentException(
					"Dados insuficientes para criar Produto. Linhas lidas: " + linhasDoObjeto.size());
		}

		try {
			String valorLinha0 = linhasDoObjeto.get(0).split(" : ")[1];
			String valorLinha1 = linhasDoObjeto.get(1).split(" : ")[1];
			String valorLinha2 = linhasDoObjeto.get(2).split(" : ")[1];
			String valorLinha3 = linhasDoObjeto.get(3).split(" : ")[1];
			String valorLinha4 = linhasDoObjeto.get(4).split(" : ")[1];

			int codigo = Integer.parseInt(valorLinha0.substring(0, valorLinha0.length() - 1));
			String nome = valorLinha1.substring(0, valorLinha1.length() - 1);
			double preco = Double.parseDouble(valorLinha2.substring(0, valorLinha2.length() - 1));
			int quantidade = Integer.parseInt(valorLinha3.substring(0, valorLinha3.length() - 1));
			CategoriasProdutos categoria = CategoriasProdutos
					.valueOf(valorLinha4.substring(0, valorLinha4.length() - 1));

			return new Produto(codigo, nome, preco, quantidade, categoria);

		} catch (Exception e) {
			throw new IllegalArgumentException(
					"Erro ao formatar dados do produto. Verifique o arquivo CSV. Erro: " + e.getMessage());
		}
	}


	@Override
    public boolean equals(Object obj) {
        // 1. Se for o mesmíssimo objeto na memória, retorna true.
        if (this == obj)
            return true;
        
        // 2. Se o outro for nulo ou de uma classe diferente (ex: Entrada), retorna false.
        if (obj == null || getClass() != obj.getClass())
            return false;
            
        // 3. Converte o objeto genérico para Produto para podermos ler os dados.
        Produto produto = (Produto) obj;
        
        // 4. São iguais se o código (ID) for igual.
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

	@Override
	public void fromCSV(String linha) {
		// TODO Auto-generated method stub

	}
}