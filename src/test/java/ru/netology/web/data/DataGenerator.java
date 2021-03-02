package ru.netology.web.data;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;

public class DataGenerator {
    public DataGenerator() {
    }

    public static void userSetUp(String login, String password, String status) {
        RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(9999)
                .setAccept(JSON)
                .setContentType(JSON)
                .log(LogDetail.ALL)
                .build();
        given()
                .spec(requestSpec)
                .body(new DataForRegistration(login, password, status))
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }
}


