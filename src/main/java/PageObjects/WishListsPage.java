package PageObjects;
import DriverTools.SingleDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class WishListsPage {
    private final WebDriver driver;
    private final By saveButton = By.id("submitWishlist");
    private final By wishListsTable = By.id("block-history");
    private final By products = By.xpath("//a[@class=\"product-name\"]");
    private final By wishListLink = By.xpath("//tr[contains(@id, 'wishlist_')]/td[1]/a");
    private final By productTitle = By.id("s_title");
    private final By deleteListLink = By.xpath("//td[@class='wishlist_delete']/a");
    private final By wishListNameField = By.id("name");
    private final By openCartLink = By.xpath("//a[@title='View my shopping cart']");
    private List<WebElement> productsList;


    public WishListsPage() throws IOException {
        driver = SingleDriver.getSingleDriverInstance().getDriver();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(saveButton));
    }


    public boolean getWishListsTable() throws NoSuchElementException {
            boolean result;
            try {
                driver.findElement(wishListsTable);
                result = true;
            }
            catch (NoSuchElementException e) {
                result = false;
            }
            return result;
    }


    private int random () {
        productsList = driver.findElements(products);
        int rndItem = new Random().nextInt(productsList.size());
        return rndItem;
    }

    public ProductPage getProduct () throws IOException {
        productsList = driver.findElements(products);
        WebElement product = productsList.get(random());
        product.click();
        return new ProductPage();
    }

    public WishListsPage openWishList () {
        driver.findElement(wishListLink).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(productTitle));
        return this;
    }

    public String getProductNameFromList () {

        return driver.findElement(productTitle).getText();
    }

    public WishListsPage deleteWishList () {
        driver.findElement(deleteListLink).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());

        alert.accept();
        return this;
    }

    public String generateListName () {
        String tempName = "";
        for (int i = 0; i <= 10  ; i++) {
            int rndChar = 97 + (new Random().nextInt(122 - 97));
            Character tempChar = (char) rndChar;
            tempName = tempName + tempChar;
        }
        return tempName;
    }

    public WishListsPage createWishList () {
        driver.findElement(wishListNameField).sendKeys(generateListName());
        driver.findElement(saveButton).click();
        return this;
    }

    public CartPage openCart () throws IOException {
        driver.findElement(openCartLink).click();
        return new CartPage();
    }

}
