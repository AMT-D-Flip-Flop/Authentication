/**
 * Date de création     : janvier 2021
 * Groupe               : AMT-D-Flip-Flop
 * Description          : Répertoire pour un utilisateur de la BDD
 * Remarque             : -
 * Sources :
 * https://www.codejava.net/frameworks/spring-boot/user-registration-and-login-tutorial
 */

package com.amt.dflipflop.Repositories;

import com.amt.dflipflop.Entities.authentification.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;


@RepositoryRestResource(exported = false)
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String userName);
}
