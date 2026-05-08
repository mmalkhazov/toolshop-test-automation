package com.toolshop.tests.API.product;

import com.toolshop.client.ProductApiClient;
import com.toolshop.dto.products.ErrorProductsResponseDto;
import com.toolshop.dto.products.ProductDataDto;
import com.toolshop.dto.products.ProductResponseDto;
import com.toolshop.tests.API.ApiTestBase;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("API Testing")
@Feature("Products API")
public class GetAllProductsApiTests extends ApiTestBase {


    @BeforeClass
    public void setUpClient() {

        productClient = new ProductApiClient(api.getBaseUrl());
    }



    @Test
    @Story("Get all products")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that GET /products returns a valid list of products with required fields")
    public void getAllProductsPositiveTest(){
        ProductResponseDto response=productClient.getAllProducts()
                .assertThat().log().all().statusCode(200)
                .extract().as(ProductResponseDto.class);


        Assert.assertNotNull(response.getData());
        Assert.assertTrue(response.getTotal() > 0);
        Assert.assertEquals(response.getCurrent_page(), 1);

        ProductDataDto firstProduct = response.getData().get(0);
        Assert.assertNotNull(firstProduct.getId());
        Assert.assertNotNull(firstProduct.getName());
        Assert.assertTrue(firstProduct.getPrice() > 0);
        Assert.assertNotNull(firstProduct.getCategory().getName());
        Assert.assertNotNull(firstProduct.getBrand().getName());

    }


    @Test
    @Story("Get all products - wrong HTTP method returns 405")
    @Severity(SeverityLevel.NORMAL)
    public void getAllProductsWithWrongMethodNegativeTest() {
        ErrorProductsResponseDto response=productClient.deleteAllProducts()
                .assertThat().log().all().statusCode(405)
                .extract().as(ErrorProductsResponseDto.class);

        Assert.assertEquals(response.getMessage(), "Method is not allowed for the requested route");
    }


}
