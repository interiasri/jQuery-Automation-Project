package DriverFactory;

import org.testng.Reporter;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import CommonFunctions.FunctionLibrary;
import Utility.ExcelFileUtils;

public class DriverScript_Widgets {
	
	public static String excelInputFilePath = "FileInput/Widgets.xlsx";
	public static String excelOutputFilePath="FileOutput/Widgets_Results.xlsx";
	ExtentReports reporter;
	ExtentTest logger;
	
	public void kickStartWidgets() throws Throwable {
		ExcelFileUtils obj = new ExcelFileUtils(excelInputFilePath);
		String sheetName=ExcelFileUtils.wb.getSheetAt(0).getSheetName();
		System.out.println(sheetName);
		int rowQty=obj.rowCount(sheetName);
		System.out.println("Number of TestCases: "+rowQty);
		boolean status = false;
		boolean ExecutionStatus = false;
		for(int i=1; i<=rowQty; i++) {
			String TCModule=obj.getCellData(sheetName, i, 1);
			String runStatus = obj.getCellData(sheetName, i, 3);
			reporter = new ExtentReports("AdvancedReports/AutomationTesting_"+TCModule+".html");
			if(runStatus.equalsIgnoreCase("Y")) {
				//Run TestCase-Pass
				logger=reporter.startTest(TCModule);
				logger.assignAuthor("Sridhar Parutabad");
				System.out.println("TestCase Name: "+TCModule);
				System.out.println("Number of rows: "+obj.rowCount(TCModule));
				for(int j=1; j<=obj.rowCount(TCModule); j++) {
					String description = obj.getCellData(TCModule, j, 1);
					String objType=obj.getCellData(TCModule, j, 2);
					String locatorType=obj.getCellData(TCModule, j, 3);
					String locatorValue=obj.getCellData(TCModule, j, 4);
					String testData=obj.getCellData(TCModule, j, 5);
					try {
						if(objType.equalsIgnoreCase("launchBrowser")) {
							FunctionLibrary.launchBrowser();
							logger.log(LogStatus.INFO, description);
						}
						if(objType.equalsIgnoreCase("launchUrl")) {
							FunctionLibrary.launchUrl();
							logger.log(LogStatus.INFO, description);
						}
						if(objType.equalsIgnoreCase("WaitForWebElement")) {
							FunctionLibrary.WaitForWebElement(locatorType, locatorValue, testData);
							logger.log(LogStatus.INFO, description);
						}
						if(objType.equalsIgnoreCase("clickAction")) {
							FunctionLibrary.clickAction(locatorType, locatorValue);
							logger.log(LogStatus.INFO, description);
						}
						if(objType.equalsIgnoreCase("switchToIframe")) {
							FunctionLibrary.switchToIframe(locatorType, locatorValue);
							logger.log(LogStatus.INFO, description);
						}
						if(objType.equalsIgnoreCase("takeScreenShot")) {
							FunctionLibrary.takeScreenShot(testData);
							logger.log(LogStatus.INFO, description);
						}
						if(objType.equalsIgnoreCase("isWebElementDisplayed")) {
							FunctionLibrary.isWebElementDisplayed(locatorType, locatorValue);
							logger.log(LogStatus.INFO, description);
						}
						if(objType.equalsIgnoreCase("isAttributeTrue")) {
							FunctionLibrary.isAttributeTrue(locatorType, locatorValue);
							logger.log(LogStatus.INFO, description);
						}
						if(objType.equalsIgnoreCase("switchToParentFrame")) {
							FunctionLibrary.switchToParentFrame();
							logger.log(LogStatus.INFO, description);
						}
						if(objType.equalsIgnoreCase("closeBrowser")) {
							FunctionLibrary.closeBrowser();
							logger.log(LogStatus.INFO, description);
						}
						if(objType.equalsIgnoreCase("typeAction")) {
							FunctionLibrary.typeAction(locatorType, locatorValue, testData);
							logger.log(LogStatus.INFO, description);
						}
						status=true;
					} catch (Exception e) {
						Reporter.log(e.getMessage(),true);
						status=false;
					}
					if(status==true) {
						obj.setCellData(TCModule, j, 6, "Pass", excelOutputFilePath);
						logger.log(LogStatus.PASS, description);
					}
					else {
						obj.setCellData(TCModule, j, 6, "Fail", excelOutputFilePath);
						logger.log(LogStatus.FAIL, description);
					}
					reporter.endTest(logger);
					reporter.flush();
				}
				ExecutionStatus = true;
			}
			else {
				//Block TestCase
				ExecutionStatus = false;
			}
			if(ExecutionStatus==true) {
				obj.setCellData(sheetName, i, 4, "Pass", excelOutputFilePath);
			}
			else {
				obj.setCellData(sheetName, i, 4, "Blocked", excelOutputFilePath);
			}
		}
	}
}
