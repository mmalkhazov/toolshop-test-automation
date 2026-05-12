package com.toolshop.tests.API.product;

import com.toolshop.client.ProductApiClient;
import com.toolshop.dto.products.ProductDataDto;
import com.toolshop.dto.products.ProductResponseDto;
import com.toolshop.tests.API.ApiTestBase;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("API")
@Feature("Products API")
public class SearchProductsApiTests extends ApiTestBase {

    @BeforeClass
    public void setUpClient() {
        productClient = new ProductApiClient(api.getBaseUrl());
    }

    @Test
    @Story("Search products by existing keyword")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that searching with a valid keyword returns relevant products")
    public void searchProductsByExistingNamePositiveTest() {
        ProductResponseDto response= productClient.getSearchProducts("hammer")
                .log().all()
                .assertThat().statusCode(200)
                .extract().as(ProductResponseDto.class);

        Assert.assertTrue(response.getTotal() > 0);
        Assert.assertNotNull(response.getData());

        for (ProductDataDto product : response.getData()) {
            boolean nameContains        = product.getName()
                    .toLowerCase().contains("hammer");
            boolean descriptionContains = product.getDescription()
                    .toLowerCase().contains("hammer");

            Assert.assertTrue(
                    nameContains || descriptionContains,
                    "Product should contain 'hammer' in name or description but was: "
                            + product.getName()
            );
        }
    }

    @Test
    @Story("Search results contain valid product fields")
    @Severity(SeverityLevel.NORMAL)
    public void searchProductsReturnsCorrectFieldsPositiveTest() {
        ProductResponseDto response = productClient.getSearchProducts("hammer")
                .assertThat().statusCode(200)
                .extract().as(ProductResponseDto.class);

        ProductDataDto firstProduct = response.getData().get(0);
        Assert.assertNotNull(firstProduct.getId());
        Assert.assertNotNull(firstProduct.getName());
        Assert.assertTrue(firstProduct.getPrice() > 0);
        Assert.assertNotNull(firstProduct.getCategory().getName());
        Assert.assertNotNull(firstProduct.getBrand().getName());
    }

    @Test
    @Story("Search with no matching results returns empty list")
    @Severity(SeverityLevel.NORMAL)
    public void searchProductsWithNoResultsNegativeTest() {
        ProductResponseDto response = productClient.getSearchProducts("xyzxyzxyz123")
                .assertThat().statusCode(200)
                .extract().as(ProductResponseDto.class);

        Assert.assertEquals(response.getTotal(), 0);
        Assert.assertTrue(response.getData().isEmpty());
    }




}
