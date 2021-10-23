package com.petclinic.selenium;

import io.github.bonigarcia.seljup.SeleniumExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.hamcrest.MatcherAssert.assertThat;
import java.util.concurrent.TimeUnit;
/**
 * User: @SmoothWin
 * Date: 2021-10-22
 * Ticket: test(VETS-CPC-499): Login Test Helper for Selenium
 * This is the actual Login helper class that will help to facilitate the login part that
 * each new selenium tests will have to require
 * (See the SeleniumLoginTestHelperTest class for an example on how to implement)
 * inside each of our End-to-End tests (Selenium tests)
 */
@ExtendWith(SeleniumExtension.class)
public class SeleniumLoginTestHelper {
    String username;
    String password;
    String service;
    WebDriver driver;

    public SeleniumLoginTestHelper(String service, WebDriver driver){
        this.username = "admin";
        this.password = "admin";
        this.service = service;
        this.driver = driver;
        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
        System.setProperty("sel.jup.screenshot.at.the.end.of.tests", "whenfailure");
        System.setProperty("sel.jup.screenshot.format", "png");
        System.setProperty("sel.jup.output.folder", "./src/test/onFailureScreenshots");
    }

    public void loginTest() throws Exception {

        try {
            WebDriverWait wait = new WebDriverWait(driver,10);
            //Go to the login page
            driver.get("http://localhost:8080/#!/login");
            driver.manage().window().maximize();

            //Locate the email input box
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"email\"]")));


            //Enter email information, password and click button
            driver.findElement(By.xpath("//*[@id=\"email\"]")).sendKeys("admin");
            driver.findElement(By.xpath("//*[@id=\"pwd\"]")).sendKeys("admin");
            driver.findElement(By.xpath("//*[@id=\"button\"]")).click();


            //Press check url
            wait.until(ExpectedConditions.urlToBe("http://localhost:8080/#!/login"));
            String currentUrl = driver.getCurrentUrl();
            assertThat(currentUrl,currentUrl != "http://localhost:8080/#!/login");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }
}
