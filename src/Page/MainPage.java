package Page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.List;
import java.util.*;

public class MainPage
{
    Robot robot;
    @FindBy(css = "div[class ^=olControlNavigationItem]")
    public WebElement btnMove;

    @FindBy(css = "div[class ^=olControlZoomBoxItem]")
    public WebElement btnZoom;

    @FindBy(css = "div[class ^=olControlMeasureItem]")
    public WebElement btnMeasure;

    @FindBy(css = "div[class ^=olControlGetInfoInPointItem]")
    public WebElement btnInfo;

    @FindBy(id = "olControlOverviewMapMaximizeButton")
    public WebElement btnNavigatorMaximize;

    @FindBy(id = "OpenLayers_Control_minimizeDiv")
    public WebElement btnNavigatorMinimize;

    @FindBy (css = "div#popupLayers")
    public WebElement popupLayer;


    @FindBy(id="dvmaps_search_text")
    public WebElement txtSearch;

    @FindBy(id="not_far_than_a_toggler")
    public WebElement lnkRadius;

    @FindBy(css = "#dvmaps_search_form > button:nth-child(2)")
    public WebElement btnSearch;
    public WebElement txtRadius;

    @FindBy (id = "dvmaps_interactive_map")
    public WebElement map;

    @FindBy (id = "btnPopupLayers")
    public WebElement btnPopupLayers;

    public boolean isOpenNavigator()
    {
        return !"none".equalsIgnoreCase(btnNavigatorMinimize.getCssValue("display"));
    }

    public Integer getZoomSliderPosition()
    {
        sldZoomSlider=sldZoomBar.findElement(By.xpath("div[3]"));
        return Integer.parseInt(sldZoomSlider.getCssValue("top").substring(0,2));
    }

    @FindBy(xpath="//div[@id='externControlPanZoom']")
    public WebElement sldZoomBar;

    @FindBy (id="OpenLayers.Control.ZoomSlider_106_OpenLayers.Map_102")
    public WebElement sldZoomSlider;

    @FindBy (css = "div.sz_tabs a.sz_toggle_hide")
    public WebElement btnHideSideBar;

    @FindBy (css = "div#sidebarZ div.sz_menu_container")
    public WebElement sideBar;

    @FindBy (css = "div#sidebarZ div.sz_toggle_wrapper a.sz_toggle_show")
    public WebElement btnShowSideBar;



    //Перемещает курсор мыши над чекбоксом, первого элемента дерева, первого уровня
    public void moveMouseOverPopupLayers (WebElement element,int offsetX, int offsetY) throws Throwable
    {
        robot = new Robot();
        int x = element.getLocation().getX();
        int y = element.getLocation().getY();
        robot.mouseMove(x+offsetX,y+offsetY);
    }


                                                                            // popup Слои
    //////////////////////////////////////////////////////////////////////////////////////////

    //Коллекция элементов первого уровня дерева (слой "Заявки")
    @FindBy(css = "div#popupLayers ul li.first_level")
    public java.util.List<WebElement> firstLvlTree;

    //Коллекция элементов второго уровня дерева (слой "Заявки")
    @FindBy(css = "div#popupLayers ul li.second_level ul.slideDown-show li.ng-scope")
    public java.util.List<WebElement> secondLvlTreeItems;


    //Чекбокс второго уровня дерева (слой "Заявки")
    @FindBy(css = "div#popupLayers ul li.second_level span.custom_checkbox")
    public WebElement chkSecondLvlTree;


    public Boolean getStateSecondLvlExpander (WebElement element)
    {
        return element.getAttribute("class").toString().contains("controls8-minus");
    }

    //Экспандер второго уровня дерева (слой "Заявки")
    @FindBy (css = "div#popupLayers ul li.second_level span[class^=controls8]")
    public WebElement btnSecondLvlExpander;

    //Включение/отключение видимости слоя




    public void switchOffLayerCheckBox(WebElement element)
{
    while (!element.getAttribute("class").equals("custom_checkbox unchecked"))
    {
        element.click();
    }
}

    public void switchOnLayerCheckBox(WebElement element)
    {
        while (!element.getAttribute("class").equals("custom_checkbox checked"))
        {
            element.click();
        }
    }



    public Boolean getCheckBoxCondition(java.util.List<WebElement> tree)
    {
        int checkItems = 0;

        for (WebElement element : tree )
        {
            if (element.findElement(By.cssSelector("span.custom_checkbox")).getAttribute("class").equals("custom_checkbox checked"))
            {checkItems++;}
        }
        return checkItems > 0;
    }

    public Boolean getCheckBoxCondition(WebElement element)
    {

        return (element.getAttribute("class").equals("custom_checkbox checked"));
    }

    //Включает все слои
    public void switchOnAllLayers () throws Throwable
    {
        //Открываем дерево слоев
        if (popupLayer.getAttribute("style").equals("display: none;")){btnPopupLayers.click();}

        //Переводим курсор на первый жоемент дерева
        moveMouseOverPopupLayers(firstLvlTree.get(1).findElement(By.cssSelector("span.custom_checkbox")),10,50);
        //Включаем все элементы первого уровня
        for (WebElement element:firstLvlTree )
        {
            switchOnLayerCheckBox(element.findElement(By.cssSelector("span.custom_checkbox")));
        }
        //Включаем общий чекбокс элементов второго уровня
        if (!getCheckBoxCondition(chkSecondLvlTree))
        {chkSecondLvlTree.click();}
    }


    public void closePopupLayers() throws Throwable
    {
        if (!popupLayer.getAttribute("style").equals("display: none;")){btnPopupLayers.click();}
    }

    //Открывает popup Слои
    public void openPopupLayers () throws Throwable
    {
    //Открываем дерево слоев
        if (popupLayer.getAttribute("style").equals("display: none;")){btnPopupLayers.click();}
        moveMouseOverPopupLayers(firstLvlTree.get(1).findElement(By.cssSelector("span.custom_checkbox")),10,50);
    }

          ///////////////////////////////////////////// Сайдбар заявки

    //Коллекция элементов второго уровня дерева (слой "Заявки")
    @FindBy(css = "div#sidebarZ_resources_menu ul.requestList li.ng-scope")
    public java.util.List<WebElement> requestList;

    @FindBy (css = "div.requestDescription span.requestName")
    public WebElement requestName;

    @FindBy (css = "div.requestDescription span.requestDate")
    public WebElement requestDate;

    @FindBy (css = "div.requestMarker div.sz_tooltip")
    public WebElement hintWindow;

    @FindBy (css = "li.ng-scope div.requestMarker span")
    public WebElement btnOnCheck;




}
