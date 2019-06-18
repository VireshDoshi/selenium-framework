package com.devops360.selenium.tests;

import org.apache.commons.io.FileUtils;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.devops360.selenium.DriverBase;
import com.devops360.selenium.page_objects.GoogleHomePage;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


public class GoogleExampleIT extends DriverBase {

    private ExpectedCondition<Boolean> pageTitleStartsWith(final String searchString) {
        return driver -> driver.getTitle().toLowerCase().startsWith(searchString.toLowerCase());
    }
    public WebDriver driver;
    public ExtentHtmlReporter htmlReporter;
    public ExtentReports extent;
    public ExtentTest logger;



    // Function select an element base on index
    // and return string
    public static String getRandomSearchString()
    {
        // create a list of Integer type
        List<String> searchList = new ArrayList<>();
        // add 5 element in ArrayList
        searchList.add("Cheese");
        searchList.add("mobiles");
        searchList.add("iphones");
        searchList.add("android");
        searchList.add("nokia");

        Random rand = new Random();

        return searchList.get(rand.nextInt(searchList.size()));
    }

    //This method is to capture the screenshot and return the path of the screenshot.
    public static String getScreenShot(WebDriver driver, String screenshotName) throws IOException {
        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);

        String reportsScreenshotDirectory = System.getProperty("user.dir")  + "/target/reports/screengrabs/";
        String screenshotNameFull = screenshotName + dateName + ".png";
        String destination = reportsScreenshotDirectory + screenshotNameFull;
        File finalDestination = new File(destination);

        FileUtils.copyFile(source, finalDestination);
        return screenshotNameFull;
    }

    @BeforeTest
    public void startReport() {
        String reportDirectory = System.getProperty("reportDirectory", "target/reports");
        htmlReporter = new ExtentHtmlReporter(reportDirectory + "/STMExtentReport.html");
        // Create an object of Extent Reports
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Host Name", "DevOps Team");
        extent.setSystemInfo("Environment", "Production");
        extent.setSystemInfo("User Name", "Viresh Doshi");

        htmlReporter.config().setDocumentTitle("Test Automation report ");
        // Name of the report
        htmlReporter.config().setReportName("The project Report Name");
        // Dark Theme
        htmlReporter.config().setTheme(Theme.STANDARD);

    }

    @Test()
    public void googleRandomSearchExample() throws Exception {
        // Test name
        logger = extent.createTest("test for cheese ");

        // Create a new WebDriver instance
        // Notice that the remainder of the code relies on the interface,
        // not the implementation.
        driver = getDriver();

        // And now use this to visit Google
        driver.get("http://www.google.com");
        // Alternatively the same thing can be done like this
        // driver.navigate().to("http://www.google.com");

        GoogleHomePage googleHomePage = new GoogleHomePage();

        // Check the title of the page
        System.out.println("Page title is: " + driver.getTitle());

        String randomSearch = getRandomSearchString();

        System.out.println("random search entry = " + randomSearch );

        googleHomePage.enterSearchTerm((randomSearch))
                .submitSearch();

        // Google's search is rendered dynamically with JavaScript.
        // Wait for the page to load, timeout after 10 seconds
        WebDriverWait wait = new WebDriverWait(driver, 10, 100);
        wait.until(pageTitleStartsWith(randomSearch));

        // Should see: "cheese! - Google Search"
        System.out.println("Page title is: " + driver.getTitle());

        Assert.assertEquals(driver.getTitle(),"Cheese - Google Search");

    }

    @Test
    public void googleSearchMilkExample() throws Exception {
        // Test name
        logger = extent.createTest("To assert for milk");

        // Create a new WebDriver instance
        // Notice that the remainder of the code relies on the interface,
        // not the implementation.
        driver = getDriver();

        // And now use this to visit Google
        driver.get("http://www.google.com");
        // Alternatively the same thing can be done like this
        // driver.navigate().to("http://www.google.com");

        GoogleHomePage googleHomePage = new GoogleHomePage();

        // Check the title of the page
        System.out.println("Page title is: " + driver.getTitle());

        googleHomePage.enterSearchTerm("Milk")
                .submitSearch();

        // Google's search is rendered dynamically with JavaScript.
        // Wait for the page to load, timeout after 10 seconds
        WebDriverWait wait = new WebDriverWait(driver, 10, 100);
        wait.until(pageTitleStartsWith("Milk"));

        // Should see: "cheese! - Google Search"
        System.out.println("Page title is: " + driver.getTitle());
        Assert.assertEquals(driver.getTitle(),"Milk - Google Search");

    }
    @AfterMethod
    public void getResult(ITestResult result) throws Exception{
        if(result.getStatus() == ITestResult.FAILURE ){
            //MarkupHelper is used to display the output in different colors
            logger.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
            logger.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable() + " - Test Case Failed", ExtentColor.RED));
            //To capture screenshot path and store the path of the screenshot in the string "screenshotPath"
            //We do pass the path captured by this method in to the extent reports using "logger.addScreenCapture" method.
            //String Scrnshot=TakeScreenshot.captuerScreenshot(driver,"TestCaseFailed");
            String screenshotPath = getScreenShot(driver, result.getName());
            //To add it in the extent report
            logger.fail("Test Case Failed Snapshot is below " + logger.addScreenCaptureFromPath("screengrabs/"  + screenshotPath));
        }
        else if(result.getStatus() == ITestResult.SKIP){
            logger.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE));
        }
        else if(result.getStatus() == ITestResult.SUCCESS) {
            logger.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " Test Case PASSED", ExtentColor.GREEN));
            String screenshotPath = getScreenShot(driver, result.getName());
            System.out.println("Screenshot path = : " + screenshotPath);

            logger.pass("Test Case Passed  Snapshot is below");

            logger.pass("all good").addScreenCaptureFromPath("screengrabs/" + screenshotPath);
        }
    }

    @AfterTest
    public void endReport() {
        extent.flush();
        driver.quit();
    }
}