package security;

import com.amt.dflipflop.Entities.authentification.Account;
import io.jsonwebtoken.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    @Value("${authentication-test.auth.tokenExpirationMsec}")
    private Long tokenExpirationMsec;

    @Value("${authentication-test.auth.refreshTokenExpirationMsec}")
    private Long refreshTokenExpirationMsec;

    private LinkedHashMap lp;

    @Override
    public Token generateAccessToken(String subject) {
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
        Claims claims = Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(token).getBody();
        return LocalDateTime.ofInstant(claims.getExpiration().toInstant(), ZoneId.systemDefault());
    }

    @Override
    public boolean validateToken(String token) throws Exception {
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
