package nl.kabisa.meetup.jwtbased.interceptors.authentication;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import nl.kabisa.meetup.jwtbased.IntegrationTest;
import nl.kabisa.meetup.jwtbased.accounts.api.AccountDto;
import nl.kabisa.meetup.jwtbased.sessions.api.*;

public class AuthenticationIntegrationTest extends IntegrationTest {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    @Test
    public void missingValidJwtToken() {
        testRestTemplate.getForEntity(getSessionUri(), StatusResponse.class);

        ResponseEntity<AccountDto> response = testRestTemplate.getForEntity(getAccountUri(), AccountDto.class);
        Assert.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void validTokenPresent() {
        ResponseEntity<AccountDto> accountToCreate = testRestTemplate.postForEntity(getAccountUri(), new AccountDto(USERNAME, PASSWORD), AccountDto.class);
        Assert.assertEquals(HttpStatus.OK, accountToCreate.getStatusCode());

        ResponseEntity<LoginResponse> loginResponse = testRestTemplate.postForEntity(getSessionUri(), new LoginRequest(USERNAME, PASSWORD), LoginResponse.class);
        Assert.assertEquals(LoginStatus.LOGGED_IN, loginResponse.getBody().getStatus());

        ResponseEntity<AccountDto> retrievedAccount = testRestTemplate.getForEntity(getAccountUri(), AccountDto.class);
        Assert.assertEquals(HttpStatus.OK, retrievedAccount.getStatusCode());
    }
}
