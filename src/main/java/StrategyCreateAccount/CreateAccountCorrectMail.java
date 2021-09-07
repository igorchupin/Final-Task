package StrategyCreateAccount;

import DriverTools.SingleDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.util.Random;

public class CreateAccountCorrectMail implements ICreateAccountStrategy {

    private WebDriver driver;
    private String randomMAil = "";
    private int mailLength = 7;
    private By emailInput = By.id("email_create");
    private By createAccountButton = By.id("SubmitCreate");


    public String generateMailName () {
        String tempEmail = "";
        for (int i = 0; i <= mailLength  ; i++) {
            int rndChar = 97 + (new Random().nextInt(122 - 97));
            Character tempChar = (char) rndChar;
            tempEmail = tempEmail + tempChar;
        }
        return tempEmail;
    }

    public CreateAccountCorrectMail() throws IOException {
        driver = SingleDriver.getSingleDriverInstance().getDriver();
        randomMAil = generateMailName()+"@yandex.by";
    }

    @Override
    public void createAccount() {
        driver.findElement(emailInput).sendKeys(randomMAil);
        driver.findElement(createAccountButton).click();
    }

    public String getAccountName () {
        return randomMAil;
    }

    //TODO
   // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!




}
