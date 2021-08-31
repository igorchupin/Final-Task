package PageObjects;

import DriverTools.SingleDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;


public class HomePage {
    private final WebDriver driver;
    private final By accountName = By.xpath("//a[@class='account']/span");
    private final By myWishlistsButton = By.xpath("//a[@title='My wishlists']");
    private final By openCartLink = By.xpath("//a[@title='View my shopping cart']");

    public HomePage() throws IOException {
        driver = SingleDriver.getSingleDriverInstance().getDriver();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(accountName));
    }

    public String getAccountName() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(accountName));
        return driver.findElement(accountName).getText();
    }

    public WishListsPage showWishlists() throws IOException {
        driver.findElement(myWishlistsButton).click();
        return new WishListsPage();
    }

    public CartPage openCart () throws IOException {
        driver.findElement(openCartLink).click();
        return new CartPage();
    }

}
