package ListenarPackage;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MyTestListeners implements ITestListener {

	private static ExtentReports extent;
	private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
	private static Map<String, ExtentTest> testMap = new HashMap<>();

	@Override
	public void onStart(ITestContext context) {
		extent = ExtentManager.getExtentReports();
	}

	@Override
	public void onTestStart(ITestResult result) {
		String methodName = result.getMethod().getMethodName();

		// Create ExtentTest for this method
		ExtentTest test = extent.createTest(methodName, result.getMethod().getDescription())
				.assignAuthor("Vishakha").assignCategory("API'S");

		// Set the test in ThreadLocal (optional, if you're using it)
		extentTest.set(test);

		// Optionally store in a map
		testMap.put(methodName, test);

		// ✅ Associate this ExtentTest with ITestResult
		result.setAttribute("extentTest", test);

		// Log test start
		test.log(Status.INFO, "Test Started: " + methodName);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		ExtentTest test = (ExtentTest) result.getAttribute("extentTest");
		if (test != null) {
			test.log(Status.PASS, "Test Passed: " + result.getMethod().getMethodName());
			String log = "This method "+result.getMethod().getMethodName()+"was successfull";
			test.pass(MarkupHelper.createLabel(log,ExtentColor.GREEN));
		}
	}

	@Override
	public void onTestFailure(ITestResult result) {
		ExtentTest test = (ExtentTest) result.getAttribute("extentTest");
		if (test != null) {
			test.log(Status.FAIL, "Test Failed: " + result.getThrowable());
			String log = "This method "+result.getMethod().getMethodName()+"was unsuccessfull";
			test.fail(MarkupHelper.createLabel(log,ExtentColor.RED));

		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		ExtentTest test = (ExtentTest) result.getAttribute("extentTest"); // Retrieve test
		if (test != null) {
			test.log(Status.SKIP, "Test Skipped: " + result.getThrowable());
		} else {
			extentTest.get().log(Status.SKIP, "Test Skipped: " + result.getThrowable());
		}
	}

	@Override
	public void onFinish(ITestContext context) {
		ExtentManager.flushExtentReports();
	}
}