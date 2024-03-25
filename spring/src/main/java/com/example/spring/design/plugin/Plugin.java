package com.example.spring.design.plugin;

public interface Plugin {
    void initialize();
    void execute(String text);
    void cleanup();
}