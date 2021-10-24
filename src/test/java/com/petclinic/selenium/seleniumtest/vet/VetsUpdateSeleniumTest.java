package com.petclinic.selenium.seleniumtest.vet;

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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SeleniumExtension.class)
public class VetsUpdateSeleniumTest
{
    ChromeDriver driver;
    SeleniumLoginTestHelper helper;
    private final String SCREENSHOTS = "./src/test/screenshots/vet_update";

    public VetsUpdateSeleniumTest(ChromeDriver driver) {
        this.driver = driver;

        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
        System.setProperty("sel.jup.screenshot.at.the.end.of.tests", "whenfailure");
        System.setProperty("sel.jup.screenshot.format", "png");
        System.setProperty("sel.jup.output.folder", "./src/test/onFailureScreenshots");
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
    @DisplayName("Test_Vet_Update")
    public void testVetUpdate() throws Exception
    {
        boolean error = false;
        boolean error2 = false;
        boolean error3 = false;
        try
        {
            WebDriverWait wait = new WebDriverWait(driver,10);
            wait.until(ExpectedConditions.urlToBe("http://localhost:8080/#!/welcome"));
            helper.getDriver().get("http://localhost:8080/#!/vets");
            wait.until(ExpectedConditions.urlToBe("http://localhost:8080/#!/vets"));
            helper.getDriver().findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/vet-list/table/tbody/tr[1]/td[2]/a/span")).click();
            wait.until(ExpectedConditions.urlToBe("http://localhost:8080/#!/vets/details/234568"));
            helper.getDriver().findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/vet-details/div/div[2]/div/div[2]/div[8]/div/a/button")).click();
            wait.until(ExpectedConditions.urlToBe("http://localhost:8080/#!/vets/234568/edit"));

            WebElement initialFirstName = helper.getDriver().findElement(By.xpath("//*[@id=\"firstName\"]"));
            assertThat(initialFirstName.getAttribute("value"), is("James"));
            WebElement initialLastName = helper.getDriver().findElement(By.xpath("//*[@id=\"lastName\"]"));
            assertThat(initialLastName.getAttribute("value"), is("Carter"));
            WebElement initialPhoneNumber = helper.getDriver().findElement(By.xpath("//*[@id=\"phoneNumber\"]"));
            assertThat(initialPhoneNumber.getAttribute("value"), is("2384"));
            WebElement initialResume = helper.getDriver().findElement(By.xpath("//*[@id=\"vetResume\"]"));
            assertThat(initialResume.getAttribute("value"), is("Practicing since 3 years"));
            WebElement initialEmail = helper.getDriver().findElement(By.xpath("//*[@id=\"email\"]"));
            assertThat(initialEmail.getAttribute("value"), is("carter.james@email.com"));
            WebElement initialWorkDays = helper.getDriver().findElement(By.xpath("//*[@id=\"workDays\"]"));
            assertThat(initialWorkDays.getAttribute("value"), is("Monday, Tuesday, Friday"));
            WebElement initialSpecialtyRadiology =  helper.getDriver().findElement(By.xpath("//*[@id=\"radiology\"]"));
            assertFalse(initialSpecialtyRadiology.isSelected());
            WebElement initialSpecialtySurgery =  helper.getDriver().findElement(By.xpath("//*[@id=\"surgery\"]"));
            assertFalse(initialSpecialtySurgery.isSelected());
            WebElement initialSpecialtyDentistry =  helper.getDriver().findElement(By.xpath("//*[@id=\"dentistry\"]"));
            assertFalse(initialSpecialtyDentistry.isSelected());
            WebElement initialSpecialtyGeneral =  helper.getDriver().findElement(By.xpath("//*[@id=\"general\"]"));
            assertTrue(initialSpecialtyGeneral.isSelected());
            WebElement initialIsActive =  helper.getDriver().findElement(By.xpath("//*[@id=\"vetForm\"]/div/div[10]/div[1]/label/input"));
            assertTrue(initialIsActive.isSelected());
        }
        catch (AssertionError e)
        {
            e.printStackTrace();
            error = true;
            throw new AssertionError(e);
        }
        finally
        {
            if(error == false)
            {
                takeSnapShot(driver, SCREENSHOTS + "/pass/checkInitialVetFields_" + System.currentTimeMillis() + ".png");
            }
            else
            {
                takeSnapShot(driver, SCREENSHOTS+"/fail/checkInitialVetFields_"+System.currentTimeMillis()+".png");
            }
        }
        try
        {

            helper.getDriver().findElement(By.xpath("//*[@id=\"firstName\"]")).sendKeys("Update");
            helper.getDriver().findElement(By.xpath("//*[@id=\"lastName\"]")).sendKeys("Update");
            WebElement changeEmail = helper.getDriver().findElement(By.xpath("//*[@id=\"email\"]"));
            for(int i = 0; i < 9; i++) { changeEmail.sendKeys(Keys.BACK_SPACE);}
            helper.getDriver().findElement(By.xpath("//*[@id=\"email\"]")).sendKeys("gmail.com");
            helper.getDriver().findElement(By.xpath("//*[@id=\"phoneNumber\"]")).sendKeys(Keys.BACK_SPACE);
            helper.getDriver().findElement(By.xpath("//*[@id=\"phoneNumber\"]")).sendKeys("1");
            driver.findElement(By.xpath("//*[@id=\"vetResume\"]")).sendKeys("Update");
            helper.getDriver().findElement(By.xpath("//*[@id=\"surgery\"]")).click();
            helper.getDriver().findElement(By.xpath("//*[@id=\"workDays\"]")).sendKeys(", Thursday");
            helper.getDriver().findElement(By.xpath("//*[@id=\"vetForm\"]/div/div[10]/div[2]/label/input")).click();
            helper.getDriver().findElement(By.xpath("//*[@id=\"vetForm\"]/div/button")).sendKeys(Keys.SPACE);

            WebDriverWait wait = new WebDriverWait(driver,10);
            wait.until(ExpectedConditions.urlToBe("http://localhost:8080/#!/vets"));
            WebElement previewName = helper.getDriver().findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/vet-list/table/tbody/tr[1]/td[2]/a/span"));
            assertThat(previewName.getText(), is("JamesUpdate CarterUpdate"));

            WebElement previewEmail = helper.getDriver().findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/vet-list/table/tbody/tr[1]/td[3]/span"));
            assertThat(previewEmail.getText(), is("(514)-634-8276 #2381"));

            WebElement previewPhone = helper.getDriver().findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/vet-list/table/tbody/tr[1]/td[4]/span"));
            assertThat(previewPhone.getText(), is("carter.james@gmail.com"));




        }
        catch (AssertionError e2)
        {
            e2.printStackTrace();
            error2 = true;
            throw new AssertionError(e2);
        }
        finally
        {
            if(error2 == false)
            {
                takeSnapShot(driver, SCREENSHOTS + "/pass/checkUpdatedFieldsInList_" + System.currentTimeMillis() + ".png");
            }
            else
            {
                takeSnapShot(driver, SCREENSHOTS+"/fail/checkUpdatedFieldsInList_"+System.currentTimeMillis()+".png");
            }
        }
        try
        {

            WebDriverWait wait = new WebDriverWait(driver,10);
            helper.getDriver().findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/vet-list/table/tbody/tr[1]/td[2]/a/span")).click();
            wait.until(ExpectedConditions.urlToBe("http://localhost:8080/#!/vets/details/234568"));

            WebElement name = driver.findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/vet-details/div/div[2]/div/div[2]/div[1]"));
            assertThat(name.getText(), is("JamesUpdate CarterUpdate"));

            WebElement email = driver.findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/vet-details/div/div[2]/div/div[2]/div[2]"));
            assertThat(email.getText(), is("carter.james@gmail.com"));

            WebElement phone = driver.findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/vet-details/div/div[2]/div/div[2]/div[3]"));
            assertThat(phone.getText(), is("(514)-634-8276 #2381"));

            WebElement resume = driver.findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/vet-details/div/div[2]/div/div[2]/div[5]/div"));
            assertThat(resume.getText(), is("Practicing since 3 yearsUpdate"));

            WebElement workday = driver.findElement(By.xpath("//*[@id=\"bg\"]/div/div/div/ui-view/vet-details/div/div[2]/div/div[2]/div[6]/div/div"));
            assertThat(workday.getText(), is("Monday, Tuesday, Friday, Thursday"));

            WebElement button = driver.findElement(By.xpath("//*[@id=\"toggle\"]"));
            assertThat(button.getText(), is("Show availabilities"));


        }
        catch (AssertionError e3)
        {
            e3.printStackTrace();
            error3 = true;
            throw new AssertionError(e3);
        }
        finally
        {
            if(error3 == false)
            {
                takeSnapShot(driver, SCREENSHOTS + "/pass/checkUpdatedFieldsInDetails_" + System.currentTimeMillis() + ".png");
            }
            else
            {
                takeSnapShot(driver, SCREENSHOTS+"/fail/checkUpdatedFieldsInDetails_"+System.currentTimeMillis()+".png");
            }
        }
        helper.getDriver().quit();

    }
}
