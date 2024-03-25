package api.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager implements ITestListener {
    public ExtentSparkReporter extentSparkReporter;
    public ExtentReports extentReports;
    public ExtentTest extentTest;

    String reportName;

    public void onStart(ITestContext testContext) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        reportName = "Test-Report - " + timeStamp + ".html";

        extentSparkReporter = new ExtentSparkReporter(".\\reports\\" + reportName);

        extentSparkReporter.config().setDocumentTitle("RestApiFrameWorkProject");
        extentSparkReporter.config().setReportName("Pet Store Users API");
        extentSparkReporter.config().setTheme(Theme.DARK);

        extentReports = new ExtentReports();
        extentReports.attachReporter(extentSparkReporter);
        extentReports.setSystemInfo("Application", "Pets Store User API");
        extentReports.setSystemInfo("Operation System", System.getProperty("os.name"));
        extentReports.setSystemInfo("User Name", System.getProperty("user.name"));
        extentReports.setSystemInfo("Environment", "Staging");
        extentReports.setSystemInfo("user", "Orel");

    }

    public void onTestSuccess(ITestResult testResult) {
        extentTest = extentReports.createTest(testResult.getName());
        extentTest.assignCategory(testResult.getMethod().getGroups());
        extentTest.createNode(testResult.getName());
        extentTest.log(Status.PASS, "Test Passed");
    }

    public void onTestFailure(ITestResult testResult) {
        extentTest = extentReports.createTest(testResult.getName());
        extentTest.createNode(testResult.getName());
        extentTest.assignCategory(testResult.getMethod().getGroups());
        extentTest.log(Status.FAIL, "Test Failed");
        extentTest.log(Status.FAIL, testResult.getThrowable().getMessage());
    }

    public void onTestSkipped(ITestResult testResult) {
        extentTest = extentReports.createTest(testResult.getName());
        extentTest.createNode(testResult.getName());
        extentTest.assignCategory(testResult.getMethod().getGroups());
        extentTest.log(Status.SKIP, "Test Skipped");
        extentTest.log(Status.SKIP, testResult.getThrowable().getMessage());
    }

    public void onFinish(ITestContext testContext) {
        extentReports.flush();
    }

}
