package com.iqbal.karim.ahmed.salik.project.databaseClasses;

public class User {
    private int id;
    private String name;
    private String password;
    private String email;
    private int image;

    public User(int id, String user, String password, String email, int image) {
        this.name = user;
        this.id = id;
        this.password = password;
        this.email = email;
        this.image = image;
    }

    public User(int id, String name, String email) {
        this.name = name;
        this.id = id;
        this.email = email;
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public User(int id, String name, String email, int image) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.image = image;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String user) {
        this.name = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
