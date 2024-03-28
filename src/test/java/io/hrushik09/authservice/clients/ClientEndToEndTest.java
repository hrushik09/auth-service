package io.hrushik09.authservice.clients;

import io.hrushik09.authservice.setup.EndToEndTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@EndToEndTest
public class ClientEndToEndTest {
    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.authentication = RestAssured.preemptive()
                .basic("user_create_client", "qwe");
    }

    @Nested
    class Create {
        @Test
        void shouldCreateClientSuccessfully() {
            given()
                    .contentType(ContentType.JSON)
                    .body("""
                            {
                            "pid": "rc",
                            "clientId": "client",
                            "clientSecret": "secret",
                            "clientAuthenticationMethod": "CLIENT_SECRET_BASIC",
                            "scopes": [
                            "OPENID",
                            "api:read",
                            "api:create"
                            ],
                            "redirectUris": [
                            "http://localhost:8080/authorized",
                            "http://localhost:8080/api/authorized"
                            ],
                            "authorizationGrantTypes": [
                            "AUTHORIZATION_CODE",
                            "REFRESH_TOKEN"
                            ]
                            }
                            """)
                    .when()
                    .post("/api/clients")
                    .then()
                    .statusCode(201)
                    .body("id", notNullValue())
                    .body("pid", equalTo("rc"))
                    .body("clientId", equalTo("client"))
                    .body("scopes", hasSize(3))
                    .body("scopes", containsInAnyOrder("OPENID", "api:read", "api:create"))
                    .body("redirectUris", hasSize(2))
                    .body("redirectUris", containsInAnyOrder("http://localhost:8080/authorized", "http://localhost:8080/api/authorized"))
                    .body("clientAuthenticationMethod", equalTo("CLIENT_SECRET_BASIC"))
                    .body("authorizationGrantTypes", hasSize(2))
                    .body("authorizationGrantTypes", containsInAnyOrder("AUTHORIZATION_CODE", "REFRESH_TOKEN"));
        }
    }
}
