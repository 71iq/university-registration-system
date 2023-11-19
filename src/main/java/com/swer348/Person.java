package com;

public abstract class Person {
    private String name;
    private String contact;

    protected Person(String name,String contact){
        this.name=name;
        this.contact=contact;
    }

    public String getName() {
        return name;
    }
    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }
    public void setName(String name) {
        this.name = name;
    }
}