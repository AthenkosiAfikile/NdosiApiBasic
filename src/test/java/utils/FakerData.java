package utils;

import com.github.javafaker.Faker;
import org.testng.annotations.Test;

public class FakerData {

    private final Faker faker = new Faker();

    public String randomFirstName() {
        return faker.name().firstName();
    }

    public String randomLastName() {
        return faker.name().lastName();
    }

    public String randomEmail() {
        return faker.internet().emailAddress();
    }

    @Test
    public void testFakerData() {
        System.out.println("Random First Name: " + randomFirstName());
        System.out.println("Random Last Name: " + randomLastName());
        System.out.println("Random Email: " + randomEmail());
    }

}
