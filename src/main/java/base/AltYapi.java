package base;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.eobjects.metamodel.DataContext;
import org.eobjects.metamodel.DataContextFactory;
import org.eobjects.metamodel.data.DataSet;
import org.eobjects.metamodel.query.Query;
import org.eobjects.metamodel.schema.Schema;
import org.eobjects.metamodel.schema.Table;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;

public class AltYapi extends DBConnect{
	public WebDriver driver;
	public WebDriverWait wait;
	public String url;
	
	public static String testDriver;
	public WebElement element;
	public String[] secimlerListesi = new String[0];
	public Actions action;	
	private DesiredCapabilities capability = null;
	private static String DEFAULT_BROWSER= "chrome";
	public Properties prop;
	
	/*static {
		String atuConfigLocation = "atu.properties";
		System.out.println("atuConfigLocation :" + atuConfigLocation);
		System.setProperty("atu.reporter.config", atuConfigLocation);
	}*/
		
	/**
	 * @return the defaultBrowser
	 */

	/**
	 * @param defaultBrowser the defaultBrowser to set
	 */


	//ATU raporu iï¿½ine verilmek istenen bilgilerin yazï¿½ldï¿½ï¿½ï¿½ fonksiyondur...
	public void raporBilgileriOlustur(){
		final Date now = new Date();
		final SimpleDateFormat dateFormatter = new SimpleDateFormat("ddMMyyyy'_'HHmm");
		ATUReports.setTestCaseReqCoverage("Bu otomatik test, test keyz aaa nin otomatize edilmiþtir.");
		ATUReports.setAuthorInfo("Test Otomasyon Ekibi", dateFormatter.format(now).toString(), "1.0");
		ATUReports.indexPageDescription = "Telaura_PSTN BUGFIX Ortamý Regresyon Seti Otomatik Test Kosumu Sonuclari";
	}

	 public String[][] exceldenVeriAl (String location){
         DataContext dataContext = DataContextFactory.createExcelDataContext(new File(location));
         Schema schema = dataContext.getDefaultSchema();
         Table table = schema.getTables()[0];
         int column = table.getColumnCount();
         Query t = new Query().selectCount().from(table);
         DataSet dataSet = dataContext.executeQuery(t);
         dataSet.next();
         int row=Integer.parseInt(dataSet.getRow().getValue(0).toString());
         Query q = new Query().from(table).selectAll();
         dataSet = dataContext.executeQuery(q);
         String[][] list=new String[row][column];
         int i=0;
         while (dataSet.next()){
               for(int j=0;j<column;j++){
                    if(dataSet.getRow().getValue(j)==null){
                         list[i][j]="";
                    }
                    else{
                         list[i][j]=dataSet.getRow().getValue(j).toString();
                    }
               }
               i++;
         }
         dataSet.close();
         return list;
    }
	
	public String getConf(String value) throws FileNotFoundException, IOException
	{
		prop = new Properties();
		prop.load(new FileInputStream("config.properties"));
		String val = prop.getProperty(value);
		return val;
	}
	
	public String getConfDepend(String value) throws FileNotFoundException, IOException
	{
		prop = new Properties();
		prop.load(new FileInputStream("siparis.properties"));
		String val = prop.getProperty(value);
		return val;
	}
	
	public void setConf(String key, String value) throws FileNotFoundException, IOException
	{
		prop = new Properties();
		prop.put(key, value);
		FileWriter writer = new FileWriter("siparis.properties");
		prop.store(writer, "host settings");
	}
	
	public void restart()
	{
		
		driver.close();
		driver.get(url);

	}

