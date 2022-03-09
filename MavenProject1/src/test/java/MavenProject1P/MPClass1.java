package MavenProject1P;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;

public class MPClass1 {
	
	
	 private WebDriver driver;		
		@Test				
		public void testEasy() {	
			driver.get("http://demo.guru99.com/test/guru99home/");  
			String title = driver.getTitle();				 
			Assert.assertTrue(title.contains("Demo Guru99 Page")); 		
		}	
		@BeforeTest
		public void beforeTest() {	
			System.setProperty("webdriver.chrome.driver","C:\\SeleniumBrowserDriver\\chromedriver.exe");
		      
		      driver = new ChromeDriver();
		     // driver.get("http://demo.guru99.com/test/guru99home/");
		      driver.manage().window().maximize(); 
		}		
		@AfterTest
		public void afterTest() {
			driver.quit();			
		}
	
}
