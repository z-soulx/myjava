package com.example.spring.design.plugin;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class PluginManager {
    private List<Plugin> plugins = new ArrayList<>();

    public void loadPlugins(String pluginDir) {
        File dir = new File(pluginDir);
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                try {
                    URL[] urls = new URL[]{file.toURI().toURL()};
                    URLClassLoader classLoader = new URLClassLoader(urls, this.getClass().getClassLoader());
                    Class<?> pluginClass = Class.forName(file.getName().replace(".jar", ".Plugin"), true, classLoader);
                    Plugin plugin = (Plugin) pluginClass.getDeclaredConstructor().newInstance();
                    plugins.add(plugin);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void initializePlugins() {
        for (Plugin plugin : plugins) {
            plugin.initialize();
        }
    }

    public void executePlugins(String text) {
        for (Plugin plugin : plugins) {
            plugin.execute(text);
        }
    }

    public void cleanupPlugins() {
        for (Plugin plugin : plugins) {
            plugin.cleanup();
        }
    }
}