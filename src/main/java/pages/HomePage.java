package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;

public class HomePage extends base.AltYapi {
	
	public WebDriver driver;
	public WebElement element;
	
	@FindBy(id="loginForm:username")
	public WebElement username;
	
	
	@FindBy(id="loginForm:content")
	public WebElement password;
	
	@FindBy(id="loginForm:submit")
	public WebElement loginButton;
	
	public HomePage(WebDriver driver)	{
		this.driver=driver;
		wait =new WebDriverWait(driver,50);
	}	
	
	public void doLogin()
	{
		
		
	}
	
	
	
}
