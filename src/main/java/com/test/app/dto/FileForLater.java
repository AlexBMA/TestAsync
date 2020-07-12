package com.test.app.dto;

public class FileForLater {

    private String id;
    private String name;
    private byte[] bytes;

    public FileForLater(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public FileForLater(String id, String name, byte[] bytes) {
        this.id = id;
        this.name = name;
        this.bytes = bytes;
    }

    public FileForLater() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
