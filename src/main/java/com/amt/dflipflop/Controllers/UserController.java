/**
 * Date de création     : 16.10.2021
 * Dernier contributeur : Ryan Sauge
 * Groupe               : AMT-D-Flip-Flop
 * Description          : Tester la connexion avec tomcat
 * Remarque             : -
 * Sources :
 * https://www.baeldung.com/spring-boot-testresttemplate
 */


package com.amt.dflipflop.Controllers;

import com.amt.dflipflop.Entities.authentification.User;
import com.amt.dflipflop.Entities.authentification.UserJson;
import com.amt.dflipflop.Entities.authentification.UserJsonResponse;
import com.amt.dflipflop.Repositories.UserRepository;
import com.amt.dflipflop.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@Controller
public class UserController {



    @Autowired
    private UserService userService;


    @GetMapping("/login")
    public String login(Model model) {
        //model.addAttribute("name", name);
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());

        return "register";
    }

    /*
https://www.it-swarm-fr.com/fr/java/spring-boot-automatic-json-object-controller/827515176/
  https://www.codejava.net/frameworks/spring-boot/user-registration-and-login-tutorial
 */
    @PostMapping("/auth/login")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserJson> login(@RequestBody UserJson user) {
        UserJson userResponse = userService.signin(user);
        return new ResponseEntity<UserJson>(userResponse, HttpStatus.OK);
    }

    /*
    https://www.it-swarm-fr.com/fr/java/spring-boot-automatic-json-object-controller/827515176/
      https://www.codejava.net/frameworks/spring-boot/user-registration-and-login-tutorial
     */
    @PostMapping("/accounts/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserJsonResponse> create(@RequestBody UserJson user) {
        UserJsonResponse createdUser = userService.signup(user);
        return new ResponseEntity<UserJsonResponse>(createdUser, HttpStatus.OK);
    }
}
