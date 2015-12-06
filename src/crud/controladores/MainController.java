/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crud.controladores;

import crud.util.Controller;
import crud.modelos.Users;
import crud.vistas.Main;
import crud.vistas.Usuarios;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author shinigamicorei7
 */
public class MainController extends Controller {

    private Main view;

    @Override
    public void showView() {
        view = new Main();
        loadActionListeners();
        view.setVisible(true);
    }

    private ActionListener showUsuarios() {
        return (ActionEvent e) -> {
            UsuariosController uc = new UsuariosController();
            uc.setMainDesk(view.jDesktopPane2);
            uc.showView();
        };
    }

    private void loadActionListeners() {
        view.showUsuarios.addActionListener(showUsuarios());
    }

}
