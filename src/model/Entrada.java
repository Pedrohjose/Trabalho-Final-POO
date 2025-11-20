package model;

import java.time.LocalDate;

/**
 * Representa uma movimentação de entrada de produto no sistema.
 *
 * <p>Herda de {@link Movimentacao} e define o tipo "ENTRADA" para o registro CSV.</p>
 *
 * <p>É responsável por criar instâncias de entradas, converter para CSV e
 * reconstruir objetos a partir de linhas CSV.</p>
 *
 * @author Carlos e Pedro Jose
 */
public class Entrada extends Movimentacao {

    /**
     * Construtor de entrada com código automático.
     *
     * @param codigoProduto Código do produto
     * @param quantidade    Quantidade de produtos
     * @param valorUnitario Valor total da movimentação
     */
    public Entrada(int codigoProduto, int quantidade, double valorUnitario) {
        super(codigoProduto, quantidade, valorUnitario);
    }

    /**
     * Construtor de entrada com todos os atributos definidos.
     *
     * @param codigo        Código da movimentação
     * @param codigoProduto Código do produto
     * @param valorTotal    Valor total da movimentação
     * @param data          Data da movimentação
     * @param quantidade    Quantidade de produtos
     */
    public Entrada(int codigo, int codigoProduto, double valorTotal, LocalDate data, int quantidade) {
        super(codigo, codigoProduto, valorTotal, data, quantidade);
    }

    /**
     * Converte a movimentação de entrada em formato CSV.
     *
     * @return String representando a entrada em CSV
     */
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

    /**
     * Constrói uma instância de {@link Entrada} a partir de uma linha CSV.
     *
     * @param linha Linha CSV contendo os dados da entrada
     * @return Instância de {@link Entrada} criada a partir do CSV
     * @throws IllegalArgumentException Caso o CSV esteja mal formatado
     */
    public static Entrada fromCSV(String linha) {
        try {
            String[] dados = linha.split(";");
            return new Entrada(
                    Integer.parseInt(dados[0]),
                    Integer.parseInt(dados[1]),
                    Double.parseDouble(dados[2]),
                    LocalDate.parse(dados[3]),
                    Integer.parseInt(dados[4])
            );
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao formatar a entrada: " + e.getMessage());
        }
    }
}
