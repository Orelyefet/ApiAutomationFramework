package api.test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UserTests {

    Faker faker;
    User userPayLoad;

    public Logger logger;

    @BeforeClass
    public void setup() {
        faker = new Faker();
        userPayLoad = new User();

        userPayLoad.setId(faker.idNumber().hashCode());
        userPayLoad.setUsername(faker.name().username());
        userPayLoad.setFirstName(faker.name().firstName());
        userPayLoad.setLastName(faker.name().lastName());
        userPayLoad.setEmail(faker.internet().safeEmailAddress());
        userPayLoad.setPassword(faker.internet().password(5, 10));
        userPayLoad.setPhone(faker.phoneNumber().cellPhone());

        logger = LoggerFactory.getLogger(UserTests.class);
    }


    @Test(priority = 1)
    public void testPostUser() {
        logger.info("-------------- Creating user --------------");
        Response response = UserEndPoints.createUser(userPayLoad);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
        logger.info("-------------- User has been created --------------");
    }

    @Test(priority = 2)
    public void testGetUserByName() {
        logger.info("-------------- Get user information by name --------------");
        Response response = UserEndPoints.readUser(this.userPayLoad.getUsername());
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);
        logger.info("-------------- User information has been displayed --------------");
    }

    @Test(priority = 3)
    public void testUpdateUserByName() {
        logger.info("-------------- Updating user data --------------");
        userPayLoad.setFirstName(faker.name().firstName());
        userPayLoad.setLastName(faker.name().lastName());
        userPayLoad.setEmail(faker.internet().safeEmailAddress());

        Response response = UserEndPoints.updateUser(this.userPayLoad.getUsername(), userPayLoad);

        response
                .then()
                .log().all();

        Assert.assertEquals(response.getStatusCode(), 200);

        Response responseAfterUpdate = UserEndPoints.readUser(this.userPayLoad.getUsername());
        responseAfterUpdate.then().log().body();
        Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);
        logger.info("-------------- User information has been successfully updated --------------");

    }

    @Test(priority = 4)
    public void testDeleteUserByName() {
        logger.info("-------------- Deleting user --------------");
        Response response = UserEndPoints.deleteUser(this.userPayLoad.getUsername());

        response
                .then()
                .log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
        logger.info("-------------- The user has been successfully deleted” --------------");
    }
}
