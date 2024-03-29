package io.hrushik09.authservice.authorities;

import io.hrushik09.authservice.authorities.dto.AuthorityDTO;
import io.hrushik09.authservice.authorities.dto.CreateAuthorityCommand;
import io.hrushik09.authservice.authorities.dto.CreateAuthorityResponse;
import io.hrushik09.authservice.authorities.exceptions.AuthorityAlreadyExists;
import io.hrushik09.authservice.authorities.exceptions.AuthorityDoesNotExist;
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
                .ifPresent(authority -> {
                    throw new AuthorityAlreadyExists(cmd.name());
                });

        Authority authority = new Authority();
        authority.setName(cmd.name());
        Authority saved = authorityRepository.save(authority);
        return CreateAuthorityResponse.from(saved);
    }

    public AuthorityDTO fetchById(Integer id) {
        return authorityRepository.findById(id)
                .map(AuthorityDTO::from)
                .orElseThrow(() -> new AuthorityDoesNotExist(id));
    }

    public Authority fetchByName(String name) {
        return authorityRepository.findByName(name)
                .orElseThrow(() -> new AuthorityDoesNotExist(name));
    }
}
