/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crud.controladores;

import crud.util.Controller;
import crud.modelos.Users;
import crud.vistas.Usuarios;
import java.util.List;
import javax.swing.JDesktopPane;
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
        view = new Usuarios();
        initUsersTable();
        loadActionListeners();
        view.show();
        view.toFront();
    }

    private void initUsersTable() {
        DefaultTableModel modelo = new UsuariosController.UsuariosTableModel(new Object[][]{}, new String[]{"Id", "Nombre"});
        usuarios = getUsuarios();
        usuarios.forEach((usuario) -> {
            modelo.addRow(new Object[]{usuario.getId(), usuario.getNombre()});
        });

        view.getjTable1().setModel(modelo);
        view.getjTable1().getColumn("Id").setMaxWidth(36);
        mainDesk.add(view);

    }

    private List<Users> getUsuarios() {
        return Users.all();
    }

    private void loadActionListeners() {
        
    }

    private class UsuariosTableModel extends DefaultTableModel {

        public UsuariosTableModel(Object[][] data, Object[] columnNames) {
            super(data, columnNames);
        }
        Class[] types = new Class[]{
            java.lang.Integer.class, java.lang.String.class
        };
        boolean[] canEdit = new boolean[]{
            false, false
        };

        @Override
        public Class getColumnClass(int columnIndex) {
            return types[columnIndex];
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit[columnIndex];
        }
    }
}
