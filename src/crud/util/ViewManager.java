/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crud.util;

import java.util.HashMap;
import javax.swing.JInternalFrame;

/**
 *
 * @author shinigamicorei7
 */
public class ViewManager {

    public static HashMap<String, JInternalFrame> views = new HashMap<>();

    public static void addView(String key, JInternalFrame jiframe) {
        views.put(key, jiframe);
    }

    public static JInternalFrame getView(String key) {
        return views.get(key);
    }
}
