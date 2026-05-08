package com.toolshop.tests.UI;

import com.toolshop.pages.HomePage;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
@Epic("Catalog")
@Feature("Product Search")
public class ProductSearchTests extends TestBase {



    @BeforeMethod
    public void precondition() {
        new HomePage(driver).header.goToHomePage();
    }



    @Test
    @Story("Search by existing keyword returns matching products")
    @Severity(SeverityLevel.CRITICAL)
    public void searchByExistingKeywordPositiveTest() {
        new HomePage(driver).sortBar.searchFor("hammer");

        Assert.assertTrue(
                new HomePage(driver).sortBar.allSearchResultsMatch("hammer"),
                "Search results contain products not matching 'hammer'"
        );
    }

    @Test
    @Story("Search returns non-empty results for valid keyword")
    @Severity(SeverityLevel.NORMAL)
    public void searchResultsAreNotEmptyPositiveTest() {
        new HomePage(driver).sortBar.searchFor("hammer");

        Assert.assertFalse(
                new HomePage(driver).sortBar.getNamesFromPage().isEmpty(),
                "Search returned no results for 'hammer'"
        );
    }

    @Test
    @Story("Search with non-existing keyword shows no results message")
    @Severity(SeverityLevel.NORMAL)
    public void searchWithNoResultsNegativeTest() {
        new HomePage(driver).sortBar.searchFor("xyzxyzxyz123");

        Assert.assertTrue(
                new HomePage(driver).sortBar.isNoResultsMessageDisplayed(),
                "No results message was not displayed"
        );
    }


    @Test
    @Story("Empty search query does not change product list")
    @Severity(SeverityLevel.MINOR)
    @Description("Verify that triggering search with an empty query does not alter the current product list")
    public void searchWithEmptyQueryDoesNothingTest() {
        List<String> before = new HomePage(driver).sortBar.getCurrentProductNames();

        new HomePage(driver).sortBar.clickSearchWithoutQuery();

        Assert.assertTrue(
                new HomePage(driver).sortBar.productsMatchList(before),
                "Product list changed after empty search"
        );
    }

    }