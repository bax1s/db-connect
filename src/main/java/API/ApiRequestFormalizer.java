package API;

import Utils.TestContext;
import io.restassured.RestAssured;

public class ApiRequestFormalizer {
    TestContext testContext= TestContext.getInstance();

    public void postRequestFormalizer() {
        RestAssured.useRelaxedHTTPSValidation();
        testContext.setResponse(
                RestAssured.given()
                        .headers(testContext.getHeader())
                        .body(testContext.getBody())
                        .post(testContext.getUrl())
                        .then()
                        .extract().response()
        );

    }

    public void getRequestFormalizer(int statusCode) {
        RestAssured.useRelaxedHTTPSValidation();
        testContext.setResponse(
        RestAssured.given()
                .headers(testContext.getHeader())
                .log().all()
                .get(testContext.getUrl())
                .then()
                .statusCode(statusCode)
                .log().all().extract().response()
        );

    }
}
