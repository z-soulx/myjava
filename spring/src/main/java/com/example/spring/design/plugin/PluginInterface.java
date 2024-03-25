package com.example.spring.design.plugin;

// PluginInterface.java
public interface PluginInterface {
    void initialize();
    void execute(String data);
    void cleanup();
}