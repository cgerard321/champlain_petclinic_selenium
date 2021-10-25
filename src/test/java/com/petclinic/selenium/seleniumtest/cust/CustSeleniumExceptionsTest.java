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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(SeleniumExtension.class)
public class CustSeleniumExceptionsTest {

    ChromeDriver driver;
    SeleniumLoginTestHelper helper;

    private final String SCREENSHOTS = "./src/test/onDemandCustServiceScreenshots/Exceptions";

    public CustSeleniumExceptionsTest(ChromeDriver driver){
        this.driver = driver;
    }

    public static void takeSnapshot(WebDriver webDriver, String fileWithPath) throws Exception{

        //Convert web driver object to take screenshot
        TakesScreenshot scrShot = ((TakesScreenshot) webDriver);
        //Call the getScreenshotAs method to create the actual file
        File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
        //Move the image file to new destination
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
    @DisplayName("Test_Cust_Invalid_Input_Exception")
    public void test_Invalid_Input_Exception_Alert() throws Exception {
        boolean error = false, error2 = false, error3 = false;

        try{
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.urlToBe("http://localhost:8080/#!/welcome"));
            helper.getDriver().get("http://localhost:8080/#!/owners");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='#!/owners/details/1']")));
            helper.getDriver().findElement(By.xpath("//a[@href='#!/owners/details/1']")).click();
            wait.until(ExpectedConditions.urlToBe("http://localhost:8080/#!/owners/details/1"));
            helper.getDriver().findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/owner-details/table[1]/tbody/tr[5]/td[1]/a")).click();

            driver.get("http://localhost:8080/#!/owners/details/nine");
            driver.manage().window().maximize();

            WebElement emptyName = driver.findElement(By.xpath("//td[@id=\"tableName\"]"));

            String emptyNameString = "";

            assertThat(emptyName.getText(), is(emptyNameString));
        }
        catch (AssertionError e)
        {
            e.printStackTrace();
            error = true;
            throw new AssertionError(e);
        }
        finally {
            if (!error) {
                takeSnapshot(driver, SCREENSHOTS + "/pass/InvalidInputException_" +System.currentTimeMillis() + ".png");
            } else {
                takeSnapshot(driver, SCREENSHOTS + "/fail/InvalidInputException_" + System.currentTimeMillis() + ".png");
            }
        }
    }

    @Test
    @DisplayName("Test_Cust_Not_Found_Exception")
    public void test_Not_Found_Exception_Alert() throws Exception {
        boolean error = false, error2 = false, error3 = false;

        try{
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.urlToBe("http://localhost:8080/#!/welcome"));
            helper.getDriver().get("http://localhost:8080/#!/owners");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='#!/owners/details/1']")));
            helper.getDriver().findElement(By.xpath("//a[@href='#!/owners/details/1']")).click();
            wait.until(ExpectedConditions.urlToBe("http://localhost:8080/#!/owners/details/1"));
            helper.getDriver().findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/owner-details/table[1]/tbody/tr[5]/td[1]/a")).click();

            driver.get("http://localhost:8080/#!/owners/details/9000");
            driver.manage().window().maximize();

            WebElement emptyName = driver.findElement(By.xpath("//td[@id=\"tableName\"]"));

            String emptyNameString = "";

            assertThat(emptyName.getText(), is(emptyNameString));
        }
        catch (AssertionError e)
        {
            e.printStackTrace();
            error = true;
            throw new AssertionError(e);
        }
        finally {
            if (!error) {
                takeSnapshot(driver, SCREENSHOTS + "/pass/NotFoundException_" +System.currentTimeMillis() + ".png");
            } else {
                takeSnapshot(driver, SCREENSHOTS + "/fail/NotFoundException_" + System.currentTimeMillis() + ".png");
            }
        }
    }
}
