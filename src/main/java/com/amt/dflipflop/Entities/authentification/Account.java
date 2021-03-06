/**
 * Date de création     : janvier 2022
 * Groupe               : AMT-D-Flip-Flop
 * Description          : Information about an user
 * Remarque             : -
 */

package com.amt.dflipflop.Entities.authentification;

import javax.persistence.Id;
import java.io.Serializable;

public class Account implements Serializable {

    private int id;
    private String username;
    private String role;


    public Account(){

    }
    public Account(int id, String username, String role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


}
