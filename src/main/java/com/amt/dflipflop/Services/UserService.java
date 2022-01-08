package com.amt.dflipflop.Services;


import com.amt.dflipflop.Entities.authentification.*;
import com.amt.dflipflop.Repositories.RoleRepository;
import com.amt.dflipflop.Repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService implements UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;


    private AuthenticationManager authenticationManager;

    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager,
                       RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    /**
     * Sign in a user into the application, with JWT-enabled authentication
     *
     * @return Optional of the Java Web Token, empty otherwise
     */
    public UserJson signin(UserJson user) {
        LOGGER.info("New user attempting to sign in");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserJson response = new UserJson();
        Optional<String> token = Optional.empty();
        Optional<User> userJpa = userRepository.findByUsername(user.getUsername());
        List<String> errors = new ArrayList<>();
        if (userJpa.isPresent() && userJpa.get().getPassword() == passwordEncoder.encode(user.getPassword())) {
            try {
                response.setUsername(userJpa.get().getUsername());
                response.setAccount(new Account(userJpa.get().getId(), userJpa.get().getUsername(), userJpa.get().getRole()));
                response.setUsername(userJpa.get().getRole());
                token = Optional.of(jwtProvider.createToken(userJpa.get().getUsername(), "user"));
                response.setToken(token.get());
            } catch (AuthenticationException | IOException e) {
                LOGGER.info("Log in failed for user {}", user.getUsername());
                errors.add("Invalid username or password");
            }
        }
        else{
            errors.add("Invalid username or password");
        }
        response.setErrors(errors);
        return response;
    }

    /**
     * Create a new user in the database.
     *
     * @return Optional of user, empty if the user already exists.
     */
    public UserJsonResponse signup(UserJson user) {
        LOGGER.info("New user attempting to sign in");
        User userJpa = new User();
        UserJsonResponse response = new UserJsonResponse();
        List<String> errors = new ArrayList<>();
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            errors.add("The username already exist");
        }
        if(!checkPasswordPolicy(user.getPassword())) {
            errors.add("The password does not match the secutity politics, it should be at least 8 char long, containe at least one uppercase char, one lowercase char, one digit and one special character");
        }
        if(errors.size() == 0) {
            try {
                //Optional<Role> role = roleRepository.findByRoleName("user");
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String encodedPassword = passwordEncoder.encode(user.getPassword());
                userJpa.setUsername(user.getUsername());
                userJpa.setPassword(encodedPassword);
                userJpa.setRole("user");
                response.setUsername(user.getUsername());
                response.setRole("user");
                userJpa = userRepository.save(userJpa);
                response.setId(userJpa.getId());
                response.setRole(userJpa.getRole());
            } catch (Exception e) {
                errors.add("Error exception");
            }
        }
        response.setErrors(errors);
        return response;
    }

    public List<User> getAll() {
        return (List<User>) userRepository.findAll();
    }

    @Autowired
    private UserRepository userRepo;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username).get();
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserDetails(new User());
    }

    private Boolean checkPasswordPolicy(String password){
        Pattern textPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,}$");
        return textPattern.matcher(password).matches();
    }
}
