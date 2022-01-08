/**
 * Date de création     :  janvier 2022
 * Groupe               : AMT-D-Flip-Flop
 * Description          : Class pour un utilisateur
 * Remarque             : -
 * Sources :
 * https://www.baeldung.com/spring-boot-testresttemplate
 */

package com.amt.dflipflop.Entities.authentification;

import javax.persistence.*;

// This tells Hibernate to make a table out of this class
@Entity
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    public void setRole(String role) {
        this.role = role;
    }

    private String role;

    private String username;

    private String email;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }


}


