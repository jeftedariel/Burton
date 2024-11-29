/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.burton.entities;

/**
 *
 * @author jefte
 */
public class UserSession {

    public static UserSession instance;
    String name, email, avatar,role;
    int id;

    private UserSession() {
        this.id = 0;
        this.name = "?";
        this.email = "?";
        this.avatar = "?";
        this.role = "?"; 
    }
    
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public void login(int id, String name, String email, String avatar , String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.avatar = avatar;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getRole() {
        return role;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "UserSession{" + "name=" + name + ", email=" + email + ", avatar=" + avatar + ", role=" + role + ", id=" + id + '}';
    }

    public void logout() {
        this.id=0;
        this.name = "?";
        this.email = "?";
        this.avatar = "?";
        this.role = "?"; 
    }

}
