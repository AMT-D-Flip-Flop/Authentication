package com.amt.dflipflop.Entities.authentification;

/**
* Date de création     : janvier 2022
* Dernier contributeur : Ryan Sauge
* Groupe               : AMT-D-Flip-Flop
* Description          : Serialiser la réponse du serveur d'authentification
*/

import java.io.Serializable;
import java.util.List;


public class UserJson implements Serializable {

    private String token;


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
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
