package com.toolshop.tests.API.user;

import com.toolshop.client.UserApiClient;
import com.toolshop.dto.user.ErrorUserResponseDto;
import com.toolshop.dto.user.RegisterRequestDto;
import com.toolshop.dto.user.UserResponseDto;
import com.toolshop.tests.API.ApiTestBase;
import com.toolshop.utils.TestDataFactory;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


@Epic("API")
@Feature("User Registration API")
public class RegistrationUserApiTests extends ApiTestBase {
    private UserApiClient userClient;

    @BeforeClass
    public void setUpClient() {
        userClient = new UserApiClient(api.getBaseUrl(), api.getToken()); // ← pass from ApiManager
    }

    @Test
    @Story("Register valid user")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that a user can be successfully registered with valid data")
    public void registerValidUserPositiveTest() {
        RegisterRequestDto request = TestDataFactory.createValidUserRequest();

        UserResponseDto response = userClient.registerUser(request).
                assertThat().log().all().statusCode(201)
                .extract()
                .as(UserResponseDto.class);

        Assert.assertEquals(response.getFirst_name(), "Maynard");
        Assert.assertEquals(response.getLast_name(), "Keenan");
        Assert.assertEquals(response.getDob(), "1964-04-17");
        Assert.assertEquals(response.getEmail(), request.getEmail());
        Assert.assertNotNull(response.getId());


    }


    @Test
    @Story("Register without email")
    @Severity(SeverityLevel.NORMAL)
    public void registerWithoutEmailNegativeTest() {
        RegisterRequestDto request = TestDataFactory.userWithoutEmail();

        ErrorUserResponseDto response = userClient.registerUser(request)
                .assertThat().log().all().statusCode(422)
                .extract().as(ErrorUserResponseDto.class);

        Assert.assertEquals(response.getEmail().get(0), "The email field is required.");

    }

    @Test
    @Story("Register without password")
    @Severity(SeverityLevel.NORMAL)
    public void registerWithoutPasswordNegativeTest() {
        RegisterRequestDto request = TestDataFactory.userWithoutPassword();

        ErrorUserResponseDto response = userClient.registerUser(request)
                .assertThat().log().all().statusCode(422)
                .extract().as(ErrorUserResponseDto.class);
        Assert.assertEquals(response.getPassword().get(0), "The password field is required.");

    }

    @Test
    @Story("Register without first name")
    @Severity(SeverityLevel.NORMAL)
    public void registerWithoutFirstNameNegativeTest() {
        RegisterRequestDto request = TestDataFactory.userWithoutFirstName();

        ErrorUserResponseDto response = userClient.registerUser(request)
                .assertThat().log().all().statusCode(422)
                .extract().as(ErrorUserResponseDto.class);

        Assert.assertEquals(response.getFirst_name().get(0), "The first name field is required.");

    }

    @Test
    @Story("Register with weak password")
    @Severity(SeverityLevel.NORMAL)
    public void registerWithWeakPasswordNegativeTest() {
        RegisterRequestDto request = TestDataFactory.userWithWeakPassword();

        ErrorUserResponseDto response = userClient.registerUser(request)
                .assertThat().log().all().statusCode(422)
                .extract().as(ErrorUserResponseDto.class);

        Assert.assertEquals(response.getPassword().get(0), "The password field must be at least 8 characters.");
        Assert.assertEquals(response.getPassword().get(1), "The password field must contain at least one uppercase and one lowercase letter.");
        Assert.assertEquals(response.getPassword().get(2), "The password field must contain at least one symbol.");
        Assert.assertEquals(response.getPassword().get(3), "The password field must contain at least one number.");

    }

    @Test
    @Story("Register duplicate email")
    @Severity(SeverityLevel.CRITICAL)
    public void registerDuplicateEmailNegativeTest() {
        RegisterRequestDto user = TestDataFactory.createValidUserRequest();

        userClient.registerUser(user)
                .assertThat().statusCode(201);

        ErrorUserResponseDto response =userClient.registerUser(user)
                .assertThat().log().all().statusCode(409)
                .extract().as(ErrorUserResponseDto.class);

        Assert.assertEquals(response.getEmail().get(0), "A customer with this email address already exists.");

    }


}
