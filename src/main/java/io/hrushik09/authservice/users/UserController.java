package io.hrushik09.authservice.users;

import io.hrushik09.authservice.users.dto.CreateUserCommand;
import io.hrushik09.authservice.users.dto.CreateUserRequest;
import io.hrushik09.authservice.users.dto.CreateUserResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @PreAuthorize("@accessControlEvaluator.isDefaultAdmin()")
    @ResponseStatus(HttpStatus.CREATED)
    CreateUserResponse create(@RequestBody @Valid CreateUserRequest request) {
        CreateUserCommand cmd = new CreateUserCommand(request.username(), request.password(), request.authorities());
        return userService.create(cmd);
    }
}
