package PageObjects;

import DriverTools.SingleDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;

public class CreateAccountPage {
    private final WebDriver driver;
    private final String phoneNumber = "123456";
    private final String lastName = "LastName";
    private final String firstName = "FirstName";
    private final String password = "passwd";
    private final String address = "Minsk, Chapaeva, 5";
    private final String city = "Minsk";
    private final String postCode = "12345";
    private final By createAccountButton = By.id("submitAccount");
    private final By errorMessage = By.xpath("//div[@class='alert alert-danger']");
    private final By errorMessageText = By.xpath("//div[@class='alert alert-danger']/p");
    private final By phoneField = By.id("phone_mobile");
    private final By lastNameField = By.id("customer_lastname");
    private final By firsNameField = By.id("customer_firstname");
    private final By passwordField = By.id("passwd");
    private final By addressField = By.id("address1");
    private final By cityField = By.id("city");
    private final By postalCodeField = By.id("postcode");
    private final By StateField = By.id("id_state");

    public CreateAccountPage() throws IOException {
        driver = SingleDriver.getSingleDriverInstance().getDriver();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(createAccountButton));
    }

    public CreateAccountPage registerWithEmptyFields() {
        WebElement createButton = driver.findElement(createAccountButton);
        createButton.click();
        return this;
    }

    public String getErrorMessageText() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
        return driver.findElement(errorMessageText).getText();
    }

    public HomePage fillAllFieldsAndRegister() throws IOException {
        driver.findElement(phoneField).sendKeys(phoneNumber);
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(firsNameField).sendKeys(firstName);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(addressField).sendKeys(address);
        driver.findElement(cityField).sendKeys(city);
        driver.findElement(postalCodeField).sendKeys(postCode);
        WebElement multiSelectForm = driver.findElement(StateField);
        Select selectObject = new Select(multiSelectForm);
        selectObject.selectByValue("7");
        WebElement createButton = driver.findElement(createAccountButton);
        createButton.click();
        return new HomePage();
    }

}




