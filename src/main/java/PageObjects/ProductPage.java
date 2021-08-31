package PageObjects;

import DriverTools.SingleDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class ProductPage {

    private final WebDriver driver;
    private final By adToCartButton = By.xpath("//p[@id='add_to_cart']/button");
    private final By addToWishlistButton = By.id("wishlist_button");
    private final By productName = By.xpath("//h1[@itemprop='name']");
    private final By closeButton = By.xpath("//a[@class='fancybox-item fancybox-close']");
    private final By price = By.id("our_price_display");
    private final By addToCartButton = By.xpath("//p[@id='add_to_cart']/button");
    private final By continueButton = By.xpath("//span[@title='Continue shopping']");

    public ProductPage () throws IOException {
        driver = SingleDriver.getSingleDriverInstance().getDriver();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(adToCartButton));
    }

    public String getProductName () {
        return driver.findElement(productName).getText();
    }

    public Double getProductPrice () {
       Double result = Double.valueOf(driver.findElement(price).getText().substring(1));
        return result;
    }

    public ProductPage addToWishlist () {
        driver.findElement(addToWishlistButton).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(closeButton));
        driver.findElement(closeButton).click();
        return this;
    }

    public WishListsPage addToCart () throws IOException {
        driver.findElement(addToCartButton).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(continueButton));
        driver.findElement(continueButton).click();
        driver.navigate().back();
        return new WishListsPage();
    }

}
