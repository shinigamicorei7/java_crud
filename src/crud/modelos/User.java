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
 * @author shinigamicorei7
 */
public class User {

    private static Connection conn;
    private int id;
    private int role_id;
    private String name;

    public User() {
    }

    public User(int id, int role_id, String name) {
        this.id = id;
        this.role_id = role_id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String nombre) {
        this.name = nombre;
    }

    public int getRoleId() {
        return role_id;
    }

    public void setRoleId(int role_id) {
        this.role_id = role_id;
    }

    public static User find(int id) {
        conn = DataBase.getConnection();
        User usuario = null;
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                usuario = new User();
                usuario.setId(rs.getInt("id"));
                usuario.setName(rs.getString("name"));
                usuario.setRoleId(rs.getInt("role_id"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usuario;
    }

    public static List<User> all() {
        conn = DataBase.getConnection();
        List<User> usuarios = new ArrayList<>();
        try {
            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                User usuario = new User();
                usuario.setId(rs.getInt("id"));
                usuario.setName(rs.getString("name"));
                usuario.setRoleId(rs.getInt("role_id"));
                usuarios.add(usuario);
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usuarios;
    }

    public Role getRole() {
        return Role.find(getRoleId());
    }

    public Boolean create() {
        conn = DataBase.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO users (name,role_id) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, getName());
            ps.setInt(2, getRoleId());
            ps.execute();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            while (generatedKeys.next()) {
                setId((int) generatedKeys.getLong(1));
            }
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public Boolean update() {
        conn = DataBase.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE users SET name = ?, role_id = ? WHERE id = ?");
            ps.setString(1, getName());
            ps.setInt(2, getRoleId());
            ps.setInt(3, getId());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static boolean delete(int user_id) {
        conn = DataBase.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM users WHERE id = ?");
            ps.setInt(1, user_id);
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
