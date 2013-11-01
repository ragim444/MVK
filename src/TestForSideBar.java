import Page.MainPage;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import utils.JdbcUtils;
import utils.MainTest;
import utils.MouseOnMap;

import java.sql.Statement;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestForSideBar     extends MainTest

{
    private MouseOnMap my_mouse;

    JdbcUtils db = new JdbcUtils();

     /*
    String databaseName = System.getenv("databaseName");
    String userName = System.getenv("userName");
    String password = System.getenv("password");
    String sqlURL = System.getenv("sqlURL");
    String hideSideBarUrl = System.getenv("hideSideBarUrl");

      */
    String databaseName = "MVK_INBOX";
    String userName = "sa";
    String password = "Ynao123";
    String sqlURL =      "jdbc:sqlserver://192.168.100.174:1433";
    String hideSideBarUrl = "http://vodokanal.dvaoblaka.ru/#/pos/80.0764/91.2373/5/0";


    @Test
    public void testTransitionToMap () throws Throwable
    {
        //openSite();
      //  waitForLoadMap(60,path + "/default_map.PNG");
        db.updateDB(sqlURL, databaseName, userName, password, driver, mPage);
     //   mPage.requestList.get(0).click();
      //  waitForLoadMap(40,path+"/points/bid/bid.png");
    }

    @Test
    public void testCheckOnControlHint () throws Throwable
    {
        openSite();
        waitForLoadMap(60,path + "/default_map.PNG");
        db.updateDB(sqlURL, databaseName, userName, password, driver, mPage);
        mPage.moveMouseOverPopupLayers(mPage.btnOnCheck, 0, 80);

        assertFalse("Подсказка 'на контроле', не появилась", mPage.hintWindow.getAttribute("style").equals("display: none;"));
        assertTrue("Подсказка некорректна", mPage.hintWindow.getText().equals("На контроле генерального директора\n" +
                                                                   "На контроле ЦДУ\n" +
                                                                   "На контроле ФСО"));
    }

    @Test
    public void testCheckRequestListWithFilter () throws Throwable
    {
        openSite();
        waitForLoadMap(60,path + "/default_map.PNG");
        db.updateDB(sqlURL, databaseName, userName, password, driver, mPage);

        //Открываем дерево слоев
        if (mPage.popupLayer.getAttribute("style").contains("display: none;")){mPage.btnPopupLayers.click();}
        //Переводим курсор на первый жоемент дерева
        mPage.moveMouseOverPopupLayers(mPage.firstLvlTree.get(1).findElement(By.cssSelector("span.custom_checkbox")),10,50);
        //Выключаем общий чекбокс элементов второго уровня
        if (mPage.getCheckBoxCondition(mPage.chkSecondLvlTree))
        {mPage.chkSecondLvlTree.click();}

        mPage.requestList = null;
        mPage = PageFactory.initElements(driver, MainPage.class);
                   robot.delay(5000);
        assertTrue("Список заявок сформирован некорректно с включенным фильтром",mPage.requestList.size()==20);
    }

    @Test
    public void testHideSideBarByButton () throws Throwable
    {
        openSite();
        mPage.btnHideSideBar.click();
        robot.delay(1000);
        assertTrue("","left: -300px; display: none;".equals(mPage.sideBar.getAttribute("style")));
        mPage.btnShowSideBar.click();
        robot.delay(1000);
        assertTrue("left: 0px;".contains(mPage.sideBar.getAttribute("style")));
    }

    @Test
    public void testHideSideBarByUrl() throws Throwable
    {
        openSite();
        driver.get(hideSideBarUrl);
        driver.navigate().refresh();
        waitForLoadMap(40,path+"/default_map.png");
        assertTrue("Сайдбар не был скрыт","display: none; left: -300px;".equals(mPage.sideBar.getAttribute("style")));
        driver.get(baseUrl);
        driver.navigate().refresh();
        waitForLoadMap(40,path+"/default_map.png");

    }

    @Test
    public void testBidSorting () throws Throwable
    {

        openSite();
        waitForLoadMap(40,path+"/default_map.png");
        db.updateDB(sqlURL, databaseName, userName, password, driver, mPage);
        String[] response;
        String[] expectedArray = new String[20];
        Statement statement =  db.getConnection(sqlURL, databaseName, userName, password);
        response = db.sortRequest(statement);
        int j = 0;
        for (WebElement element:mPage.requestList )
        {
          expectedArray[j] = element.findElement(By.cssSelector("div.requestDescription span.requestName")).getText() +", "+
                  element.findElement(By.cssSelector("div.requestDescription span.requestDate")).getText() ;
            j++;
        }

       // assertTrue(response.equals(expectedArray));
        for (int k = 0; k< expectedArray.length;k++)
        {
            assertTrue("Сортировка некорректна",response[k].toString().equals( expectedArray[k].toString()));
        }



        statement.close();




    }


}
