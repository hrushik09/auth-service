package io.hrushik09.authservice.authorities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorityServiceTest {
    private AuthorityService authorityService;
    @Mock
    private AuthorityRepository authorityRepository;

    @BeforeEach
    void setUp() {
        authorityService = new AuthorityService(authorityRepository);
    }

    @Test
    void shouldThrowWhenAuthorityWithNameAlreadyExists() {
        String duplicateAuthority = "duplicateAuthority";
        when(authorityRepository.findByName(duplicateAuthority))
                .thenThrow(new AuthorityAlreadyExists(duplicateAuthority));

        assertThatThrownBy(() -> authorityService.create(new CreateAuthorityCommand(duplicateAuthority)))
                .isInstanceOf(AuthorityAlreadyExists.class)
                .hasMessage("Authority with name duplicateAuthority already exists");
    }
}