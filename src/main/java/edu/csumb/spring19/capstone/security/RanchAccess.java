package edu.csumb.spring19.capstone.security;

import edu.csumb.spring19.capstone.models.card.Card;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

@Component
public class RanchAccess {
    public List<String> getRanchList() {
        String token = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getCredentials());
        String unsignedToken = token.substring(0, token.lastIndexOf('.') + 1);

        String list = String.valueOf(
              Jwts.parser().parseClaimsJwt(unsignedToken).getBody().get("auth", LinkedHashMap.class).get("ranchAccess")
        );

        list = list.substring(1, list.length() - 1);

        return Arrays.asList(list.split(", "));
    }

    public boolean cardAccessAllowed(Optional<Card> card) {

        // Card exists, card is open, and rancher has access to ranch
        return (
                    card.isPresent() &&
                    !card.get().getClosed() &&
                    this.getRanchList().contains(card.get().getRanchName())
        );
    }
}
