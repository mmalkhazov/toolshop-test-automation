package com.toolshop.pages.components;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;

public class FilterBar {

    WebDriver driver;

    public FilterBar(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }



    @FindBy(xpath = "//select[@data-test='sort']")
    WebElement sortDropDownMenu;

    public void selectSortingOption(String sortingOption) {
        if (sortingOption != null && !sortingOption.isEmpty()) {
            Select select = new Select(sortDropDownMenu);
            select.selectByVisibleText(sortingOption);
        }
    }

    public FilterBar selectAscendingSortingByPrice() {
        selectSortingOption("Price (Low - High)");
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.stalenessOf(productPrice.get(0)));
        return this;
    }

    public FilterBar selectDescendingSortingByPrice() {
        selectSortingOption("Price (High - Low)");
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.stalenessOf(productPrice.get(0)));
        return this;
    }

    public FilterBar selectAscendingSortingByName() {
        selectSortingOption("Name (A - Z)");
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.stalenessOf(productName.get(0)));

        return this;
    }

    public FilterBar selectDescendingSortingByName() {
        selectSortingOption("Name (Z - A)");
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.stalenessOf(productName.get(0)));
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfAllElements(productName));
        return this;
    }

    @FindBy(xpath = "//input[@name='category_id']/parent::label")
    List<WebElement> filterCategories;


    public FilterBar selectFilterByCategory(String filterName) {
        String firstProductBefore = productName.get(0).getText();

        for (WebElement category : filterCategories) {
            if (category.getText().contains(filterName)) {
                category.click();
                break;
            }
        }

        if (!filterName.equals("Hand Tools")) {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(driver -> !productName.get(0).getText().equals(firstProductBefore));
        }

        return this;
    }

    @FindBy(xpath = "//span[@data-test='product-price']")
    List<WebElement> productPrice;

    @FindBy(xpath = "//h5[@data-test='product-name']")
    List<WebElement> productName;


    public List<Double> sortedByPriceList() {
        List<Double> newList = new ArrayList<>();


        for (WebElement p : productPrice) {
            newList.add(Double.valueOf(
                    p.getText().replace("$", "")));
        }

        return newList;
    }

    public List<Double> sortedByAscendingPriceList() {
        List<Double> list = sortedByPriceList();
        Collections.sort(list);
        return list;
    }


    public boolean isSortedByPriceAscending() {
        List<Double> actual = getAllPrices();

        List<Double> sorted = new ArrayList<>(actual);
        Collections.sort(sorted);
        System.out.println("Actual after: " + actual);
        System.out.println("Sorted after: " + sorted);
        return actual.equals(sorted);
    }

    public boolean isSortedByPriceDescending() {
        List<Double> actual = getAllPrices();

        List<Double> sorted = new ArrayList<>(actual);
        Collections.sort(sorted, Collections.reverseOrder());

        System.out.println("Actual after: " + actual);
        System.out.println("Sorted after: " + sorted);
        return actual.equals(sorted);
    }

    public boolean isSortedByNameAscending() {
        List<String> actual = getAllNames();

        List<String> sorted = new ArrayList<>(actual);
        Collections.sort(sorted, String.CASE_INSENSITIVE_ORDER);
        System.out.println("Actual after: " + actual);
        System.out.println("Sorted after: " + sorted);
        return actual.equals(sorted);
    }

    public boolean isSortedByNameDescending() {
        List<String> actual = getAllNames();

        List<String> sorted = new ArrayList<>(actual);
        Collections.sort(sorted, Collections.reverseOrder(String.CASE_INSENSITIVE_ORDER));
        System.out.println("Actual after: " + actual);
        System.out.println("Sorted after: " + sorted);
        return actual.equals(sorted);
    }


    @FindBy(xpath = "//ul[contains(@class,'pagination')]//a[contains(@aria-label,'Page-')]")
    List<WebElement> pagesOfTheProduct;

    private List<WebElement> getPageLinks() {
        return pagesOfTheProduct;
    }

    public boolean switchPages() {
        int totalPages = getPageLinks().size();

        for (int i = 0; i < totalPages; i++) {
            List<WebElement> currentPages = getPageLinks();

            WebElement page = currentPages.get(i);

            new WebDriverWait(driver, Duration.ofSeconds(20))
                    .until(ExpectedConditions.elementToBeClickable(page));

            page.click();
            new WebDriverWait(driver, Duration.ofSeconds(20))
                    .until(ExpectedConditions.visibilityOfAllElements(getPageLinks()));


        }
        return false;
    }

    private List<WebElement> getProductsPrice() {
        return productPrice;
    }

    private List<WebElement> getProductsName() {
        return productName;
    }


    private List<Double> getPricesFromCurrentPage() {
        List<Double> prices = new ArrayList<>();
        int size = getProductsPrice().size();

        for (int i = 0; i < size; i++) {
            final int index = i;
            String text = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .ignoring(StaleElementReferenceException.class)
                    .until(driver ->
                            getProductsPrice().get(index).getText()
                    );
            prices.add(Double.valueOf(text.replace("$", "").trim()));
        }
        return prices;
    }


    private List<String> getNamesFromCurrentPage() {
        List<String> names = new ArrayList<>();
        for (WebElement el : getProductsName()) {
            names.add(el.getText().trim());
        }
        return names;
    }

    //                                it works
    public List<Double> getAllPrices() {
        List<Double> allPrices = new ArrayList<>();
        int totalPages = getPageLinks().size();


        List<Double> page1 = getPricesFromCurrentPage();
        System.out.println("Page 1: " + page1);
        allPrices.addAll(page1);

        for (int i = 1; i < totalPages; i++) {
            final String previousFirstPrice =
                    getProductsPrice().get(0).getText();

            getPageLinks().get(i).click();


            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .ignoring(StaleElementReferenceException.class)
                    .until(driver ->
                            !getProductsPrice().get(0).getText().equals(previousFirstPrice)
                    );

            List<Double> pagePrices = getPricesFromCurrentPage();
            System.out.println("Page " + (i + 1) + ": " + pagePrices);
            allPrices.addAll(pagePrices);
        }

        return allPrices;
    }

    public List<String> getAllNames() {
        List<String> allNames = new ArrayList<>();

        allNames.addAll(getNamesFromCurrentPage());

        int i = 1;
        while (i < getPageLinks().size()) {
            final String previousFirstName = getProductsName().get(0).getText();
            final int pageIndex = i;

            getPageLinks().get(pageIndex).click();

            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .ignoring(StaleElementReferenceException.class)
                    .until(driver ->
                            !getProductsName().get(0).getText().equals(previousFirstName)
                    );

            allNames.addAll(getNamesFromCurrentPage());
            i++;
        }
        return allNames;
    }


    public boolean isFilteredByCategory( String categoryName) {
        List<String> names=getAllNames();

        return allNamesMatchToCategory(names, categoryName);

    }


    public boolean allNamesMatchToCategory(List<String> names, String categoryName) {

        List<String> keywords = categoryName.equals("Hand Tools")
                ? List.of("hammer", "saw", "wrench", "screwdriver",
                "pliers", "chisel", "measure", "bolt", "spanner", "tape", "ruler")
                : categoryName.equals("Measures")
                ? List.of("measure", "tape", "ruler", "square")
                : List.of(categoryName.toLowerCase());

        for (String name : names) {
            boolean matchesAny = false;
            for (String keyword : keywords) {
                if (name.toLowerCase().contains(keyword)) {
                    matchesAny = true;
                    break;
                }
            }
            if (!matchesAny) {
                System.out.println("Wrong product: " + name);
                return false;
            }
        }
        return true;
    }


    @FindBy(id = "search-query")
    WebElement searchInput;

    @FindBy(xpath = "//button[@data-test='search-submit']")
    WebElement searchSubmitButton;

    @FindBy(xpath = "//*[@data-test='search-result-count']")
    WebElement searchResultCount;



    public FilterBar searchFor(String query) {
        WebElement firstProduct = productName.get(0);
        searchInput.clear();
        searchInput.sendKeys(query);
        searchSubmitButton.click();

        if (!query.isEmpty()) {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.stalenessOf(firstProduct));
        } else {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.visibilityOf(searchResultCount));
        }
        return this;
    }


    public List<String> getNamesFromPage() {

        List<String> names = new ArrayList<>();
        for (WebElement el : productName) {
            names.add(el.getText().trim());
        }
        return names;
    }


    public boolean allSearchResultsMatch(String keyword) {

        Map<String, List<String>> exceptions = Map.of(
                "hammer", List.of("leather toolbelt")
//                "pliers", List.of("some other product")  //
        );

        List<String> knownExceptions = exceptions.getOrDefault(
                keyword.toLowerCase(), List.of()
        );

        List<String> names = getNamesFromPage();

        for (String name : names) {
            boolean matchesName      = name.toLowerCase().contains(keyword.toLowerCase());
            boolean isKnownException = knownExceptions.contains(name.toLowerCase());

            if (!matchesName && !isKnownException) {
                System.out.println(" Unexpected product: " + name);
                return false;
            }
        }
        return true;
    }

    @FindBy(xpath = "//*[@data-test='no-results']")
    WebElement noResultsMessage;

    public boolean isNoResultsMessageDisplayed() {
        try {
            return noResultsMessage.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public FilterBar clickSearchWithoutQuery() {
        searchSubmitButton.click();
        return this;
    }

    public List<String> getCurrentProductNames() {
        return getNamesFromPage();
    }

    public boolean productsMatchList(List<String> expected) {
        return getNamesFromPage().equals(expected);
    }

    public FilterBar waitForProductsToLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(60))
                .until(ExpectedConditions.visibilityOfAllElements(productName));
        return this;
    }
}