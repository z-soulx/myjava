package com.example.spring.design.plugin;

import java.io.File;
import java.net.URL;

// Main.java
public class Main {
    public static void main(String[] args) {
        PluginLoader loader = new PluginLoader();
        try {
            URL url = ClassLoader.getSystemClassLoader().getResource("com/example/spring/design/plugin/");
            File dir = new File(url.getFile());
            String pluginDir = System.getProperty("user.dir") + "/com/example/spring/design/plugin/";
            loader.loadPlugins(pluginDir,dir);
            for (PluginInterface plugin : loader.getPlugins()) {
                plugin.initialize();
                plugin.execute("Hello, this is a test string.");
                plugin.cleanup();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
