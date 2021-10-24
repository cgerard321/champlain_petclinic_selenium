package com.petclinic.selenium.seleniumtest.visit;

import com.petclinic.selenium.SeleniumLoginTestHelper;
import io.github.bonigarcia.seljup.SeleniumExtension;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.concurrent.TimeUnit;

@ExtendWith(SeleniumExtension.class)
public class CancelVisitSeleniumTest {

    WebDriver driver;
    SeleniumLoginTestHelper helper;
    private final String SCREENSHOTS = "./src/test/onDemandVisitServiceScreenshots";

    public CancelVisitSeleniumTest(FirefoxDriver driver) {
        this.driver = driver;

        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
        System.setProperty("sel.jup.screenshot.at.the.end.of.tests", "whenfailure");
        System.setProperty("sel.jup.screenshot.format", "png");
        System.setProperty("sel.jup.output.folder", "./src/test/failureScreenshots");
    }

    public static void takeSnapShot(WebDriver webDriver, String fileWithPath) throws Exception {
        TakesScreenshot scrShot = ((TakesScreenshot)webDriver);
        File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
        File DestFile = new File(fileWithPath);
        FileUtils.copyFile(SrcFile, DestFile);
    }

    @BeforeEach
    void setupLogin() throws Exception {
        this.helper = new SeleniumLoginTestHelper("LoginTestHelper", driver);
        helper.loginTest();
    }

    @Test
    @DisplayName("trying_to_cancel_a_visit")
    void test_cancel_visit(TestInfo testInfo) throws Exception {

        WebDriverWait wait = new WebDriverWait(driver,10);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        //navigate to owners table
        driver.findElement(By.id("navbarDropdown")).click();
        driver.findElement(By.xpath("//a[@href='#!/owners']")).click();

        //waits until Owners Row with Jean Coleman is visible and clicks it
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Jean Coleman")));
        driver.findElement(By.linkText("Jean Coleman")).click();

        //waits until the button to add a visit for the pet is visible adn then clicks it
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='#!/owners/6/pets/7/visits']")));
        driver.findElement(By.xpath("//a[@href='#!/owners/6/pets/7/visits']")).click();

        //waits for the first cancel button to be visible and then scrolls down to it and clicks it
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("table.table:nth-child(12) > tbody:nth-child(2) > tr:nth-child(1) > td:nth-child(5) > button:nth-child(1)")));
        WebElement cancel_button = driver.findElement(By.cssSelector("table.table:nth-child(12) > tbody:nth-child(2) > tr:nth-child(1) > td:nth-child(5) > button:nth-child(1)"));
        js.executeScript("arguments[0].scrollIntoView();", cancel_button);
        cancel_button.click();

        //waits until the confirm button for the confirmation is visible and clicks it
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#confirmationModalConfirmButton")));
        driver.findElement(By.cssSelector("#confirmationModalConfirmButton")).click();

        //takes a screenshot with the success notifcation
        takeSnapShot(driver, SCREENSHOTS + "\\" + testInfo.getDisplayName() + "_" + System.currentTimeMillis() + ".png");
    }
}
