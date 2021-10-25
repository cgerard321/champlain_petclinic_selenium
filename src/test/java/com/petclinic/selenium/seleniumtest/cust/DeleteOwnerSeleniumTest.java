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

import java.io.File;

@ExtendWith(SeleniumExtension.class)
public class DeleteOwnerSeleniumTest {

    ChromeDriver driver;

    //Use to fill the login form
    SeleniumLoginTestHelper helper;

    private final String SCREENSHOTS = "./src/test/onDemandCustomerServiceScreenshots";

    public DeleteOwnerSeleniumTest(ChromeDriver driver){
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

        driver.get("http://localhost:8080/owners/9");
        driver.manage().window().maximize();

        WebElement deleteButton = driver.findElement(By.xpath("//input[@name='']"));
        deleteButton.click();


        String method = testInfo.getDisplayName();
        takeSnapshot(driver, SCREENSHOTS + "\\" + method + "_" + System.currentTimeMillis() + ".png");

        driver.quit();
    }



}
