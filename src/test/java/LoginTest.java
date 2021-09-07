import DriverTools.SingleDriver;
import PageObjects.HomePage;
import PageObjects.LoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.openqa.selenium.WebDriver;


import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(TestListeners.class)

public class LoginTest {
    private static final String EXPECTED_ERROR_MESSAGE = "Password is required.";
    private static final String EXPECTED_ACCOUNT_NAME = "First Last";
    private static WebDriver driver;
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
    void setUp() throws IOException {
        loginPage = new LoginPage();
    }

    @AfterEach
    void tearDown() {
        driver.manage().deleteAllCookies();
    }

    @TmsLink(value = "5T")
    @Feature("Login with correct email and empty password")
    @Description(value = "This test verifies error after attempt to login with valid email and empty password")
    @ParameterizedTest
    @CsvFileSource(resources = "/logindata.csv", numLinesToSkip = 1)
    @Tag("login_test")
    @DisplayName("Login with empty password")
    void loginWithEmptyPassword(String username, String password) throws IOException {
        loginPage.loginWithEmptyPassword(username);
        assertTrue(loginPage.getErrorMessagePasswordRequiredText().contains(EXPECTED_ERROR_MESSAGE),
                "Incorrect error message was shown:" + loginPage.getErrorMessagePasswordRequiredText());
    }

    @TmsLink(value = "6T")
    @Feature("Login with correct email and password")
    @Description(value = "This test verifies error after attempt to login with valid email and password")
    @ParameterizedTest
    @CsvFileSource(resources = "/logindata.csv", numLinesToSkip = 1)
    @Tag("login_test")
    @DisplayName("Login with password")
    void loginWithPassword(String username, String password) throws IOException {
        HomePage homepage = loginPage.loginWithPassword(username, password);
        assertTrue(homepage.getAccountName().contains(EXPECTED_ACCOUNT_NAME),
                " Incorrect account name was shown:" + homepage.getAccountName());
    }
}