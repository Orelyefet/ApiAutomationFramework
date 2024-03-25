package api.test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import com.github.javafaker.Faker;
import com.sun.net.httpserver.Request;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UserTests {

    Faker faker;
    User userPayLoad;

    @BeforeClass
    public void setupData() {
        faker = new Faker();
        userPayLoad = new User();

        userPayLoad.setId(faker.idNumber().hashCode());
        userPayLoad.setUsername(faker.name().username());
        userPayLoad.setFirstName(faker.name().firstName());
        userPayLoad.setLastName(faker.name().lastName());
        userPayLoad.setEmail(faker.internet().safeEmailAddress());
        userPayLoad.setPassword(faker.internet().password(5, 10));
        userPayLoad.setPhone(faker.phoneNumber().cellPhone());
    }


    @Test(priority = 1)
    public void testPostUser() {
        Response response = UserEndPoints.createUser(userPayLoad);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 2)
    public void testGetUserByName() {
        Response response = UserEndPoints.readUser(this.userPayLoad.getUsername());
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 3)
    public void testUpdateUserByName() {

        userPayLoad.setFirstName(faker.name().firstName());
        userPayLoad.setLastName(faker.name().lastName());
        userPayLoad.setEmail(faker.internet().safeEmailAddress());

        Response response = UserEndPoints.updateUser(this.userPayLoad.getUsername(), userPayLoad);
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);

        Response responseAfterUpdate = UserEndPoints.readUser(this.userPayLoad.getUsername());
        responseAfterUpdate.then().log().body();
        Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);
    }

    @Test(priority = 4)
    public void testDeleteUserByName() {
        Response response = UserEndPoints.deleteUser(this.userPayLoad.getUsername());
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
