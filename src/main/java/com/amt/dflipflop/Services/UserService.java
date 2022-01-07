package com.amt.dflipflop.Services;


import com.amt.dflipflop.Entities.authentification.*;
import com.amt.dflipflop.Repositories.RoleRepository;
import com.amt.dflipflop.Repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
        UserJson response = new UserJson();
        Optional<String> token = Optional.empty();
        Optional<User> userJpa = userRepository.findByUsername(user.getUsername());
        if (userJpa.isPresent()) {
            try {
                response.setUsername(userJpa.get().getUsername());
                //public Account(int id, String username, String role)
                response.setAccount(new Account(userJpa.get().getId(), userJpa.get().getUsername(), userJpa.get().getRole()));
                response.setUsername(userJpa.get().getRole());
                token = Optional.of(jwtProvider.createToken(userJpa.get().getUsername(), "user"));
                response.setToken(token.get());
            } catch (AuthenticationException | IOException e) {
                LOGGER.info("Log in failed for user {}", user.getUsername());
            }
        }
        return response;
    }

    /**
     * Create a new user in the database.
     *
     * @return Optional of user, empty if the user already exists.
     */
    public UserJsonResponse signup(UserJson user) {
        LOGGER.info("New user attempting to sign in");
        //Optional<User> user = Optional.empty();
        User userJpa = new User();
        UserJsonResponse response = new UserJsonResponse();
        if (!userRepository.findByUsername(user.getUsername()).isPresent()) {
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
            /*user = Optional.of(userRepository.save(new User(user.getUsername(),
                    passwordEncoder.encode(user.getPassword()),
                    role.get())));*/
            } catch (Exception e) {
                response.setError("Error exception");
            }

        } else {
            response.setError("The username already exist");
        }
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
}
