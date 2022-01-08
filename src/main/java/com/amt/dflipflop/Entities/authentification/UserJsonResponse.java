package com.amt.dflipflop.Entities.authentification;


/**
 * Date de création     : janvier 2021
 * Dernier contributeur : Ryan Sauge
 * Groupe               : AMT-D-Flip-Flop
 * Description          : Serialiser la réponse du serveur d'authentification
 */

import java.io.Serializable;
import java.util.List;


public class UserJsonResponse implements Serializable {

    private String token;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    //Create count
    private int id;

    //Create role
    private String role;

    public List<String> getErrors() {
        return this.errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    private List<String> errors;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String username;
    private Account account;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setAccountPublic(int id, String username, String role) {
        setAccount(new Account(id, username, role));
    }
}

