package model;

import java.time.LocalDate;
import java.util.List;


public class Saida extends Movimentacao {

    private TipoSaida tipoSaida;

    public Saida(int codigoProduto, LocalDate data, int quantidade, double valorUnitario, TipoSaida tipoSaida) {
        super(codigoProduto, data, quantidade, valorUnitario);
        this.tipoSaida = tipoSaida;
    }

    @Override
    public String toCSV() {
        String EOL = System.lineSeparator();
        StringBuilder sb = new StringBuilder();
        
        sb.append("tipo : SAIDA;").append(EOL);
        sb.append("codigoProduto : ").append(codigoProduto).append(";").append(EOL);
        sb.append("data : ").append(data).append(";").append(EOL);
        sb.append("quantidade : ").append(quantidade).append(";").append(EOL);
        sb.append("valorUnitario : ").append(valorUnitario).append(";").append(EOL);
        sb.append("tipoSaida : ").append(tipoSaida.name()).append(";");
        
        return sb.toString();
    }

    public static Movimentacao fromCSV(List<String> linhasDoObjeto) {
        if (linhasDoObjeto.size() < 6) {
            throw new IllegalArgumentException("Dados insuficientes para criar saida.");
        }
        
        try {
            int codigoProduto = Integer.parseInt(extrairValor(linhasDoObjeto.get(1)));
            LocalDate data = LocalDate.parse(extrairValor(linhasDoObjeto.get(2)));
            int quantidade = Integer.parseInt(extrairValor(linhasDoObjeto.get(3)));
            double preco = Double.parseDouble(extrairValor(linhasDoObjeto.get(4)));
            TipoSaida tipo = TipoSaida.valueOf(extrairValor(linhasDoObjeto.get(5)));
            
            return new Saida(codigoProduto, data, quantidade, preco, tipo);
            
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao formatar a Saida: " + e.getMessage());
        }
    }

    // Getters e Setters
    
    public TipoSaida getTipoSaida() {
        return tipoSaida;
    }

    public void setTipoSaida(TipoSaida tipoSaida) {
        this.tipoSaida = tipoSaida;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        Saida saida = (Saida) obj;
        return tipoSaida == saida.tipoSaida;
    }

  

	@Override
	public void fromCSV(String linha) {
		// TODO Auto-generated method stub
		
	}
}