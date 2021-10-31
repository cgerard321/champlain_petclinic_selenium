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
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SeleniumExtension.class)
public class CustPetDeleteSeleniumTest {

    ChromeDriver chromeDriver;
    SeleniumLoginTestHelper loginHelper;
    private final String SCREENSHOTS = "./src/test/onDemandCustServiceScreenshots/PetDelete";

    public CustPetDeleteSeleniumTest(ChromeDriver driver) {
        chromeDriver = driver;

        DesiredCapabilities descap = new DesiredCapabilities();
        descap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
        System.setProperty("sel.jup.screenshot.at.the.end.of.tests", "whenfailure");
        System.setProperty("sel.jup.screenshot.format", "png");
        System.setProperty("sel.jup.output.folder", "./src/test/onFailureCustServiceScreenshots");
    }


    public static void takeSnapShot(WebDriver webDriver, String fileWithPath) throws Exception {
        //Convert web driver object to TakeScreenshot
        TakesScreenshot scrShot = ((TakesScreenshot) webDriver);
        //call get Screenshot method to create actual image file
        File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
        //Move image file to new destination
        File DestFile = new File(fileWithPath);
        //Copy file at destination
        FileUtils.copyFile(SrcFile, DestFile);
    }

    @BeforeEach
    public void setup() throws Exception {
        this.loginHelper = new SeleniumLoginTestHelper("Cust", chromeDriver);
        loginHelper.loginTest();
    }

    @DisplayName("Test_Cust_DeletePet")
    @Test
    public void testCustDeletePet(TestInfo testInfo) throws Exception {
            chromeDriver.get("http://localhost:8080/#!/owners");
            chromeDriver.manage().window().maximize();
            String method = testInfo.getDisplayName();
            Thread.sleep(5000);

            WebDriverWait webDriverWait = new WebDriverWait(chromeDriver, 10);
            webDriverWait.until(ExpectedConditions.urlToBe("http://localhost:8080/#!/welcome"));
            loginHelper.getDriver().get("http://localhost:8080/#!/owners");
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href" +
                    "='#!/owners/details/10']")));
            takeSnapShot(chromeDriver,
                    SCREENSHOTS + "\\" + method + "_" + System.currentTimeMillis() + ".png");
            Thread.sleep(5000);
            loginHelper.getDriver().findElement(By.xpath("//a[@href='#!/owners/details/10']")).click();
            Thread.sleep(5000);
            webDriverWait.until(ExpectedConditions.urlToBe("http://localhost:8080/#!/owners" +
                    "/details/10"));
            Thread.sleep(5000);
            WebElement deleteButton = chromeDriver.findElement(By.xpath("//*[contains(text()," +
                    "'Delete " +
                    "Pet')]"));
            deleteButton.click();
            webDriverWait.until(ExpectedConditions.urlToBe("http://localhost:8080/#!/owners/10" +
                    "/pets/13/delete"));
            Thread.sleep(5000);
            WebElement submitButton = chromeDriver.findElement(By.xpath("//*[contains(text()," +
                    "'Submit')]"));
            submitButton.click();
            Thread.sleep(5000);

            chromeDriver.navigate().back();
            assertNull(chromeDriver.findElement(By.xpath("//a[@href='#!/owners/10/pets/13/delete" +
                    "]")));

            takeSnapShot(chromeDriver,
                SCREENSHOTS + "\\" + method + "_" + System.currentTimeMillis() + ".png");

            chromeDriver.quit();
    }
}

