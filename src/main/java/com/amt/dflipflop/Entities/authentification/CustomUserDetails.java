/**
 * Date de création     : janvier 2021
 * Groupe               : AMT-D-Flip-Flop
 * Description          : DTO représentant un utilisateur issu de la BDD
 * Remarque             : -
 * Sources :
 * https://www.baeldung.com/spring-boot-testresttemplate
 * https://www.codejava.net/frameworks/spring-boot/user-registration-and-login-tutorial
 */


package com.amt.dflipflop.Entities.authentification;


import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

    private User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getFullName() {
        //return user. + " " + user.getLastName();
        //TODO : set firstname
        return "";
    }

}
