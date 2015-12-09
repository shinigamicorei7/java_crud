/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crud.controladores;

import crud.util.Controller;
import crud.util.Config;
import crud.vistas.Configuration;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.ButtonModel;
import javax.swing.JFileChooser;

/**
 *
 * @author shinigamicorei7
 */
public class ConfigController extends Controller {

    private Configuration view;
    private String directorio = "";

    public String getDirectorio() {
        return directorio;
    }

    public void setDirectorio(String directorio) {
        this.directorio = directorio;
    }

    @Override
    public void showView() {
        view = new Configuration();
        loadActionListeners();
        view.directorio.setText(getDirectorio());
        view.setVisible(true);
        view.setLocationRelativeTo(null);
    }

    private void loadActionListeners() {
        view.importConfig.addActionListener(importConfig());
        view.makeConfig.addActionListener(makeConfig());
        view.save.addActionListener(loadConfig());
        view.buscar.addActionListener(buscar());
    }

    private ActionListener importConfig() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.directorio.setEnabled(true);
                view.buscar.setEnabled(true);
            }
        };
    }

    private ActionListener loadConfig() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ButtonModel button = view.buttonGroup1.getSelection();
                String caso = button.getActionCommand();
                boolean resp;
                switch (caso) {
                    case "import":
                        resp = Config.importConfig(view.directorio.getText());
                        break;
                    case "make":
                        resp = Config.makeConfig();
                        break;
                    default:
                        resp = false;
                        break;
                }

                if (resp) {
                    view.dispose();
                    MainController m = new MainController();
                    m.showView();
                }

            }
        };
    }

    private ActionListener makeConfig() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.directorio.setEnabled(false);
                view.buscar.setEnabled(false);
            }
        };
    }

    private ActionListener buscar() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jf = new JFileChooser();
                jf.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int resp = jf.showSaveDialog(view);
                if (resp == JFileChooser.APPROVE_OPTION) {
                    File file = jf.getSelectedFile();
                    view.directorio.setText(file.getPath());
                }
            }
        };
    }

}
