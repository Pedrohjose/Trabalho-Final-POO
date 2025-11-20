package model;

import view.*;

/**
 * Classe principal do sistema.
 *
 * <p>Responsável por inicializar a aplicação Java Swing, configurando o
 * Look and Feel para "Nimbus" e exibindo a tela principal de produtos.</p>
 *
 * <p>O ponto de entrada {@link #main(String[])} lança a interface gráfica
 * no Event Dispatch Thread (EDT) para garantir a execução correta da GUI.</p>
 *
 * <p>Essa classe não possui estado, sendo usada apenas para inicialização.</p>
 *
 * @author Carlos
 */
public class Main {
    /**
     * Construtor padrao
     */
    public Main() {
        //Construtor padrao
    }

     /**
     * <p>Configura o Look and Feel para "Nimbus" se disponível e abre a tela
     * de produtos no Event Dispatch Thread (EDT).</p>
     *
     * @param args Argumentos de linha de comando (não utilizados)
     */
    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        java.awt.EventQueue.invokeLater(() -> new TelaPrincipal().setVisible(true));
    }
}
