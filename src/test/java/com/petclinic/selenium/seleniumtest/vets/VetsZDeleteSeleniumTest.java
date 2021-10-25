package com.petclinic.selenium.seleniumtest.vets;


import com.petclinic.selenium.SeleniumLoginTestHelper;
import io.github.bonigarcia.seljup.SeleniumExtension;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SeleniumExtension.class)
public class VetsZDeleteSeleniumTest {
    private final String SCREENSHOTS = "./src/test/screenshots/vet_update";
    ChromeDriver driver;
    SeleniumLoginTestHelper helper;

    public VetsZDeleteSeleniumTest(ChromeDriver driver) {
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
        //call get Screenshot method to create actual image file
        File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
        //Move image file to new destination
        File DestFile = new File(fileWithPath);
        //Copy file at destination
        FileUtils.copyFile(SrcFile, DestFile);
    }

    @BeforeEach
    public void setup() throws Exception {
        this.helper = new SeleniumLoginTestHelper("Vets", driver);
        helper.loginTest();
    }
    @Test
    @DisplayName("test-delete-vet-pop-up-cancel")
    void test_delete_vet_pop_up_cancel() throws Exception {
        driver.get("http://localhost:8080");
        driver.manage().window().maximize();

        driver.findElement(By.linkText("Veterinarians")).click();
        //to allow for load time of data
        Thread.sleep(2000);
        WebElement deleteButton = driver.findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/vet-list/table/tbody/tr[1]/td[7]/a"));
        deleteButton.click();
        Thread.sleep(3000);

        Alert alert = driver.switchTo().alert();
        alert.dismiss();

        WebElement td1 = driver.findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/vet-list/table/tbody/tr[1]/td[2]"));
        assertThat(td1.getText(), is("James Carter"));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();
    }

    @Test
    @DisplayName("test-delete-vet-pop-up-ok")
    void test_delete_vet_pop_up_ok() throws Exception {
        driver.get("http://localhost:8080");
        driver.manage().window().maximize();

        driver.findElement(By.linkText("Veterinarians")).click();
        //to allow for load time of data
        Thread.sleep(2000);
        WebElement deleteButton = driver.findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/vet-list/table/tbody/tr[1]/td[7]/a"));
        deleteButton.click();
        Thread.sleep(5000);

        Alert alert = driver.switchTo().alert();
        assertThat(alert.getText(), is("Want to delete vet with vetId:234568. Are you sure?"));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();
    }

    @Test
    @DisplayName("test-delete-first-vet-from-view-refresh")
    void test_delete_vet_from_view() throws Exception {
        driver.get("http://localhost:8080");
        driver.manage().window().maximize();

        driver.findElement(By.linkText("Veterinarians")).click();
        //to allow for load time of data
        Thread.sleep(1000);
        WebElement deleteButton = driver.findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/vet-list/table/tbody/tr[1]/td[7]/a"));
        deleteButton.click();
        Thread.sleep(1000);

        Alert alert = driver.switchTo().alert();
        assertThat(alert.getText(), is("Want to delete vet with vetId:234568. Are you sure?"));
        alert.accept();
        WebElement td2 = driver.findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/vet-list/table/tbody/tr[1]/td[2]/a"));
        assertThat(td2.getText(), is("Helen Leary"));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();
    }


}