	public void init(final String browserName, String testAdi) throws FileNotFoundException, IOException{
		raporBilgileriOlustur();		
		String url = getConf("url");
		//String url="http://dev1.tt.etiya.com/telaura/faces/telaura.jspx";
		//String seleniumHubUrl = getConf("selenium.hub.url");
		String chromeDriverLoc = null;		
		//useRemoteDriver=false;

		  System.setProperty("webdriver.chrome.driver", "c:\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-extensions");	
		options.addArguments("--start-maximized");
		options.addArguments("--disable-notifications");

		this.driver=new ChromeDriver(options);
		ATUReports.setWebDriver(driver);
		//driver.manage().window().maximize();		
		driver.get(url);
		wait = new WebDriverWait(driver, 120);
		//driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		//driver = new ChromeDriver(options);
		System.out.println(this.getClass().getName()+" adlý test baþlatýlmýþtýr.");

		//driver.manage().window().maximize();
	}
	
	public void kulakCek(List<WebElement> list)
	{
		for(int i=0;i<list.size();i++)
		{
			if(list.get(i).isDisplayed())
			{
				
				list.get(i).click();
			}
		}
		
	}

	public void navigate() throws FileNotFoundException, IOException{
		String configFileLocation = String.format("%s%sconfig.properties", System.getProperty("user.dir"), File.separator, File.separator, File.separator, File.separator);
		Properties prop = new Properties();
		prop.load(new FileInputStream(configFileLocation));
		String url = prop.getProperty("url");
		driver.navigate().to(url);
	}

