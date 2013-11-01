import Page.WellPage;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import utils.MainTest;
import utils.MouseOnMap;

import static org.junit.Assert.assertTrue;

public class TestForMultimap extends MainTest
{
    private MouseOnMap my_mouse;




    @Test
    public void testSearchWell () throws Throwable
    {
        openMultimap();
        wellPageControls.waiForLoadMultimap(driver);

        wellPageControls.edtWellNumberSearch.clear();
        wellPageControls.edtWellNumberSearch.sendKeys("18412");
        wellPageControls.resultEven.click();
        waitForLoadMap(30, path + "/points/searchMultimap/18214_200.png");

        wellPageControls.ddlSelectScale.findElement(By.xpath("option[3]")).click();
        waitForLoadMap(30, path + "/points/searchMultimap/18214_100.png");
        wellPageControls.btnResetScaleMultimap.click();
        waitForLoadMap(30,path+"/points/searchMultimap/18214_50.png");

        String S = wellPageControls.txtWellInfo.findElement(By.xpath("//div/p[1]")).getText();

        assertTrue("Информация по колодцу не верна",S.equals("Number: 18412"));

    }

}
