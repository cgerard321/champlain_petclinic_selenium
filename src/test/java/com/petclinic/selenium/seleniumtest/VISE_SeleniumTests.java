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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

        //wait max 5 seconds before a timeout
        WebDriverWait wait=new WebDriverWait(driver, 5);

        //setting up path and window size
        driver.get("http://localhost:8080");
        driver.manage().window().maximize();

        //enter authentication information
        driver.findElement(By.id("email")).sendKeys("admin");
        driver.findElement(By.id("pwd")).sendKeys("admin");
        driver.findElement(By.id("button")).click();


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Owners")));





                //navigation to visits page
        driver.findElement(By.linkText("Owners")).click();

        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"navbarSupportedContent\"]/ul[1]/li[4]/div/a[1]")));
        WebElement a1 = driver.findElement(By.xpath("//*[@id=\"navbarSupportedContent\"]/ul[1]/li[4]/div/a[1]"));
        a1.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Betty Davis"))); //Will crash here if not wait
        a1 = driver.findElement(By.linkText("Betty Davis"));
        a1.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Add Visit")));
        a1 = driver.findElement(By.linkText("Add Visit"));
        a1.click();





             //Selecting the vet : "Helen"

        TimeUnit.SECONDS.sleep(2);//special case because it's a textbox

        driver.findElement(By.xpath("//*[@id=\"selectedVet\"]/option[1]")).click();
        TimeUnit.SECONDS.sleep(2);
        String selectedVet = driver.findElement(By.xpath("//*[@id=\"selectedVet\"]/option[3]")).getText();
        System.out.println(selectedVet +" : vetname");
        assertThat(selectedVet, is("Helen Leary"));


            // Necessary scroll
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,250)", "");

        driver.findElement(By.xpath("//*[@id=\"selectedVet\"]/option[3]")).click();
         TimeUnit.SECONDS.sleep(1);


            //typing TestVisitDescription inside the text box labelled "description"

        driver.findElement(By.xpath("//*[@id=\'description_textarea\']")).sendKeys("TestVisitDescription");



       driver.findElement(By.id("submit_button")).click();
       wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("confirmationModalConfirmButton")));

       driver.findElement(By.id("confirmationModalConfirmButton")).click();

            //For console display purposes
        String testname = "TestVisitDescription";

            // Checks the document if the string is part of it.

        if ( driver.getPageSource().contains("TestVisitDescription")){
            System.out.println("Text: " + testname + " was created successfully. ");
            assertThat( driver.getPageSource().contains("TestVisitDescription"), is(Boolean.TRUE));
        } else {
            System.out.println("Text: " + testname + " was NOT created successfully. ");

        }



        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();
    }

}
