package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtils {
    public static ScreenRegion screen = new DesktopScreenRegion();

    public static void getScreenshot(String PathName, String FileName)
    {
        try {
            ImageIO.write(screen.capture(), "png", new File(PathName + "\\" +FileName));}
        catch (Exception ignored) {}
    }
    public static ScreenRegion findMap(String map_file) throws IOException {
        ScreenRegion screen_default = new DesktopScreenRegion();


        Target t = new ImageTarget(new File(map_file));
        return screen_default.find(t);
    }

    public static void takeScreenshotElement (WebDriver driver, WebElement elem, String file_path) throws IOException {
        //Get entire page screenshot
        File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        BufferedImage  fullImg = ImageIO.read(screenshot);
        //Get the location of element on the page
        Point point = elem.getLocation();
        //Get width and height of the element
        int eleWidth = elem.getSize().getWidth();
        int eleHeight = elem.getSize().getHeight();
        //Crop the entire page screenshot to get only element screenshot
        BufferedImage eleScreenshot= fullImg.getSubimage(point.getX(), point.getY(), eleWidth, eleHeight);
        ImageIO.write(eleScreenshot, "png", screenshot);
        //Copy the element screenshot to disk
        FileUtils.copyFile(screenshot, new File(file_path));
    }

}
