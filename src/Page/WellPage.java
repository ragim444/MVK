package Page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WellPage
{
    @FindBy (css = "div.dialogwin_fader")
    public WebElement multimapWindow;

    @FindBy(xpath = "//div[@class='olControlZoomSlider olControlNoSelect']")
    public WebElement sldMmpZoomBar;

    public WebElement sldZoomSlider;

    @FindBy (id = "well-search")
    public WebElement edtWellNumberSearch;

    @FindBy (css = "div.dialogwin_body div.well_column_right div.wrap select.ng-pristine")
    public WebElement ddlSelectScale;

    @FindBy (css = "div.dialogwin_body div.well_column_right div.wrap p.well_attributes")
    public WebElement txtWellInfo;

    @FindBy (css = "div.dialogwin_body div.well_column_right div.wrap span.well_scale")
    public WebElement btnResetScaleMultimap;

    @FindBy (css = "div.ac_results ul li.ac_even")
    public WebElement resultEven;

    public void waiForLoadMultimap (WebDriver driver) throws Throwable
    {
        //Ожидание получения списка слоев
        (new WebDriverWait(driver, 20)).until(new ExpectedCondition<Boolean>()
        {
            public Boolean apply(WebDriver d)
            {return (!multimapWindow.getAttribute("style").equals("display: none;") );}});

    }


    public Integer getZoomSliderPosition()
    {
        sldZoomSlider=sldMmpZoomBar.findElement(By.xpath("div[3]"));
        return Integer.parseInt(sldZoomSlider.getCssValue("top").substring(0,2));
    }
}