	@SuppressWarnings("deprecation")
	public void click(WebElement element) throws InterruptedException{
		try{

			wait.until(ExpectedConditions.visibilityOf(element));
			wait.until(ExpectedConditions.elementToBeClickable(element));
			String id =element.getAttribute("id");
			beklemeSuresiBelirt(500);
			
			element.click();
		
			ATUReports.add("Xpath üzerine týklanmýþtýr.", id, "", "PASS", false);
		}
//
		catch(StaleElementReferenceException e){
			System.out.println("s");			
			click(element);
		}
		
		catch(Exception e){
			beklemeSuresiBelirt(1500);
			ATUReports.add(e.getMessage(), "hata", "", "FAIL", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			driver.quit();
			
			try{
				throw new Exception(e.getMessage());
			}
			
			catch (Exception e1){
				e1.printStackTrace();
			}
		}		
	}
	
	@SuppressWarnings("deprecation")
	public void pickToSelectText(WebElement element,String metin) throws InterruptedException{
		try{
			wait.until(ExpectedConditions.visibilityOf(element));
			wait.until(ExpectedConditions.elementToBeClickable(element));
			Select elements=new Select(element);
			elements.selectByVisibleText(metin);
   		   	ATUReports.add("Comboboxtan metin secildi.", metin, "", "PASS", false);
		
		}
		catch(StaleElementReferenceException e){			
			pickToSelectText(element, metin);
		}
		
		catch(Exception e){
			beklemeSuresiBelirt(1500);
			ATUReports.add("Comboboxtan metin secilemedi.", e.getMessage(), "", "FAIL", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
    }

	@SuppressWarnings("deprecation")
	public void pickToSelectIndex(WebElement element,int index) throws InterruptedException{
		try{
			wait.until(ExpectedConditions.visibilityOf(element));
			wait.until(ExpectedConditions.elementToBeClickable(element));
			Select elements=new Select(element);
			elements.selectByIndex(index);
   		   	ATUReports.add("Comboboxtan metin secildi.", "1", "", "PASS", false);
		}
		
		catch(Exception e){
			beklemeSuresiBelirt(1500);
			ATUReports.add("Comboboxtan metin secilemedi.", "Hata", "", "FAIL", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
    }

	@SuppressWarnings("deprecation")
	public void write(WebElement element,String metin) throws InterruptedException{
		try{
			wait.until(ExpectedConditions.visibilityOf(element));
			beklemeSuresiBelirt(700);
			element.clear();
			beklemeSuresiBelirt(500);
			element.sendKeys(metin);
			element.sendKeys(Keys.TAB);
			ATUReports.add("Xpath parçasý ile yazýlmýþtýr.", element.getAttribute("id"), metin, "PASS", false);
		}
		
		catch(StaleElementReferenceException e){
			write(element, metin);
		}
		
		catch(final Exception exception){
			beklemeSuresiBelirt(1500);
			ATUReports.add("Xpath parçasý ile yazýlamamýþtýr.", "yazýlamadý", metin, "FAIL", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			driver.quit();
			try{
				throw new Exception(exception.getMessage());
			}
			
			catch (Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public void beklemeSuresiBelirt(long i) throws InterruptedException{
		Thread.sleep(i);	
	}
	
	public void changeFrame(){
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.cssSelector("iframe[title='Ýçerik']")));
		try{
			beklemeSuresiBelirt(2000);
		}
		
		catch (InterruptedException e){
			e.printStackTrace();
		}	
	}
	
	public String generateString(Random rng, String characters, int length){
        char[] text = new char[length];
        for (int i = 0; i < length; i++){
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        
        return new String(text);
    }
	
	
	
	public void xpathParcasiMesajKontrolEt(WebElement element,String mesaj) throws InterruptedException{ 
		try{
			wait.until(ExpectedConditions.textToBePresentInElement(element, mesaj));
			System.out.println(mesaj+" "+ " görüldü");
			ATUReports.add("Menü elemaný görülmüþtür.", element.getAttribute("id"), mesaj, "PASS", false);
		}
		
		
		catch(final Exception exception){
			beklemeSuresiBelirt(1500);
			ATUReports.add("Menü elemaný görülmemiþtir.", "Görülemedi", mesaj, "FAIL", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			driver.quit();
			try{
				throw new Exception(exception.getMessage());
			}
			
			catch (Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public void pickAllCheckbox(List<WebElement> checkBoxLists){		
		for(WebElement checkBox:checkBoxLists){
			if(checkBox.isEnabled()&&checkBox.isDisplayed()){
				checkBox.click(); 
			}
		}
	}
	
	public void pickAllSelectBox(List<WebElement> selectLists) throws InterruptedException{
		for(WebElement selectBox:selectLists){
			if(selectBox.isEnabled()){
				pickToSelectIndex(selectBox, 1);
			}
        }
	}
	//*[@id="r1:0:r22:0:i6:0:pgl16"]/tbody/tr/td[3]/text()
	public boolean isAlertPresent() {

		  boolean presentFlag = false;

		  try {

		   // Check the presence of alert
		   Alert alert = driver.switchTo().alert();
		   // Alert present; set the flag
		   presentFlag = true;
		   // if present consume the alert
		   alert.accept();

		  } catch (NoAlertPresentException ex) {
		   // Alert not present
		   ex.printStackTrace();
		  }

		  return presentFlag;

		 }
	public String metniAl(WebElement element) throws InterruptedException{
		String s = "";
		try{
			wait.until(ExpectedConditions.visibilityOf(element));
			beklemeSuresiBelirt(1500);
			s=element.getText();
			System.out.println(s);
			ATUReports.add("Metin Alýnmýþtýr.", s, "", "PASS", false);
		}

		catch(StaleElementReferenceException e){
			System.out.println("hata");			
			//click(element);
		}
		
		catch(Exception e){
			beklemeSuresiBelirt(1500);
			ATUReports.add(e.getMessage(), "hata/metin alýnamamýþtýr.", "", "FAIL", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			driver.quit();
			
			try{
				throw new Exception(e.getMessage());
			}
			
			catch (Exception e1){
				e1.printStackTrace();
			}
		}
		return s;		
	}
	
	public String addDay(int dayIncrement)
	{
		Calendar now = Calendar.getInstance();   // Gets the current date and time
		int year = now.get(Calendar.YEAR);       // The current year
		int day=now.get(Calendar.DAY_OF_MONTH);
		int month=now.get(Calendar.MONTH);
		
		SimpleDateFormat format=new SimpleDateFormat("dd.MM.yyyy");

		GregorianCalendar gc = new GregorianCalendar(year, month, day+dayIncrement);
	    java.util.Date mn = gc.getTime();
	   return format.format(mn);
		
	}

}
