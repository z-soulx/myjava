package com.example.spring.test.dyload;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CustomClassLoader2 extends ClassLoader {
    private String classPath;
    private Map<String, Class<?>> loadedClasses = new ConcurrentHashMap<>();

    public CustomClassLoader2(String classPath) {
        this.classPath = classPath;
    }

    public void clearLoadedClass(String name) {
        loadedClasses.remove(name);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if (loadedClasses.containsKey(name)) {
            loadedClasses.remove(name);
        }
        try {
            String filePath = classPath + name.replace(".", "/") + ".class";
            FileInputStream fileInputStream = new FileInputStream(filePath);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int data;
            while ((data = fileInputStream.read()) != -1) {
                byteArrayOutputStream.write(data);
            }
            byte[] bytes = byteArrayOutputStream.toByteArray();
            Class<?> clazz = defineClass(name, bytes, 0, bytes.length);
            loadedClasses.put(name, clazz);
            return clazz;
        } catch (IOException e) {
            throw new ClassNotFoundException("Class not found: " + name, e);
        }
    }
}

