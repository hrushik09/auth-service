package io.hrushik09.authservice.users;

import io.hrushik09.authservice.users.dto.CreateUserCommand;
import io.hrushik09.authservice.users.dto.CreateUserRequest;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @PreAuthorize("@accessControlEvaluator.isDefaultAdmin()")
    void create(@RequestBody @Valid CreateUserRequest request) {
        CreateUserCommand cmd = new CreateUserCommand(request.username());
        userService.create(cmd);
    }
}
