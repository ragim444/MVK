package utils;

import Page.MainPage;
import Page.WellPage;
import esteru.selenium.factory.WebDriverFactory;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;

import static org.junit.Assert.assertTrue;

public class MainTest
{
    protected WebDriver driver;
    protected String baseUrl;
    protected String browser;
    protected Robot robot;
    protected MainPage mPage;
    protected String path;
    protected WellPage wellPageControls;

    @Before
    public void setUp () throws Throwable
  {
      robot = new Robot();

      baseUrl = "http://vodokanal.dvaoblaka.ru/#/pos/80.0764/91.2373/5/1";
     //baseUrl = "http://vodokanal.dvaoblaka.ru/#/well/Wells/22986";
      path = "/MVK";
      browser = "firefox";

             /*
      baseUrl = System.getenv("baseURL");
      path = System.getenv("path");
      browser = System.getenv("browser");
              */
      getDriver(browser);

  }


    public  void getDriver (String browser) throws Throwable
    {
        /*
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setBrowserName(System.getProperty("browser"));
        driver = WebDriverFactory.getDriver(caps) ;
        */

        if (browser.equals("firefox")) {driver = WebDriverFactory.getDriver(DesiredCapabilities.firefox()); }
        if (browser.equals("chrome")) {driver = WebDriverFactory.getDriver(DesiredCapabilities.chrome()); }
        if (browser.equals("internetExplorer")) {driver = WebDriverFactory.getDriver(DesiredCapabilities.internetExplorer()); }

        driver.manage().window().maximize();
    }


    //Инициализация
    protected void openSite() throws Throwable {
       try
        {
        driver.get(baseUrl);
        new WebDriverWait(driver, 40).until(ExpectedConditions.visibilityOfElementLocated(By.id("btnPopupLayers")));

        mPage = PageFactory.initElements(driver, MainPage.class);
        //Ожидание получения списка слоев
        (new WebDriverWait(driver, 20)).until(new ExpectedCondition<Boolean>()
        {
            public Boolean apply(WebDriver d)
            {return (mPage.firstLvlTree.size() > 0 );}});

        mPage.openPopupLayers();

        mPage.switchOnAllLayers();
            mPage.closePopupLayers();

         }
        catch (Exception e)
        {
            assertTrue("Ошибка инициализации",false);
        }

    }
    protected  void openMultimap() throws Throwable
    {
        driver.get(baseUrl);
        new WebDriverWait(driver, 40).until(ExpectedConditions.invisibilityOfElementLocated(By.className("well_popup")));
        wellPageControls = PageFactory.initElements(driver, WellPage.class);
    }


    //Ожидание загрузки карты
    public void waitForLoadMap(int sec,final String mapPath)     throws Throwable
    {
        (new WebDriverWait(driver, sec)).until(new ExpectedCondition<Boolean>()
        {public Boolean apply(WebDriver d)
            {return (MouseOnMap.findMap1(mapPath) != (null));}});
    }



}
