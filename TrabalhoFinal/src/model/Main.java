package model;
import view.TelaPrincipalUi;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */


/**
 *
 * @author carlos
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("GTK+".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        /* Criar e exibir a tela */
        java.awt.EventQueue.invokeLater(() -> new TelaPrincipalUi().setVisible(true));
    }
}
