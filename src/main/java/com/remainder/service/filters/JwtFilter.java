package com.remainder.service.filters;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtFilter extends OncePerRequestFilter {
	
	private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    
	private static final Logger log = LoggerFactory.getLogger(JwtFilter.class);

    private String secret;
    private long session;
    private long expirationTime;
    
    public JwtFilter(String secret, int hours, int minutes, int threshold) {
    	
    	this.secret = secret;
        this.session = TimeUnit.HOURS.toMillis(hours) + TimeUnit.MINUTES.toMillis(minutes);
        this.expirationTime = (this.session * threshold) / 100;

        log.info(String.format("Session duration: %d millis", this.session));
        log.info(String.format("Expiration time: %d millis", this.expirationTime));
    }

    public void sessionSettings(int hours, int minutes, int threshold) {
        this.session = TimeUnit.HOURS.toMillis(hours)
                + TimeUnit.MINUTES.toMillis(minutes);
        this.expirationTime = (this.session * threshold) / 100;

        log.info(String.format("Session duration : %d millis", this.session));
        log.info(String.format("Session : %d expiration_time", this.expirationTime));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {
        log.info(String.format("Secret: %s", secret));
        final HttpServletRequest request = (HttpServletRequest) req;

        final String authHeader = request.getHeader(AUTHORIZATION_HEADER);
        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            log.warn("Missing or invalid Authorization header.");
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header.");
            return;
        }

        try {
            final String token = authHeader.substring(BEARER_PREFIX.length());
            final Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            if (isTokenExpired(claims)) {
                updateTokenExpiration(request, claims);
            } else {
                request.setAttribute("token_update", false);
            }
        } catch (ExpiredJwtException e) {
            log.warn("Token has expired.", e);
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "The token has expired.");
            return;
        } catch (MalformedJwtException e) {
            log.warn("Invalid token.", e);
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "The token is not valid.");
            return;
        } catch (Exception e) {
            log.warn("Unexpected error while processing token.", e);
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unexpected error while processing token.");
            return;
        }

        chain.doFilter(req, res);
    }

    private boolean isTokenExpired(Claims claims) {
        long expirationTimeMillis = claims.getExpiration().getTime();
        long currentTimeMillis = System.currentTimeMillis();
        return expirationTimeMillis - currentTimeMillis < this.expirationTime;
    }

    private void updateTokenExpiration(HttpServletRequest request, Claims claims) {
        Date current = new Date(claims.getExpiration().getTime());
        claims.setIssuedAt(current)
                .setExpiration(new Date(current.getTime() + this.session));
        String updatedToken = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
        request.setAttribute("token_updated", updatedToken);
        request.setAttribute("token_update", true);
    }

}