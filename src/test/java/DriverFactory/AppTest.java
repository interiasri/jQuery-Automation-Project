package DriverFactory;

import org.testng.annotations.Test;

public class AppTest {
	
	@Test(priority = 1, enabled = false)
	public void interActions() throws Throwable {
		DriverScript_InterActions runTest = new DriverScript_InterActions();
		runTest.kickStartInteractions();
	}
	
	@Test(priority = 2)
	public void widgets() throws Throwable {
		DriverScript_Widgets test = new DriverScript_Widgets();
		test.kickStartWidgets();
	}
	
}
