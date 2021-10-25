package com.petclinic.selenium.seleniumbillingservicetest;

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
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SeleniumExtension.class)
public class SeleniumBillingServiceTest {
    ChromeDriver driver;
    SeleniumLoginTestHelper helper;
    private final String SCREENSHOTS = "./src/test/onDemandBillServiceScreenshots";

    public SeleniumBillingServiceTest(ChromeDriver driver) {
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

    @BeforeEach
    void setupLogin() throws Exception {
        this.helper = new SeleniumLoginTestHelper("LoginTestHelper", driver);
        helper.loginTest();
    }

    @Test
    @DisplayName("Test to see if the history page loads")
    public void takeBillingServiceSnapshot(TestInfo testInfo) throws Exception {
        WebElement billsTab = helper.getDriver().findElement(By.id("navbarDropdown1"));
        billsTab.click();

        WebElement billHistoryLink = helper.getDriver().findElement(By.xpath("//a[@href='#!/bills']"));
        billHistoryLink.click();

        WebElement billHistoryHeader = helper.getDriver().findElement(By.className("titleOwner"));

        String method = testInfo.getDisplayName();
        takeSnapShot(helper.getDriver(), SCREENSHOTS + "\\" + method + "_" + System.currentTimeMillis() + ".png");

        assertThat(billHistoryHeader.getText(), is("Bill History"));

        TimeUnit.SECONDS.sleep(1);

        helper.getDriver().quit();
    }

    @Test
    @DisplayName("Test a snapshot to get the table data")
    public void takeBillingServiceHistoryPageSnapshot(TestInfo testInfo) throws Exception {
        WebElement billsTab = helper.getDriver().findElement(By.id("navbarDropdown1"));
        billsTab.click();

        WebElement billHistoryLink = helper.getDriver().findElement(By.xpath("//a[@href='#!/bills']"));
        billHistoryLink.click();

        WebDriverWait wait = new WebDriverWait(driver,2);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@class='table table-striped']")));

        WebElement table = helper.getDriver().findElement(By.xpath("//table[@class='table table-striped']"));
        List<WebElement> rows = table.findElements(By.tagName("tr"));

        String method = testInfo.getDisplayName();
        takeSnapShot(helper.getDriver(), SCREENSHOTS + "\\" + method + "_" + System.currentTimeMillis() + ".png");

        TimeUnit.SECONDS.sleep(1);

        assertThat(rows.size(), is(7));

        helper.getDriver().quit();
    }

    @Test
    @DisplayName("Take a snapshot of bill details page")
    public void takeBillingServiceDetailsPageSnapshot(TestInfo testInfo) throws Exception{
        WebElement billsTab = helper.getDriver().findElement(By.id("navbarDropdown1"));
        billsTab.click();

        WebElement billHistoryLink = helper.getDriver().findElement(By.xpath("//a[@href='#!/bills']"));
        billHistoryLink.click();

        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@class='table table-striped']")));

        WebElement billDetailsLink = helper.getDriver().findElement(By.linkText("Get Details"));
        billDetailsLink.click();

        WebDriverWait waitDetails = new WebDriverWait(driver,2);
        waitDetails.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[@class='titleOwner ng-binding']")));

        WebElement billIDDetail = helper.getDriver().findElement(By.xpath("//h2[@class='titleOwner ng-binding']"));

        String method = testInfo.getDisplayName();
        takeSnapShot(helper.getDriver(), SCREENSHOTS + "\\" + method + "_" + System.currentTimeMillis() + ".png");

        TimeUnit.SECONDS.sleep(1);

        assertThat(billIDDetail, not(""));

        helper.getDriver().quit();

    }
}
