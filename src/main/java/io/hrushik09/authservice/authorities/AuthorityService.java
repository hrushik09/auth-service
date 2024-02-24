package io.hrushik09.authservice.authorities;

public class AuthorityService {
    private final AuthorityRepository authorityRepository;

    public AuthorityService(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

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
