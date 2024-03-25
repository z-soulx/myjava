package com.example.spring.design.plugin;

// SpellCheckPlugin.java
public class SpellCheckPlugin implements PluginInterface {
    @Override // 初始化拼写检查器
    public void initialize() {
        System.out.println("SpellCheckPlugin initialized.");
    }

    @Override  // 对文本执行拼写检查
    public void execute(String data) {
        System.out.println("Executing Spell Check on: " + data);
        // Simulate spell check logic
        System.out.println("Spell Check Complete: No errors found.");
    }

    @Override // 清理资源
    public void cleanup() {
        System.out.println("SpellCheckPlugin cleaned up.");
    }
}