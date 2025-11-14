package model;

import java.time.LocalDate;

public class Saida extends Movimentacao {

    private TipoSaida tipoSaida;

    public Saida(int codigoProduto, int quantidade, double valorTotal, TipoSaida tipoSaida) {
        super(codigoProduto, quantidade, valorTotal);
        this.tipoSaida = tipoSaida;
    }

    public Saida(int codigo, int codigoProduto, double valorTotal, LocalDate data, int quantidade, TipoSaida tipoSaida) {
        super(codigo, codigoProduto, valorTotal, data, quantidade);
        this.tipoSaida = tipoSaida;
    }

    @Override
    public String toCSV() {
        StringBuilder sb = new StringBuilder();
        
        sb.append(getCodigo()).append(";");
        sb.append(getCodigoProduto()).append(";");
        sb.append(getvalorTotal()).append(";");
        sb.append(getData()).append(";");
        sb.append(getQuantidade()).append(";");
        sb.append(getTipoSaida());
        
        return sb.toString();
    }

    @Override
	public Saida fromCSV(String linha) {
        try {
        String[] dados = linha.split(";");

        return new Saida(Integer.parseInt(dados[0]), Integer.parseInt(dados[1]), Double.parseDouble(dados[2]), LocalDate.parse(dados[3]), Integer.parseInt(dados[4]), TipoSaida.valueOf(dados[5]));
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao formatar a Saida: " + e.getMessage());
        }
	}
    
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
}