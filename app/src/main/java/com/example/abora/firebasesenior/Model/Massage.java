package com.example.abora.firebasesenior.Model;

public class Massage {
    private String name;
    private String massage;
private String massageKey ;

    public String getMassageKey() {
        return massageKey;
    }

    public void setMassageKey(String massageKey) {
        this.massageKey = massageKey;
    }

    public String getName() {
        return name;
    }

    public Massage setName(String name) {
        this.name = name;
        return this;
    }

    public String getMassage() {
        return massage;
    }

    public Massage setMassage(String massage) {
        this.massage = massage;
        return this;
    }
}
