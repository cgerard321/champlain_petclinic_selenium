package com.petclinic.selenium.seleniumtest;

import io.github.bonigarcia.seljup.SeleniumExtension;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;



@ExtendWith(SeleniumExtension.class)
public class VISE_SeleniumTests {
    ChromeDriver driver;
    private final String SCREENSHOTS = "./src/test/onDemandScreenshots";

    public VISE_SeleniumTests(ChromeDriver driver) {
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
    @DisplayName("test_create_a_visit")
    void test_create_a_visit(TestInfo testInfo) throws Exception {
        driver.get("http://localhost:8080");
        driver.manage().window().maximize();

      driver.findElement(By.linkText("Owners")).click();
        //navigation to visits page
        WebElement a1 = driver.findElement(By.xpath("//*[@id=\"navbarSupportedContent\"]/ul[1]/li[4]/div/a[1]"));
        a1.click();
        Thread.sleep(2000); // VERY VERY VERY NECESSARY PART OF THE CODE. Without the line it will try to click on something that doesn't exist yet.
        a1 = driver.findElement(By.linkText("Betty Davis"));
        a1.click();
        Thread.sleep(2000);
        a1 = driver.findElement(By.linkText("Add Visit"));
        a1.click();



        //getting a vet
        driver.findElement(By.id("selectedVet")).click();
        Thread.sleep(2000);


        //Selecting the vet : "Helen"
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"selectedVet\"]/option[1]")).click();
        Thread.sleep(2000);
        System.out.println(driver.findElement(By.xpath("//*[@id=\"selectedVet\"]/option[3]")).getText() +"asdwad");
        driver.findElement(By.xpath("//*[@id=\"selectedVet\"]/option[3]")).click();

        //typing TestVisitDescription inside of the text box labelled "description"
        driver.findElement(By.xpath("//*[@id=\'description_textarea\']")).sendKeys("TestVisitDescription");


        driver.findElement(By.id("submit_button")).click();

        // getting back to the betty davis customer
        // CHANGE IN THE FUTURE
        Thread.sleep(2000);
        a1 = driver.findElement(By.linkText("Betty Davis"));
        a1.click();
        Thread.sleep(2000);
        a1 = driver.findElement(By.linkText("Add Visit"));
        a1.click();

        WebElement createdTestVisit = driver.findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/visits/table[1]/tbody/tr[3]/td[2]"));

        assertThat(createdTestVisit.getText(), is("TestVisitDescription") );


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();
    }

}
