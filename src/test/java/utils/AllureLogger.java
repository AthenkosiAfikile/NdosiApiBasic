package utils;

import io.qameta.allure.Allure;
import io.restassured.response.Response;

public class AllureLogger {

    public static void logRequest(String method, String endpoint, String payload) {
        Allure.step("Request Method: " + method);
        Allure.step("Endpoint: " + endpoint);

        if (payload != null && !payload.isEmpty()) {
            Allure.addAttachment("Request Body", "application/json", payload, ".json");
        }
    }

    public static void logResponse(Response response) {
        Allure.step("Status Code: " + response.getStatusCode());

        Allure.addAttachment(
                "Response Body",
                "application/json",
                response.asPrettyString(),
                ".json"
        );
    }
}