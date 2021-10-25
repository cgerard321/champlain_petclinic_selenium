package com.petclinic.selenium.seleniumtest.visit;

import com.petclinic.selenium.SeleniumLoginTestHelper;
import io.github.bonigarcia.seljup.SeleniumExtension;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SeleniumExtension.class)
public class UpdateVisitSeleniumTest {

    WebDriver driver;
    SeleniumLoginTestHelper helper;
    private final String SCREENSHOTS = "./src/test/onDemandVisitServiceScreenshots/update";
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH`mm`ss.SSS");
    private final String UPDATED_DESCRIPTION = "Updated Description";
    private final String UPDATED_VET = "Sharon Jenkins";
    private final String UPDATED_DATE = "2021-10-29";

    public UpdateVisitSeleniumTest(FirefoxDriver driver) {
        this.driver = driver;

        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
        System.setProperty("sel.jup.screenshot.at.the.end.of.tests", "whenfailure");
        System.setProperty("sel.jup.screenshot.format", "png");
        System.setProperty("sel.jup.output.folder", "./src/test/failureScreenshots");
    }

    public static void takeSnapShot(WebDriver webDriver, String fileWithPath) throws Exception {
        TakesScreenshot scrShot = ((TakesScreenshot)webDriver);
        File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
        File DestFile = new File(fileWithPath);
        FileUtils.copyFile(SrcFile, DestFile);
    }

    @BeforeEach
    void setupLogin() throws Exception {
        this.helper = new SeleniumLoginTestHelper("LoginTestHelper", driver);
        helper.loginTest();
    }

    @Test
    @DisplayName("trying_to_update_a_visit")
    void test_update_visit(TestInfo testInfo) throws Exception {

        WebDriverWait wait = new WebDriverWait(driver,10);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("navbarDropdown")));

        //navigate to owners table
        driver.findElement(By.id("navbarDropdown")).click();
        driver.findElement(By.xpath("//a[@href='#!/owners']")).click();

        //waits until Owners Row for Jean Coleman is visible, clicks it
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='#!/owners/details/6']")));
        driver.findElement(By.xpath("//a[@href='#!/owners/details/6']")).click();

        //waits until the button to add a visit for the pet is visible, then clicks it
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='#!/owners/6/pets/7/visits']")));
        driver.findElement(By.xpath("//a[@href='#!/owners/6/pets/7/visits']")).click();

        //waits for the first edit button to be visible and then scrolls down to it and clicks it
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("/html/body/div/div/div/ui-view/visits/table[1]/tbody/tr[1]/td[6]/button")));
        WebElement edit_button = driver
                .findElement(By.xpath("/html/body/div/div/div/ui-view/visits/table[1]/tbody/tr[1]/td[6]/button"));
        js.executeScript("arguments[0].scrollIntoView();", edit_button);
        WebElement visit_date = driver.findElement(By
                .xpath("/html/body/div/div/div/ui-view/visits/table[1]/tbody/tr[1]/td[1]"));
        WebElement visit_description = driver.findElement(By
                .xpath("/html/body/div/div/div/ui-view/visits/table[1]/tbody/tr[1]/td[2]"));
        WebElement visit_vet = driver.findElement(By
                .xpath("/html/body/div/div/div/ui-view/visits/table[1]/tbody/tr[1]/td[3]"));
        edit_button.click();

        // Wait until Update Visit button appears, get the values buttons in the form
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Update Visit']")));
        WebElement update_button = driver.findElement(By.id("submit_button"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cancel_button")));
        WebElement cancel_button = driver.findElement(By.id("cancel_button"));
        Select form_vet = new Select(driver.findElement(By.id("selectedVet")));
        WebElement form_date = driver.findElement(By.id("date_input"));
        WebElement form_description = driver.findElement(By.id("description_textarea"));

        // Assert that the values in the form correspond to the values of the visit that was clicked
        assertEquals(visit_vet.getText(), form_vet.getFirstSelectedOption().getText());
        assertEquals(visit_date.getText(), form_date.getAttribute("value"));
        assertEquals(visit_description.getText(), form_description.getAttribute("value"));

        takeSnapShot(driver, SCREENSHOTS + "\\" + testInfo.getDisplayName() + "_switch_to_update_form_"
                + sdf.format(new Date(System.currentTimeMillis())) + ".png");

        System.out.println(sdf.format(new Date(System.currentTimeMillis())));

        cancel_button.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Add New Visit']")));
        WebElement add_button = driver.findElement(By.id("submit_button"));

        assertEquals("Please Select A Veterinarian", form_vet.getFirstSelectedOption().getText());
        assertEquals("", form_date.getAttribute("value"));
        assertEquals("", form_description.getAttribute("value"));

        takeSnapShot(driver, SCREENSHOTS + "\\" + testInfo.getDisplayName() + "_switch_to_add_form_after_cancel" +
                sdf.format(new Date(System.currentTimeMillis())) + ".png");

        js.executeScript("arguments[0].scrollIntoView();", edit_button);
        edit_button.click();

        form_vet.selectByVisibleText(UPDATED_VET);
        form_date.sendKeys(UPDATED_DATE);
        form_description.sendKeys(UPDATED_DESCRIPTION);
        update_button.click();

        //waits until the confirm button for the confirmation is visible and clicks it
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#confirmationModalConfirmButton")));
        driver.findElement(By.cssSelector("#confirmationModalConfirmButton")).click();

        // Assert that the alert says the right thing
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert")));
        WebElement success_alert = driver.findElement(By.className("alert"));
        assertEquals("Successfully updated visit!", success_alert.getText());

        //takes a screenshot with the success notification
        takeSnapShot(driver, SCREENSHOTS + "\\" + testInfo.getDisplayName() + "_success_notification_" +
                sdf.format(new Date(System.currentTimeMillis())) + ".png");
    }


}
