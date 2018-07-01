package com.example.abora.firebasesenior.Model;

public class Status {
    private String key;
    private String name;
    private String status;

    public String getKey() {
        return key;
    }

    //method chaining
    public Status setKey(String key) {
        this.key = key;
        return this;
    }

    public String getName() {
        return name;
    }

    public Status setName(String name) {
        this.name = name;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Status setStatus(String status) {
        this.status = status;
        return this;
    }
}
