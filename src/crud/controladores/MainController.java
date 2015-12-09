/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crud.controladores;

import crud.util.Controller;
import crud.vistas.Main;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UsuariosController uc = new UsuariosController();
                uc.setMainDesk(view.jDesktopPane2);
                uc.showView();
            }
        };
    }

    private void loadActionListeners() {
        view.showUsuarios.addActionListener(showUsuarios());
    }

}
