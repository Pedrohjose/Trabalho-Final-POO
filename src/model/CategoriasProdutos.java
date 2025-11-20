package model;

/**
 * Enumeração que representa as categorias de produtos disponíveis no sistema.
 *
 * <p>As categorias possíveis são:</p>
 * <ul>
 *     <li>{@link #HARDWARE} - Componentes de hardware</li>
 *     <li>{@link #PERIFERICOS} - Periféricos de computador</li>
 *     <li>{@link #ACESSORIOS} - Acessórios diversos</li>
 *     <li>{@link #OUTROS} - Qualquer outra categoria não especificada</li>
 * </ul>
 *
 * <p>Essa enum é usada para classificar produtos no sistema de inventário.</p>
 *
 * @author Pedro Jose
 */
public enum CategoriasProdutos {
    /** Peças dos computador **/
    HARDWARE,

    /** Perifericos em geral **/
    PERIFERICOS,

    /** Acessorios como extensões usb coisas do tipo **/
    ACESSORIOS,

    /** Outros produtos **/
    OUTROS
}
