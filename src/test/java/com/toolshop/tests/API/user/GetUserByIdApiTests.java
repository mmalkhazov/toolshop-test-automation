package com.toolshop.tests.API.user;

import com.toolshop.client.UserApiClient;
import com.toolshop.dto.user.ErrorUserResponseDto;
import com.toolshop.dto.user.RegisterRequestDto;
import com.toolshop.dto.user.UserResponseDto;
import com.toolshop.tests.API.ApiTestBase;
import com.toolshop.utils.TestDataFactory;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("API")
@Feature("User API")
public class GetUserByIdApiTests extends ApiTestBase {

    private UserApiClient userClient;
    private String userId;
    private String userToken;



    @BeforeClass
    public void setUpClient() {
        RegisterRequestDto user = TestDataFactory.createValidUserRequest();


        UserApiClient adminClient = new UserApiClient(
                api.getBaseUrl(), api.getToken());

        userId = adminClient.registerAndGetId(user);
        userToken = adminClient.loginAndGetToken(
                user.getEmail(), user.getPassword());

        userClient = new UserApiClient(api.getBaseUrl(), userToken);
    }


    @Test
    @Story("Get user by valid ID")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that a user can retrieve their profile using valid ID and token")
    public void getUserByIdPositiveTest() {


        UserResponseDto response = userClient.getUserById(userId)
                .assertThat().log().all().statusCode(200)
                .extract().as(UserResponseDto.class);

        Assert.assertEquals(response.getFirst_name(), "Maynard");
        Assert.assertEquals(response.getId(), userId);
        Assert.assertNotNull(response.getEmail(), "Maynard");

    }

    @Test
    @Story("Get user by invalid ID")
    @Severity(SeverityLevel.NORMAL)
    public void getUserByIdWithInvalidIdNegativeTest() {
        ErrorUserResponseDto response = userClient.getUserById("99999")
                        .assertThat().log().all().statusCode(404)
                        .extract().as(ErrorUserResponseDto.class);

        Assert.assertEquals(response.getError(),
                "You are not authorized to view this user.");
    }

    @Test
    @Story("Get user with invalid token")
    @Severity(SeverityLevel.CRITICAL)
    public void getUserByIdWithInvalidTokenNegativeTest() {
        ErrorUserResponseDto response = userClient.getUserByIdInvalid(userId)
                .assertThat().log().all().statusCode(401)
                .extract().as(ErrorUserResponseDto.class);

        Assert.assertEquals(response.getMessage(),
                "Unauthorized");

    }

    @AfterClass
    public void postcondition() {
        userClient.deleteUserById(userId);
    }


}
