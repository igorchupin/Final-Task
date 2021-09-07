package PageObjects;

import DriverTools.SingleDriver;
import StrategyCreateAccount.ICreateAccountStrategy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;

public class LoginPage {
    private static final String LOGIN_PAGE_URL = "http://automationpractice.com/index.php?controller=authentication&back=my-account";
    private final WebDriver driver;
    private ICreateAccountStrategy strategy;
    private final By createAccountError = By.xpath("//div[@id='create_account_error']/ol/li");
    private final By emailField = By.id("email");
    private final By passwordField = By.id("passwd");
    private final By signInButton = By.id("SubmitLogin");
    private final By passwordRequiredError = By.xpath("//div[@class='alert alert-danger']/ol/li");

    public LoginPage(ICreateAccountStrategy strategy) throws IOException {
        this.strategy = strategy;
        driver = SingleDriver.getSingleDriverInstance().getDriver();
        this.driver.navigate().to(LOGIN_PAGE_URL);
    }

    public LoginPage() throws IOException {
        driver = SingleDriver.getSingleDriverInstance().getDriver();
        this.driver.navigate().to(LOGIN_PAGE_URL);
    }

    public LoginPage createAccount() {
        this.strategy.createAccount();
        return this;
    }

    public CreateAccountPage createAccountCorrectMail() throws IOException {
        this.strategy.createAccount();
        return new CreateAccountPage();
    }


    public String getErrorMessageText() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(createAccountError));
        return driver.findElement(createAccountError).getText();
    }

    public LoginPage loginWithEmptyPassword(String login) throws IOException {
        driver.findElement(emailField).sendKeys(login);
        driver.findElement(signInButton).click();
        return this;
    }

    public String getErrorMessagePasswordRequiredText() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordRequiredError));
        return driver.findElement(passwordRequiredError).getText();
    }

    public HomePage loginWithPassword(String login, String password) throws IOException {
        driver.findElement(emailField).sendKeys(login);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(signInButton).click();
        return new HomePage();
    }

}
