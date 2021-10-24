package com.petclinic.selenium.seleniumtest.visit;

import io.github.bonigarcia.seljup.SeleniumExtension;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;


@ExtendWith(SeleniumExtension.class)
public class CalendarUIVisitSeleniumTest {
    ChromeDriver driver;
    private final String SCREENSHOTS = "./src/test/onDemandScreenshots";

    public CalendarUIVisitSeleniumTest(ChromeDriver driver) {
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
    @DisplayName("test_CalendarUI")
    void test_CalendarUI(TestInfo testInfo) throws Exception {

        //wait max 5 seconds before a timeout
        WebDriverWait wait=new WebDriverWait(driver, 5);

        //setting up path and window size
        driver.get("http://localhost:8080");
        driver.manage().window().maximize();

        //enter authentication information
        driver.findElement(By.id("email")).sendKeys("admin");
        driver.findElement(By.id("pwd")).sendKeys("admin");
        driver.findElement(By.id("button")).click();


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Owners")));





        //navigation to visits page
        driver.findElement(By.linkText("Owners")).click();


        WebElement a1 = driver.findElement(By.xpath("//a[@href='#!/owners']"));
        a1.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Betty Davis"))); //Will crash here if not wait
        a1 = driver.findElement(By.linkText("Betty Davis"));
        a1.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Add Visit")));
        a1 = driver.findElement(By.linkText("Add Visit"));
        a1.click();





        //Selecting the vet : "Helen"

        TimeUnit.SECONDS.sleep(2);//special case

        driver.findElement(By.xpath("//*[@id=\"selectedVet\"]/option[1]")).click();
        TimeUnit.SECONDS.sleep(2);
        String selectedVet = driver.findElement(By.xpath("//*[@id=\"selectedVet\"]/option[3]")).getText();
        System.out.println(selectedVet +" : vetname");
        assertThat(selectedVet, is("Helen Leary"));


        // Necessary scroll
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,250)", "");

        driver.findElement(By.xpath("//*[@id=\"selectedVet\"]/option[3]")).click();
        TimeUnit.SECONDS.sleep(1);

        Calendar calendar = Calendar.getInstance();
        String year = String.valueOf(calendar.getInstance().get(Calendar.YEAR));
  
        WebElement currentMonthCalendar = driver.findElement(By.xpath("//*[@id=\'bg\']/div/div/div/ui-view/visits/div[1]/div/div[1]/div/h1"));
        WebElement currentYearCalendar = driver.findElement(By.xpath("//*[@id=\'bg\']/div/div/div/ui-view/visits/div[1]/div/div[1]/div/p"));
        WebElement currentVetNameCalendar = driver.findElement(By.xpath("//*[@id=\'bg\']/div/div/div/ui-view/visits/div[1]/div/div[4]/div[1]/div/h1"));

            //calendar displays the current month
        assertThat(currentMonthCalendar.getText().toString().toLowerCase(Locale.ROOT), is(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH ).toString().toLowerCase(Locale.ROOT)));
            //calendar displays the current year
        assertThat(currentYearCalendar.getText(), is(year));
            //calendar displays the same name as the one selected from the dropdown
        assertThat(currentVetNameCalendar.getText(), is("Helen Leary"));






        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();
    }



    @Test
    @DisplayName("test_CalendarUI_SwitchMonth")
    void test_CalendarUI_SwitchMonth(TestInfo testInfo) throws Exception {

        //wait max 5 seconds before a timeout
        WebDriverWait wait=new WebDriverWait(driver, 5);

        //setting up path and window size
        driver.get("http://localhost:8080");
        driver.manage().window().maximize();

        //enter authentication information
        driver.findElement(By.id("email")).sendKeys("admin");
        driver.findElement(By.id("pwd")).sendKeys("admin");
        driver.findElement(By.id("button")).click();


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Owners")));





        //navigation to visits page
        driver.findElement(By.linkText("Owners")).click();


        WebElement a1 = driver.findElement(By.xpath("//a[@href='#!/owners']"));
        a1.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Betty Davis"))); //Will crash here if not wait
        a1 = driver.findElement(By.linkText("Betty Davis"));
        a1.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Add Visit")));
        a1 = driver.findElement(By.linkText("Add Visit"));
        a1.click();





        //Selecting the vet : "Helen"

        TimeUnit.SECONDS.sleep(2);//special case

        driver.findElement(By.xpath("//*[@id=\"selectedVet\"]/option[1]")).click();
        TimeUnit.SECONDS.sleep(2);
        String selectedVet = driver.findElement(By.xpath("//*[@id=\"selectedVet\"]/option[3]")).getText();
        System.out.println(selectedVet +" : vetname");
        assertThat(selectedVet, is("Helen Leary"));


        // Necessary scroll
        JavascriptExecutor js = (JavascriptExecutor) driver;


        driver.findElement(By.xpath("//*[@id=\"selectedVet\"]/option[3]")).click();
        TimeUnit.SECONDS.sleep(1);

        Calendar calendar = Calendar.getInstance();

        WebElement currentMonthCalendar = driver.findElement(By.xpath("//*[@id=\'bg\']/div/div/div/ui-view/visits/div[1]/div/div[1]/div/h1"));


        //calendar displays the current month
        assertThat(currentMonthCalendar.getText().toString().toLowerCase(Locale.ROOT), is(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH ).toString().toLowerCase(Locale.ROOT)));

        //testing next month button ">"
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\'bg\']/div/div/div/ui-view/visits/div[1]/div/div[1]/i[2]")));
        driver.findElement(By.xpath("//*[@id=\'bg\']/div/div/div/ui-view/visits/div[1]/div/div[1]/i[2]")).click();
        WebElement nextMonthCalendar = driver.findElement(By.xpath("//*[@id=\'bg\']/div/div/div/ui-view/visits/div[1]/div/div[1]/div/h1"));
        assertThat(nextMonthCalendar.getText().toString().toLowerCase(Locale.ROOT), is("november"));


        //testing previous month button "<"
        driver.findElement(By.xpath("//*[@id=\'bg\']/div/div/div/ui-view/visits/div[1]/div/div[1]/i[1]")).click();
        driver.findElement(By.xpath("//*[@id=\'bg\']/div/div/div/ui-view/visits/div[1]/div/div[1]/i[1]")).click();

        assertThat(nextMonthCalendar.getText().toString().toLowerCase(Locale.ROOT), is("september"));




        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();
    }
}
