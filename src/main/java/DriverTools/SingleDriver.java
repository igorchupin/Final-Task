package DriverTools;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class SingleDriver {

    static CapabilitySettings userSet = new CapabilitySettings();
    private static SingleDriver singleDriver = null;
    private WebDriver driver;

    private SingleDriver() throws IOException {  // SauceLabs/Docker
        ReadCapt();
        switch (userSet.getEnvironment()) {
            case "locally":
                SelectLocalBrowser(userSet);
                break;
            case "selenium grid":
                SelectGridBrowser(userSet);
                break;
            case "sauce labs":
                SelectSauceLabsDriver(userSet);
                break;
            case "docker":
                SelectDockerBrowser(userSet);
                break;
            default:
                SelectLocalBrowser(userSet);
                System.out.println("No such Environment. Let's use locally browser!");
                break;
        }
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public static SingleDriver getSingleDriverInstance() throws IOException {
        if (singleDriver == null) {
            synchronized (SingleDriver.class) {
                if (singleDriver == null) {
                    singleDriver = new SingleDriver();
                }
            }
        }
        return singleDriver;
    }

    public WebDriver getDriver() throws IOException {
        return driver;
    }

    public void closeDriver() {
        if (singleDriver != null) {
            driver.close();
            singleDriver = null;
        }
    }

    public void ReadCapt() throws IOException {
        try {
            userSet = CapabilitySettings.SettingsRead(userSet);
        } catch (IOException e) {
        }
    }


    public WebDriver SelectLocalBrowser(CapabilitySettings capSettings) {
        switch (capSettings.getBrowserName()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            default:
                driver = new ChromeDriver();
                WebDriverManager.chromedriver().setup();
                System.out.println("No such driver. Let's use Chrome driver!");

        }
        return driver;
    }

    public WebDriver SelectGridBrowser(CapabilitySettings capSettings) throws MalformedURLException {
        switch (capSettings.getBrowserName()) {
            case "chrome":
                DesiredCapabilities capatibilityChrome = DesiredCapabilities.chrome();
                driver = new RemoteWebDriver(new URL(capSettings.getURL() + ":" + capSettings.getPort() + "/wd/hub"), capatibilityChrome);
                break;
            case "firefox":
                DesiredCapabilities capatibilityFireFox = DesiredCapabilities.firefox();
                driver = new RemoteWebDriver(new URL(capSettings.getURL() + ":" + capSettings.getPort() + "/wd/hub"), capatibilityFireFox);
                break;
            default:
                DesiredCapabilities capatibilityDefault = DesiredCapabilities.chrome();
                driver = new RemoteWebDriver(new URL(capSettings.getURL() + ":" + capSettings.getPort() + "/wd/hub"), capatibilityDefault);
                System.out.println("No such driver. Let's use Chrome driver!");
        }
        return driver;
    }

    public WebDriver SelectSauceLabsDriver(CapabilitySettings capSettings) throws MalformedURLException {
        switch (capSettings.getBrowserName()) {
            case "chrome":
                ChromeOptions browserOptionsChrome = new ChromeOptions();
                browserOptionsChrome.setCapability("platformName", capSettings.getPlatform() + capSettings.getPlatformVersion());
                browserOptionsChrome.setCapability("browserVersion", capSettings.getBrowserVersion());
                driver = new RemoteWebDriver(new URL(capSettings.getURL()), browserOptionsChrome);
                break;
            case "firefox":
                FirefoxOptions browserOptionsFireFox = new FirefoxOptions();
                browserOptionsFireFox.setCapability("platformName", capSettings.getPlatform() + capSettings.getPlatformVersion());
                browserOptionsFireFox.setCapability("browserVersion", capSettings.getBrowserVersion());
                driver = new RemoteWebDriver(new URL(capSettings.getURL()), browserOptionsFireFox);

            default:
                ChromeOptions browserOptionsChromeDefault = new ChromeOptions();
                browserOptionsChromeDefault.setCapability("platformName", capSettings.getPlatform() + capSettings.getPlatformVersion());
                browserOptionsChromeDefault.setCapability("browserVersion", capSettings.getBrowserVersion());
                driver = new RemoteWebDriver(new URL(capSettings.getURL()), browserOptionsChromeDefault);
                System.out.println("No such driver. Let's use Chrome driver!");
        }
        return driver;
    }

    public WebDriver SelectDockerBrowser(CapabilitySettings capSettings) throws MalformedURLException {
        switch (capSettings.getBrowserName()) {
            case "chrome":
                DesiredCapabilities capabilitiesChrome = DesiredCapabilities.chrome();
                capabilitiesChrome.setCapability("browserName", capSettings.getBrowserName());
                capabilitiesChrome.setCapability("browserVersion", capSettings.getBrowserVersion());
                driver = new RemoteWebDriver(URI.create(capSettings.getURL() + ":" + capSettings.getPort() + "/wd/hub").toURL(), capabilitiesChrome);
                break;
            case "firefox":
                DesiredCapabilities capabilitiesFFD = DesiredCapabilities.firefox();
                capabilitiesFFD.setCapability("browserName", capSettings.getBrowserName());
                capabilitiesFFD.setCapability("browserVersion", capSettings.getBrowserVersion());
                driver = new RemoteWebDriver(URI.create(capSettings.getURL() + ":" + capSettings.getPort() + "/wd/hub").toURL(), capabilitiesFFD);
                break;

            default:
                DesiredCapabilities capabilitiesChromeDefault = DesiredCapabilities.chrome();
                capabilitiesChromeDefault.setCapability("browserName", capSettings.getBrowserName());
                capabilitiesChromeDefault.setCapability("browserVersion", capSettings.getBrowserVersion());
                driver = new RemoteWebDriver(URI.create(capSettings.getURL() + ":" + capSettings.getPort() + "/wd/hub").toURL(), capabilitiesChromeDefault);
                System.out.println("No such driver. Let's use Chrome driver!");
                break;
        }
        return driver;
    }
}
