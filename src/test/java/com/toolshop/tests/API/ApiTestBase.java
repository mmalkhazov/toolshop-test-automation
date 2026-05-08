package com.toolshop.tests.API;

import com.toolshop.client.CartApiClient;
import com.toolshop.client.ProductApiClient;
import com.toolshop.client.UserApiClient;
import com.toolshop.config.ApiManager;
import com.toolshop.dto.user.RegisterRequestDto;
import com.toolshop.utils.TestDataFactory;
import org.testng.annotations.BeforeSuite;

public class ApiTestBase {

    protected ApiManager api = new ApiManager();

    protected ProductApiClient productClient;
    protected CartApiClient cartClient;
    protected UserApiClient userClient;


    @BeforeSuite
    public void setUp() {
        api.startApi();


    }
}



