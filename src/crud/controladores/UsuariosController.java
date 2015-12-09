/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crud.controladores;

import crud.modelos.Role;
import crud.util.Controller;
import crud.modelos.User;
import crud.util.ViewManager;
import crud.vistas.Usuarios;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * @author shinigamicorei7
 */
public class UsuariosController extends Controller {

    private Usuarios view;
    private JDesktopPane mainDesk;

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
            
            DefaultTableModel modelo = (DefaultTableModel) view.jTable1.getModel();
            view.jTable1.getColumn("Id").setMaxWidth(36);

            for (Role role : Role.all()) {
                view.listaRoles.addItem(role.getName());
                view.update_rol.addItem(role.getName());
            }

            for(User usuario : User.all()){
                modelo.addRow(new Object[]{
                        usuario.getId(),
                        usuario.getName(),
                        usuario.getRole().getName()
                });
            }

            mainDesk.add(view);
            ViewManager.addView(Usuarios.class.getName(), view);
            view.setVisible(true);
        }
    }

    private void loadActionListeners() {
        view.jTable1.addMouseListener(modificarUsuario());
        view.crear_usuario.addActionListener(crearUsuario());
        view.elimiar_usuarios.addActionListener(eliminarUsuariosSelecionados());
        view.update_usuario.addActionListener(actualizarUsuario());
    }

    private ActionListener actualizarUsuario() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel modelo = (DefaultTableModel) view.jTable1.getModel();
                int id = Integer.parseInt(view.update_id.getText());
                String nombre = view.update_nombre.getText();
                int role = view.update_rol.getSelectedIndex() + 1;
                String nombre_rol = (String) view.update_rol.getSelectedItem();

                User user = User.find(id);
                user.setName(nombre);
                user.setRoleId(role);
                if (user.update()) {
                    view.update_nombre.setText("");
                    view.update_id.setText("");
                    view.update_rol.setSelectedIndex(0);
                    view.update_nombre.setEnabled(false);
                    view.update_rol.setEnabled(false);
                    view.update_usuario.setEnabled(false);
                    for (int u = 0; u < modelo.getRowCount(); u++) {
                        if ((int) modelo.getValueAt(u, 0) == id) {
                            modelo.setValueAt(nombre, u, 1);
                            modelo.setValueAt(nombre_rol, u, 2);
                            break;
                        }
                    }
                } else {
                    JOptionPane.showInternalMessageDialog(view, "El usuario no pudo se modificado.", "Error!", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
    }

    private ActionListener eliminarUsuariosSelecionados() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel modelo = (DefaultTableModel) view.jTable1.getModel();
                int[] rows = view.jTable1.getSelectedRows();
                int[] ids = new int[rows.length];
                for (int i = 0; i < rows.length; i++) {
                    int row = rows[i];
                    ids[i] = (int) modelo.getValueAt(row, 0);
                }

                for (int i = 0; i < ids.length; i++) {
                    if (User.delete(ids[i])) {
                        for (int u = 0; u < modelo.getRowCount(); u++) {
                            if ((int) modelo.getValueAt(u, 0) == ids[i]) {
                                modelo.removeRow(u);
                                break;
                            }
                        }
                    }
                }
            }
        };
    }

    private MouseListener modificarUsuario() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = view.jTable1.rowAtPoint(e.getPoint());
                    int id = (int) view.jTable1.getModel().getValueAt(row, 0);
                    String nombre = (String) view.jTable1.getModel().getValueAt(row, 1);
                    String role = (String) view.jTable1.getModel().getValueAt(row, 2);
                    view.update_nombre.setEnabled(true);
                    view.update_rol.setEnabled(true);
                    view.update_usuario.setEnabled(true);
                    view.update_id.setText(String.valueOf(id));
                    view.update_nombre.setText(nombre);
                    int size = view.update_rol.getModel().getSize();
                    for (int i = 0; i < size; i++) {
                        String item = view.update_rol.getItemAt(i);
                        if (item.equals(role)) {
                            view.update_rol.setSelectedIndex(i);
                            break;
                        }
                    }
                }
            }
        };
    }

    private ActionListener crearUsuario() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = view.form_nombre.getText();
                int role = view.listaRoles.getSelectedIndex();
                if (nombre.equals("")) {
                    JOptionPane.showInternalMessageDialog(view, "El Campo nombre es obligatorio.", "Error!", JOptionPane.ERROR_MESSAGE);
                } else {
                    User user = new User();
                    user.setName(nombre);
                    user.setRoleId((role + 1));
                    if (user.create()) {
                        DefaultTableModel modelo = (DefaultTableModel) view.jTable1.getModel();
                        modelo.addRow(new Object[]{user.getId(), user.getName(), user.getRole().getName()});
                    } else {
                        JOptionPane.showInternalMessageDialog(view, "Existe un problema con la conexión\nEl usuario no se pudo crear.", "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        };
    }
}
