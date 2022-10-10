package com.example.childsecuritysystem;

public class Person {
    int image;
    String Name;
    String Des;

    public Person(int image, String name, String des) {
        this.image = image;
        Name = name;
        Des = des;
    }

    public Person() {

    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDes() {
        return Des;
    }

    public void setDes(String des) {
        Des = des;
    }

}
