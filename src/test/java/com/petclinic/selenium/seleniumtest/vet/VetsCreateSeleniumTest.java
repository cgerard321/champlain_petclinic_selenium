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
/*
This test was prepared by Shariful Islam
It's testing UI Creation of a VET
IMPORTANT NOTICE: This test only works once as soon as the system is up as it tests assuming the database is starting fresh.
 */
@ExtendWith(SeleniumExtension.class)
public class VetsCreateSeleniumTest {
    ChromeDriver driver;
    private final String SCREENSHOTS = "./src/test/onDemandScreenshots";

    public VetsCreateSeleniumTest(ChromeDriver driver) {
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
    @DisplayName("Testing the creation of a vet and Verifying")
    void test_create_vet() throws Exception {

        driver.get("http://localhost:8080");
        driver.manage().window().maximize();

        Login();

        driver.findElement(By.linkText("Veterinarians")).click();
        driver.findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/vet-list/a/button")).click();

        driver.findElement(By.xpath("//*[@id=\"firstName\"]")).sendKeys("New");
        driver.findElement(By.xpath("//*[@id=\"lastName\"]")).sendKeys("User");
        driver.findElement(By.xpath("//*[@id=\"email\"]")).sendKeys("new@user.com");
        driver.findElement(By.xpath("//*[@id=\"phoneNumber\"]")).sendKeys("6543");
        driver.findElement(By.xpath("//*[@id=\"vetResume\"]")).sendKeys("New User Resume");
        driver.findElement(By.xpath("//*[@id=\"surgery\"]")).sendKeys(Keys.SPACE);
        driver.findElement(By.xpath("//*[@id=\"workDays\"]")).sendKeys("Monday, Tuesday");
        driver.findElement(By.xpath("//*[@id=\"vetForm\"]/div/div[10]/div[1]/label/input")).sendKeys(Keys.SPACE);
        driver.findElement(By.xpath("//*[@id=\"vetForm\"]/div/button")).sendKeys(Keys.SPACE);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement previewName = driver.findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/vet-list/table/tbody/tr[7]/td[2]/a/span"));
        assertThat(previewName.getText(), is("New User"));

        WebElement previewEmail = driver.findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/vet-list/table/tbody/tr[7]/td[3]/span"));
        assertThat(previewEmail.getText(), is("(514)-634-8276 #6543"));

        WebElement previewPhone = driver.findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/vet-list/table/tbody/tr[7]/td[4]/span"));
        assertThat(previewPhone.getText(), is("new@user.com"));

        WebElement previewSpecialty = driver.findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/vet-list/table/tbody/tr[7]/td[5]/span"));
        assertThat(previewSpecialty.getText(), is("surgery"));


        driver.findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/vet-list/table/tbody/tr[7]/td[2]/a")).click();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement name = driver.findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/vet-details/div/div[2]/div/div[2]/div[1]"));
        assertThat(name.getText(), is("New User"));

        WebElement email = driver.findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/vet-details/div/div[2]/div/div[2]/div[2]"));
        assertThat(email.getText(), is("new@user.com"));

        WebElement phone = driver.findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/vet-details/div/div[2]/div/div[2]/div[3]"));
        assertThat(phone.getText(), is("(514)-634-8276 #6543"));

        WebElement speciality = driver.findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/vet-details/div/div[2]/div/div[2]/div[4]/div/span"));
        assertThat(speciality.getText(), is("surgery"));

        WebElement resume = driver.findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/vet-details/div/div[2]/div/div[2]/div[5]/div"));
        assertThat(resume.getText(), is("New User Resume"));

        WebElement workday = driver.findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/vet-details/div/div[2]/div/div[2]/div[6]/div/div"));
        assertThat(workday.getText(), is("Monday, Tuesday"));

        WebElement button = driver.findElement(By.xpath("//*[@id=\"toggle\"]"));
        assertThat(button.getText(), is("Show availabilities"));

        WebElement editButton = driver.findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/vet-details/div/div[2]/div/div[2]/div[8]/div/a/button"));
        assertThat(editButton.getText(), is("Edit Vet"));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();
    }
}