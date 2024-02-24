package io.hrushik09.authservice.authorities;

import io.hrushik09.authservice.authorities.dto.AuthorityDTO;
import io.hrushik09.authservice.authorities.dto.CreateAuthorityCommand;
import io.hrushik09.authservice.authorities.dto.CreateAuthorityResponse;
import io.hrushik09.authservice.authorities.exceptions.AuthorityAlreadyExists;
import io.hrushik09.authservice.authorities.exceptions.AuthorityDoesNotExist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static io.hrushik09.authservice.authorities.AuthorityBuilder.anAuthority;
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
                    .thenReturn(Optional.of(anAuthority().withId(23).build()));

            assertThatThrownBy(() -> authorityService.create(new CreateAuthorityCommand(duplicateAuthority)))
                    .isInstanceOf(AuthorityAlreadyExists.class)
                    .hasMessage("Authority with name duplicateAuthority already exists");
        }

        @Test
        void shouldSaveInRepositoryWhenCreatingAuthority() {
            String name = "api:write";
            when(authorityRepository.save(any()))
                    .thenReturn(anAuthority().withName(name).build());

            authorityService.create(new CreateAuthorityCommand(name));

            ArgumentCaptor<Authority> authorityArgumentCaptor = ArgumentCaptor.forClass(Authority.class);
            verify(authorityRepository).save(authorityArgumentCaptor.capture());
            Authority captorValue = authorityArgumentCaptor.getValue();
            assertThat(captorValue.getName()).isEqualTo(name);
        }

        @Test
        void shouldReturnCreatedAuthority() {
            String name = "api:update";
            when(authorityRepository.save(any()))
                    .thenReturn(anAuthority().withName(name).build());

            CreateAuthorityResponse created = authorityService.create(new CreateAuthorityCommand(name));

            assertThat(created).isNotNull();
            assertThat(created.id()).isNotNull();
            assertThat(created.name()).isEqualTo(name);
        }
    }

    @Nested
    class FetchById {
        @Test
        void shouldThrownWhenFetchingNonExistingAuthority() {
            Integer nonExistingId = 4;
            when(authorityRepository.findById(nonExistingId))
                    .thenReturn(Optional.empty());

            assertThatThrownBy(() -> authorityService.fetchById(nonExistingId))
                    .isInstanceOf(AuthorityDoesNotExist.class)
                    .hasMessage("Authority with id " + nonExistingId + " does not exist");
        }

        @Test
        void shouldFetchAuthorityById() {
            Integer id = 2;
            String name = "api:read";
            when(authorityRepository.findById(id))
                    .thenReturn(Optional.of(anAuthority().withId(id).withName(name).build()));

            AuthorityDTO fetched = authorityService.fetchById(id);

            assertThat(fetched).isNotNull();
            assertThat(fetched.id()).isEqualTo(id);
            assertThat(fetched.name()).isEqualTo(name);
            assertThat(fetched.createdAt()).isNotNull();
            assertThat(fetched.updatedAt()).isNotNull();
        }
    }
}