package Extent;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class DefaultSuiteTest {
    private WebDriver driver;
    private Map<String, Object> vars;
    private ExtentReports extent;
    private ExtentTest test;

//    @BeforeMethod
//    public void setUp() {
//        driver = new ChromeDriver();
//        vars = new HashMap<>();
//    }
//    @AfterMethod
//    public void tearDown() {
//        driver.quit();
//    }

    @BeforeSuite
    public void setUpExtentReports() {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("ExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @AfterSuite
    public void tearDownExtentReports() {
        extent.flush();
    }



    @Test
    public void validTest() throws IOException {
        test = extent.createTest("Valid Test", "Test with valid inputs");
        driver = new ChromeDriver();
        vars = new HashMap<>();

        driver.get("http://127.0.0.1:5500/index.html");
        driver.manage().window().setSize(new Dimension(953, 728));

        testStep("Enter amount");
        driver.findElement(By.id("amount")).click();
        driver.findElement(By.id("amount")).sendKeys("100");

        testStep("Select 'USD' in 'From' dropdown");
        driver.findElement(By.name("from")).click();
        driver.findElement(By.id("from")).findElement(By.xpath(".//option[. = 'USD']")).click();

        testStep("Select 'ILS' in 'To' dropdown");
        driver.findElement(By.id("to")).click();
        driver.findElement(By.id("to")).findElement(By.xpath(".//option[. = 'ILS']")).click();

        testStep("Click Convert button");
        driver.findElement(By.id("convert")).click();

        String screenshotPath = captureScreenshot(driver, "validTest");
        test.addScreenCaptureFromPath(screenshotPath);

        test.pass("Test passed");

        driver.quit();
    }

    @Test
    public void allEmpty() throws IOException {
        test = extent.createTest("All Fields Empty Test", "Test with all fields empty");
        driver = new ChromeDriver();
        vars = new HashMap<>();

        driver.get("http://127.0.0.1:5500/index.html");
        driver.manage().window().setSize(new Dimension(953, 728));

        testStep("Click Convert button without entering any values");
        driver.findElement(By.id("convert")).click();

        String screenshotPath = captureScreenshot(driver, "allEmpty");
        test.addScreenCaptureFromPath(screenshotPath);

        test.pass("Test passed: No inputs provided");

        driver.quit();
    }

    @Test
    public void emptyAmount() throws IOException {
        test = extent.createTest("Empty Amount Test", "Test with empty amount field");
        driver = new ChromeDriver();
        vars = new HashMap<>();

        driver.get("http://127.0.0.1:5500/index.html");
        driver.manage().window().setSize(new Dimension(953, 728));

        testStep("Select 'USD' in 'From' dropdown");
        driver.findElement(By.name("from")).click();
        driver.findElement(By.id("from")).findElement(By.xpath(".//option[. = 'USD']")).click();

        testStep("Select 'ILS' in 'To' dropdown");
        driver.findElement(By.id("to")).click();
        driver.findElement(By.id("to")).findElement(By.xpath(".//option[. = 'ILS']")).click();

        testStep("Click Convert button without entering the amount");
        driver.findElement(By.id("convert")).click();

        String screenshotPath = captureScreenshot(driver, "emptyAmount");
        test.addScreenCaptureFromPath(screenshotPath);
        test.pass("Test passed: Empty amount field");

        driver.quit();
    }

    @Test
    public void emptyFrom() throws IOException {
        test = extent.createTest("Empty From Test", "Test with empty 'From' dropdown");
        driver = new ChromeDriver();
        vars = new HashMap<>();

        driver.get("http://127.0.0.1:5500/index.html");
        driver.manage().window().setSize(new Dimension(953, 728));

        testStep("Enter amount");
        driver.findElement(By.id("amount")).click();
        driver.findElement(By.id("amount")).sendKeys("100");


        testStep("Select 'ILS' in 'To' dropdown");
        driver.findElement(By.id("to")).click();
        driver.findElement(By.id("to")).findElement(By.xpath(".//option[. = 'ILS']")).click();

        testStep("Click Convert button without selecting 'From' currency");
        driver.findElement(By.id("convert")).click();

        String screenshotPath = captureScreenshot(driver, "emptyFrom");
        test.addScreenCaptureFromPath(screenshotPath);
        test.pass("Test passed: Empty 'From' dropdown");
        driver.quit();
    }

    @Test
    public void emptyTo() throws IOException {
        test = extent.createTest("Empty To Test", "Test with empty 'To' dropdown");
        driver = new ChromeDriver();
        vars = new HashMap<>();

        driver.get("http://127.0.0.1:5500/index.html");
        driver.manage().window().setSize(new Dimension(953, 728));

        testStep("Enter amount");
        driver.findElement(By.id("amount")).click();
        driver.findElement(By.id("amount")).sendKeys("100");

        testStep("Select 'USD' in 'From' dropdown");
        driver.findElement(By.name("from")).click();
        driver.findElement(By.id("from")).findElement(By.xpath(".//option[. = 'USD']")).click();

        testStep("Click Convert button without selecting 'To' currency");
        driver.findElement(By.id("convert")).click();

        String screenshotPath = captureScreenshot(driver, "emptyTo");
        test.addScreenCaptureFromPath(screenshotPath);
        test.pass("Test passed: Empty 'To' dropdown");

        driver.quit();
    }

    @Test
    public void negAmount() throws IOException {
        test = extent.createTest("Negative Amount Test", "Test with negative amount");
        driver = new ChromeDriver();
        vars = new HashMap<>();

        driver.get("http://127.0.0.1:5500/index.html");
        driver.manage().window().setSize(new Dimension(953, 728));

        testStep("Enter negative amount");
        driver.findElement(By.id("amount")).click();
        driver.findElement(By.id("amount")).sendKeys("-1");

        testStep("Select 'USD' in 'From' dropdown");
        driver.findElement(By.name("from")).click();
        driver.findElement(By.id("from")).findElement(By.xpath(".//option[. = 'USD']")).click();

        testStep("Select 'ILS' in 'To' dropdown");
        driver.findElement(By.id("to")).click();
        driver.findElement(By.id("to")).findElement(By.xpath(".//option[. = 'ILS']")).click();

        testStep("Click Convert button");
        driver.findElement(By.id("convert")).click();

        String screenshotPath = captureScreenshot(driver, "negAmount");
        test.addScreenCaptureFromPath(screenshotPath);

        test.pass("Test passed: Negative amount");

        driver.quit();
    }

    @Test
    public void withReset() throws IOException {
        test = extent.createTest("With Reset Test", "Test with values and then reset");
        driver = new ChromeDriver();
        vars = new HashMap<>();

        driver.get("http://127.0.0.1:5500/index.html");
        driver.manage().window().setSize(new Dimension(953, 728));

        testStep("Enter amount");
        driver.findElement(By.id("amount")).click();
        driver.findElement(By.id("amount")).sendKeys("100");

        testStep("Select 'USD' in 'From' dropdown");
        driver.findElement(By.name("from")).click();
        driver.findElement(By.id("from")).findElement(By.xpath(".//option[. = 'USD']")).click();

        testStep("Select 'ILS' in 'To' dropdown");
        driver.findElement(By.id("to")).click();
        driver.findElement(By.id("to")).findElement(By.xpath(".//option[. = 'ILS']")).click();

        testStep("Click Convert button");
        driver.findElement(By.id("convert")).click();

        testStep("Click Reset button");
        driver.findElement(By.id("reset")).click();

        test.pass("Test passed");
        String screenshotPath = captureScreenshot(driver, "withReset");
        test.addScreenCaptureFromPath(screenshotPath);

        driver.quit();
    }



    private void testStep(String stepDescription) {
        test.log(Status.INFO, stepDescription);
    }

    public String captureScreenshot(WebDriver driver, String screenshotName) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            String destinationPath = System.getProperty("user.dir") + "/screenshots/" + screenshotName + ".png";
            File destination = new File(destinationPath);
            FileUtils.copyFile(source, destination);
            return destinationPath;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

}
