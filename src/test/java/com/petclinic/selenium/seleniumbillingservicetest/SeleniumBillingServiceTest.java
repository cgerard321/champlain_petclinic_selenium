package com.petclinic.selenium.seleniumbillingservicetest;

import io.github.bonigarcia.seljup.SeleniumExtension;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SeleniumExtension.class)
public class SeleniumBillingServiceTest {
    ChromeDriver driver;
    private final String SCREENSHOTS = "./src/test/onDemandBillServiceScreenshots";

    public SeleniumBillingServiceTest(ChromeDriver driver) {
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
    @DisplayName("test_petclinic")
    public void takeBillingServiceSnapshot(TestInfo testInfo) throws Exception {
        driver.get("http://localhost:8080/#!/bills");
        driver.manage().window().maximize();

        WebElement billHistoryHeader = driver.findElement(By.className("titleOwner"));

        String method = testInfo.getDisplayName();
        takeSnapShot(driver, SCREENSHOTS + "\\" + method + "_" + System.currentTimeMillis() + ".png");

        assertThat(billHistoryHeader.getText(), is("Bill History"));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.quit();
    }

    @Test
    @DisplayName("test_petclinic")
    public void takeBillingServiceHistoryPageSnapshot(TestInfo testInfo) throws Exception {
        Actions act = new Actions(driver);

        driver.get("http://localhost:8080/#!/login");
        driver.manage().window().maximize();

        WebElement loginLabel = driver.findElement(By.id("email"));
        WebElement passLabel = driver.findElement(By.id("pwd"));
        WebElement loginButton = driver.findElement(By.id("button"));

        loginLabel.sendKeys("admin");
        passLabel.sendKeys("admin");
        act.doubleClick(loginButton).perform();

        WebElement billsTab = driver.findElement(By.id("navbarDropdown1"));
        billsTab.click();

        driver.quit();
    }
}
