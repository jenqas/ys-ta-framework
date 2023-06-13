package org.ys.automation.webservices;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.ys.automation.webservices.model.User;

public class SpringRestTest {

    @Test
    public void checkStatusCode() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<User[]> response = restTemplate.getForEntity("http://jsonplaceholder.typicode.com/users", User[].class);

        int actualStatsusCode = response.getStatusCode().value();

        Assert.assertEquals(actualStatsusCode, 200);
    }

    @Test
    public void checkResponseHeader() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<User[]> response = restTemplate.getForEntity("http://jsonplaceholder.typicode.com/users", User[].class);

        HttpHeaders headers = response.getHeaders();
        String actualContentTypeValue = headers.getContentType().toString();

        Assert.assertEquals(actualContentTypeValue, "application/json;charset=utf-8");
    }

    @Test
    public void checkResponseBody() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<User[]> response = restTemplate.getForEntity("http://jsonplaceholder.typicode.com/users", User[].class);

        User[] users = response.getBody();

        Assert.assertEquals(users.length, 10);
    }


}
