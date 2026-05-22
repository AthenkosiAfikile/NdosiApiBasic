package payloadBuilder;

import utils.FakerData;

public class UserPayloadBuilder {

    static FakerData faker = new FakerData();
    public static String FirstName = faker.randomFirstName();
    public static String LastName = faker.randomLastName();

    public static String registerPayload(String email) {
        return "{\n" +
                "  \"firstName\": \""+FirstName+"\",\n" +
                "  \"lastName\": \""+LastName+"\",\n" +
                "  \"email\": \"" + email + "\",\n" +
                "  \"password\": \"@SecurePass123\",\n" +
                "  \"confirmPassword\": \"@SecurePass123\",\n" +
                "  \"groupId\": \"92833dab-c6eb-41ac-bc8c-dbe6b35d58e3\"\n" +
                "}";
    }

    public static String loginPayload(String email, String password) {
        return "{\n" +
                "  \"email\": \"" + email + "\",\n" +
                "  \"password\": \"" + password + "\"\n" +
                "}";
    }

    public static String rolePayload(String role) {
        return "{\n" +
                "  \"role\": \"" + role + "\"\n" +
                "}";
    }
}