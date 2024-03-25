package com.example.spring.design.commonObjectPool;

public class ResponseData {
    private int id;
    private String name;
    private String description;

    // 构造器、getter和setter省略

    public ResponseData() {
        System.out.println("ResponseData created");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":")
            .append(id);
        sb.append(",\"name\":\"")
            .append(name).append('\"');
        sb.append(",\"description\":\"")
            .append(description).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
