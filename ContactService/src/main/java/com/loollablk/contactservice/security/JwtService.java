package com.loollablk.contactservice.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    //TODO move this to valut
    public static final String SECRET = "357638792F423F4428472B4B6250655368566D597133743677397A2443264629";

    /**
     *
     * akes a JWT token as input and extracts the subject (usually the username) from the token’s claims.
     * It uses the `extractClaim` method to extract the subject claim

     * @param token
     * @return
     *
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * extracts the expiration date from the JWT token’s claims.
     * It’s used to determine whether the token has expired or not
     *
     * @param token
     * @return
     */
    public Date extractExpiration( String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     *
     *  generic method used to extract a specific claim from the JWT token’s claims.
     *  It takes a JWT token and a `Function` that specifies how to extract the desired claim
     *  (e.g., subject or expiration) and returns the extracted claim
     *
     * @param token
     * @param claimsResolver
     * @param <T>
     * @return
     */
    public <T> T extractClaim(String token, Function <Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * parses the JWT token and extracts all of its claims.
     * It uses the `Jwts` builder to create a parser that is configured with the appropriate
     * signing key and then extracts the token’s claims
     *
     * @param token
     * @return
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignKey ())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * checks whether a JWT token has expired by comparing the token’s expiration date
     * (obtained using `extractExpiration`) to the current date.
     * If the token has expired, it returns `true`;
     * otherwise, it returns `false`.
     *
     * @param token
     * @return
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     *  validate a JWT token.
     *  It first extracts the username from the token and then checks whether
     *  it matches the username of the provided `UserDetails` object. Additionally, it verifies whether the token has expired.
     *  If the token is valid, it returns `true`;
     *  otherwise, it returns `false`.
     *
     * @param token
     * @param userDetails
     * @return
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


    /**
     * generate a JWT token. It takes a username as input, creates a set of claims (e.g., subject, issued-at, expiration),
     * and then builds a JWT token using the claims and the signing key.
     * The resulting token is returned.
     *
     * @param username
     * @return
     */
    public String GenerateToken(String username){
        Map <String, Object> claims = new HashMap <> ();
        return createToken(claims, username);
    }


    /**
     *  responsible for creating the JWT token.
     *  It uses the `Jwts` builder to specify the claims, subject, issue date, expiration date, and the signing key.
     *  The token is then signed and compacted to produce the final JWT token
     *
     * @param claims
     * @param username
     * @return
     */
    private String createToken(Map<String, Object> claims, String username) {

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject (username)
                .issuedAt ( new Date(System.currentTimeMillis()))
                .expiration (new Date(System.currentTimeMillis()+1000*60*1))
                .and ()
                .signWith(getSignKey())
                .compact();
    }

    /**
     * obtain the signing key for JWT token creation and validation.
     * It decodes the `SECRET` key, which is typically a Base64-encoded key,
     * and converts it into a cryptographic key using the `Keys.hmacShaKeyFor` method
     *
     * @return
     */
    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        SecretKey secret = Keys.hmacShaKeyFor(keyBytes);
        return secret;
    }
}
