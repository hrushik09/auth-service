package io.hrushik09.authservice.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AccessControlEvaluator {
    public boolean isDefaultAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!"default-admin".equals(authentication.getName())) {
            return false;
        }
        Set<String> authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        return authorities.contains("defaultAdmin");
    }
}
