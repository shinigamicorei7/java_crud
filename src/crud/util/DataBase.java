/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crud.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * @author shinigamicorei7
 */
public class DataBase {

    private static Connection connection = null;

    public static void init(String path) {
        try {
            Class.forName("org.sqlite.JDBC");

            try {
                setConnection(path);
                Statement statement = connection.createStatement();
                statement.setQueryTimeout(30);

                statement.executeUpdate("CREATE TABLE IF NOT EXISTS users(id INTEGER PRIMARY KEY ASC AUTOINCREMENT, name TEXT UNIQUE, role_id INTEGER NOT NULL);");
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS roles(id INTEGER PRIMARY KEY ASC AUTOINCREMENT, name TEXT NOT NULL UNIQUE, description TEXT);");

                statement.executeUpdate("INSERT INTO roles (name,description) VALUES ('Admin','Administrador Global')");
                statement.executeUpdate("INSERT INTO roles (name,description) VALUES ('Mod','Moderador principal')");
                statement.executeUpdate("INSERT INTO roles (name,description) VALUES ('User','Usuario simple')");

                statement.executeUpdate("INSERT INTO users (name,role_id) VALUES ('Bryan',1)");
                statement.executeUpdate("INSERT INTO users (name,role_id) VALUES ('Israel',2)");
                statement.executeUpdate("INSERT INTO users (name,role_id) VALUES ('Alex',3)");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.toString(), "Error con el archivo", JOptionPane.ERROR_MESSAGE);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.toString(), "Error con el driver.", JOptionPane.ERROR_MESSAGE);
        }

    }

    public static void setConnection(String path) {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + path);
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        DataBase.connection = conn;
    }

    public static Connection getConnection() {
        Config c = new Config();
        if (DataBase.connection == null) {
            DataBase.setConnection(c.getDatabaseFile());
        }

        return connection;
    }
}
