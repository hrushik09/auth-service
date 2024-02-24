package io.hrushik09.authservice.authorities;

import io.hrushik09.authservice.authorities.dto.CreateAuthorityCommand;
import io.hrushik09.authservice.authorities.dto.CreateAuthorityRequest;
import io.hrushik09.authservice.authorities.dto.CreateAuthorityResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authorities")
public class AuthorityController {
    private final AuthorityService authorityService;

    public AuthorityController(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @PostMapping
    @PreAuthorize("@accessControlEvaluator.isDefaultAdmin()")
    @ResponseStatus(HttpStatus.CREATED)
    CreateAuthorityResponse create(@RequestBody @Valid CreateAuthorityRequest request) {
        CreateAuthorityCommand cmd = new CreateAuthorityCommand(request.name());
        return authorityService.create(cmd);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@accessControlEvaluator.isDefaultAdmin()")
    void fetchById(@PathVariable Integer id) {
        authorityService.fetchById(id);
    }
}
