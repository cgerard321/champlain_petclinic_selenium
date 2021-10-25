package com.petclinic.selenium.seleniumtest.cust;

import com.petclinic.selenium.SeleniumLoginTestHelper;
import io.github.bonigarcia.seljup.SeleniumExtension;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;

/**
 * @Author Louis-P. Simard
 * Date: October 12, 2021
 * Ticket: feat(CUST-CPC-420): Implement Selenium Test for deleteOwner
 * Selenium Test for deleteOwner. Pretty simple.
 */

@ExtendWith(SeleniumExtension.class)
public class CustDeleteOwnerSeleniumTest {

    ChromeDriver driver;

    //Use to fill the login form
    SeleniumLoginTestHelper helper;

    private final String SCREENSHOTS = "./src/test/onDemandCustServiceScreenshots/DeleteOwner";

    public CustDeleteOwnerSeleniumTest(ChromeDriver driver){
        this.driver = driver;
    }

    public static void takeSnapshot(WebDriver webDriver, String fileWithPath) throws Exception{

        //Convert web driver object to take screenshot
        TakesScreenshot scrShot = ((TakesScreenshot) webDriver);
        //Call the getScreenshotAs method to create the actual file
        File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
        //Move the image file to new destination
        File DestFile = new File(fileWithPath);
        //Copy file at destination
        FileUtils.copyFile(SrcFile, DestFile);
    }

    @BeforeEach
    public void setup() throws Exception {
        this.helper = new SeleniumLoginTestHelper("Cust", driver);
        helper.loginTest();
    }

    @DisplayName("selenium_deleteOwner")
    @Test
    public void test_DeleteOwner_Selenium(TestInfo testInfo) throws Exception{

        driver.get("http://localhost:8080/#!/owners");
        driver.manage().window().maximize();
        String method = testInfo.getDisplayName();
        Thread.sleep(2000);

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.urlToBe("http://localhost:8080/#!/welcome"));
        helper.getDriver().get("http://localhost:8080/#!/owners");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='#!/owners/details/5']")));
        takeSnapshot(driver, SCREENSHOTS + "\\" + method + "_" + System.currentTimeMillis() + ".png");
        Thread.sleep(2000);
        helper.getDriver().findElement(By.xpath("//a[@href='#!/owners/details/5']")).click();
        takeSnapshot(driver, SCREENSHOTS + "\\" + method + "_" + System.currentTimeMillis() + ".png");
        Thread.sleep(2000);
        wait.until(ExpectedConditions.urlToBe("http://localhost:8080/#!/owners/details/5"));
        Thread.sleep(5000);
        WebElement deleteButton = driver.findElement(By.xpath("//*[contains(text(),'Delete Owner')]"));
        deleteButton.click();
        wait.until(ExpectedConditions.urlToBe("http://localhost:8080/#!/owners/5/delete"));
        Thread.sleep(2000);
        WebElement submitButton = driver.findElement(By.xpath("//*[contains(text(),'Submit')]"));
        submitButton.click();
        Thread.sleep(5000);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//a[@href='#!/owners/details/5']")));
        takeSnapshot(driver, SCREENSHOTS + "\\" + method + "_" + System.currentTimeMillis() + ".png");

        driver.quit();
    }



}
