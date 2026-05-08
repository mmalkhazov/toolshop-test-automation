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
@Feature("User API")
public class GetUserApiTests extends ApiTestBase {

    private UserApiClient userClient;
    private String userId;
    private String userToken;

    @BeforeClass
    public void precondition(){
        RegisterRequestDto user = TestDataFactory.createValidUserRequest();


        UserApiClient adminClient = new UserApiClient(
                api.getBaseUrl(), api.getToken());

        userId = adminClient.registerAndGetId(user);
        userToken = adminClient.loginAndGetToken(
                user.getEmail(), user.getPassword());

        userClient = new UserApiClient(api.getBaseUrl(), userToken);
    }

    @Test
    @Story("Get user with valid token")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that authenticated user can retrieve their profile data")
    public void getPositiveTests(){
        UserResponseDto response=userClient.getUser()
                .assertThat().log().all()
                .extract().as(UserResponseDto.class);


        Assert.assertEquals(response.getFirst_name(), "Maynard");
        Assert.assertEquals(response.getLast_name(), "Keenan");
        Assert.assertEquals(response.getDob(), "1964-04-17");
        Assert.assertEquals(response.getId(), userId);
        Assert.assertNotNull(response.getId());
    }

    @Test
    @Story("Get user without valid authentication")
    @Severity(SeverityLevel.NORMAL)
    public void getNegativeTests(){
        ErrorUserResponseDto response=userClient.getInvalidUser().
                assertThat().log().all().statusCode(401)
                .extract().as(ErrorUserResponseDto.class);

        Assert.assertEquals(response.getMessage(),
                "Unauthorized");
    }
}
