package ListenarPackage;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import lombok.Synchronized;

public class ExtentManager {

	private static ExtentReports extent;
	ExtentTest test;

	public synchronized static ExtentReports getExtentReports() {

		String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
		String reportPath = System.getProperty("user.dir") + "/Reports/ExtentReport_" + timestamp + ".html";

		ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
		extent = new ExtentReports();
		spark.config().setTheme(Theme.DARK);
		spark.config().setDocumentTitle("Rest Assured Document");

		extent.attachReporter(spark);

		extent.setSystemInfo("OS", "Windows");
		extent.setSystemInfo("Tester", "Vishakha");
		return extent;
	}

	public synchronized static void flushExtentReports() {
		if (extent != null) {
			extent.flush();
		}
	}
}
