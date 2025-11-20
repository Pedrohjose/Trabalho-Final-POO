package model;

/**
 * Enumeração que define os tipos de saída de produtos no sistema.
 *
 * <p>Os tipos disponíveis são:</p>
 * <ul>
 *     <li>{@link #VENDA_CLIENTE} – Saída de produto para venda a cliente.</li>
 *     <li>{@link #USO_INTERNO} – Saída de produto para uso interno da empresa.</li>
 *     <li>{@link #DEVOLUCAO_FORNECEDOR} – Saída de produto para devolução ao fornecedor.</li>
 *     <li>{@link #OUTRAS_SAIDAS} – Qualquer outra saída não especificada.</li>
 * </ul>
 *
 * <p>Este enum é usado principalmente na classe {@link Saida} para indicar
 * o motivo ou tipo da movimentação de saída.</p>
 *
 * @author Pedro Jose
 */
public enum TipoSaida {
    /** Motivo de saida por conta de venda **/
    VENDA_CLIENTE,

    /** Motivo de saida por conta de uso interno */
    USO_INTERNO,

    /** Motivo de saida por conta de devolução ao fornecedor */
    DEVOLUCAO_FORNECEDOR,

    /** Outros motivos de saída */
    OUTRAS_SAIDAS
}
