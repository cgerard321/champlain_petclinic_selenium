package com.petclinic.selenium.seleniumtest.vet;

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
public class VetsDetailSeleniumTest {
    ChromeDriver driver;
    private final String SCREENSHOTS = "./src/test/onDemandScreenshots";

    public VetsDetailSeleniumTest(ChromeDriver driver) {
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

    void Login(){
        driver.get("http://localhost:8080");
        driver.manage().window().maximize();

        driver.findElement(By.xpath("//*[@id=\"email\"]")).sendKeys("admin");
        driver.findElement(By.xpath("//*[@id=\"pwd\"]")).sendKeys("admin");
        driver.findElement(By.xpath("//*[@id=\"button\"]")).click();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("test_detail_vet")
    void test_detail_vet() throws Exception {

        driver.get("http://localhost:8080");
        driver.manage().window().maximize();

        Login();

        driver.findElement(By.linkText("Veterinarians")).click();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/vet-list/table/tbody/tr[1]/td[2]/a")).click();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement name = driver.findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/vet-details/div/div[2]/div/div[2]/div[1]"));
        assertThat(name.getText(), is("James Carter"));

        WebElement email = driver.findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/vet-details/div/div[2]/div/div[2]/div[2]"));
        assertThat(email.getText(), is("carter.james@email.com"));

        WebElement phone = driver.findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/vet-details/div/div[2]/div/div[2]/div[3]"));
        assertThat(phone.getText(), is("(514)-634-8276 #2384"));

        WebElement resume = driver.findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/vet-details/div/div[2]/div/div[2]/div[4]/div"));
        assertThat(resume.getText(), is("Practicing since 3 years"));

        WebElement workday = driver.findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/vet-details/div/div[2]/div/div[2]/div[5]/div/div"));
        assertThat(workday.getText(), is("Monday, Tuesday, Friday"));

        WebElement button = driver.findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/vet-details/div/div[2]/div/div[2]/div[6]/div/button"));
        assertThat(button.getText(), is("Show availabilities"));

        driver.findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/vet-details/div/div[2]/div/div[2]/div[6]/div/button")).click();

        WebElement button2 = driver.findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/vet-details/div/div[2]/div/div[2]/div[6]/div/button"));
        assertThat(button2.getText(), is("Hide availabilities"));

        driver.findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/vet-details/div/div[2]/div/div[2]/div[6]/div/button")).click();

        assertThat(button.getText(), is("Show availabilities"));

        WebElement editButton = driver.findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/vet-details/div/div[2]/div/div[2]/div[7]/div/a"));
        assertThat(editButton.getText(), is("Edit Vet"));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();
    }

}
