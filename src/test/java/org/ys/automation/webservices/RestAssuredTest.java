package org.ys.automation.webservices;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.ys.automation.webservices.model.User;

public class RestAssuredTest {

    @BeforeTest
    public void initTest() {
        RestAssured.baseURI = "http://jsonplaceholder.typicode.com";
    }


    @Test(description = "Rest Assured - check status code")
    public void checkStatusCode() {
        Response response = RestAssured.when()
                .get("/users")
                .andReturn();

        Assert.assertEquals(response.getStatusLine(), "HTTP/1.1 200 OK", "Status line should be - HTTP/1.1 200 OK");
    }


    @Test(description = "Rest Assured - check presence of response header and it's value")
    public void checkResponseHeader() {
        Response response = RestAssured.when()
                .get("/users")
                .andReturn();

        String rpContentTypeHeader = response.getHeader("Content-Type");

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(response.getHeaders().toString().contains("Content-Type"),
                "Content-Type header exists in the obtained response");
        softAssert.assertEquals(rpContentTypeHeader, "application/json; charset=utf-8",
                "The value of content-type header is 'application/json; charset=utf-8'");
        softAssert.assertAll();
    }


    @Test(description = "Rest Assured - check response body for containing 10 users")
    public void checkResponseBody() {
        Response response = RestAssured.when()
                .get("/users")
                .andReturn();

        User[] responseUsersArray = response.getBody().as(User[].class);

        Assert.assertEquals(responseUsersArray.length, 10,
                "Users array should have 10 objects");
    }
}
