package model;

import java.time.LocalDate;
import java.util.List;

public class Entrada extends Movimentacao {

    public Entrada(int codigoProduto, LocalDate data, int quantidade, double valorUnitario) {
        super(codigoProduto, data, quantidade, valorUnitario);
    }

    @Override
    public String toCSV() {
        String ls = System.lineSeparator();
        StringBuilder sb = new StringBuilder();
        
        sb.append("tipo : ENTRADA;").append(ls);
        sb.append("codigoProduto : ").append(codigoProduto).append(";").append(ls);
        sb.append("data : ").append(data).append(";").append(ls);
        sb.append("quantidade : ").append(quantidade).append(";").append(ls);
        sb.append("valorUnitario : ").append(valorUnitario).append(";").append(ls);
        sb.append("tipoSaida : N/A;"); // Linha extra para manter o padrão de 6 linhas
        
        return sb.toString();
    }

    public static Movimentacao fromCSV(List<String> linhasDoObjeto) {
        // Espera 6 linhas (tipo, codigo, data, qtd, valor, tipoSaida N/A)
        if (linhasDoObjeto.size() < 6) { 
            throw new IllegalArgumentException("Dados insuficientes para criar Entrada.");
        }
        
        try {
            // Pula a linha 0 (tipo : ENTRADA)
            int codigoProduto = Integer.parseInt(parseValor(linhasDoObjeto.get(1)));
            LocalDate data = LocalDate.parse(parseValor(linhasDoObjeto.get(2)));
            int quantidade = Integer.parseInt(parseValor(linhasDoObjeto.get(3)));
            double preco = Double.parseDouble(parseValor(linhasDoObjeto.get(4)));
            // Pula a linha 5 (tipoSaida : N/A)
            
            return new Entrada(codigoProduto, data, quantidade, preco);
            
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao formatar dados da Entrada: " + e.getMessage());
        }
    }

	@Override
	public void fromCSV(String linha) {
		
	}
}