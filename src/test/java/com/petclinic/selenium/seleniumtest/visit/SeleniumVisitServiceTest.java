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
public class SeleniumVisitServiceTest {

    WebDriver driver;
    SeleniumLoginTestHelper helper;

    public SeleniumVisitServiceTest(FirefoxDriver driver) {
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
    @DisplayName("Trying to Cancel a Visit")
    void test_cancel_visit(TestInfo testInfo) throws Exception {

        WebDriverWait wait = new WebDriverWait(driver,10);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        driver.findElement(By.id("navbarDropdown")).click();
        driver.findElement(By.xpath("//a[@href='#!/owners']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Jean Coleman")));
        driver.findElement(By.linkText("Jean Coleman")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='#!/owners/6/pets/7/visits']")));
        driver.findElement(By.xpath("//a[@href='#!/owners/6/pets/7/visits']")).click();

        WebElement cancel_button = driver.findElement(By.className("cancel\\x2\\dbutton"));
        js.executeScript("arguments[0].scrollIntoView();", cancel_button);

        cancel_button.click();

        TimeUnit.SECONDS.sleep(2);
    }
}
