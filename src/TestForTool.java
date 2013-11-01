import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.ImageUtils;
import utils.MainTest;
import utils.MouseOnMap;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class TestForTool extends MainTest
{
    private MouseOnMap my_mouse;

        @Test
        public void testShowNavigator() throws Throwable{
            openSite();

            //ImageUtils.takeScreenshotElement(driver, mPage.map, path +"/points/showNavigator" + "/testShowNavigator_BEFORE.png");

            mPage.btnNavigatorMaximize.click();

           // ImageUtils.takeScreenshotElement(driver,mPage.map,  path +"/points/showNavigator"+"/testShowNavigator_AFTER.png");
            //ImageUtils.takeScreenshotElement(driver,driver.findElement(By.id("dvmaps_interactive_map")),  path +"/points/showNavigator"+"/testShowNavigator_AFTER.png");
            assertTrue("Не открылся навигатор", mPage.isOpenNavigator());
        }


        @Test
        public void testMouseZoomTool()  throws Throwable
        {
            openSite();

            ImageUtils.getScreenshot(path+"/points/MouseZoomTool","screen2.png");
            ImageUtils.takeScreenshotElement(driver,driver.findElement(By.id("dvmaps_interactive_map")),path+"/points/MouseZoomTool"+"/screen1.png");

            my_mouse = new MouseOnMap("default_map.png");

            int position = mPage.getZoomSliderPosition();

            my_mouse.mouseWheelUp(1);

            ImageUtils.takeScreenshotElement(driver,driver.findElement(By.id("dvmaps_interactive_map")),path+"/points/MouseZoomTool"+"/screen_plus.png");

            //Ожидаем загрузки карты после зумирования
           waitForLoadMap(30,path+"/points/MouseZoomTool/zoom_plus.png");

            assertTrue("Шаг1: Позиция слайдера не измениласть: до=" + position + " после=" + mPage.getZoomSliderPosition(), position != mPage.getZoomSliderPosition());
            position = mPage.getZoomSliderPosition();

            my_mouse.mouseWheelDown(1);

            //Ожидаем изменение положения слайдера масштаба, соответствующее 50%
           waitForLoadMap(30,path + "//points/MouseZoomTool/zoom_minus.png");
            assertTrue("Шаг2: Позиция слайдера не измениласть:  до="+position+" после="+mPage.getZoomSliderPosition(), position != mPage.getZoomSliderPosition());
        }

        //Проверяет корректное перемещение по карте
        @Test
        public void testMouseMoveTool() throws Throwable{
            openSite();
            ImageUtils.takeScreenshotElement(driver,driver.findElement(By.id("dvmaps_interactive_map")), path + "/points/MouseMoveTool"+"/testMouseMoveTool_BEFORE.png");
            my_mouse = new MouseOnMap("default_map.png");
            mPage.btnZoom.click();
            my_mouse.mouseMove(-200,-200);
            ImageUtils.takeScreenshotElement(driver,driver.findElement(By.id("dvmaps_interactive_map")), path +"/points/MouseMoveTool"+"/testMouseMoveTool_1STEP.png");
            mPage.btnZoom.click();
            my_mouse.mouseMove(200,200);
            ImageUtils.takeScreenshotElement(driver,driver.findElement(By.id("dvmaps_interactive_map")),path +"/points/MouseMoveTool"+"/testMouseMoveTool_AFTER.png");
        }

        //Проверяет работу инструмента Линейка
        @Test
        public void testMouseMeasureTool() throws Throwable{
            openSite();
            my_mouse = new MouseOnMap("default_map.png");
            mPage.btnMeasure.click();
            my_mouse.isclickOnImage(path+"/points/MouseMeasureTool"+"/point1.png");
            assertFalse("В элементе measurementsOutput при первом клике не установлено значение   0.00 m", driver.findElement(By.id("measurementsOutput")).getText().equals("  0.00 m"));
            my_mouse.isclickOnImage(path+"/points/MouseMeasureTool"+"/point2.png");
            ImageUtils.takeScreenshotElement(driver,driver.findElement(By.id("dvmaps_interactive_map")),path+"/points/MouseMeasureTool"+"/testMouseMeasureTool_AFTER.png");
            assertFalse("Расстояние осталось нулевым", driver.findElement(By.id("measurementsOutput")).getText().equals("  0.00 m"));
        }


       /*
        @Test
        public void testMouseInfoTool() throws Throwable{
            my_mouse = new MouseOnMap("default_map.png");
            mPage.btnInfo.click();
            my_mouse.isclickOnImage("point2.png");
            try{
                new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfElementLocated(By.id("map_loader")));
            }
            catch (TimeoutException e){
                assertTrue("Не появился map_loader",false);
            }
            try{
                new WebDriverWait(driver, 40).until(ExpectedConditions.visibilityOfElementLocated(By.id("helpWindow")));
                try{
                    new WebDriverWait(driver, 60).until(ExpectedConditions.visibilityOfElementLocated(By.id("helpLayers")));
                }
                catch (TimeoutException e)            {
                    assertTrue("Ждали 60 с справка не загрузилась",false);
                }
            }
            catch (TimeoutException e){
                assertTrue("Не появилось окно справки",false);
            }
            if (driver.findElements(By.xpath("//div/div[8]/div/div[2]")).size()>0)
            {
                assertTrue("Окно с сообщением об ошибке: "+
                        driver.findElement(By.xpath("//div/div[8]/div/div[2]")).getText(),false);
            }
        }

              */
    }


