package MyProfile;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class T01_MyProfile {
	
	public String baseUrl = "https://wordpress.com/me";
    String driverPath = "C:\\SeleniumBrowserDriver\\chromedriver.exe";
    public WebDriver driver ; 
	
    Date currentdate = new Date();
    public String timestamp = currentdate.toString().replace(",","-").replace(":","-").replace(" ", "-");  // Creating Time Stamp for screen shots
	 
 
  @Test (priority=1 )
  
  public void launchBrowser() {
	  
	  
	  System.setProperty("webdriver.chrome.silentOutput", "true");
	  
	  System.out.println("launching chome browser"); 
      System.setProperty("webdriver.chrome.driver",driverPath);
      
      driver = new ChromeDriver();
      driver.get(baseUrl);
      driver.manage().window().maximize();
      driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);	
      String title=driver.getTitle();				// Page Title
      System.out.println("Page Title = "+title); 
  }
    
    
    
    @Test (priority=2)
  
  public void verifyHomepageTitle() 
   {
      
      
      String expectedTitle = "Log In — WordPress.com";
      String actualTitle = driver.getTitle();
      AssertJUnit.assertEquals(actualTitle, expectedTitle);
      driver.findElement(By.id("usernameOrEmail")).sendKeys("braimin@gmail.com");     // User Name
      driver.findElement(By.xpath("//button[@type='submit']")).click();
      
      driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("Metro12345");   // Password
      driver.findElement(By.xpath("//button[@type='submit']")).click();
      String expectedUser= driver.findElement(By.xpath("//*[@id=\"secondary\"]/ul/li/div[1]/h2")).getText();    // Get LoggedIn User
      AssertJUnit.assertEquals("braimin", expectedUser);														// Logged in User CheckPoint
      System.out.println(expectedUser+" has been successfully logged into My Profile page. "); 
     
  }
    
    
   @Test (priority=3)
    
    public void dataEntry() throws InterruptedException, IOException               // Data entry at home page
     {
        
    	driver.findElement(By.id("first_name")).sendKeys("Qaisar");
    	driver.findElement(By.id("last_name")).sendKeys("Amin");
    	driver.findElement(By.id("description")).sendKeys("Amin");
    	driver.findElement(By.xpath("//button[@type='submit']")).click();           // Form Submit
    	
    	File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);                  // Take Screenshot of the submitted form with data
        FileUtils.copyFile(screenshotFile, new File(".//screenshots/DataEntry-"+timestamp+".png"));         // Save Screenshot of the submitted form with data
    	
     }	
       
   
   @Test (priority=4)
        
        public void chkBoxValidation() throws IOException                                      // Check Box Validation
         {
            
	   		System.out.println("================Check Box functionality Validation ============ ");
        	WebElement chkBox= driver.findElement(By.id("inspector-toggle-control-0"));
        	System.out.println(" -- Checkbox Functionality Validation --  ");
        	    								
            for (int i=0; i<2; i++) 
            {											
                chkBox.click (); 			
                System.out.println("Checkbox Status is=   "+chkBox.isSelected());					//Select and Deselect the CheckBox	
                
                File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);					  // Take Screenshot of the CheckBox Selection
                FileUtils.copyFile(screenshotFile, new File(".//screenshots/CheckBox-"+i+timestamp+".png"));         // Save Screenshot 
                
            }	
       
  
         }
    
   
   @Test (priority=5)
   
   public void urlEntry() throws IOException                      // Adding URL
    {
       
   	
	   	System.out.println("================ADD URL functionality Validation ============ ");
	   	driver.findElement(By.xpath("//*[@id=\"primary\"]/main/div[3]/div[2]/button")).click();   // Click on Add button
	   	driver.findElement(By.xpath("/html/body/div[4]/div/div[2]/div/button[2]")).click();    // Click on Add URL option
	   	driver.findElement(By.name("value")).sendKeys("https://wordpress.com/me");				// Add the URL
	   	driver.findElement(By.name("title")).sendKeys("Wordpress Profile");						// Add the description
	   	driver.findElement(By.xpath("//*[@id=\"primary\"]/main/div[4]/form/fieldset/button[1]")).click();  // Click on Add Cite
	   	System.out.println("URL has been added succesfully. ");
   		
	   	File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);			  // Take Screenshot of the CheckBox Selection
        FileUtils.copyFile(screenshotFile, new File(".//screenshots/URL-"+timestamp+".png"));         // Save Screenshot 
    }	
      
   
 @Test (priority=6)
   
   public void urlDuplicate() throws InterruptedException , IOException                           // Avoid Duplicate Entry of the URL

    {
	 
	 	System.out.println("================URL Duplicate functionality Validation ============ ");
	 	driver.findElement(By.xpath("//*[@id=\"primary\"]/main/div[3]/div[2]/button")).click();   // Click on Add button
	   	driver.findElement(By.xpath("/html/body/div[4]/div/div[2]/div/button[2]")).click();    // Click on Add URL option
	   	driver.findElement(By.name("value")).sendKeys("https://wordpress.com/me");				// Add the URL
	   	driver.findElement(By.name("title")).sendKeys("Wordpress Profile");						// Add the description
	   	driver.findElement(By.xpath("//*[@id=\"primary\"]/main/div[4]/form/fieldset/button[1]")).click();  // Click on Add Cite
	
	   	Thread.sleep(1000);
	   		  
	    String actualNoticeText= "That link is already in your profile links. No changes were made."; 	
	    String expectedNoticeText=driver.findElement(By.xpath("//*[@id=\"primary\"]/main/div[4]/div/div/span[2]/span")).getText();
	
		if (actualNoticeText.equals(expectedNoticeText))
   		{
			File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);			  // Take Screenshot of the popup
	        FileUtils.copyFile(screenshotFile, new File(".//screenshots/URL-"+timestamp+".png"));         // Save Screenshot 
			
			System.out.println("Duplicate Links are not allowed=  "+expectedNoticeText);
   			driver.findElement(By.xpath("//*[@id=\"primary\"]/main/div[4]/div/div/button")).click();    // Close the popup
   		}
   	else
   		{
   		System.out.println("URL has been added succesfully. ");
   		
   		}  

    }
 

 @Test (priority=7)
 
 public void urlDelete()                     // Validation of Delete button
  {
  
	 System.out.println("================URL Delete functionality Validation ============ ");
 	driver.findElement(By.xpath("//*[@id=\"primary\"]/main/div[4]/div/ul/li/button")).click();     // Click on Delete button X
 	String actualText="Manage which sites appear in your profile.";
 	String expectedText= driver.findElement(By.xpath("//*[@id=\"primary\"]/main/div[4]/div/p[1]")).getText();   // Get the text after delete
 	
 	if (actualText.equals(expectedText))
 		{
 			System.out.println("URL has been deleted, Delete functionality is working ");
 		}
 	else
 		{
 		System.out.println("URL is still there, Delete functionality is Not working ");
 		
 		}
  }
 
 
    @AfterTest
    
    public void terminateBrowser() {
    	
    	
    	driver.close();
    }
}
