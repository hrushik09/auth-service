package io.hrushik09.authservice.users;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @PostMapping
    @PreAuthorize("@accessControlEvaluator.isDefaultAdmin()")
    void create() {

    }
}
