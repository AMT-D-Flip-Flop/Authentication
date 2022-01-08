package com.amt.dflipflop.Services;


import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Utility Class for common Java Web Token operations
 * <p>
 * Created by Mary Ellen Bowman
 * <p>
 * Other sources : https://www.toptal.com/java/rest-security-with-jwt-spring-security-and-java
 */
@Component
public class JwtProvider {
    Logger logger = LoggerFactory.getLogger(JwtProvider.class);
    //Define the key choice for jwt
    //private String mode = "prod";
    private String mode = "noProd";
    private String jwtfileNamePath = "/opt/tomcat/webapps/zone_secret/jwt.txt";
    //@Value("${authentication-test.auth.tokenSecret}")
    private String tokenSecret = "secret";

    //Define the json string in jwt
    private final String ROLES_KEY = "role";

    private JwtParser parser;

    private long validityInMilliseconds;

    private boolean keyGenerated;

    private static String readLine(String filePath) throws IOException {
        // File path is passed as parameter
        File file = new File(
                filePath);

        // Note:  Double backquote is to avoid compiler
        // interpret words
        // like \test as \t (ie. as a escape sequence)

        // Creating an object of BufferedReader class
        BufferedReader br
                = new BufferedReader(new FileReader(file));

        // Declaring a string variable
        String st = br.readLine();
        return st;
    }

    void generateKey() throws IOException {
        if (!keyGenerated && mode.equals("prod")) {
            logger.error("reade file");
            tokenSecret = readLine(jwtfileNamePath);
            keyGenerated = true;
        }
    }

    @Autowired
    public JwtProvider() throws IOException {
        generateKey();
        this.validityInMilliseconds = 86400000;//24h00
    }


    /**
     * Create JWT string given username and roles.
     *
     * @param username
     * @param role
     * @return jwt string
     */
    public String createToken(String username, String role) throws IOException {

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", role);
        claims.setIssuer("DFLIPFLOP");
        Date now = new Date();
        Date expiresAt = new Date(now.getTime() + validityInMilliseconds);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiresAt)
                .signWith(SignatureAlgorithm.HS256, tokenSecret.getBytes(Charset.forName("UTF-8")))
                .compact();
    }

}
