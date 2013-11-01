import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.MainTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestForPopupLayers extends MainTest
{
    //Проверяет переход во включенное состояние общего чекбокса дерева второго уровня из смешанного
    @Test
    public void testSwitchSecondLvlCheckBoxOnCheckStateAfterMixedMod () throws Throwable
    {
        openSite();
       mPage.openPopupLayers();
        //Разворачиваем дерево второго уровня, если это необходимо
        if (!mPage.getStateSecondLvlExpander(mPage.btnSecondLvlExpander))
            mPage.btnSecondLvlExpander.click();

        //Отключаем общий чекбокс
       mPage.switchOffLayerCheckBox(mPage.chkSecondLvlTree);


        // Проверяет что общий чекбокс второго уровня отключен
        assertFalse("Общий чекбокс для слоев второго уровня не был отключен",mPage.getCheckBoxCondition(mPage.chkSecondLvlTree));

        // Проверяет что все слои второго уровня отключены
        assertFalse("Не все слои второго уровня были отключены",mPage.getCheckBoxCondition(mPage.secondLvlTreeItems));


        mPage.secondLvlTreeItems.get(1).findElement(By.cssSelector("span.custom_checkbox")).click();
        assertTrue("Состояние чекбокса включения дерева второго уровня не является смешанным",mPage.chkSecondLvlTree.getAttribute("class").contains("partly"));

        mPage.chkSecondLvlTree.click();

        // Проверяет что общий чекбокс второго уровня включен
        assertTrue("Общий чекбокс для слоев второго уровня не был включен",mPage.getCheckBoxCondition(mPage.chkSecondLvlTree));
        // Проверяет что все слои второго уровня включен
        assertTrue("Не все слои второго уровня были включены", mPage.getCheckBoxCondition(mPage.secondLvlTreeItems));

    }


    //Проверяет отключение всех слоев карты
    @Test
    public void testSwitchOffAllLayers () throws Throwable    {
        openSite();
        mPage.openPopupLayers();
        for (WebElement element:mPage.firstLvlTree )        {
            mPage.switchOffLayerCheckBox(element.findElement(By.cssSelector("span.custom_checkbox")));
        }
        if (mPage.getCheckBoxCondition(mPage.chkSecondLvlTree))
        {
            mPage.chkSecondLvlTree.click();
        }

        //Разворачиваем дерево второго уровня, если это необходимо
        if (!mPage.getStateSecondLvlExpander(mPage.btnSecondLvlExpander))
        mPage.btnSecondLvlExpander.click();

        // Проверяет что все слои первого уровня отключены
        assertFalse("Не все слои первого уровня были отключены",mPage.getCheckBoxCondition(mPage.firstLvlTree));
        // Проверяет что общий чекбокс второго уровня отключен
        assertFalse("Общий чекбокс для слоев второго уровня не был отключен",mPage.getCheckBoxCondition(mPage.chkSecondLvlTree));
        // Проверяет что все слои второго уровня отключены
        assertFalse("Не все слои второго уровня были отключены",mPage.getCheckBoxCondition(mPage.secondLvlTreeItems));
    }

    //Проверяет закрытие popup окна "Слои", если курсор мыши не находится над ним, в течении 2х секунд
    @Test
    public void testTurnOffPopupWindowAfterTwoSecond () throws  Throwable
    {       openSite();
        mPage.moveMouseOverPopupLayers(mPage.btnPopupLayers,0,0);
        mPage.btnPopupLayers.click();

        robot.delay(2000);
        assertTrue("display: none;".equals(driver.findElement(By.id("popupLayersTriangle")).getAttribute("style")));
    }

    //Проверяет включение всех слоев второго уровня
    @Test
    public void testSwitchOnAllSecondLvlLayers() throws Throwable
    {          openSite();
       mPage.openPopupLayers();

        //Разворачиваем дерево второго уровня, если это необходимо
        if (!mPage.getStateSecondLvlExpander(mPage.btnSecondLvlExpander))
            mPage.btnSecondLvlExpander.click();

        if (!mPage.getCheckBoxCondition(mPage.chkSecondLvlTree))
        {
            mPage.chkSecondLvlTree.click();
        }
        // Проверяет что общий чекбокс второго уровня отключен
        assertTrue("Общий чекбокс для слоев второго уровня не был включен", mPage.getCheckBoxCondition(mPage.chkSecondLvlTree));
        // Проверяет что все слои второго уровня отключены
        assertTrue("Не все слои второго уровня были включены", mPage.getCheckBoxCondition(mPage.secondLvlTreeItems));
    }


    //Проверяет переход в смешанное состояние общего чекбокса дерева второго уровня
    @Test
    public void testSwitchSecondLvlCheckBoxOnMixedMod () throws Throwable
    {        openSite();
        mPage.openPopupLayers();

        //Разворачиваем дерево второго уровня, если это необходимо
        if (!mPage.getStateSecondLvlExpander(mPage.btnSecondLvlExpander))
            mPage.btnSecondLvlExpander.click();
        mPage.chkSecondLvlTree.click();
        // Проверяет что общий чекбокс второго уровня отключен
        assertFalse("Общий чекбокс для слоев второго уровня не был отключен",mPage.getCheckBoxCondition(mPage.chkSecondLvlTree));
        // Проверяет что все слои второго уровня отключены
        assertFalse("Не все слои второго уровня были отключены",mPage.getCheckBoxCondition(mPage.secondLvlTreeItems));


        mPage.secondLvlTreeItems.get(1).findElement(By.cssSelector("span.custom_checkbox")).click();
        assertTrue("Состояние чекбокса включения дерева второго уровня не является смешанным",mPage.chkSecondLvlTree.getAttribute("class").contains("partly"));
    }
}
