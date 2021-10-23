package com.petclinic.selenium;

import io.github.bonigarcia.seljup.SeleniumExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * User: @SmoothWin
 * Date: 2021-10-22
 * Ticket: test(VETS-CPC-499): Login Test Helper for Selenium
 * This is an example test demonstrating how we should implement the SeleniumLoginTestHelper class
 * inside each of our End-to-End tests (Selenium tests)
 */
@ExtendWith(SeleniumExtension.class)
public class SeleniumLoginTestHelperTest {
    ChromeDriver driver; //This can be any driver you want :)
    SeleniumLoginTestHelper helper; //You will need this SeleniumLoginTestHelper field
    public SeleniumLoginTestHelperTest(ChromeDriver driver) {
        this.driver = driver;

        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
        System.setProperty("sel.jup.screenshot.at.the.end.of.tests", "whenfailure");
        System.setProperty("sel.jup.screenshot.format", "png");
        System.setProperty("sel.jup.output.folder", "./src/test/onFailureScreenshots/Log");
    }
    @BeforeEach
    void setupLogin() throws Exception {
        this.helper = new SeleniumLoginTestHelper("LoginTestHelper", driver);
        helper.loginTest();
    }
    @Test
    @DisplayName("Selenium Login Test Helper Test")
    public void seleniumLoginTestHelperTest() throws Exception{
        helper.getDriver().quit(); //This has to be done in order to close the window derived from
                                    // the SeleniumLoginTestHelper class instantiation
    }
}
