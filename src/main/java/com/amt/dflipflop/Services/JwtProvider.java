/**
 * Date de création     : janvier 2021
 * Groupe               : AMT-D-Flip-Flop
 * Description          : Provider pour les JWT
 * Remarque             : -
 * Sources :
 * -Mary Ellen Bowman - Linkedin
 * -https://www.toptal.com/java/rest-security-with-jwt-spring-security-and-java
 */


package com.amt.dflipflop.Services;


import com.amt.dflipflop.Constantes;
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


@Component
public class JwtProvider {
    Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    //Define the key choice for jwt
    private String tokenSecret = Constantes.tokenSecretDefault;

    //Define the json string in jwt
    private final String ROLES_KEY = "role";
    private final String ISSUER = "DFLIPFLOP";
    private final String CHARSET = "UTF-8";


    private final long validityInMilliseconds = 86400000; //24h00

    private boolean keyGenerated;

    private static String readLine(String filePath) throws IOException {
        // File path is passed as parameter
        File file = new File(
                filePath);

        // Creating an object of BufferedReader class
        BufferedReader br
                = new BufferedReader(new FileReader(file));
        // read line
        return br.readLine();
    }

    void generateKey() throws IOException {
        if (!keyGenerated && Constantes.IS_PROD) {
            logger.error("reade file");
            tokenSecret = readLine(Constantes.jwtfileNamePath);
            keyGenerated = true;
        }
    }

    @Autowired
    public JwtProvider() throws IOException {
        generateKey();
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
        claims.put(ROLES_KEY, role);
        claims.setIssuer(ISSUER);
        Date now = new Date();
        Date expiresAt = new Date(now.getTime() + validityInMilliseconds);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiresAt)
                .signWith(SignatureAlgorithm.HS256, tokenSecret.getBytes(Charset.forName(CHARSET)))
                .compact();
    }

}
