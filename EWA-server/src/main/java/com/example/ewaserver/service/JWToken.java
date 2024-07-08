package com.example.ewaserver.service;

import com.example.ewaserver.Config;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;

public class JWToken {

    private static final String JWT_ISSUER_CLAIM = "iss";
    private static final String JWT_CALLNAME_CLAIM = "sub";
    private static final String JWT_ACCOUNTID_CLAIM = "id";
    private static final String JWT_ROLE_CLAIM = "role";
    public static final String JWT_ATTRIBUTE_NAME = "JWTokenInfo";

    private long accountId = 0;
    private String callName = null;
    private String role = null;
    private Date issued_at;
    private Date expired_at;


    public JWToken(String callName, Long accountId, String role) {
        this.callName = callName;
        this.accountId = accountId;
        this.role = role;
    }

    public String encode(String issuer, String passphrase, int expiration) {
        Key key = getKey(passphrase);
        this.issued_at = new Date();
        this.expired_at = new Date(System.currentTimeMillis() + expiration * 1000L);
        return Jwts.builder()
                .claim(JWT_CALLNAME_CLAIM, this.callName)
                .claim(JWT_ACCOUNTID_CLAIM, this.accountId)
                .claim(JWT_ROLE_CLAIM, this.role)
                .setIssuer(issuer)
                .setIssuedAt(this.issued_at)
                .setExpiration(this.expired_at)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    private static Key getKey(String passphrase) {
        byte[] hmacKey = passphrase.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(hmacKey, SignatureAlgorithm.HS256.getJcaName());
    }

    public static JWToken decode(String token, String issuer, String passphrase)
            throws ExpiredJwtException, MalformedJwtException {
        // Validate the token string and extract the claims
        Key key = getKey(passphrase);
        Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token);
        Claims claims = jws.getBody();

        if (!claims.get(JWT_ISSUER_CLAIM).toString().equals(issuer)) {
            throw new MalformedJwtException("Invalid issuer");
        }
        // build our token from the extracted claims
        JWToken jwToken = new JWToken(
                claims.get(JWT_CALLNAME_CLAIM).toString(),
                Long.valueOf(claims.get(JWT_ACCOUNTID_CLAIM).toString()),
                claims.get(JWT_ROLE_CLAIM).toString()
        );
        jwToken.setIssued_at(claims.getIssuedAt());
        jwToken.setExpired_at(claims.getExpiration());
        return jwToken;
    }


    public long validateImpersonation(long targetAccountId) {
        // checks whether the current account is authorised to access or impersonate the targetAccountId
        if (targetAccountId == 0)
            return this.getAccountId();
        else if (targetAccountId == this.getAccountId() || this.isAdmin())
            return targetAccountId;
        else
            return -1L;
    }

    public boolean isAdmin() {
        return this.role.toLowerCase().contains("admin");
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getCallName() {
        return callName;
    }

    public void setCallName(String callName) {
        this.callName = callName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public Date getIssued_at() {
        return issued_at;
    }

    public void setIssued_at(Date issued_at) {
        this.issued_at = issued_at;
    }

    public Date getExpired_at() {
        return expired_at;
    }

    public void setExpired_at(Date expired_at) {
        this.expired_at = expired_at;
    }
}
