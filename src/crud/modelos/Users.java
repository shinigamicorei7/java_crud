/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crud.modelos;

import crud.util.DataBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author shinigamicorei7
 */
public class Users {

    private static Connection conn;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public static Users find(int id) {
        conn = DataBase.getConnection();
        Users usuario = null;
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE id = ?");
            ps.setInt(1, id);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            rs.first();
            usuario = new Users();
            usuario.setId(rs.getInt("id"));
            usuario.setNombre(rs.getString("name"));
        } catch (SQLException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usuario;
    }

    public static List<Users> all() {
        conn = DataBase.getConnection();
        List<Users> usuarios;
        usuarios = new ArrayList<>();
        int i = 0;
        try {
            Statement ps = conn.createStatement();

            ResultSet rs = ps.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                Users usuario = new Users();
                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("name"));
                usuarios.add(usuario);
                i++;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usuarios;
    }

}
