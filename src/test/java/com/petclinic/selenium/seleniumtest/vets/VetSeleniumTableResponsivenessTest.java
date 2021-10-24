package com.petclinic.selenium.seleniumtest.vets;

import com.petclinic.selenium.SeleniumLoginTestHelper;
import io.github.bonigarcia.seljup.SeleniumExtension;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * User: @SmoothWin
 * Date: 2021-10-23
 * Ticket: feat(VETS-CPC-399): Vet_Service_Mobile_Version
 * Implemented selenium tests for checking if, depending on the current screen width,
 * table data is present and modal data is present because the current UI
 * has an implementation that dependent on different screen width data show up
 * on the modal instead of the table.
 *
 * These tests help test this use case
 */

@ExtendWith(SeleniumExtension.class)
public class VetSeleniumTableResponsivenessTest {
    ChromeDriver driver;
    SeleniumLoginTestHelper helper; //You will need this SeleniumLoginTestHelper field
    private final String SCREENSHOTS = "./src/test/screenshots/vet_modal";

    public VetSeleniumTableResponsivenessTest(ChromeDriver driver){
        this.driver = driver;

        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
        System.setProperty("sel.jup.screenshot.at.the.end.of.tests","whenfailure");
        System.setProperty("sel.jup.screenshot.format", "png");
        System.setProperty("sel.jup.output.folder","./src/test/failureScreenshots/");
    }

    public static void takeSnapShot(WebDriver webDriver, String fileWithPath) throws Exception{

        //Convert web driver object to TakeScreenshot
        TakesScreenshot scrShot = ((TakesScreenshot) webDriver);
        //call get Screenshot method to create actual image file
        File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
        //Move image file to new destination
        File DestFile = new File(fileWithPath);
        //Copy file at destination
        FileUtils.copyFile(SrcFile,DestFile);
    }

    @BeforeEach
    public void setup() throws Exception{
        this.helper = new SeleniumLoginTestHelper("Vets", driver);
        helper.loginTest();
    }

