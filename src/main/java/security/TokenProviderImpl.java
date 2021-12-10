package security;

import com.amt.dflipflop.Entities.authentification.Account;
import io.jsonwebtoken.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: TCMALTUNKAN - MEHMET ANIL ALTUNKAN
 * @Date: 30.12.2019:09:40, Pzt
 **/
@Service
public class TokenProviderImpl implements TokenProvider {

    //@Value("${authentication-test.auth.tokenSecret}")
    private String tokenSecret = "secret";
    private boolean keyGenerated = false;

    @Value("${mode}")
    private String mode;

    @Value("${authentication-test.auth.tokenExpirationMsec}")
    private Long tokenExpirationMsec;

    @Value("${authentication-test.auth.refreshTokenExpirationMsec}")
    private Long refreshTokenExpirationMsec;

    private LinkedHashMap lp;

    //Read file content into the string with - Files.lines(Path path, Charset cs)

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
        // Consition holds true till
        // there is character in a string
        return st;
    }

    void generateKey() {
        if(!keyGenerated && mode.equals("prod")){
            try {
                tokenSecret = readLine(        "zone_secret/jwt.txt");
            }catch(Exception e){
                //
            }

            keyGenerated = true;
        }

    }

    @Override
    public Token generateAccessToken(String subject) {
        generateKey();
        Date now = new Date();
        Long duration = now.getTime() + tokenExpirationMsec;
        Date expiryDate = new Date(duration);
        String token = Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, tokenSecret)
                .compact();
        return new Token(Token.TokenType.ACCESS, token, duration, LocalDateTime.ofInstant(expiryDate.toInstant(), ZoneId.systemDefault()));
    }

    @Override
    public Token generateRefreshToken(String subject) {
        generateKey();
        Date now = new Date();
        Long duration = now.getTime() + refreshTokenExpirationMsec;
        Date expiryDate = new Date(duration);
        String token = Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, tokenSecret)
                .compact();
        return new Token(Token.TokenType.REFRESH, token, duration, LocalDateTime.ofInstant(expiryDate.toInstant(), ZoneId.systemDefault()));
    }
    @Override
    public HashMap getAccountFromToken(String token){
        generateKey();
        Claims claims = Jwts.parser().setSigningKey(tokenSecret.getBytes(Charset.forName("UTF-8"))).parseClaimsJws(token).getBody();
        lp = (LinkedHashMap) claims.get("account");
        return lp;
    }

    @Override
    public String getUsernameFromToken(String token) throws Exception {

        return (String) lp.get("username");
        //return claims.get("account").;

        //throw new Exception("d" + claims.getSubject() + claims.getExpiration() + claims.get("account") + claims.get("Account"));
        //return claims.getSubject();

    }



    @Override
    public LocalDateTime getExpiryDateFromToken(String token) {
        generateKey();
        Claims claims = Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(token).getBody();
        return LocalDateTime.ofInstant(claims.getExpiration().toInstant(), ZoneId.systemDefault());
    }

    @Override
    public boolean validateToken(String token) throws Exception {
        generateKey();
        try {
            /*
            https://stackoverflow.com/questions/65306718/io-jsonwebtoken-signatureexception-jwt-signature-does-not-match-locally-compute
             */
            JwtParser jwt = Jwts.parser().setSigningKey(tokenSecret.getBytes(Charset.forName("UTF-8")));
            jwt.parseClaimsJws(token.replace("{", "").replace("}","")).getBody();
            //jwt.parse(token);
            return true;
        } catch (SignatureException ex) {
            ex.printStackTrace();
        } catch (MalformedJwtException ex) {
            ex.printStackTrace();
        } catch (ExpiredJwtException ex) {
            ex.printStackTrace();
        } catch (UnsupportedJwtException ex) {
            ex.printStackTrace();
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
