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

        return this;
    }

    @FindBy(xpath = "//input[@name='category_id']/parent::label")
    List<WebElement> filterCategories;


    public FilterBar selectFilterByCategory(String filterName) {
        for(WebElement category:filterCategories){
            if(category.getText().contains(filterName)){
                category.click();
                break;

            }

        }
        new WebDriverWait(driver, Duration.ofSeconds(5))
//                .until(ExpectedConditions.stalenessOf(productName.get(0)));
                .until(ExpectedConditions.visibilityOfAllElements(productName));

        return this;
    }


    @FindBy(xpath = "//span[@data-test='product-price']")
    List<WebElement> productPrice;

    @FindBy(xpath = "//h5[@data-test='product-name']")
    List<WebElement> productName;


    public List<Double> sortedByPriceList() {
        List<Double> newList = new ArrayList<>();

//        while (switchPages()) {
        for (WebElement p : productPrice) {
            newList.add(Double.valueOf(
                    p.getText().replace("$", "")));
        }
//        }
        return newList;
    }

    public List<Double> sortedByAscendingPriceList() {
        List<Double> list = sortedByPriceList();
        Collections.sort(list);
        return list;
    }


    public boolean isSortedByPriceAscending() {
        List<Double> actual = getAllPrices(); // текущий порядок на UI

        List<Double> sorted = new ArrayList<>(actual); // копия списка
        Collections.sort(sorted); // сортируем копию
        System.out.println("Actual after: " + actual);
        System.out.println("Sorted after: " + sorted);
        return actual.equals(sorted); // сравниваем
    }

    public boolean isSortedByPriceDescending() {
        List<Double> actual = getAllPrices(); // текущий порядок на UI

        List<Double> sorted = new ArrayList<>(actual); // копия списка
        Collections.sort(sorted, Collections.reverseOrder()); // сортируем копию

        System.out.println("Actual after: " + actual);
        System.out.println("Sorted after: " + sorted);
        return actual.equals(sorted); // сравниваем
    }

    public boolean isSortedByNameAscending() {
        List<String> actual = getAllNames(); // текущий порядок на UI

        List<String> sorted = new ArrayList<>(actual); // копия списка
        Collections.sort(sorted, String.CASE_INSENSITIVE_ORDER); // сортируем копию
        System.out.println("Actual after: " + actual);
        System.out.println("Sorted after: " + sorted);
        return actual.equals(sorted); // сравниваем
    }

    public boolean isSortedByNameDescending() {
        List<String> actual = getAllNames(); // текущий порядок на UI

        List<String> sorted = new ArrayList<>(actual); // копия списка
        Collections.sort(sorted, Collections.reverseOrder(String.CASE_INSENSITIVE_ORDER)); // сортируем копию
        System.out.println("Actual after: " + actual);
        System.out.println("Sorted after: " + sorted);
        return actual.equals(sorted); // сравниваем
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
                            getProductsPrice().get(index).getText() // ✅ fresh fetch every retry
                    );
            prices.add(Double.valueOf(text.replace("$", "").trim()));
        }
        return prices;
    }

    private List<String> getNamesFromCurrentPage() {
        List<String> names = new ArrayList<>();
        int size = getProductsName().size();

        for (int i = 0; i < size; i++) {
            final int index = i;
            String text = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .ignoring(StaleElementReferenceException.class)
                    .until(driver ->
                            getProductsName().get(index).getText() // ✅ fresh fetch every retry
                    );
            names.add(text.trim());
        }
        return names;
    }



    //                                it works
    public List<Double> getAllPrices() {
        List<Double> allPrices = new ArrayList<>();
        int totalPages = getPageLinks().size();

//        should change to "do while" loop
        // read page 1 without clicking
        List<Double> page1 = getPricesFromCurrentPage();
        System.out.println("Page 1: " + page1);
        allPrices.addAll(page1);

        for (int i = 1; i < totalPages; i++) {
            final String previousFirstPrice =
                    getProductsPrice().get(0).getText(); // remember current first price

            getPageLinks().get(i).click();

            // ✅ wait until first price ACTUALLY changes
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
        int totalPages = getPageLinks().size();

        // read page 1 without clicking
        List<String> page1 = getNamesFromCurrentPage();
        System.out.println("Page 1: " + page1);
        allNames.addAll(page1);

        for (int i = 1; i < totalPages; i++) {
            final String previousFirstName =
                    getProductsName().get(0).getText(); // remember current first name

            getPageLinks().get(i).click();

            // ✅ wait until first price ACTUALLY changes
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .ignoring(StaleElementReferenceException.class)
                    .until(driver ->
                            !getProductsName().get(0).getText().equals(previousFirstName)
                    );

            List<String> pageNames = getNamesFromCurrentPage();
            System.out.println("Page " + (i + 1) + ": " + pageNames);
            allNames.addAll(pageNames);
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
        WebElement firstProduct = productName.get(0); // grab BEFORE click
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
//    public SortBar searchFor(String query) {
//        searchInput.clear();
//        searchInput.sendKeys(query);
//        searchSubmitButton.click();
//        new WebDriverWait(driver, Duration.ofSeconds(5))
//                .until(ExpectedConditions.visibilityOf(searchResultCount));
//        return this;
//    }

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
//                "pliers", List.of("some other product")  // add when you discover them
        );

        List<String> knownExceptions = exceptions.getOrDefault(
                keyword.toLowerCase(), List.of()
        );

        List<String> names = getNamesFromPage();

        for (String name : names) {
            boolean matchesName      = name.toLowerCase().contains(keyword.toLowerCase());
            boolean isKnownException = knownExceptions.contains(name.toLowerCase());

            if (!matchesName && !isKnownException) {
                System.out.println("❌ Unexpected product: " + name);
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


}