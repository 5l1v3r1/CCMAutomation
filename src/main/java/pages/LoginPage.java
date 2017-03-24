package pages;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import atu.testng.reports.ATUReports;

public class LoginPage extends base.AltYapi{
	
public WebDriver driver;
public WebElement element;

@FindBy(id="loginForm:username")
public WebElement userName;


@FindBy(id="loginForm:content")
public WebElement password;

@FindBy(id="loginForm:submit")
public WebElement loginButton;

public LoginPage(WebDriver driver)	{
	this.driver=driver;
	wait =new WebDriverWait(driver,50);
}	

public void doLogin() throws FileNotFoundException, InterruptedException, IOException
{
	
	ATUReports.add("Chrome tarayýcýsý açýldý.",false);
	System.out.println("Chrome tarayýcýsý açýldý.");
	wait.until(ExpectedConditions.elementToBeClickable(loginButton));
	write(userName,getConf("username"));
	beklemeSuresiBelirt(1000);
	write(this.password,getConf("password"));
	beklemeSuresiBelirt(1000);
	click(loginButton);		
}
	

	
	
	
}
