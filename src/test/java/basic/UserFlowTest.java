package basic;

import common.BaseTest;
import io.qameta.allure.*;
import io.qameta.allure.testng.AllureTestNg;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import payloadBuilder.UserPayloadBuilder;
import utils.AllureLogger;
import static payloadBuilder.UserPayloadBuilder.FirstName;
import static payloadBuilder.UserPayloadBuilder.LastName;

@Listeners({AllureTestNg.class})
@Epic("User Flow Tests")
@Feature("End-to-End User Registration, Approval, Role Update, and Login")
public class UserFlowTest extends BaseTest {

    @Test(priority = 1)
    @Story("Verify API Health Check Endpoint")
    @Description("This test verifies that the " +
            "/health endpoint is accessible and returns a 200 status code.")
    public void healthCheckTest() {

        Response response = RestAssured.given()
                .baseUri(baseURL)
                .basePath("/health")
                .header("accept", "*/*")
                .log().all()
                .get()
                .prettyPeek();

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 2)
    @Story("Register a New User")
    @Feature("User Registration")
    @Description("This test registers a new user with unique email and verifies the response.")
    @Severity(SeverityLevel.CRITICAL)
    public void registerNewUserTest() {

        userEmail = FirstName +"."+ LastName + "@gmail.com";

        Response response = RestAssured.given()
                .baseUri(baseURL)
                .basePath("/register")
                .header("accept", "*/*")
                .header("Content-Type", "application/json")
                .body(UserPayloadBuilder.registerPayload(userEmail))
                .log().all()
                .post()
                .prettyPeek();

        userId = response.jsonPath().getString("data.id");

        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertEquals(response.jsonPath().getString("message"),
                "Registration submitted successfully. Your account is pending admin approval.");
        Assert.assertEquals(response.jsonPath().getString("data.approvalStatus"), "pending");
    }

    @Test(priority = 3)
    @Story("Admin Login")
    @Feature("Admin Authentication")
    @Description("This test logs in as admin and retrieves the authentication token.")
    @Severity(SeverityLevel.BLOCKER)
    public void loginAdminTest() {

        String path = "/login";
        String payload = UserPayloadBuilder.loginPayload("admin@gmail.com", "@12345678");

        AllureLogger.logRequest("POST", baseURL + path, payload);

        Response response = RestAssured.given()
                .baseUri(baseURL)
                .basePath(path)
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(payload)
                .log().all()
                .post()
                .prettyPeek();

        AllureLogger.logResponse(response);

        adminToken = response.jsonPath().getString("data.token");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertNotNull(adminToken);
    }

    @Test(priority = 4)
    @Story("Approve Registered User")
    @Description("This test approves the newly registered user using admin privileges.")
    @Epic("Admin/Instructor Actions")
    public void approveRegisteredUserTest() {

        Response response = RestAssured.given()
                .baseUri(baseURL)
                .basePath("/admin/users/" + userId + "/approve")
                .header("accept", "*/*")
                .header("Authorization", "Bearer " + adminToken)
                .log().all()
                .put()
                .prettyPeek();

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 5)
    @Story("Update User Role")
    @Description("This test updates the role of the approved user to admin.")
    public void updateUserRoleTest() {

        Response response = RestAssured.given()
                .baseUri(baseURL)
                .basePath("/admin/users/" + userId + "/role")
                .header("accept", "*/*")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + adminToken)
                .body(UserPayloadBuilder.rolePayload("admin"))
                .log().all()
                .put()
                .prettyPeek();

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 6)
    @Story("Login Approved User")
    @Feature("User Authentication")
    @Description("This test logs in with the approved user's credentials and verifies the token retrieval.")
    public void loginApprovedUserTest() {

        Response response = RestAssured.given()
                .baseUri(baseURL)
                .basePath("/login")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(UserPayloadBuilder.loginPayload(userEmail, "@SecurePass123"))
                .log().all()
                .post()
                .prettyPeek();

        userToken = response.jsonPath().getString("data.token");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertNotNull(userToken);
    }

    @Test(priority = 7)
    @Story("Get Instructor Groups")
    @Description("This test retrieves the list of instructor groups using admin token.")
    public void getInstructorGroupsTest() {

        String path = "/instructor/groups";

        AllureLogger.logRequest("GET", baseURL + path, null);

        Response response = RestAssured.given()
                .baseUri(baseURL)
                .basePath(path)
                .queryParam("limit", 50)
                .queryParam("offset", 0)
                .queryParam("activeOnly", true)
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + adminToken)
                .log().all()
                .get()
                .prettyPeek();

        AllureLogger.logResponse(response);

        Assert.assertEquals(response.getStatusCode(), 200);
    }
}