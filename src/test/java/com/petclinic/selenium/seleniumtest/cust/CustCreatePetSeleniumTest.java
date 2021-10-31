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
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(SeleniumExtension.class)
public class CustCreatePetSeleniumTest {
    ChromeDriver driver;
    SeleniumLoginTestHelper helper;
    private final String SCREENSHOTS = "./src/test/onDemandCustServiceScreenshots/OwnerCreate";

    public CustCreatePetSeleniumTest(ChromeDriver driver) {
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
    @DisplayName("Test_Cust_CreatePet")
    public void CreatePetTest() throws Exception{
        boolean error1 = false; boolean error2 = false;

        try{
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.urlToBe("http://localhost:8080/#!/welcome"));
            helper.getDriver().get("http://localhost:8080/#!/owners/details/8");
            wait.until(ExpectedConditions.urlToBe("http://localhost:8080/#!/owners/details/8"));

            WebElement addPetBtn = helper.getDriver().findElement((By.xpath("//*[contains(text(),'Add New Pet')]")));
            addPetBtn.click();
            wait.until(ExpectedConditions.urlToBe("http://localhost:8080/#!/owners/8/pets/add-pet"));

            try{
                Thread.sleep(5000);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }

            WebElement inputFirstName = helper.getDriver().findElement(By.xpath("//*[@name=\"name\"]"));
            inputFirstName.sendKeys("John");
            WebElement inputBirthdate = helper.getDriver().findElement(By.xpath("//*[@name=\"dbo\"]"));
            inputBirthdate.sendKeys("0020010330");
            Select drpType = new Select(driver.findElement(By.name("type")));
            drpType.selectByIndex(4);
            Thread.sleep(3000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
            error1 = true;
        }
        finally{
            if (!error1) {
                takeSnapShot(driver, SCREENSHOTS + "/pass/checkPageAndInsertInfo_" + System.currentTimeMillis() + ".png");
            } else {
                takeSnapShot(driver, SCREENSHOTS + "/fail/checkPageAndInsertInfo_" + System.currentTimeMillis() + ".png");
            }
        }

        try {
            helper.getDriver().findElement(By.xpath("//*[@id=\"newBtn\"]")).click();

            Thread.sleep(5000);
            WebElement previewName = helper.getDriver().findElement(By.xpath("//dd[contains(., 'John')]"));
            assertThat(previewName.getText(), is("John"));

            WebElement previewBirthdate = helper.getDriver().findElement(By.xpath("//dd[contains(., '2001 Mar 30')]"));
            assertThat(previewBirthdate.getText(), is("2001 Mar 30"));

            WebElement previewType = helper.getDriver().findElement(By.xpath("//dd[contains(., 'hamster')]"));
            assertThat(previewType.getText(), is("hamster"));

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
