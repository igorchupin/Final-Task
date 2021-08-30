package StrategyCreateAccount;

import DriverTools.SingleDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

public class CreateAccountEmptyMail implements ICreateAccountStrategy {
    private WebDriver driver;
    private String emptyMAil = "";
    private By emailInput = By.id("email_create");
    private By createAccountButton = By.id("SubmitCreate");

    public CreateAccountEmptyMail() throws IOException {
        driver = SingleDriver.getSingleDriverInstance().getDriver();
    }

    @Override
    public void createAccount() {
        driver.findElement(emailInput).sendKeys(emptyMAil);
        driver.findElement(createAccountButton).click();
    }
}
