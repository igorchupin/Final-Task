package PageObjects;

import DriverTools.SingleDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;

public class CartPage {

    private final WebDriver driver;
    private final By adToCartButton = By.xpath("//p[@id='add_to_cart']/button");
    private final By totalPrice = By.id("total_price");
    private final By totalShipping = By.id("total_shipping");
    private final By countProducts = By.xpath("//span[@class='ajax_cart_quantity']");
    private Double cartTotalPrice = 0.0;


    public CartPage () throws IOException {
        driver = SingleDriver.getSingleDriverInstance().getDriver();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(totalPrice));
    }

    public Double getTotalPrice () {
        Double result = Double.valueOf(driver.findElement(totalPrice).getText().substring(1));
        Double totalShippingSumm = Double.valueOf(driver.findElement(totalShipping).getText().substring(1));
        return result - totalShippingSumm;
    }

    public Integer getCountProducts () {
        return Integer.valueOf(driver.findElement(countProducts).getText());
    }

}
