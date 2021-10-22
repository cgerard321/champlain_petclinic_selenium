package com.petclinic.selenium.seleniumtest.auth;

import io.github.bonigarcia.seljup.SeleniumExtension;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(SeleniumExtension.class)
public class loginSeleniumTest {

    EdgeDriver driver;
    private final String SCREENSHOTS = "./src/test/onDemandScreenshots";

    public loginSeleniumTest(EdgeDriver driver) {
        this.driver = driver;

        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
        System.setProperty("sel.jup.screenshot.at.the.end.of.tests", "whenfailure");
        System.setProperty("sel.jup.screenshot.format", "png");
        System.setProperty("sel.jup.output.folder", "./src/test/onFailureScreenshots");
    }

    public static void takeSnapShot(WebDriver webDriver, String fileWithPath) throws Exception {

        //Convert web driver object to TakeScreenshot
        TakesScreenshot scrShot = ((TakesScreenshot) webDriver);
        //call getScreenshotAs method to create the actual image file
        File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
        //Move image file to new destination
        File DestFile = new File(fileWithPath);
        //Copy file at destination
        FileUtils.copyFile(SrcFile, DestFile);
    }

    @Test
    @DisplayName("Input_valid_information")
    void valid_input_test(TestInfo testInfo) throws Exception {

        //Go to the login page
        driver.get("http://localhost:7000/#!/login");
        driver.manage().window().maximize();

        //Locate the login header
        WebElement loginHeader = driver.findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/login-form/div/div/h2"));
        TimeUnit.SECONDS.sleep(2);

        //Enter email information
        WebElement emailBox = driver.findElement(By.id("email"));
        emailBox.sendKeys("good@email.com");
        TimeUnit.SECONDS.sleep(2);

        //Enter password information
        WebElement passwordBox = driver.findElement(By.id("pwd"));
        passwordBox.sendKeys("GoodPa$$word123");
        TimeUnit.SECONDS.sleep(2);

        //Press the login button
        WebElement loginButton = driver.findElement(By.id("button"));
        loginButton.click();
        TimeUnit.SECONDS.sleep(2);

        //Take a snapshot
        String method = testInfo.getDisplayName();
        takeSnapShot(driver, SCREENSHOTS + "\\" + method + "_" + System.currentTimeMillis() + ".png");
        //Check if expected value is displayed
        assertThat(loginHeader.getText(), is("Login"));

        Thread.sleep(2000);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.quit();
    }

    @Test
    @DisplayName("Test_empty_email")
    void empty_email_test(TestInfo testInfo) throws Exception {

        //Go to the signup page
        driver.get("http://localhost:7000/#!/login");
        driver.manage().window().maximize();

        //Locate the login header
        WebElement loginHeader = driver.findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/login-form/div/div/h2"));
        TimeUnit.SECONDS.sleep(2);

        //Enter password information
        WebElement passwordBox = driver.findElement(By.id("pwd"));
        passwordBox.sendKeys("GoodPa$$word123");
        TimeUnit.SECONDS.sleep(2);

        //Press the login button
        WebElement loginButton = driver.findElement(By.id("button"));
        loginButton.click();
        TimeUnit.SECONDS.sleep(2);

        //Take a snapshot
        String method = testInfo.getDisplayName();
        takeSnapShot(driver, SCREENSHOTS + "\\" + method + "_" + System.currentTimeMillis() + ".png");
        //Check if expected value is displayed
        assertThat(loginHeader.getText(), is("Login"));

        Thread.sleep(2000);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.quit();
        TimeUnit.SECONDS.sleep(2);
    }

    @Test
    @DisplayName("Test_empty_password")
    void empty_password_test(TestInfo testInfo) throws Exception {

        //Go to the login page
        driver.get("http://localhost:7000/#!/login");
        driver.manage().window().maximize();

        //Locate the login header
        WebElement loginHeader = driver.findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/login-form/div/div/h2"));
        TimeUnit.SECONDS.sleep(2);

        //Enter email information
        WebElement emailBox = driver.findElement(By.id("email"));
        emailBox.sendKeys("good@email.com");
        TimeUnit.SECONDS.sleep(2);

        //Press the login button
        WebElement loginButton = driver.findElement(By.id("pwd"));
        loginButton.click();
        TimeUnit.SECONDS.sleep(2);

        //Take a snapshot
        String method = testInfo.getDisplayName();
        takeSnapShot(driver, SCREENSHOTS + "\\" + method + "_" + System.currentTimeMillis() + ".png");
        //Check if expected value is displayed
        assertThat(loginHeader.getText(), is("Login"));

        Thread.sleep(2000);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.quit();

    }

    @Test
    @DisplayName("Input_invalid_password_to_test_password_strength_requirements.")
    void invalid_password_test(TestInfo testInfo) throws Exception {

        //Go to the login page
        driver.get("http://localhost:7000/#!/login");
        driver.manage().window().maximize();

        //Locate the Signup header
        WebElement loginHeader = driver.findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/login-form/div/div/h2"));
        TimeUnit.SECONDS.sleep(2);

        //Enter email information
        WebElement emailBox = driver.findElement(By.id("email"));
        emailBox.sendKeys("good@email.com");
        TimeUnit.SECONDS.sleep(2);

        //Enter password information
        WebElement passwordBox = driver.findElement(By.id("pwd"));
        passwordBox.sendKeys("badpassword");
        TimeUnit.SECONDS.sleep(2);

        //Press the submit button
        WebElement loginButton = driver.findElement(By.id("button"));
        loginButton.click();
        TimeUnit.SECONDS.sleep(2);

        //Take a snapshot
        String method = testInfo.getDisplayName();
        takeSnapShot(driver, SCREENSHOTS + "\\" + method + "_" + System.currentTimeMillis() + ".png");
        //Check if expected value is displayed
        assertThat(loginHeader.getText(), is("Login"));

        Thread.sleep(2000);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.quit();
        TimeUnit.SECONDS.sleep(2);
    }
    @Test
    @DisplayName("Input_invalid_email_information")
    void invalid_email_test(TestInfo testInfo) throws Exception {

        //Go to the login page
        driver.get("http://localhost:7000/#!/login");
        driver.manage().window().maximize();

        //Locate the login header
        WebElement loginHeader = driver.findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/login-form/div/div/h2"));
        TimeUnit.SECONDS.sleep(2);

        //Enter email information
        WebElement emailBox = driver.findElement(By.id("email"));
        emailBox.sendKeys("bademail.com");
        TimeUnit.SECONDS.sleep(2);

        //Enter password information
        WebElement passwordBox = driver.findElement(By.id("pwd"));
        passwordBox.sendKeys("GoodPa$$word123");
        TimeUnit.SECONDS.sleep(2);

        //Press the login button
        WebElement loginButton = driver.findElement(By.id("button"));
        loginButton.click();
        TimeUnit.SECONDS.sleep(2);

        //Take a snapshot
        String method = testInfo.getDisplayName();
        takeSnapShot(driver, SCREENSHOTS + "\\" + method + "_" + System.currentTimeMillis() + ".png");
        //Check if expected value is displayed
        assertThat(loginHeader.getText(), is("Login"));

        Thread.sleep(2000);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.quit();
        TimeUnit.SECONDS.sleep(2);
    }

}
