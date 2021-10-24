package com.petclinic.selenium.seleniumtest.cust;

import io.github.bonigarcia.seljup.SeleniumExtension;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Alert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;

@ExtendWith(SeleniumExtension.class)
public class CustSeleniumExceptionsTest {

    ChromeDriver driver;
    private final String SCREENSHOTS = "./src/test/CustSeleniumExceptionsScreenshots";

    public CustSeleniumExceptionsTest(ChromeDriver driver){
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

    @Test
    public void test_Invalid_Input_Exception_Alert(){

        driver.get("http://localhost:8080/owners/nine/");
        driver.manage().window().maximize();

        Alert alert = driver.switchTo().alert();

        String alertMessage = driver.switchTo().alert().getText();

        alert.accept();

        driver.quit();
    }

    @Test
    public void test_Not_Found_Exception_Alert(){

        driver.get("http://localhost:8080/owners/9999/");
        driver.manage().window().maximize();

        Alert alert = driver.switchTo().alert();

        String alertMessage = driver.switchTo().alert().getText();

        alert.accept();

        driver.quit();
    }

}
