/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crud;

import crud.controladores.ConfigController;
import crud.controladores.MainController;
import crud.util.Config;
import crud.vistas.Configuration;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author shinigamicorei7
 */
public class Crud {

    public static double version = 0.1;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            UIManager.put("Synthetica.window.decoration", false);
            UIManager.put("Synthetica.extendedFileChooser.enabled", false);
            UIManager.setLookAndFeel("de.javasoft.plaf.synthetica.SyntheticaBlueSteelLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println("mierda aki esta el error");
        }

        Config c = new Config();
        boolean exists = c.existsConfigDir();
        if (exists) {
            if (c.isCompatible()) {
                MainController mc = new MainController();
                mc.showView();
            } else {
                ConfigController cc = new ConfigController();
                cc.setDirectorio(c.getConfigDir());
                cc.showView();
            }
        } else {
            System.out.println("No existe instalación anterior!");
            ConfigController controller = new ConfigController();
            controller.showView();
        }
    }

}
