package io.hrushik09.authservice.authorities;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authorities")
public class AuthorityController {
    @PostMapping
    @PreAuthorize("@accessControlEvaluator.isDefaultAdmin()")
    void create() {
    }
}
