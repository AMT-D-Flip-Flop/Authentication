package security;

import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * @Author: TCMALTUNKAN - MEHMET ANIL ALTUNKAN
 * @Date: 30.12.2019:09:39, Pzt
 **/
public interface TokenProvider {
    Token generateAccessToken(String subject);

    Token generateRefreshToken(String subject);

    String getUsernameFromToken(String token) throws Exception;

    HashMap getAccountFromToken(String token);

    LocalDateTime getExpiryDateFromToken(String token);

    boolean validateToken(String token) throws Exception;

}
