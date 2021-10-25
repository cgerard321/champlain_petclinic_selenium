package com.petclinic.selenium.seleniumtest.cust;

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

@ExtendWith(SeleniumExtension.class)
public class CustOwnerCreateSeleniumTest {

    ChromeDriver driver;
    SeleniumLoginTestHelper helper;
    private final String SCREENSHOTS = "./src/test/onDemandCustServiceScreenshots/OwnerCreate";

    public CustOwnerCreateSeleniumTest(ChromeDriver driver) {
        this.driver = driver;

        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
        System.setProperty("sel.jup.screenshot.at.the.end.of.tests", "whenfailure");
        System.setProperty("sel.jup.screenshot.format", "png");
        System.setProperty("sel.jup.output.folder", "./src/test/onFailureCustServiceScreenshots");
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
        this.helper = new SeleniumLoginTestHelper("Cust", driver);
        helper.loginTest();
    }

    @Test
    @DisplayName("Test_Cust_CreateOwner")
    public void testCustCreateOwner() throws Exception {
        boolean error = false, error2 = false;

        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.urlToBe("http://localhost:8080/#!/welcome"));
            helper.getDriver().get("http://localhost:8080/#!/owners/new");
            wait.until(ExpectedConditions.urlToBe("http://localhost:8080/#!/owners/new"));

            WebElement inputFirstName = helper.getDriver().findElement(By.xpath("//*[@name=\"firstName\"]"));
            inputFirstName.sendKeys("John");
            WebElement inputLastName = helper.getDriver().findElement(By.xpath("//*[@name=\"lastName\"]"));
            inputLastName.sendKeys("Wick");
            WebElement inputAddress = helper.getDriver().findElement(By.xpath("//*[@name=\"address\"]"));
            inputAddress.sendKeys("21 Jump Street");
            WebElement inputCity = helper.getDriver().findElement(By.xpath("//*[@name=\"city\"]"));
            inputCity.sendKeys("Metropolis");
            WebElement inputPhone = helper.getDriver().findElement(By.xpath("//*[@name=\"telephone\"]"));
            inputPhone.sendKeys("1234567890");

            assertThat(inputFirstName.getAttribute("value"), is("John"));
            assertThat(inputLastName.getAttribute("value"), is("Wick"));
            assertThat(inputAddress.getAttribute("value"), is("21 Jump Street"));
            assertThat(inputCity.getAttribute("value"), is("Metropolis"));
            assertThat(inputPhone.getAttribute("value"), is("1234567890"));
        } catch (AssertionError e) {
            e.printStackTrace();
            error = true;
            throw new AssertionError(e);
        } finally {
            if (!error) {
                takeSnapShot(driver, SCREENSHOTS + "/pass/checkPageAndInsertInfo_" + System.currentTimeMillis() + ".png");
            } else {
                takeSnapShot(driver, SCREENSHOTS + "/fail/checkPageAndInsertInfo_" + System.currentTimeMillis() + ".png");
            }
        }

        try {
            helper.getDriver().findElement(By.xpath("//*[@id=\"newBtn\"]")).click();

            Thread.sleep(5000);
            WebElement previewName = helper.getDriver().findElement(By.xpath("//td[contains(., 'John Wick')]"));
            assertThat(previewName.getText(), is("John Wick"));

            WebElement previewAddress = helper.getDriver().findElement(By.xpath("//td[contains(., '21 Jump Street')]"));
            assertThat(previewAddress.getText(), is("21 Jump Street"));

            WebElement previewCity = helper.getDriver().findElement(By.xpath("//td[contains(., 'Metropolis')]"));
            assertThat(previewCity.getText(), is("Metropolis"));

            WebElement previewPhone = helper.getDriver().findElement(By.xpath("//td[contains(., '1234567890')]"));
            assertThat(previewPhone.getText(), is("1234567890"));
        } catch (AssertionError e2) {
            e2.printStackTrace();
            error2 = true;
            throw new AssertionError(e2);
        } finally {
            if (!error2) {
                takeSnapShot(driver, SCREENSHOTS + "/pass/checkCreatedFieldsInList_" + System.currentTimeMillis() + ".png");
            } else {
                takeSnapShot(driver, SCREENSHOTS + "/fail/checkCreatedFieldsInList_" + System.currentTimeMillis() + ".png");
            }
        }
        helper.getDriver().quit();
    }
}
