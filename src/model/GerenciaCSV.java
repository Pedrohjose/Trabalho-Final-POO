package model;

/**
 * Interface que define a capacidade de um objeto ser convertido para CSV
 * e, opcionalmente, reconstruído a partir de uma linha CSV.
 *
 * <p>Classes que implementam esta interface devem fornecer a implementação
 * de {@link #toCSV()} para serializar os dados.</p>
 *
 * <p>O método {@link #fromCSV(String)} é estático e pode ser sobrescrito
 * nas classes concretas para criar instâncias a partir de uma linha CSV.</p>
 *
 * @author Carlos e Pedro Jose
 */
public interface GerenciaCSV {

    /**
     * Converte o objeto em uma representação CSV (valores separados por ponto e vírgula).
     *
     * @return String CSV representando o objeto
     */
    String toCSV();

    /**
     * Método estático padrão que pode ser sobrescrito pelas classes concretas
     * para criar um objeto a partir de uma linha CSV.
     *
     * @param linha Linha CSV contendo os dados
     * @return Objeto reconstruído a partir da linha CSV, ou null se não implementado
     */
    static Object fromCSV(String linha) {
        return null;
    }
}
