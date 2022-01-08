/**
 * Date de création     : 16.10.2021
 * Dernier contributeur : Ryan Sauge
 * Groupe               : AMT-D-Flip-Flop
 * Description          : Tester la connexion avec tomcat
 * Remarque             : -
 * Sources :
 * https://www.baeldung.com/spring-boot-testresttemplate
 */

package com.amt.dflipflop.Repositories;
import com.amt.dflipflop.Entities.authentification.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.Optional;

/*
Source : https://www.codejava.net/frameworks/spring-boot/user-registration-and-login-tutorial
 */
@RepositoryRestResource(exported = false)
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String userName);
}
