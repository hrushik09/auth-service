package io.hrushik09.authservice.authorities;

import io.hrushik09.authservice.authorities.dto.CreateAuthorityResponse;
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
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@EndToEndTest
public class AuthorityEndToEndTest {
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
        void shouldCreateAuthoritySuccessfully() {
            given()
                    .contentType(JSON)
                    .body("""
                            {
                            "name": "api:read"
                            }
                            """)
                    .when()
                    .post("/api/authorities")
                    .then()
                    .statusCode(201)
                    .body("id", notNullValue())
                    .body("name", equalTo("api:read"));
        }

        @Test
        void shouldNotCreateDuplicateAuthority() {
            given()
                    .contentType(JSON)
                    .body("""
                            {
                            "name": "api:read"
                            }
                            """)
                    .when()
                    .post("/api/authorities")
                    .then()
                    .statusCode(201);

            given()
                    .contentType(JSON)
                    .body("""
                            {
                            "name": "api:read"
                            }
                            """)
                    .when()
                    .post("/api/authorities")
                    .then()
                    .statusCode(400);
        }
    }

    @Nested
    class FetchById {
        @Test
        void shouldFetchAuthorityById() {
            CreateAuthorityResponse savedAuthority = having.persistedAuthority("api:update");

            given()
                    .contentType(JSON)
                    .when()
                    .get("/api/authorities/{id}", savedAuthority.id())
                    .then()
                    .statusCode(200)
                    .body("id", equalTo(savedAuthority.id()))
                    .body("name", equalTo("api:update"))
                    .body("createdAt", notNullValue())
                    .body("updatedAt", notNullValue());
        }
    }
}
