package com.example.spring.design.plugin;

public class TextProcessor {
    public static void main(String[] args) {
        PluginManager pluginManager = new PluginManager();
        pluginManager.loadPlugins("plugins");
        pluginManager.initializePlugins();

        String text = "..."; // 要处理的文本
        pluginManager.executePlugins(text);

        pluginManager.cleanupPlugins();
    }
}