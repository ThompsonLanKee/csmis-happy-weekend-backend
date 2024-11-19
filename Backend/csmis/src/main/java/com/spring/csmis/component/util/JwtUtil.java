package com.spring.csmis.component.util;
//
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import javax.crypto.spec.SecretKeySpec;
//import java.security.Key;
//import java.util.Base64;
//import java.util.Date;
//
//@Component
//public class JwtUtil {
//
//    // Secret key encoded in Base64
//    private String jwtSecret = "7vWcVtAvT5OhnKAHADRb/h1gvvtz7uViMPdQKN1Oa1r5WT+MQ4lJQpvl+hN7cQ4iAzANs7cT0XbcFukNisX0nw==";
//    // JWT expiration time in milliseconds (e.g., 24 hours)
//    //private String jwtSecret = "c2298565199026c972da42cd334b32add2227077904013b7d1d2265c0502977d";
//    private int jwtExpirationMs = 60 * 2000;
//
//    // Decode the Base64 encoded secret key and create a Key object
//    private Key getSigningKey() {
//        byte[] keyBytes = Base64.getDecoder().decode(jwtSecret);
//        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS512.getJcaName());
//    }
//
//    public String getIdentityNoFromToken(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(getSigningKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody()
//                .get("identity_no", String.class);
//    }
//
//
//
//    // Generate the JWT token using the user's email
//    public String generateJwtToken(UserDetails userDetails) {
//        return Jwts.builder()
//                .setSubject(userDetails.getUsername())  // Use email as the subject
////                .claim("role", role)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))  // Set expiration
//                .signWith(getSigningKey(), SignatureAlgorithm.HS512)  // Use the signing key
//                .compact();  // Create the token
//    }
//
//    public boolean validateJwtToken(String authToken) {
//        try {
//            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(authToken);
//            return !isTokenExpired(authToken);  // Check if the token has expired
//        } catch (ExpiredJwtException e) {
//            System.out.println("JWT token is expired: " + e.getMessage());
//        } catch (Exception e) {
//            System.out.println("Invalid JWT token: " + e.getMessage());
//        }
//
//        return false;  // Return false if token is expired or invalid
//    }
//
//
//    // Extract email (subject) from the token
//    public String getEmailFromToken(String authToken) {
//        return Jwts.parserBuilder()
//                .setSigningKey(getSigningKey())  // Use the signing key
//                .build()
//                .parseClaimsJws(authToken)
//                .getBody()
//                .getSubject();  // The subject in the token is the email
//    }
//
//    // Check if the token has expired
//    public boolean isTokenExpired(String token) {
//        Date expiration = Jwts.parserBuilder()
//                .setSigningKey(getSigningKey())  // Use the signing key
//                .build()
//                .parseClaimsJws(token)
//                .getBody()
//                .getExpiration();
//        return expiration.before(new Date());  // Check if the expiration date has passed
//    }
//}

import com.spring.csmis.service.user.UserDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    // Secret key encoded in Base64
    private String jwtSecret = "7vWcVtAvT5OhnKAHADRb/h1gvvtz7uViMPdQKN1Oa1r5WT+MQ4lJQpvl+hN7cQ4iAzANs7cT0XbcFukNisX0nw==";
    private int jwtExpirationMs = 60 * 20000;  // Token expiration time (e.g., 2 minutes for testing)

    // Decode the Base64 encoded secret key and create a Key object
    private Key getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(jwtSecret);
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS512.getJcaName());
    }

    // Extract identity number from the token
    public String getIdentityNoFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("identity_no", String.class);
    }

    // Generate the JWT token using the user's details
    public String generateJwtToken(UserDetailsImpl userDetails) {
        // Get the role(s) from UserDetails
        String roles = userDetails.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .reduce((first, second) -> first + ", " + second)
                .orElse("");  // Join multiple roles into a single string

        String jwt= Jwts.builder()
                .setSubject(userDetails.getUsername())  // Use username (identityNo)
                .claim("roles", roles)  // Store the roles
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))  // Set expiration
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)  // Use the signing key
                .compact();  // Create the token

        return jwt;
    }

    // Validate the JWT token
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(authToken);
            return !isTokenExpired(authToken);  // Check if the token has expired
        } catch (ExpiredJwtException e) {
            System.out.println("JWT token is expired: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Invalid JWT token: " + e.getMessage());
        }

        return false;  // Return false if token is expired or invalid
    }

    // Extract email (subject) from the token
    public String getEmailFromToken(String authToken) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())  // Use the signing key
                .build()
                .parseClaimsJws(authToken)
                .getBody()
                .getSubject();  // The subject in the token is the email
    }

    // Check if the token has expired
    public boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())  // Use the signing key
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());  // Check if the expiration date has passed
    }
}
