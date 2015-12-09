package crud;

import crud.controladores.ConfigController;
import crud.controladores.MainController;
import crud.util.Config;
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
            javax.swing.UIManager.put("Synthetica.window.decoration", false);
            javax.swing.UIManager.put("Synthetica.extendedFileChooser.enabled", false);
            javax.swing.UIManager.setLookAndFeel("de.javasoft.plaf.synthetica.SyntheticaBlueSteelLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println("No se pudo cargar. El look and Feel de Syntetica");
            try {
                javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            }
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
