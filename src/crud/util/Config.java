/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crud.util;

import crud.Crud;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author shinigamicorei7
 */
public class Config {

    private final HashMap<String, String> params;

    private String configDir;

    public Config() {
        params = new HashMap<>();
        params.put("os", System.getProperty("os.name"));
        params.put("home", System.getProperty("user.home"));
        params.put("separador", System.getProperty("file.separator"));
        setConfigDir();
    }

    public Config(HashMap<String, String> params) {
        this.params = params;
        setConfigDir();
    }

    public static boolean importConfig(String text) {
        System.out.println(text);
        return true;
    }

    public static boolean makeConfig() {
        Config c = new Config();
        File dir = new File(c.getConfigDir());
        FileWriter version = null;
        if (dir.mkdir()) {
            try {
                version = new FileWriter(c.getVersionFile());
                PrintWriter pw = new PrintWriter(version);
                pw.println(Double.toString(Crud.version));
            } catch (IOException e) {
                return false;
            } finally {
                try {
                    // Nuevamente aprovechamos el finally para
                    // asegurarnos que se cierra el fichero.
                    if (null != version) {
                        version.close();
                    }
                } catch (Exception e2) {
                }
            }
            DataBase.init(c.getDatabaseFile());
        } else {
            System.err.println("No pudo crear el directorio");
            return false;
        }
        return true;
    }

    public boolean existsConfigDir() {
        File dir = new File(getConfigDir());
        return dir.exists() && dir.isDirectory();
    }

    public String getConfigDir() {
        return configDir;
    }

    public final void setConfigDir() {
        switch (params.get("os")) {
            case "Linux":
                configDir = params.get("home") + params.get("separador") + ".crud";
                break;
            default:
                configDir = params.get("home") + params.get("separador") + "crud";
                break;
        }
    }

    public boolean isCompatible() {
        File archivo = new File(getVersionFile());
        if (archivo.exists() && archivo.isFile()) {
            FileReader fr;
            try {
                fr = new FileReader(archivo);
                BufferedReader br = new BufferedReader(fr);
                Double version = Double.parseDouble(br.readLine());
                if (version == Crud.version) {
                    return true;
                } else if (version < Crud.version) {
                    return false;
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException e) {
                Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, e);
            }

        }
        return false;
    }

    public String getVersionFile() {
        return getConfigDir() + params.get("separador") + "version";
    }

    public String getDatabaseFile() {
        return getConfigDir() + params.get("separador") + "database.sqlite";
    }

}
