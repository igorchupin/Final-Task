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


public class AddingItemToWishListTest {

    private static WebDriver driver;
    private LoginPage loginPage;


    @BeforeAll
    static void beforeAll() throws IOException {
        driver = SingleDriver.getSingleDriverInstance().getDriver();

    }

    @BeforeEach
    void setUp() throws IOException {
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

    @TmsLink(value = "8T")
    @Feature("Wishlist manually creation and adding items")
    @Description(value = "This test verifies wishlist manually creation and adding items")
    @ParameterizedTest
    @CsvFileSource(resources = "/logindata.csv", numLinesToSkip = 1)
    @Tag("wishlist_test")
    @DisplayName("Wishlist manually creation and adding items")
    void WishlistManuallyCreation(String username, String password) throws IOException {
        HomePage homePage = loginPage.loginWithPassword(username, password);
        WishListsPage wishListsPage = homePage.showWishlists();
        wishListsPage.createWishList();
        ProductPage productPage = wishListsPage.getProduct();
        productPage.addToWishlist();
        String productNameExpected = productPage.getProductName();
        driver.navigate().back();
        driver.navigate().refresh();
        wishListsPage.openWishList();

        assertTrue(wishListsPage.getProductNameFromList().contains(productNameExpected),
                        "List does not contain selected product" + wishListsPage.getProductNameFromList());

    }
}