package io.hrushik09.authservice.clients;

import io.hrushik09.authservice.setup.EndToEndTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@EndToEndTest
public class ClientEndToEndTest {
    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Nested
    class Create {
        @Test
        void shouldCreateClientSuccessfully() {
            given()
                    .contentType(ContentType.JSON)
                    .body("""
                            {
                            "id": "rc",
                            "clientId": "client",
                            "clientSecret": "secret",
                            "clientAuthenticationMethod": "CLIENT_SECRET_BASIC",
                            "scope": "OPENID",
                            "redirectUri": "http://localhost:8080/authorized",
                            "authorizationGrantType": "AUTHORIZATION_CODE"
                            }
                            """)
                    .when()
                    .post("/api/clients")
                    .then()
                    .statusCode(201)
                    .body("id", equalTo("rc"))
                    .body("clientId", equalTo("client"))
                    .body("clientSecret", equalTo("secret"))
                    .body("scope", equalTo("OPENID"))
                    .body("redirectUri", equalTo("http://localhost:8080/authorized"))
                    .body("clientAuthenticationMethod", equalTo("CLIENT_SECRET_BASIC"))
                    .body("authorizationGrantType", equalTo("AUTHORIZATION_CODE"));
        }
    }
}
