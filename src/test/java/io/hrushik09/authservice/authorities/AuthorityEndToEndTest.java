package io.hrushik09.authservice.authorities;

import io.hrushik09.authservice.setup.EndToEndTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@EndToEndTest
public class AuthorityEndToEndTest {
    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.authentication = RestAssured.preemptive()
                .basic("default-admin", "qwe");
    }

    @Test
    void shouldCreateAuthoritySuccessfully() {
        given()
                .contentType(ContentType.JSON)
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
}
