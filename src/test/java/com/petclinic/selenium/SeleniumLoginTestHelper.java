package com.petclinic.selenium;

import io.github.bonigarcia.seljup.SeleniumExtension;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

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

    public WebDriver loginTest() throws Exception {

        //Go to the login page
        driver.get("http://localhost:8080/#!/login");
        driver.manage().window().maximize();

        //Locate the login header
        WebElement loginHeader = driver.findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/login-form/div/div/h2"));
        TimeUnit.SECONDS.sleep(2);

        //Enter email information
        WebElement emailBox = driver.findElement(By.id("email"));
        emailBox.sendKeys(username);
        TimeUnit.SECONDS.sleep(2);

        //Enter password information
        WebElement passwordBox = driver.findElement(By.id("pwd"));
        passwordBox.sendKeys(password);
        TimeUnit.SECONDS.sleep(2);

        //Press the login button
        WebElement loginButton = driver.findElement(By.id("button"));
        loginButton.click();
        TimeUnit.SECONDS.sleep(2);

        Thread.sleep(2000);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TimeUnit.SECONDS.sleep(2);
        return driver;
    }
    public WebDriver getWebDriver(){
        return driver;
    }
    public void setWebDriver(WebDriver driver){
        this.driver = driver;
    }
}
