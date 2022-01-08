package edu.csumb.spring19.capstone.security;

import edu.csumb.spring19.capstone.dto.RestDTO;
import edu.csumb.spring19.capstone.models.authentication.PLRole;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {
    private final String authURL = "http://userservice:8080/api/user/me/tokenValid";

    public Authentication getAuthentication(String token) {
        String unsignedToken = token.substring(0, token.lastIndexOf('.') + 1);
        return new UsernamePasswordAuthenticationToken(getUsername(unsignedToken), getPassword(unsignedToken), getPermissions(unsignedToken));
    }

    public String getUsername(String token) {
        return Jwts.parserBuilder().build().parseClaimsJwt(token).getBody().getSubject();
    }

    public String getPassword(String token) {
        return token;
    }

    public List<GrantedAuthority> getPermissions(String token) {
        String list = String.valueOf(
              Jwts.parserBuilder().build().parseClaimsJwt(token).getBody().get("auth", LinkedHashMap.class).get("permissions")
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

    public boolean validateToken(String token) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            HttpEntity entity = new HttpEntity(headers);

            ResponseEntity<RestDTO> auth = (new RestTemplate()).exchange(authURL, HttpMethod.GET, entity, RestDTO.class);
            if (auth.hasBody() && auth.getBody() != null) return auth.getBody().getSuccess();
        } catch (Exception ignored) {}

        return false;
    }
}
