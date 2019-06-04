package edu.csumb.spring19.capstone.security;

import edu.csumb.spring19.capstone.models.authentication.PLRole;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    public Authentication getAuthentication(String token) {
        String unsignedToken = token.substring(0, token.lastIndexOf('.') + 1);
        return new UsernamePasswordAuthenticationToken(getUsername(unsignedToken), getPassword(unsignedToken), getPermissions(unsignedToken));
    }

    public String getUsername(String token) {
        return Jwts.parser().parseClaimsJwt(token).getBody().getSubject();
    }

    public String getPassword(String token) {
        return token;
    }

    public List<GrantedAuthority> getPermissions(String token) {
        String list = String.valueOf(
              Jwts.parser().parseClaimsJwt(token).getBody().get("auth", LinkedHashMap.class).get("permissions")
        );

        list = list.substring(1, list.length() - 1);

        return Arrays.stream(list.split(", ")).map(PLRole::valueOf).collect(Collectors.toList());
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) throws Exception {
        try {
            // TODO: Perform API call to user service
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new Exception("Expired or invalid JWT token.");
        }
    }
}
