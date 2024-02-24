package io.hrushik09.authservice.authorities;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authorities")
public class AuthorityController {
    private final AuthorityService authorityService;

    public AuthorityController(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @PostMapping
    @PreAuthorize("@accessControlEvaluator.isDefaultAdmin()")
    @ResponseStatus(HttpStatus.CREATED)
    CreateAuthorityResponse create(@RequestBody @Valid CreateAuthorityRequest request) {
        return authorityService.create(request);
    }
}
