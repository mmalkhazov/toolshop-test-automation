package com.toolshop.tests.API.user;

import com.toolshop.client.UserApiClient;
import com.toolshop.dto.user.AuthRequestDto;
import com.toolshop.dto.user.AuthResponseDto;
import com.toolshop.dto.user.ErrorUserResponseDto;
import com.toolshop.tests.API.ApiTestBase;
import com.toolshop.utils.TestDataFactory;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


@Epic("API")
@Feature("User Authentication API")
public class LoginUserApiTests extends ApiTestBase {


@BeforeClass
public void precondition(){

    userClient=new UserApiClient(api.getBaseUrl(), api.getToken());
}




    @Test
    @Story("Login with valid credentials")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that valid admin credentials return access token")
    public void loginWithValidCredentialsPositiveTest() {
        AuthRequestDto request = TestDataFactory.validAdmin();

        AuthResponseDto response= userClient.loginUser(request)
                .log().all()
                .assertThat().statusCode(200)
                .extract().as(AuthResponseDto.class);

        Assert.assertNotNull(response.getAccess_token());
        Assert.assertEquals(response.getToken_type(), "bearer");
        Assert.assertEquals(response.getExpires_in(), "300");


    }

    @Test
    @Story("Login with wrong password")
    @Severity(SeverityLevel.NORMAL)
    public void loginWithWrongPasswordNegativeTest() {
        AuthRequestDto request = TestDataFactory.adminWithWrongPassword();

        ErrorUserResponseDto response= userClient.loginUser(request)
                .log().all()
                .assertThat().statusCode(401)
                .extract().as(ErrorUserResponseDto.class);

        Assert.assertEquals(response.getError(), "Unauthorized");

    }

    @Test
    @Story("Login with wrong email")
    @Severity(SeverityLevel.NORMAL)
    public void loginWithWrongEmailNegativeTest() {
        AuthRequestDto request = TestDataFactory.adminWithWrongEmail();

        ErrorUserResponseDto response= userClient.loginUser(request)
                .log().all()
                .assertThat().statusCode(401)
                .extract().as(ErrorUserResponseDto.class);

        Assert.assertEquals(response.getError(), "Unauthorized");

    }

    @Test
    @Story("Login with empty email")
    @Severity(SeverityLevel.NORMAL)
    public void loginWithEmptyEmailNegativeTest() {
        AuthRequestDto request = TestDataFactory.adminWithEmptyEmail();

        ErrorUserResponseDto response= userClient.loginUser(request)
                .log().all()
                .assertThat().statusCode(401)
                .extract().as(ErrorUserResponseDto.class);

        Assert.assertEquals(response.getError(), "Invalid login request");

    }

    @Test
    @Story("Login with empty password")
    @Severity(SeverityLevel.NORMAL)
    public void loginWithEmptyPasswordNegativeTest() {
        AuthRequestDto request = TestDataFactory.adminWithEmptyPassword();

        ErrorUserResponseDto response= userClient.loginUser(request)
                .log().all()
                .assertThat().statusCode(401)
                .extract().as(ErrorUserResponseDto.class);

        Assert.assertEquals(response.getError(), "Invalid login request");


    }

    @Test
    @Story("Login with invalid email format")
    @Severity(SeverityLevel.NORMAL)
    public void loginWithInvalidEmailFormatNegativeTest() {
        AuthRequestDto request = TestDataFactory.adminWithInvalidEmailFormat();

        ErrorUserResponseDto response= userClient.loginUser(request)
                .log().all()
                .assertThat().statusCode(401)
                .extract().as(ErrorUserResponseDto.class);

        Assert.assertEquals(response.getError(), "Unauthorized");

    }
}
