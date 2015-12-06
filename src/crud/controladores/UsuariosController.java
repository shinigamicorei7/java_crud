/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crud.controladores;

import crud.util.Controller;
import crud.modelos.Users;
import crud.util.ViewManager;
import crud.vistas.Usuarios;
import crud.vistas.usuarios.AddUser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author shinigamicorei7
 */
public class UsuariosController extends Controller {

    private Usuarios view;
    private JDesktopPane mainDesk;
    private List<Users> usuarios;

    public void setMainDesk(JDesktopPane mainDesk) {
        this.mainDesk = mainDesk;
    }

    @Override
    public void showView() {

        view = (Usuarios) ViewManager.getView(Usuarios.class.getName());
        initUsersTable();
        loadActionListeners();
        view.toFront();
    }

    private void initUsersTable() {
        if (view == null || view.isClosed()) {
            view = new Usuarios();
            DefaultTableModel modelo = (DefaultTableModel) view.getjTable1().getModel();
            view.getjTable1().getColumn("Id").setMaxWidth(36);
            usuarios = getUsuarios();
            usuarios.forEach((usuario) -> {
                modelo.addRow(new Object[]{usuario.getId(), usuario.getNombre()});
            });
            mainDesk.add(view);
            ViewManager.addView(Usuarios.class.getName(), view);
            view.setVisible(true);
        }
    }

    private List<Users> getUsuarios() {
        return Users.all();
    }

    private void loadActionListeners() {
        view.addUser.addActionListener(showAddUser());
    }

    private ActionListener showAddUser() {
        return (ActionEvent e) -> {

            AddUser add;
            add = (AddUser) ViewManager.getView(AddUser.class.getName());
            if (add == null || add.isClosed()) {
                add = new AddUser();
                mainDesk.add(add);
                ViewManager.addView(AddUser.class.getName(), add);
                add.setVisible(true);
            }
            add.toFront();
        };
    }
}
