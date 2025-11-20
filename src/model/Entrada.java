package model;

import java.time.LocalDate;

public class Entrada extends Movimentacao {

    public Entrada(int codigoProduto, int quantidade, double valorUnitario) {
        super(codigoProduto, quantidade, valorUnitario);
    }

    public Entrada(int codigo, int codigoProduto, double valorTotal, LocalDate data, int quantidade, TipoSaida tipoSaida) {
        super(codigo, codigoProduto, valorTotal, data, quantidade);
    }

    @Override
    public String toCSV() {
        StringBuilder sb = new StringBuilder();
        
        sb.append(getCodigo()).append(";");
        sb.append(getCodigoProduto()).append(";");
        sb.append(getvalorTotal()).append(";");
        sb.append(getData()).append(";");
        sb.append(getQuantidade()).append(";");  
        sb.append("ENTRADA");

        return sb.toString();
    }

    @Override
    public Movimentacao fromCSV(String linha) {
        try {
            String[] dados = linha.split(";");

            return new Entrada(Integer.parseInt(dados[0]), Integer.parseInt(dados[1]), Double.parseDouble(dados[2]), LocalDate.parse(dados[3]), Integer.parseInt(dados[4]), TipoSaida.valueOf(dados[5]));
            
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao formatar a entrada: " + e.getMessage());
        }
    }
}