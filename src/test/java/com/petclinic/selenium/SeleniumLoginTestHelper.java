package com.petclinic.selenium;

import io.github.bonigarcia.seljup.SeleniumExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
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
        //Go to the login page
        driver.get("http://localhost:8080/#!/login");
        driver.manage().window().maximize();

        //Locate the login header
        WebElement loginHeader = driver.findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/login-form/div/div/h2"));
        TimeUnit.SECONDS.sleep(1);

        //Enter email information
        WebElement emailBox = driver.findElement(By.id("email"));
        emailBox.sendKeys(username);
        TimeUnit.SECONDS.sleep(1);

        //Enter password information
        WebElement passwordBox = driver.findElement(By.id("pwd"));
        passwordBox.sendKeys(password);
        TimeUnit.SECONDS.sleep(1);

        //Press the login button
        WebElement loginButton = driver.findElement(By.id("button"));
        loginButton.click();
        TimeUnit.SECONDS.sleep(2);
        String currentUrl = driver.getCurrentUrl();
        assertThat(currentUrl,currentUrl != "http://localhost:8080/#!/login");


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TimeUnit.SECONDS.sleep(1);
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
