package io.hrushik09.authservice.authorities;

import io.hrushik09.authservice.authorities.dto.CreateAuthorityCommand;
import io.hrushik09.authservice.authorities.dto.CreateAuthorityResponse;
import io.hrushik09.authservice.authorities.exceptions.AuthorityAlreadyExists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AuthorityService {
    private final AuthorityRepository authorityRepository;

    public AuthorityService(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Transactional
    public CreateAuthorityResponse create(CreateAuthorityCommand cmd) {
        authorityRepository.findByName(cmd.name())
                .ifPresent(a -> {
                    throw new AuthorityAlreadyExists(cmd.name());
                });

        Authority authority = new Authority();
        authority.setName(cmd.name());
        Authority saved = authorityRepository.save(authority);
        return CreateAuthorityResponse.from(saved);
    }
}
