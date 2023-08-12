package com.example.spring.test.dyload;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class CustomClassLoader extends ClassLoader {
    private String classPath; // 类加载路径

    public CustomClassLoader(String classPath) {
        this.classPath = classPath;
    }
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            String filePath = classPath + name.replace(".", "/") + ".class";
            FileInputStream fileInputStream = new FileInputStream(filePath);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int data;
            while ((data = fileInputStream.read()) != -1) {
                byteArrayOutputStream.write(data);
            }
            byte[] bytes = byteArrayOutputStream.toByteArray();
            return defineClass(name, bytes, 0, bytes.length);
        } catch (IOException e) {
            throw new ClassNotFoundException("Class not found: " + name, e);
        }
    }
}

