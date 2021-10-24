package com.petclinic.selenium.seleniumtest.visit;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.*;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DeleteVisitSeleniumTest {

    OperaDriver driver;
    private final String SCREENSHOTS = "./src/test/onDemandScreenshots";

    public DeleteVisitSeleniumTest(OperaDriver driver) {
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
    @DisplayName("test_delete_a_visit")
    void test_delete_a_visit(TestInfo testInfo) throws Exception {

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
        WebElement driverElement = driver.findElement(By.xpath("//a[@href='#!/owners']"));
        driverElement.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Betty Davis")));
        driverElement = driver.findElement(By.linkText("Betty Davis"));
        driverElement.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Add Visit")));
        driverElement = driver.findElement(By.linkText("Add Visit"));
        driverElement.click();

        TimeUnit.SECONDS.sleep(2);


        //Scrolling in order to find the delete button on the screen
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,650)", "");
        TimeUnit.SECONDS.sleep(1);


        //specify the position of the delete button in the row
        if ( driver.getPageSource().contains("Not Canceled")){
            System.out.println("It works!!!");
        }
        WebElement deleteButton = driver.findElement(By.xpath("//table/tbody/tr/td[7]"));
        deleteButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("confirmationModalConfirmButton")));
        driver.findElement(By.id("confirmationModalConfirmButton")).click();


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();
    }

    @Test
    @DisplayName("test_create_a_visit_then_delete_the_one_created")
    void test_create_a_visit_then_delete_the_one_created(TestInfo testInfo) throws Exception {

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
        WebElement driverElement = driver.findElement(By.xpath("//a[@href='#!/owners']"));
        driverElement.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Betty Davis")));
        driverElement = driver.findElement(By.linkText("Betty Davis"));
        driverElement.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Add Visit")));
        driverElement = driver.findElement(By.linkText("Add Visit"));
        driverElement.click();


        //Create a visit
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"selectedVet\"]/option[1]")));
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


        driver.findElement(By.xpath("//*[@id=\'description_textarea\']")).sendKeys("TestVisitDescription");
        driver.findElement(By.id("submit_button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("confirmationModalConfirmButton")));
        driver.findElement(By.id("confirmationModalConfirmButton")).click();
        TimeUnit.SECONDS.sleep(2);//to let time to the application to create the visit


        //delete the visit created
        if ( driver.getPageSource().contains("Not Canceled")){
            System.out.println("It works!!!");
        }
        WebElement deleteButton = driver.findElement(By.xpath("//table/tbody/tr/td[7]"));
        deleteButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("confirmationModalConfirmButton")));
        driver.findElement(By.id("confirmationModalConfirmButton")).click();

        TimeUnit.SECONDS.sleep(4);
    }
}
