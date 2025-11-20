package model;

import java.time.LocalDate;

/**
 * Representa uma movimentação de saída de produto no sistema.
 *
 * <p>Herda de {@link Movimentacao} e adiciona o atributo {@link #tipoSaida}
 * para indicar o tipo da saída.</p>
 *
 * <p>Fornece métodos para conversão para CSV e reconstrução a partir de CSV.</p>
 *
 * @author Carlos e Pedro Jose
 */
public class Saida extends Movimentacao {

    /** Tipo da saída do produto */
    private TipoSaida tipoSaida;

    /**
     * Construtor de saída com código automático.
     *
     * @param codigoProduto Código do produto
     * @param quantidade Quantidade de produtos
     * @param valorTotal Valor total da movimentação
     * @param tipoSaida Tipo da saída
     */
    public Saida(int codigoProduto, int quantidade, double valorTotal, TipoSaida tipoSaida) {
        super(codigoProduto, quantidade, valorTotal);
        this.tipoSaida = tipoSaida;
    }

    /**
     * Construtor de saída com todos os atributos definidos.
     *
     * @param codigo Código da movimentação
     * @param codigoProduto Código do produto
     * @param valorTotal Valor total da movimentação
     * @param data Data da movimentação
     * @param quantidade Quantidade de produtos
     * @param tipoSaida Tipo da saída
     */
    public Saida(int codigo, int codigoProduto, double valorTotal, LocalDate data, int quantidade, TipoSaida tipoSaida) {
        super(codigo, codigoProduto, valorTotal, data, quantidade);
        this.tipoSaida = tipoSaida;
    }

    /**
     * Converte a saída em uma linha CSV.
     *
     * @return String CSV representando a saída
     */
    @Override
    public String toCSV() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.getCodigo()).append(";");
        sb.append(super.getCodigoProduto()).append(";");
        sb.append(super.getvalorTotal()).append(";");
        sb.append(super.getData()).append(";");
        sb.append(super.getQuantidade()).append(";");
        sb.append(getTipoSaida());
        return sb.toString();
    }

    /**
     * Cria uma instância de {@link Saida} a partir de uma linha CSV.
     *
     * @param linha Linha CSV contendo os dados da saída
     * @return Instância de {@link Saida} correspondente à linha CSV
     * @throws IllegalArgumentException Se a linha estiver mal formatada
     */
    public static Saida fromCSV(String linha) {
        try {
            String[] dados = linha.split(";");
            return new Saida(
                    Integer.parseInt(dados[0]),
                    Integer.parseInt(dados[1]),
                    Double.parseDouble(dados[2]),
                    LocalDate.parse(dados[3]),
                    Integer.parseInt(dados[4]),
                    TipoSaida.valueOf(dados[5])
            );
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao formatar a Saida: " + e.getMessage());
        }
    }

    /** Retorna o tipo de saida
     * @return Tipo da saída */
    public TipoSaida getTipoSaida() {
        return tipoSaida;
    }

    /** Define o tipo da saída */
    public void setTipoSaida(TipoSaida tipoSaida) {
        this.tipoSaida = tipoSaida;
    }

    /**
     * Compara duas saídas, considerando atributos da superclasse e o tipo de saída.
     *
     * @param obj Objeto a ser comparado
     * @return true se forem iguais, false caso contrário
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        Saida saida = (Saida) obj;
        return tipoSaida == saida.tipoSaida;
    }
}