    @Test
    @DisplayName("Test_Vet_Table_FullScreen_Modal_and_Table_data")
    public void testModalAndTableDataFullScreen(TestInfo testInfo) throws Exception{
        String method = testInfo.getDisplayName();
        boolean error = false;
        //assert
        try {
            Dimension dimension = new Dimension(1920, 1080);
            helper.getDriver().manage().window().setSize(dimension);
            WebDriverWait wait = new WebDriverWait(driver,10);
            wait.until(ExpectedConditions.urlToBe("http://localhost:8080/#!/welcome"));
            helper.getDriver().get("http://localhost:8080/#!/vets");
            wait.until(ExpectedConditions.urlToBe("http://localhost:8080/#!/vets"));
            WebElement phone = helper.getDriver().findElement(By.className("vet_phone"));
            WebElement email = helper.getDriver().findElement(By.className("vet_email"));
            WebElement speciality = helper.getDriver().findElement(By.className("vet_speciality"));
            assertThat(phone.isDisplayed(), is(true));
            assertThat(email.isDisplayed(), is(true));
            assertThat(speciality.isDisplayed(),is(true));

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("info")));
            Actions actions = new Actions(helper.getDriver());
            WebElement info = helper.getDriver().findElement(By.className("info"));
            actions.moveToElement(info);
            actions.build().perform();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modalOn")));
            WebElement modal = helper.getDriver().findElement(By.className("modalOn"));
            WebElement modalPhone = helper.getDriver().findElement(By.className("modal_phone"));
            WebElement modalEmail = helper.getDriver().findElement(By.className("modal_email"));
            assertThat(modal.isDisplayed(), is(true));
            assertThat(modalPhone.isDisplayed(), is(false));
            assertThat(modalEmail.isDisplayed(), is(false));

        }catch (AssertionError e){
            e.printStackTrace();
            error = true;
            throw new AssertionError(e);
        }
        finally {
            if(error) {
                takeSnapShot(driver, SCREENSHOTS+"/fail/"+method+"_"+System.currentTimeMillis()+".png");
            }
            helper.getDriver().quit();
        }
    }
    @Test
    @DisplayName("Test_Vet_Table_768pxWidth_Modal_and_Table_data")
    public void testModalAndTableData768pxWidth(TestInfo testInfo) throws Exception{
        String method = testInfo.getDisplayName();
        boolean error = false;
        //assert
        try {
            Dimension dimension = new Dimension(768, 1080);
            helper.getDriver().manage().window().setSize(dimension);
            WebDriverWait wait = new WebDriverWait(driver,10);
            wait.until(ExpectedConditions.urlToBe("http://localhost:8080/#!/welcome"));
            helper.getDriver().get("http://localhost:8080/#!/vets");
            wait.until(ExpectedConditions.urlToBe("http://localhost:8080/#!/vets"));
            WebElement phone = helper.getDriver().findElement(By.className("vet_phone"));
            WebElement email = helper.getDriver().findElement(By.className("vet_email"));
            WebElement speciality = helper.getDriver().findElement(By.className("vet_speciality"));
            assertThat(phone.isDisplayed(), is(false));
            assertThat(email.isDisplayed(), is(true));
            assertThat(speciality.isDisplayed(),is(true));

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("info")));
            Actions actions = new Actions(helper.getDriver());
            WebElement info = helper.getDriver().findElement(By.className("info"));
            actions.moveToElement(info);
            actions.build().perform();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modalOn")));
            WebElement modal = helper.getDriver().findElement(By.className("modalOn"));
            WebElement modalPhone = helper.getDriver().findElement(By.className("modal_phone"));
            WebElement modalEmail = helper.getDriver().findElement(By.className("modal_email"));
            assertThat(modal.isDisplayed(), is(true));
            assertThat(modalPhone.isDisplayed(), is(true));
            assertThat(modalEmail.isDisplayed(), is(false));

        }catch (AssertionError e){
            e.printStackTrace();
            error = true;
            throw new AssertionError(e);
        }
        finally {
            if(error) {
                takeSnapShot(driver, SCREENSHOTS+"/fail/"+method+"_"+System.currentTimeMillis()+".png");
            }
            helper.getDriver().quit();
        }
    }
    @Test
    @DisplayName("Test_Vet_Table_550pxWidth_Modal_and_Table_data")
    public void testModalAndTableData550pxWidth(TestInfo testInfo) throws Exception{
        String method = testInfo.getDisplayName();
        boolean error = false;
        //assert
        try {
            Dimension dimension = new Dimension(550, 1080);
            helper.getDriver().manage().window().setSize(dimension);
            WebDriverWait wait = new WebDriverWait(driver,10);
            wait.until(ExpectedConditions.urlToBe("http://localhost:8080/#!/welcome"));
            helper.getDriver().get("http://localhost:8080/#!/vets");
            wait.until(ExpectedConditions.urlToBe("http://localhost:8080/#!/vets"));
            WebElement phone = helper.getDriver().findElement(By.className("vet_phone"));
            WebElement email = helper.getDriver().findElement(By.className("vet_email"));
            WebElement speciality = helper.getDriver().findElement(By.className("vet_speciality"));
            assertThat(phone.isDisplayed(), is(false));
            assertThat(email.isDisplayed(), is(false));
            assertThat(speciality.isDisplayed(),is(true));

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("info")));
            Actions actions = new Actions(helper.getDriver());
            WebElement info = helper.getDriver().findElement(By.className("info"));
            actions.moveToElement(info);
            actions.build().perform();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modalOn")));
            WebElement modal = helper.getDriver().findElement(By.className("modalOn"));
            WebElement modalPhone = helper.getDriver().findElement(By.className("modal_phone"));
            WebElement modalEmail = helper.getDriver().findElement(By.className("modal_email"));
            assertThat(modal.isDisplayed(), is(true));
            assertThat(modalPhone.isDisplayed(), is(true));
            assertThat(modalEmail.isDisplayed(), is(true));

        }catch (AssertionError e){
            e.printStackTrace();
            error = true;
            throw new AssertionError(e);
        }
        finally {
            if(error) {
                takeSnapShot(driver, SCREENSHOTS+"/fail/"+method+"_"+System.currentTimeMillis()+".png");
            }
            helper.getDriver().quit();
        }
    }
    @Test
    @DisplayName("Test_Vet_Table_380pxWidth_Modal_and_Table_data")
    public void testModalAndTableData380pxWidth(TestInfo testInfo) throws Exception{
        String method = testInfo.getDisplayName();
        boolean error = false;
        //assert
        try {
            Dimension dimension = new Dimension(380, 1080);
            helper.getDriver().manage().window().setSize(dimension);
            WebDriverWait wait = new WebDriverWait(driver,10);
            wait.until(ExpectedConditions.urlToBe("http://localhost:8080/#!/welcome"));
            helper.getDriver().get("http://localhost:8080/#!/vets");
            wait.until(ExpectedConditions.urlToBe("http://localhost:8080/#!/vets"));
            WebElement phone = helper.getDriver().findElement(By.className("vet_phone"));
            WebElement email = helper.getDriver().findElement(By.className("vet_email"));
            WebElement speciality = helper.getDriver().findElement(By.className("vet_speciality"));
            assertThat(phone.isDisplayed(), is(false));
            assertThat(email.isDisplayed(), is(false));
            assertThat(speciality.isDisplayed(),is(false));

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("info")));
            Actions actions = new Actions(helper.getDriver());
            WebElement info = helper.getDriver().findElement(By.className("info"));
            actions.moveToElement(info);
            actions.build().perform();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modalOn")));
            WebElement modal = helper.getDriver().findElement(By.className("modalOn"));
            WebElement modalPhone = helper.getDriver().findElement(By.className("modal_phone"));
            WebElement modalEmail = helper.getDriver().findElement(By.className("modal_email"));
            assertThat(modal.isDisplayed(), is(true));
            assertThat(modalPhone.isDisplayed(), is(true));
            assertThat(modalEmail.isDisplayed(), is(true));

        }catch (AssertionError e){
            e.printStackTrace();
            error = true;
            throw new AssertionError(e);
        }
        finally {
            if(error) {
                takeSnapShot(driver, SCREENSHOTS+"/fail/"+method+"_"+System.currentTimeMillis()+".png");
            }
            helper.getDriver().quit();
        }
    }
}
