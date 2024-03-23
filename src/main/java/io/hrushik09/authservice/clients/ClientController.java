package io.hrushik09.authservice.clients;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    @PostMapping
    @PreAuthorize("hasAuthority('clients:create')")
    void create() {

    }
}
