package io.hrushik09.authservice.users;

import io.hrushik09.authservice.setup.EndToEndTest;
import io.hrushik09.authservice.setup.EndToEndTestDataPersister;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

@EndToEndTest
public class UserEndToEndTest {
    @LocalServerPort
    private Integer port;
    @Autowired
    private EndToEndTestDataPersister having;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.authentication = RestAssured.preemptive()
                .basic("default-admin", "qwe");
    }

    @Nested
    class Create {
        @Test
        void shouldCreateUserSuccessfully() {
            having.persistedAuthority("api:read");
            having.persistedAuthority("api:write");

            given()
                    .contentType(JSON)
                    .body("""
                            {
                            "username": "User 12",
                            "password": "dfd&234n",
                            "authorities": [
                                "api:read",
                                "api:write"
                            ]
                            }
                            """)
                    .when()
                    .post("/api/users")
                    .then()
                    .statusCode(201)
                    .body("id", notNullValue())
                    .body("username", equalTo("User 12"))
                    .body("authorities", hasSize(2))
                    .body("authorities.id", hasSize(2))
                    .body("authorities.name", contains("api:read", "api:write"));
        }
    }
}
