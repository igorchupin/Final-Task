import DriverTools.SingleDriver;
import PageObjects.*;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.WebDriver;
import java.io.IOException;
import java.text.DecimalFormat;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(TestListeners.class)


public class AddingItemsToCart {

    private static WebDriver driver;
    private LoginPage loginPage;
    private String totalPrice;


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
        driver.manage().deleteAllCookies();
    }

    @TmsLink(value = "9T")
    @Feature("Adding items to cart")
    @Description(value = "This test verifies adding items to cart")
    @ParameterizedTest
    @CsvFileSource(resources = "/logindata.csv", numLinesToSkip = 1)
    @Tag("cart_test")
    @DisplayName("Adding items to cart")
    void addingItemsToCart(String username, String password) throws IOException {
       Double total = 0.0;
        HomePage homePage = loginPage.loginWithPassword(username, password);
        for (int i = 0; i < 3; i++) {
            WishListsPage wishListsPage = homePage.showWishlists();
            ProductPage productPage = wishListsPage.getProduct();
            total = total + productPage.getProductPrice();
            productPage.addToCart();
            driver.navigate().refresh();
        }
        CartPage cartPage = homePage.openCart();
        totalPrice = Double.toString((double)Math.round(total * 100000d) / 100000d);

       assertAll("Cart Page",
               () -> assertEquals(3, cartPage.getCountProducts(), "Incorrect products count"),
               () -> assertEquals(totalPrice, cartPage.getTotalPrice().toString(), "Incorrect total price" + cartPage.getTotalPrice().toString())
        );

    }
}
