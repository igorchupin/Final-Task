import DriverTools.SingleDriver;
import PageObjects.CreateAccountPage;
import PageObjects.HomePage;
import PageObjects.LoginPage;
import StrategyCreateAccount.CreateAccountCorrectMail;
import StrategyCreateAccount.CreateAccountEmptyMail;
import StrategyCreateAccount.CreateAccountIncorrectMail;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(TestListeners.class)

public class CreateAccountTest {
    private static final String EXPECTED_ERROR_MESSAGE = "Invalid email address.";
    private static final String EXPECTED_MESSAGE_EMPTY_FIELDS = "There are 8 errors";
    private static final String EXPECTED_ACCOUNT_NAME = "FirstName LastName";
    private static WebDriver driver;
    private CreateAccountIncorrectMail incorrectMailStrategy;
    private CreateAccountEmptyMail emptyMailStrategy;
    private CreateAccountCorrectMail correctMailStrategy;
    private LoginPage loginPage;

    @BeforeAll
    static void beforeAll() throws IOException {
        driver = SingleDriver.getSingleDriverInstance().getDriver();

    }

    @AfterAll
    static void afterAll() throws IOException {
        SingleDriver.getSingleDriverInstance().closeDriver();

    }

    @BeforeEach
    public void beforeTest() throws IOException {
        incorrectMailStrategy = new CreateAccountIncorrectMail();
        emptyMailStrategy = new CreateAccountEmptyMail();
        correctMailStrategy = new CreateAccountCorrectMail();
    }

    @AfterEach
    void tearDown() {

        driver.manage().deleteAllCookies();
    }

    @TmsLink(value = "1T")
    @Feature("Create Account with invalid e-mail")
    @Description(value = "This test verifies error after attempt to create account with invalid email")
    @Test
    @Tag("create_account_test")
    @DisplayName("Create Account with invalid e-mail")
    void CreateAccountIncorrectMailTest() throws IOException {
        loginPage = new LoginPage(incorrectMailStrategy);
        loginPage.createAccount();
        assertTrue(loginPage.getErrorMessageText().contains(EXPECTED_ERROR_MESSAGE),
                "Incorrect error message was shown:" + loginPage.getErrorMessageText());
    }

    @TmsLink(value = "2T")
    @Feature("Create Account with empty e-mail")
    @Description(value = "This test verifies error after attempt to create account with empty email")
    @Test
    @Tag("create_account_test")
    @DisplayName("Create Account with empty e-mail")
    void CreateAccountEmptyMailTest() throws IOException {
        loginPage = new LoginPage(emptyMailStrategy);
        loginPage.createAccount();
        assertTrue(loginPage.getErrorMessageText().contains(EXPECTED_ERROR_MESSAGE),
                "Incorrect error message was shown:" + loginPage.getErrorMessageText());
    }

    @TmsLink(value = "3T")
    @Feature("Create Account with correct e-mail and empty fields")
    @Description(value = "This test verifies error after attempt to create account with correct email and empty fields")
    @Test
    @Tag("create_account_test")
    @DisplayName("Create Account with correct e-mail and empty fields")
    void CreateAccountCorrectMailEmptyFieldsTest() throws IOException {
        loginPage = new LoginPage(correctMailStrategy);
        CreateAccountPage createAccountPage = loginPage.createAccountCorrectMail();
        createAccountPage.registerWithEmptyFields();
        assertTrue(createAccountPage.getErrorMessageText().contains(EXPECTED_MESSAGE_EMPTY_FIELDS),
                "Incorrect error message was shown:" + createAccountPage.getErrorMessageText());
    }

    @TmsLink(value = "4T")
    @Feature("Create Account with correct e-mail and filled fields")
    @Description(value = "This test verifies error after attempt to create account with correct email and filled fields")
    @Test
    @Tag("create_account_test")
    @DisplayName("Create Account with correct e-mail and filled fields")
    void CreateAccountCorrectMailFilledFieldsTest() throws IOException {
        loginPage = new LoginPage(correctMailStrategy);
        CreateAccountPage createAccountPage = loginPage.createAccountCorrectMail();
        HomePage homepage = createAccountPage.fillAllFieldsAndRegister();
        assertTrue(homepage.getAccountName().contains(EXPECTED_ACCOUNT_NAME),
                " Incorrect account name was shown:" + homepage.getAccountName());
    }

}
