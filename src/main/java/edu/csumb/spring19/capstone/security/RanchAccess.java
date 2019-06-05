package edu.csumb.spring19.capstone.security;

import edu.csumb.spring19.capstone.models.authentication.PLRole;
import edu.csumb.spring19.capstone.models.card.Card;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

@Component
public class RanchAccess {
    public boolean cardViewAccessAllowed(Optional<Card> card) {
        return (card.isPresent() && (

              // Has all access
              this.hasRole(PLRole.DATA_VIEW)

              || // or

              // Card is open, and rancher has access to ranch
              (!card.get().getClosed() &&
                    this.getRanchList().contains(card.get().getRanchName()))

        ));
    }

    public boolean hasRole(GrantedAuthority a) {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(a);
    }

    public List<String> getRanchList() {
        String token = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getCredentials());
        String unsignedToken = token.substring(0, token.lastIndexOf('.') + 1);

        String list = String.valueOf(
              Jwts.parser().parseClaimsJwt(unsignedToken).getBody().get("auth", LinkedHashMap.class).get("ranchAccess")
        );

        list = list.substring(1, list.length() - 1);

        return Arrays.asList(list.split(", "));
    }
}
