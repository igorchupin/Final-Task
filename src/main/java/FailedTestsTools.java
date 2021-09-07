import DriverTools.SingleDriver;
import io.qameta.allure.Attachment;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FailedTestsTools {
    private static WebDriver driver;

    @Attachment
    public static byte[] attachScreenshot() throws IOException {
        driver = SingleDriver.getSingleDriverInstance().getDriver();
        TakesScreenshot screenshot = ((TakesScreenshot) driver);
        return screenshot.getScreenshotAs(OutputType.BYTES);
    }

    @Attachment
    public static String attachBrowserVersion() {
        Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
        String browserName = cap.getBrowserName();
        String browserVersion = cap.getVersion();
        String platformName = cap.getPlatform().name();
        return "Browser name is: " + browserName + ", Browser version is: " + browserVersion + ", Platform " + platformName;
    }

    @Attachment
    public static String attachDateAndTime() {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        return "Date/time: " + timeStamp;
    }
}
