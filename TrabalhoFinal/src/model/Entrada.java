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
        return sb.toString();
    }

    public static Movimentacao fromCSV(List<String> linhasDoObjeto) {
        if (linhasDoObjeto.size() < 6) { 
            throw new IllegalArgumentException("Dados insuficientes para criar entrada.");
        }
        
        try {
            int codigoProduto = Integer.parseInt(extrairValor(linhasDoObjeto.get(1)));
            LocalDate data = LocalDate.parse(extrairValor(linhasDoObjeto.get(2)));
            int quantidade = Integer.parseInt(extrairValor(linhasDoObjeto.get(3)));
            double preco = Double.parseDouble(extrairValor(linhasDoObjeto.get(4)));
            
            return new Entrada(codigoProduto, data, quantidade, preco);
            
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao formatar a entrada: " + e.getMessage());
        }
    }

	@Override
	public void fromCSV(String linha) {
		
	}
}