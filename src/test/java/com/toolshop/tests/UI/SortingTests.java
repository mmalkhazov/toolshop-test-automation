package com.toolshop.tests.UI;

import com.toolshop.pages.HomePage;
import com.toolshop.pages.components.Header;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Catalog")
@Feature("Sorting and Filtering")
public class SortingTests extends TestBase {

    private HomePage homePage;

    @BeforeMethod
    public void precondition() {
        homePage = new HomePage(driver);
        homePage.header.goToHomePage();
    }


    @Test
    @Story("Sort products by price ascending")
    @Severity(SeverityLevel.CRITICAL)
    public void sortingAscendingByPriceTest() {
        HomePage homePage = new HomePage(driver);
        homePage.sortBar.selectAscendingSortingByPrice();

        Assert.assertTrue(
                homePage.sortBar
                        .isSortedByPriceAscending(),
                "Products are NOT sorted by price ascending"
        );
    }


    @Test
    @Story("Sort products by price descending")
    @Severity(SeverityLevel.CRITICAL)
    public void sortingDescendingByPriceTest() {
        HomePage homePage = new HomePage(driver);
        homePage.sortBar.selectDescendingSortingByPrice();

        Assert.assertTrue(
                homePage.sortBar
                        .isSortedByPriceDescending(),
                "Products are NOT sorted by price descending"
        );
    }


    @Test
    @Story("Sort products by name ascending")
    @Severity(SeverityLevel.NORMAL)
    public void sortingAscendingByNameTest() {
        HomePage homePage = new HomePage(driver);
        homePage.sortBar.selectAscendingSortingByName();

        Assert.assertTrue(
                homePage.sortBar
                        .isSortedByNameAscending(),
                "Products are NOT sorted by name ascending"
        );
    }


    @Test
    @Story("Sort products by name descending")
    @Severity(SeverityLevel.NORMAL)
    public void sortingDescendingByNameTest() {
        HomePage homePage = new HomePage(driver);
        homePage.sortBar.selectDescendingSortingByName();

        Assert.assertTrue(
                homePage.sortBar
                        .isSortedByNameDescending(),
                "Products are NOT sorted by name descending"
        );
    }


   @Test
   @Story("Filter products by category - Hand Tools")
   @Severity(SeverityLevel.CRITICAL)
    public void filterByCategoryHandToolsTest(){
        HomePage homePage=new HomePage(driver);
        homePage.sortBar.selectFilterByCategory("Hand Tools");


        Assert.assertTrue(
                homePage.sortBar
                        .isFilteredByCategory("Hand Tools"),
                "Products are NOT filtered by category "
        );
   }

    @Test
    @Story("Filter products by category - Hammer")
    @Severity(SeverityLevel.NORMAL)
    public void filterByCategoryHammerTest(){
        HomePage homePage=new HomePage(driver);
        homePage.sortBar.selectFilterByCategory("Hammer");


        Assert.assertTrue(
                homePage.sortBar
                        .isFilteredByCategory("Hammer"),
                "Products are NOT filtered by category "
        );
    }

    @Test
    @Story("Filter products by category - Measures")
    @Severity(SeverityLevel.NORMAL)
    public void filterByCategoryMeasuresTest(){
        HomePage homePage=new HomePage(driver);
        homePage.sortBar.selectFilterByCategory("Measures");


        Assert.assertTrue(
                homePage.sortBar
                        .isFilteredByCategory("Measures"),
                "Products are NOT filtered by category "
        );
    }

}
