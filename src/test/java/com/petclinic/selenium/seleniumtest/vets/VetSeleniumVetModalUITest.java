package com.petclinic.selenium.seleniumtest.vets;

import com.petclinic.selenium.SeleniumLoginTestHelper;
import io.github.bonigarcia.seljup.SeleniumExtension;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * User: @SmoothWin
 * Date: 2021-10-21
 * Ticket: feat(VETS-CPC-399): Vet_Service_Mobile_Version
 * Implemented selenium test for the vet modal that is associated to the story ticket above
 */

@ExtendWith(SeleniumExtension.class)
public class VetSeleniumVetModalUITest {
    ChromeDriver driver;
    SeleniumLoginTestHelper helper; //You will need this SeleniumLoginTestHelper field
    private final String SCREENSHOTS = "./src/test/screenshots/vet_modal";

    public VetSeleniumVetModalUITest(ChromeDriver driver){
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
        this.helper = new SeleniumLoginTestHelper("LoginTestHelper", driver);
        helper.loginTest();
    }

    @Test
    @DisplayName("Test_Vet_Modal_Information_Presence")
    public void testModalAppear(TestInfo testInfo) throws Exception{
        String method = testInfo.getDisplayName();
        boolean error = false;
        //assert
        //open web page
        try {
            WebDriverWait wait = new WebDriverWait(driver,10);
            wait.until(ExpectedConditions.urlToBe("http://localhost:8080/#!/welcome"));
            helper.getDriver().get("http://localhost:8080/#!/vets");
            wait.until(ExpectedConditions.urlToBe("http://localhost:8080/#!/vets"));
            Actions actions = new Actions(helper.getDriver());
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("info")));
            WebElement info = helper.getDriver().findElement(By.className("info"));
            actions.moveToElement(info);
            actions.build().perform();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modalOn")));
            WebElement modal = helper.getDriver().findElement(By.className("modalOn"));
            assertThat(modal.isDisplayed(), is(true));
        }catch (AssertionError e){
            e.printStackTrace();
            error = true;
            throw new AssertionError(e);
        }
        finally {
            if(error == false) {
                takeSnapShot(driver, SCREENSHOTS + "/pass/" + method + "_" + System.currentTimeMillis() + ".png");
            }
            else{
                takeSnapShot(driver, SCREENSHOTS+"/fail/"+method+"_"+System.currentTimeMillis()+".png");
            }
            helper.getDriver().quit();
        }
    }
}
