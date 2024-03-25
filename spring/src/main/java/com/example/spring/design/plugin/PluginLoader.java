package com.example.spring.design.plugin;// PluginLoader.java
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class PluginLoader {
    private final List<PluginInterface> plugins = new ArrayList<>();

    public void loadPlugins(String pluginsDirPath,File file2) throws Exception {
        File pluginsDir = new File(pluginsDirPath);
        if (file2 != null) pluginsDir = file2;
        File[] files = pluginsDir.listFiles();
	    if (files == null) {
		    return;
	    }

        URL[] urls = new URL[files.length];
        for (int i = 0; i < files.length; i++) {
            urls[i] = files[i].toURI().toURL();
        }

        URLClassLoader classLoader = new URLClassLoader(urls);
        for (File file : files) {
            if (file.getName().endsWith(".class")) {
                String className = file.getName().substring(0, file.getName().length() - 6);
                String packageName = "com.example.spring.design.plugin";  // replace with your actual package name if different
                if (!"SpellCheckPlugin".equals(className)) {
                    continue;
                }
                Class<?> cls = classLoader.loadClass(packageName + "." + className);

                if (PluginInterface.class.isAssignableFrom(cls) && !cls.isInterface()) {
                    plugins.add((PluginInterface) cls.getDeclaredConstructor().newInstance());
                }
            }
        }
    }

    public List<PluginInterface> getPlugins() {
        return plugins;
    }
}