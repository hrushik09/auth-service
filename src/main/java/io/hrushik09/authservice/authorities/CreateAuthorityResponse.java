package io.hrushik09.authservice.authorities;

public record CreateAuthorityResponse(
        Integer id,
        String name
) {
    public static CreateAuthorityResponse from(Authority authority) {
        return new CreateAuthorityResponse(authority.getId(), authority.getName());
    }
}
