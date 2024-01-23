package Extent;

import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.ExtentReports;

public class ExtentManager {
    private static ExtentReports extent;
    private static String reportFileName = "ExtentReport.html";
    private static String reportFilePath = System.getProperty("user.dir") + "/test-output/" + reportFileName;

    public static ExtentReports getInstance() {
        if (extent == null)
            createInstance();
        return extent;
    }

    public static ExtentReports createInstance() {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(reportFilePath);
        htmlReporter.config().setReportName("Test Execution Report");
        htmlReporter.config().setDocumentTitle("Automation Test Results");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        return extent;
    }
}
