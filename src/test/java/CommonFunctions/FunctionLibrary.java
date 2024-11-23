package CommonFunctions;

import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;


public class FunctionLibrary {
	public static WebDriver driver;
	public static Properties p;
	
//	Open Browser
	public static void launchBrowser() throws Throwable{
		p = new Properties();
		p.load(new FileInputStream("ObjectRepository/Object.properties"));
		if(p.getProperty("Browser").equalsIgnoreCase("chrome")) {
			
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
		else if(p.getProperty("Browser").equalsIgnoreCase("FireFox")) {
			
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
		else {
			
			driver = new EdgeDriver();
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
	}
	
//	Enter URL
	public static void launchUrl() {
		driver.get(p.getProperty("url"));
	}
	
//	WaitForWebElement
	public static void WaitForWebElement(String locatorType, String locatorValue, String testData) {
		WebDriverWait wait = new WebDriverWait(driver, Integer.parseInt(testData));
		if(locatorType.equalsIgnoreCase("linkText")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(locatorValue)));
		}
		else if(locatorType.equalsIgnoreCase("xpath")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorValue)));
		}
		else if(locatorType.equalsIgnoreCase("id")) {
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id(locatorValue)));
		}
		else if(locatorType.equalsIgnoreCase("name")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locatorValue)));
		}
		else if(locatorType.equalsIgnoreCase("className")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(locatorValue)));
		}
		else {
			Reporter.log(locatorType+" is not valid");
		}
	}
	
//	Click Web Element
	public static void clickAction(String locatorType, String locatorValue) throws Throwable {
		if(locatorType.equalsIgnoreCase("id")) {
			driver.findElement(By.id(locatorValue)).click();
			Thread.sleep(2000);
		}
		else if(locatorType.equalsIgnoreCase("name")) {
			driver.findElement(By.name(locatorValue)).click();
			Thread.sleep(2000);
		}
		else if(locatorType.equalsIgnoreCase("className")) {
			driver.findElement(By.className(locatorValue)).click();
			Thread.sleep(2000);
		}
		else if(locatorType.equalsIgnoreCase("xpath")) {
			driver.findElement(By.xpath(locatorValue)).click();
			Thread.sleep(2000);
		}
		else if(locatorType.equalsIgnoreCase("linkText")) {
			driver.findElement(By.linkText(locatorValue)).click();
			Thread.sleep(2000);
		}
		else {
			Reporter.log(locatorType+" is not valid");
		}
	}
	
//	Switch to Frame
	public static void switchToIframe(String locatorType, String locatorValue) {
		if(locatorType.equalsIgnoreCase("xpath")) {
			driver.switchTo().frame(driver.findElement(By.xpath(locatorValue)));
			
		}
		else if(locatorType.equalsIgnoreCase("name")) {
			driver.switchTo().frame(driver.findElement(By.name(locatorValue)));
		}
		else if(locatorType.equalsIgnoreCase("id")) {
			driver.switchTo().frame(driver.findElement(By.id(locatorValue)));
		}
		else {
			Reporter.log(locatorType+" is not valid");
		}
	}
	
//	Switch to Main Frame
	public static void switchToParentFrame() {
		driver.switchTo().parentFrame();
	}
	
//	Drag Item
	
	public static void dragItem(String locatorType, String locatorValue) {
		Actions act = new Actions(driver);
		if(locatorType.equalsIgnoreCase("id")) {
			act.moveToElement(driver.findElement(By.id(locatorValue)));
			act.dragAndDropBy(driver.findElement(By.id(locatorValue)), 50, 125);
			act.build().perform();
		}
		else if(locatorType.equalsIgnoreCase("name")) {
			act.moveToElement(driver.findElement(By.name(locatorValue)));
			act.dragAndDropBy(driver.findElement(By.name(locatorValue)), 50, 125);
			act.build().perform();
		}
		else if(locatorType.equalsIgnoreCase("xpath")) {
			act.moveToElement(driver.findElement(By.xpath(locatorValue)));
			act.dragAndDropBy(driver.findElement(By.xpath(locatorValue)), 50, 125);
			act.build().perform();
		}
		else {
			Reporter.log(locatorType+" is not valid");
		}
	}
	
