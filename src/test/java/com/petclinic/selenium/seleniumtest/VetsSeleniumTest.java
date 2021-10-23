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
public class VetsSeleniumTest{
    ChromeDriver driver;
    private final String SCREENSHOTS = "./src/test/onDemandScreenshots";

    public VetsSeleniumTest(ChromeDriver driver) {
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
    @DisplayName("test_filter-vets-all")
    void test_filter_vets_all() throws Exception {
        driver.get("http://localhost:8080");
        driver.manage().window().maximize();

        driver.findElement(By.linkText("Veterinarians")).click();

        WebElement rows = driver.findElement(By.xpath("//table[@class='table table-striped']"));
        List<WebElement> TotalRowsList = rows.findElements(By.tagName("tr"));

        assertThat(TotalRowsList.size(), is(7));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();
    }

    @Test
    @DisplayName("test_filter-vets-available")
    void test_filter_vets_available() throws Exception {
        driver.get("http://localhost:8080");
        driver.manage().window().maximize();

        driver.findElement(By.linkText("Veterinarians")).click();

        Select dropdown = new Select(driver.findElement(By.id("filterOption")));
        dropdown.selectByValue("Available");

        WebElement td1 = driver.findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/vet-list/table/tbody/tr[1]/td[2]"));
        assertThat(td1.getText(), is("James Carter"));

        WebElement td2 = driver.findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/vet-list/table/tbody/tr[2]/td[2]"));
        assertThat(td2.getText(), is("Helen Leary"));

        WebElement td3 = driver.findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/vet-list/table/tbody/tr[3]/td[2]"));
        assertThat(td3.getText(), is("Linda Douglas"));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();
    }

    @Test
    @DisplayName("test_filter-vets-unavailable")
    void unavailable() throws Exception {
        driver.get("http://localhost:8080");
        driver.manage().window().maximize();

        driver.findElement(By.linkText("Veterinarians")).click();

        Select dropdown = new Select(driver.findElement(By.id("filterOption")));
        dropdown.selectByValue("Unavailable");

        WebElement td1 = driver.findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/vet-list/table/tbody/tr[1]/td[2]"));
        assertThat(td1.getText(), is("Rafael Ortega"));

        WebElement td2 = driver.findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/vet-list/table/tbody/tr[2]/td[2]"));
        assertThat(td2.getText(), is("Henry Stevens"));

        WebElement td3 = driver.findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/vet-list/table/tbody/tr[3]/td[2]"));
        assertThat(td3.getText(), is("Sharon Jenkins"));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();
    }

    @Test
    @DisplayName("test_detail_vet")
    void test_detail_vet() throws Exception {
        driver.manage().window().maximize();
        driver.findElement(By.linkText("Veterinarians")).click();
        driver.findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/vet-list/table/tbody/tr[1]/td[2]")).click();

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

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();
    }
}
