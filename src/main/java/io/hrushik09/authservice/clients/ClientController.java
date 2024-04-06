package io.hrushik09.authservice.clients;

import io.hrushik09.authservice.clients.dto.CreateClientCommand;
import io.hrushik09.authservice.clients.dto.CreateClientRequest;
import io.hrushik09.authservice.clients.dto.CreateClientResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('clients:create')")
    @ResponseStatus(HttpStatus.CREATED)
    CreateClientResponse create(@RequestBody @Valid CreateClientRequest request) {
        AuthenticationMethod authenticationMethod = AuthenticationMethod.valueOf(request.authenticationMethod());
        List<AuthorizationGrantType> authorizationGrantTypes = request.authorizationGrantTypes().stream()
                .map(AuthorizationGrantType::valueOf)
                .toList();
        CreateClientCommand cmd = new CreateClientCommand(request.pid(), request.clientId(), request.clientSecret(), authenticationMethod, request.scopes(), request.redirectUris(), authorizationGrantTypes);
        return clientService.create(cmd);
    }
}
