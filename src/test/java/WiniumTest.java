import java.io.File;
import java.io.IOException;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.By;
import org.openqa.selenium.winium.DesktopOptions;
import org.openqa.selenium.winium.WiniumDriver;
import org.openqa.selenium.winium.WiniumDriverService;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class WiniumTest {

    static DesktopOptions options;
    static WiniumDriverService service;
    static WiniumDriver driver;
    static ExtentSparkReporter htmlReporter;
    static ExtentReports extent;
    static ExtentTest test1;

    @BeforeTest
    public static void setupEnvironment(){

        options = new DesktopOptions();
        options.setApplicationPath("C:\\Windows\\System32\\calc.exe");
        File driverPath = new File("./viniumdriver/Winium.Desktop.Driver.exe");
        service = new WiniumDriverService.Builder().usingDriverExecutable(driverPath).usingPort(9999).withVerbose(true).withSilent(false).buildDesktopService();
        try {
            service.start();
        } catch (IOException e) {
            System.out.println("Exception while starting WINIUM service");
            e.printStackTrace();
        }
        htmlReporter = new ExtentSparkReporter("extentReport.html");
//create ExtentReports and attach reporter(s)
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        driver = new WiniumDriver(service,options);

    }

    @Test
    public void calculatorTest() throws InterruptedException {
        test1 = extent.createTest("Calculator Test", "test to validate multiple ");
        test1.log(Status.INFO, "Starting test case");
        Thread.sleep(5000);
        driver.findElement(By.id("num8Button")).click();
        Thread.sleep(5000);
        test1.pass("clicked 8");
        driver.findElement(By.name("Multiply by")).click();
        Thread.sleep(5000);
        test1.pass("clicked multiple");
        driver.findElement(By.id("num9Button")).click();
        Thread.sleep(5000);
        test1.pass("clicked 9");
        driver.findElement(By.id("equalButton")).click();
        Thread.sleep(5000);
        test1.pass("result is shown");
        String results = driver.findElement(By.id("CalculatorResults")).getAttribute("Name");
        System.out.println("Result is: "+ results);
        test1.pass("test completed");
        driver.findElement(By.id("Close")).click();
    }

    @AfterTest
    public void tearDown(){
        test1.pass("closing the app");
        test1.info("test completed");
        extent.flush();
       service.stop();
    }
}


