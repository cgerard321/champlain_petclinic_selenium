package com.petclinic.selenium;

import io.github.bonigarcia.seljup.SeleniumExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

@ExtendWith(SeleniumExtension.class)
public class SeleniumLoginTestHelperTest {
    ChromeDriver driver;
    public SeleniumLoginTestHelperTest(ChromeDriver driver) {
        this.driver = driver;
        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
        System.setProperty("sel.jup.screenshot.at.the.end.of.tests", "whenfailure");
        System.setProperty("sel.jup.screenshot.format", "png");
        System.setProperty("sel.jup.output.folder", "./src/test/onFailureScreenshots/Log");
    }
    @Test
    @DisplayName("Selenium Login Test Helper Test")
    public void seleniumLoginTestHelperTest() throws Exception{
        SeleniumLoginTestHelper helper = new SeleniumLoginTestHelper("LoginTestHelper", driver);
        helper.loginTest();
        helper.getDriver().quit();
    }
}