//	Drag And Drop 
	public static void dragAndDrop(String locatorType, String locatorValue, String testData) {
		Actions act = new Actions(driver);
		WebElement dragItem;
		WebElement droppable;
		if(locatorType.equalsIgnoreCase("id")) {
			dragItem = driver.findElement(By.id(locatorValue));
			droppable= driver.findElement(By.xpath(testData));
			act.dragAndDrop(dragItem, droppable);
			act.build().perform();
		}
		else if(locatorType.equalsIgnoreCase("name")) {
			dragItem=driver.findElement(By.name(locatorValue));
			droppable= driver.findElement(By.xpath(testData));
			act.dragAndDrop(dragItem, droppable);
			act.build().perform();
		}
		else if(locatorType.equalsIgnoreCase("xpath")) {
			dragItem = driver.findElement(By.xpath(locatorValue));
			droppable= driver.findElement(By.xpath(testData));
			act.dragAndDrop(dragItem, droppable);
			act.build().perform();
		}
		else {
			Reporter.log(locatorType+" is not valid");
		}
		
	}
	
//	Verify Web Element
	public static void verifyWebElement(String locatorType, String locatorValue) {
		if(locatorType.equalsIgnoreCase("id")) {
			boolean Item=driver.findElement(By.id(locatorValue)).isDisplayed();
			if(Item==true) {
				System.out.println("Web Element Displayed");
			}
			else {
				System.out.println("WebElement Not Displayed");
			}
		}
		else if(locatorType.equalsIgnoreCase("name")) {
			boolean item=driver.findElement(By.name(locatorValue)).isDisplayed();
			if(item==true) {
				System.out.println("Web Element Displayed");
			}
			else {
				System.out.println("WebElement Not Displayed");
			}
		}
		else {
			Reporter.log(locatorType+" is not valid");
		}
	}
	
//	isWebElementDisplayed
	public static void isWebElementDisplayed(String locatorType, String locatorValue) {
		boolean element;
		if(locatorType.equalsIgnoreCase("xpath")) {
			element=driver.findElement(By.xpath(locatorValue)).isDisplayed();
			if(element==true) {
				Reporter.log(locatorType+" is Diplayed", true);
			}
			else {
				Reporter.log(locatorType+" is not Diplayed", true);
			}
		}
		else if(locatorType.equalsIgnoreCase("id")) {
			element=driver.findElement(By.id(locatorValue)).isDisplayed();
			if(element==true) {
				Reporter.log(locatorType+" is Diplayed", true);
			}
			else {
				Reporter.log(locatorType+" is not Diplayed", true);
			}
		}
		else if(locatorType.equalsIgnoreCase("name")) {
			element = driver.findElement(By.name(locatorValue)).isDisplayed();
			if(element==true) {
				Reporter.log(locatorType+" is Diplayed", true);
			}
			else {
				Reporter.log(locatorType+" is not Diplayed", true);
			}
		}
		else {
			Reporter.log(locatorType+" is not valid");
		}
	}
	
//	isAttributeTrue
	public static void isAttributeTrue(String locatorType, String locatorValue) {
		if(locatorType.equalsIgnoreCase("xpath")) {
			String value=driver.findElement(By.xpath(locatorType)).getAttribute("aria-expanded");
			if(value.equalsIgnoreCase("true")) {
				Reporter.log("Area Expanded",true);
			}
			else {
				Reporter.log("Area Collapsed", true);
			}
		}
	}
	
//	Take Screen Shot
	public static void takeScreenShot(String testData) throws Throwable {
		TakesScreenshot ts = (TakesScreenshot)driver;
		File src=ts.getScreenshotAs(OutputType.FILE);
		File trg= new File(testData);
		FileUtils.copyFile(src, trg);
	}
	
//	Close Browser
	public static void closeBrowser() {
		driver.quit();
	}
	
//	TypeAction
	public static void typeAction(String locatorType, String locatorValue, String testData) throws Throwable {
		
		if(locatorType.equalsIgnoreCase("id")) {
			
			driver.findElement(By.id(locatorValue)).sendKeys(testData);
			
			
		}
		else if(locatorType.equalsIgnoreCase("name")) {
			driver.findElement(By.name(locatorValue)).sendKeys(testData);
			Thread.sleep(2000);
			
		}
		else if(locatorType.equalsIgnoreCase("xpath")) {
			driver.findElement(By.xpath(locatorValue)).sendKeys(testData);
			
			
		}
		else if(locatorType.equalsIgnoreCase("className")) {
			driver.findElement(By.xpath(locatorValue)).sendKeys(testData);
			
			
		}
		else {
			Reporter.log(locatorType+" is not valid");
		}
	}
	
//	getText
	
	
//	Testing All Functions-Demo Test
	public static void main(String[] args) throws Throwable {
		launchBrowser();
		launchUrl();
		WaitForWebElement("linkText", "Resizable", "10");
		clickAction("linkText", "Resizable");
		switchToIframe("xpath", "//iframe[@src='/resources/demos/resizable/default.html']");
		dragItem("xpath", "//div[@id='resizable']/div[3]");
		switchToParentFrame();
		closeBrowser();
	}
}
