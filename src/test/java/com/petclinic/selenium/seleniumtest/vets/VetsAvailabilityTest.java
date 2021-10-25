package com.petclinic.selenium.seleniumtest.vets;

import io.github.bonigarcia.seljup.SeleniumExtension;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;


import java.util.concurrent.TimeUnit;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(SeleniumExtension.class)
public class VetsAvailabilityTest {
    ChromeDriver driver;
    private final String SCREENSHOTS = "./src/test/onDemandScreenshots";

    public VetsAvailabilityTest(ChromeDriver driver) {
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

    void Login() {
        try {
            driver.get("http://localhost:8080");
            driver.manage().window().maximize();

            driver.findElement(By.xpath("//*[@id=\"email\"]")).sendKeys("admin");
            driver.findElement(By.xpath("//*[@id=\"pwd\"]")).sendKeys("admin");
            driver.findElement(By.xpath("//*[@id=\"button\"]")).click();
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void test_vet_available_days() throws Exception {
        Login();

        try {
            driver.findElement(By.linkText("Veterinarians")).click();
            TimeUnit.SECONDS.sleep(3);
            driver.findElement(By.linkText("Helen Leary")).click();
            TimeUnit.SECONDS.sleep(2);
            String workDays = driver.findElement(By.xpath("//*[@id=\"workDays\"]")).getText();

            String[] arrWorkDays = workDays.split(", ");
            String[] xPaths = new String[arrWorkDays.length];
            for(int i = 0; i<arrWorkDays.length; i++) {
                xPaths[i] = String.format("//*[@id=\"%s\"]",arrWorkDays[i]);
            }


            JavascriptExecutor js = ((JavascriptExecutor) driver);
            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
            TimeUnit.SECONDS.sleep(3);
            driver.findElement(By.xpath("//*[@id=\"toggle\"]")).click();

            TimeUnit.SECONDS.sleep(1);

            String[] daysColor = new String[arrWorkDays.length];
            for(int i = 0; i<daysColor.length; i++) {
                daysColor[i] = driver.findElement(By.xpath(xPaths[i])).getCssValue("background-color");
            }

            for (String s : daysColor) {
                assertThat(s, is("rgba(199, 255, 220, 1)"));
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
