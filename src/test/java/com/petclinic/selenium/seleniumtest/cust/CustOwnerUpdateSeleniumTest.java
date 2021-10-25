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
public class CustOwnerUpdateSeleniumTest {

    ChromeDriver driver;
    SeleniumLoginTestHelper helper;
    private final String SCREENSHOTS = "./src/test/onDemandCustServiceScreenshots/OwnerUpdate";

    public CustOwnerUpdateSeleniumTest(ChromeDriver driver) {
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
    @DisplayName("Test_Cust_UpdateOwner")
    public void testCustUpdateOwner() throws Exception {
        boolean error = false, error2 = false, error3 = false;

        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.urlToBe("http://localhost:8080/#!/welcome"));
            helper.getDriver().get("http://localhost:8080/#!/owners");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='#!/owners/details/1']")));
            helper.getDriver().findElement(By.xpath("//a[@href='#!/owners/details/1']")).click();
            wait.until(ExpectedConditions.urlToBe("http://localhost:8080/#!/owners/details/1"));
            helper.getDriver().findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/owner-details/table[1]/tbody/tr[5]/td[1]/a")).click();
            Thread.sleep(5000);

            WebElement initialFirstName = helper.getDriver().findElement(By.xpath("//*[@name=\"firstName\"]"));
            assertThat(initialFirstName.getAttribute("value"), is("George"));
            WebElement initialLastName = helper.getDriver().findElement(By.xpath("//*[@name=\"lastName\"]"));
            assertThat(initialLastName.getAttribute("value"), is("Franklin"));
            WebElement initialAddress = helper.getDriver().findElement(By.xpath("//*[@name=\"address\"]"));
            assertThat(initialAddress.getAttribute("value"), is("110 W. Liberty St."));
            WebElement initialCity = helper.getDriver().findElement(By.xpath("//*[@name=\"city\"]"));
            assertThat(initialCity.getAttribute("value"), is("Madison"));
            WebElement initialPhone = helper.getDriver().findElement(By.xpath("//*[@name=\"telephone\"]"));
            assertThat(initialPhone.getAttribute("value"), is("6085551023"));
        } catch (AssertionError e) {
            e.printStackTrace();
            error = true;
            throw new AssertionError(e);
        } finally {
            if (!error) {
                takeSnapShot(driver, SCREENSHOTS + "/pass/checkInitialOwnerFields_" + System.currentTimeMillis() + ".png");
            } else {
                takeSnapShot(driver, SCREENSHOTS + "/fail/checkInitialOwnerFields_" + System.currentTimeMillis() + ".png");
            }
        }

        try {
            helper.getDriver().findElement(By.xpath("//*[@name=\"firstName\"]")).sendKeys("-Updated");
            helper.getDriver().findElement(By.xpath("//*[@name=\"lastName\"]")).sendKeys("-Updated");
            helper.getDriver().findElement(By.xpath("//*[@name=\"address\"]")).sendKeys("-Updated");
            helper.getDriver().findElement(By.xpath("//*[@name=\"city\"]")).sendKeys("-Updated");
            helper.getDriver().findElement(By.xpath("//*[@id=\"newBtn\"]")).click();

            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='#!/owners/details/1']")));
            WebElement previewName = helper.getDriver().findElement(By.xpath("//td[contains(., 'George-Updated Franklin-Updated')]"));
            assertThat(previewName.getText(), is("George-Updated Franklin-Updated"));

            WebElement previewAddress = helper.getDriver().findElement(By.xpath("//td[contains(., '110 W. Liberty St.-Updated')]"));
            assertThat(previewAddress.getText(), is("110 W. Liberty St.-Updated"));

            WebElement previewCity = helper.getDriver().findElement(By.xpath("//td[contains(., 'Madison-Updated')]"));
            assertThat(previewCity.getText(), is("Madison-Updated"));
        } catch (AssertionError e2) {
            e2.printStackTrace();
            error2 = true;
            throw new AssertionError(e2);
        } finally {
            if (!error2) {
                takeSnapShot(driver, SCREENSHOTS + "/pass/checkUpdatedFieldsInList_" + System.currentTimeMillis() + ".png");
            } else {
                takeSnapShot(driver, SCREENSHOTS + "/fail/checkUpdatedFieldsInList_" + System.currentTimeMillis() + ".png");
            }
        }

        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            helper.getDriver().findElement(By.xpath("//a[@href='#!/owners/details/1']")).click();
            wait.until(ExpectedConditions.urlToBe("http://localhost:8080/#!/owners/details/1"));
            helper.getDriver().findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/owner-details/table[1]/tbody/tr[5]/td[1]/a")).click();
            Thread.sleep(5000);

            WebElement initialFirstName = helper.getDriver().findElement(By.xpath("//*[@name=\"firstName\"]"));
            assertThat(initialFirstName.getAttribute("value"), is("George-Updated"));
            WebElement initialLastName = helper.getDriver().findElement(By.xpath("//*[@name=\"lastName\"]"));
            assertThat(initialLastName.getAttribute("value"), is("Franklin-Updated"));
            WebElement initialAddress = helper.getDriver().findElement(By.xpath("//*[@name=\"address\"]"));
            assertThat(initialAddress.getAttribute("value"), is("110 W. Liberty St.-Updated"));
            WebElement initialCity = helper.getDriver().findElement(By.xpath("//*[@name=\"city\"]"));
            assertThat(initialCity.getAttribute("value"), is("Madison-Updated"));
            WebElement initialPhone = helper.getDriver().findElement(By.xpath("//*[@name=\"telephone\"]"));
            assertThat(initialPhone.getAttribute("value"), is("6085551023"));
        } catch (AssertionError e3) {
            e3.printStackTrace();
            error3 = true;
            throw new AssertionError(e3);
        } finally {
            if (!error3) {
                takeSnapShot(driver, SCREENSHOTS + "/pass/checkUpdatedFieldsInDetails_" + System.currentTimeMillis() + ".png");
            } else {
                takeSnapShot(driver, SCREENSHOTS + "/fail/checkUpdatedFieldsInDetails_" + System.currentTimeMillis() + ".png");
            }
        }
        helper.getDriver().quit();
    }
}
