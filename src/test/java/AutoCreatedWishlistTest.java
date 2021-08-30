import DriverTools.SingleDriver;
import PageObjects.HomePage;
import PageObjects.LoginPage;
import PageObjects.ProductPage;
import PageObjects.WishListsPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.WebDriver;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(TestListeners.class)

public class AutoCreatedWishlistTest {

    private static WebDriver driver;
    private LoginPage loginPage;
    private WishListsPage wishListsPage;

    @BeforeAll
    static void beforeAll() throws IOException {
        driver = SingleDriver.getSingleDriverInstance().getDriver();

    }
    @BeforeEach
    void setUp () throws IOException {
        loginPage = new LoginPage();
    }

    @AfterAll
    static void afterAll() throws IOException {
       SingleDriver.getSingleDriverInstance().closeDriver();

    }

    @AfterEach
    void tearDown() throws IOException {
         WishListsPage wishListsPage = new WishListsPage();
         wishListsPage.deleteWishList();
         driver.navigate().refresh();
         driver.manage().deleteAllCookies();
    }

    @TmsLink(value = "7T")
    @Feature("Wishlist automatically creation")
    @Description(value = "This test verifies wishlist automatically creation")
    @ParameterizedTest
    @CsvFileSource(resources = "/logindata.csv", numLinesToSkip = 1)
    @Tag("wishlist_test")
    @DisplayName("Wishlist automatically creation")
    void WishlistAutomaticallyCreation(String username, String password) throws IOException {
        HomePage homePage = loginPage.loginWithPassword(username, password);
        WishListsPage wishListsPage = homePage.showWishlists();
        boolean result = wishListsPage.getWishListsTable();
        ProductPage productPage = wishListsPage.getProduct();
        productPage.addToWishlist();
        String productNameExpected = productPage.getProductName();
        driver.navigate().back();
        driver.navigate().refresh();
        wishListsPage.openWishList();

        assertAll("Choose account Page",
                () -> assertFalse(result, "Wishlist already exists"),
                () -> assertTrue(wishListsPage.getProductNameFromList().contains(productNameExpected),
                        "List does not contain selected product" + wishListsPage.getProductNameFromList())
        );









    }




}


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////




/*
  assertAll("Choose account Page",
                () -> assertTrue(chooseAccountPage.getTextThatUserWasLoggedOut().matches("You are logged out of:"),
                        String.format("Incorrect page was opened. The opened page does not contain text 'You are logged out of:'" +
                                                                            " and has the title: %s", driver.getTitle())),
                () -> assertEquals(username, chooseAccountPage.getAccountName(),
                        "Incorrect user account name is shown")
        );
 */