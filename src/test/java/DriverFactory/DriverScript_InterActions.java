package DriverFactory;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import CommonFunctions.FunctionLibrary;
import Utility.ExcelFileUtils;

public class DriverScript_InterActions {
	
	ExtentReports reporter;
	ExtentTest logger;
	public void kickStartInteractions() throws Throwable {
		ExcelFileUtils obj = new ExcelFileUtils(ExcelFileUtils.inputFilePath);
		int rowQty=obj.rowCount(ExcelFileUtils.wb.getSheetAt(0).getSheetName());
		System.out.println("Total Number of Rows: "+rowQty);
		String sheetName=ExcelFileUtils.wb.getSheetAt(0).getSheetName();
		boolean ExeStatus = false;
		boolean testStepStatus = false;
		for(int i=1; i<=rowQty; i++) {
			
			String TCModule=obj.getCellData(sheetName, i, 1);
			String runStatus= obj.getCellData(sheetName, i, 3);
			reporter = new ExtentReports("AdvancedReports/AutomationTesting_"+TCModule+".html");
			if(runStatus.equalsIgnoreCase("Y")) {
				logger=reporter.startTest(TCModule);
				logger.assignAuthor("Sridhar Parutabad");
				// Run TestCase
				ExeStatus = true;
				int rQty=obj.rowCount(TCModule);
				System.out.println("Number of Rows is "+TCModule+": "+rQty);
				for(int j=1; j<=rQty; j++) {
					String description = obj.getCellData(TCModule, j, 1);
					
					String objType=obj.getCellData(TCModule, j, 2);
					String locatorType=obj.getCellData(TCModule, j, 3);
					String locatorValue = obj.getCellData(TCModule, j, 4);
					String testData = obj.getCellData(TCModule, j, 5);
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
						if(objType.equalsIgnoreCase("switchToParentFrame")) {
							FunctionLibrary.switchToParentFrame();
							logger.log(LogStatus.INFO, description);
						}
						if(objType.equalsIgnoreCase("dragItem")) {
							FunctionLibrary.dragItem(locatorType, locatorValue);
							logger.log(LogStatus.INFO, description);
						}
						if(objType.equalsIgnoreCase("takeScreenShot")) {
							FunctionLibrary.takeScreenShot(testData);
							logger.log(LogStatus.INFO, description);
						}
						if(objType.equalsIgnoreCase("dragAndDrop")) {
							FunctionLibrary.dragAndDrop(locatorType, locatorValue, testData);
							logger.log(LogStatus.INFO, description);
						}
						if(objType.equalsIgnoreCase("closeBrowser")) {
							FunctionLibrary.closeBrowser();
							logger.log(LogStatus.INFO, description);
						}
						testStepStatus = true;
						
					} catch (Exception e) {
						// TODO: handle exception
						testStepStatus=false;
					}
					if(testStepStatus==true) {
						obj.setCellData(TCModule, j, 6, "Pass", ExcelFileUtils.outputFilePath);
						logger.log(LogStatus.PASS, description);
					}
					else {
						obj.setCellData(TCModule, j, 6, "Fail", ExcelFileUtils.outputFilePath);
						logger.log(LogStatus.FAIL, description);
					}
					reporter.endTest(logger);
					reporter.flush();
				}
				
				ExeStatus = true;
			}
			else {
				//Block TestCase
				ExeStatus = false;
			}
			
			if(ExeStatus==true) {
				obj.setCellData(sheetName, i, 4, "Pass", ExcelFileUtils.outputFilePath);
				
			}
			else {
				obj.setCellData(sheetName, i, 4, "Blocked", ExcelFileUtils.outputFilePath);
			}
		}
		
	}

}
