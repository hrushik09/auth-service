package io.hrushik09.authservice.authorities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
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

    @Nested
    class Create {
        @Test
        void shouldThrowWhenAuthorityWithNameAlreadyExists() {
            String duplicateAuthority = "duplicateAuthority";
            when(authorityRepository.findByName(duplicateAuthority))
                    .thenThrow(new AuthorityAlreadyExists(duplicateAuthority));

            assertThatThrownBy(() -> authorityService.create(new CreateAuthorityCommand(duplicateAuthority)))
                    .isInstanceOf(AuthorityAlreadyExists.class)
                    .hasMessage("Authority with name duplicateAuthority already exists");
        }

        @Test
        void shouldSaveInRepositoryWhenCreatingAuthority() {
            String name = "api:write";
            Authority a = new Authority();
            a.setId(23);
            a.setName(name);
            when(authorityRepository.save(any()))
                    .thenReturn(a);

            authorityService.create(new CreateAuthorityCommand(name));

            ArgumentCaptor<Authority> authorityArgumentCaptor = ArgumentCaptor.forClass(Authority.class);
            verify(authorityRepository).save(authorityArgumentCaptor.capture());
            Authority captorValue = authorityArgumentCaptor.getValue();
            assertThat(captorValue.getName()).isEqualTo(name);
        }

        @Test
        void shouldReturnCreatedAuthority() {
            String name = "api:update";
            Authority a = new Authority();
            a.setId(23);
            a.setName(name);
            when(authorityRepository.save(any()))
                    .thenReturn(a);

            CreateAuthorityResponse created = authorityService.create(new CreateAuthorityCommand(name));

            assertThat(created).isNotNull();
            assertThat(created.id()).isNotNull();
            assertThat(created.name()).isEqualTo(name);
        }
    }
}