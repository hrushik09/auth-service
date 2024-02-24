package io.hrushik09.authservice.setup;

import io.hrushik09.authservice.authorities.AuthorityService;
import io.hrushik09.authservice.authorities.dto.CreateAuthorityCommand;
import io.hrushik09.authservice.authorities.dto.CreateAuthorityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EndToEndTestDataPersister {
    @Autowired
    private AuthorityService authorityService;

    public CreateAuthorityResponse persistedAuthority(String name) {
        return authorityService.create(new CreateAuthorityCommand(name));
    }
}
