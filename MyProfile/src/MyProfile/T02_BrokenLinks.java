package MyProfile;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Driver;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;


public class T02_BrokenLinks {
	
	
	public String baseUrl = "https://wordpress.com/me";
	public String domainUrl = "https://wordpress.com";
    String driverPath = "C:\\SeleniumBrowserDriver\\chromedriver.exe";
    public WebDriver driver ; 
    
    
    @Test (priority=1 )
    
    public void launchBrowser() {
  	  
  	  
  	  System.setProperty("webdriver.chrome.silentOutput", "true");
  	  
  	  System.out.println("launching chome browser"); 
        System.setProperty("webdriver.chrome.driver",driverPath);
        
        driver = new ChromeDriver();
        driver.get(baseUrl);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);	
        String title=driver.getTitle();
        System.out.println("Page Title = "+title); 
    }
      
      
      
      @Test (priority=2)
    
    public void verifyHomepageTitle() 
     {
        
        
        String expectedTitle = "Log In — WordPress.com";
        String actualTitle = driver.getTitle();
        AssertJUnit.assertEquals(actualTitle, expectedTitle);
        driver.findElement(By.id("usernameOrEmail")).sendKeys("braimin@gmail.com");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        
        driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("Metro12345");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        String expectedUser= driver.findElement(By.xpath("//*[@id=\"secondary\"]/ul/li/div[1]/h2")).getText();
        AssertJUnit.assertEquals("braimin", expectedUser);
        System.out.println(expectedUser+" has been successfully logged into My Profile page. "); 
       
    }
    
    
 
      @Test (priority=3)
      

      public void BrokenLinksValidation() {
    	  
      String url = "";
    
      HttpURLConnection huc = null;
      int respCode = 200;

      List<WebElement> links = driver.findElements(By.tagName("a"));

      Iterator<WebElement> it = links.iterator();

      while(it.hasNext()){

      url = it.next().getAttribute("href");
     
      if(url == null || url.isEmpty()){
      System.out.println(url  +" ---URL is either not configured for anchor tag or it is empty");
      continue;
      }

      if(!url.startsWith(domainUrl)){
      System.out.println(url+" ---URL belongs to another domain, skipping it.");
      continue;
      }

      try {
      huc = (HttpURLConnection)(new URL(url).openConnection());

      huc.setRequestMethod("HEAD");

      huc.connect();

      respCode = huc.getResponseCode();

      if(respCode >= 400){
      System.out.println(url +" ---is a broken link");
      }
      else{
      System.out.println(url +" --- is a valid link");
      }

      } catch (MalformedURLException e) {
      //  catch block
      e.printStackTrace();
      } catch (IOException e) {
      //  catch block
      e.printStackTrace();
      }
      }

      driver.quit();

      }		
			
}
