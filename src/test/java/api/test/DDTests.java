package api.test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DDTests {


    @Test(priority = 1, dataProvider = "AllData", dataProviderClass = DataProviders.class)
    public void testPostUser(String userId, String userName, String firstName, String lastName, String email, String password, String phoneNumber) {
        User userPayLoad = new User();

        userPayLoad.setId(Integer.parseInt(userId));
        userPayLoad.setUsername(userName);
        userPayLoad.setFirstName(firstName);
        userPayLoad.setLastName(lastName);
        userPayLoad.setEmail(email);
        userPayLoad.setPassword(password);
        userPayLoad.setPhone(phoneNumber);

        Response response = UserEndPoints.createUser(userPayLoad);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 2, dataProvider = "UserNames", dataProviderClass = DataProviders.class)
    public void testDeleteUserByName (String userName) {
        Response response = UserEndPoints.deleteUser(userName);
        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
