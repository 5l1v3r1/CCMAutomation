package cases;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pages.LoginPage;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class,MethodListener.class })
public class ManuelBildirim extends base.AltYapi{
	
	{
		String atuConfigLocation = String.format("%s%ssrc%smain%sresources%satu.properties",System.getProperty("user.dir"), File.separator, File.separator, File.separator,File.separator, File.separator);
		System.out.println("atuConfigLocation :" + atuConfigLocation);
		System.setProperty("atu.reporter.config", atuConfigLocation);
	}
		
@Test
public void test()  throws Exception{
	init("", "Login");	
	LoginPage lp=PageFactory.initElements(driver, LoginPage.class);	
	lp.doLogin();
}

@AfterMethod
public void testiSonlandir() throws InterruptedException, IOException {
	if(driver!= null) {
		driver.quit();
	}
}

}