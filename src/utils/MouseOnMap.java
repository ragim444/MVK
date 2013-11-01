package utils;


import org.sikuli.api.*;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopMouse;

import java.io.File;
import java.io.IOException;


public class MouseOnMap {
    protected ScreenRegion map;
    private static Mouse mouse = new DesktopMouse();
    private static ScreenLocation mouse_location;

    public MouseOnMap(String map_file) throws IOException {
        ScreenRegion m = ImageUtils.findMap(map_file);
        this.map = m;
        mouse_location = m.getCenter();
    }

    public void mouseWheelDown(int steps)
    {
        mouse.click(mouse_location);
        mouse.wheel(1,steps);
        try{ Thread.sleep(10000);} catch (Exception ignored) {}
    }

    public void mouseWheelUp(int steps)
    {
        mouse.click(mouse_location);
        mouse.wheel(-1,steps);
        try{ Thread.sleep(6000);} catch (Exception ignored) {}
    }

    public void mouseMove(int x, int y)
    {
        Integer sl_x, sl_y;
        sl_x = mouse_location.getX() + x;
        sl_y = mouse_location.getY() + y;
        mouse_location.setX(sl_x); mouse_location.setY(sl_y);
        mouse.drag(map.getCenter());
        mouse.drop(mouse_location);
        try{ Thread.sleep(4000);} catch (Exception ignored) {}
    }

    public void isclickOnImage(String image_file)
    {
        ScreenRegion screen = new DesktopScreenRegion();
        Target target_image = new ImageTarget(new File(image_file));
        ScreenRegion image = screen.find(target_image);
        mouse.click((ScreenLocation) image.getCenter());

    }

    public static ScreenRegion findMap1(String map_file)
    {
           ScreenRegion screen_default = new DesktopScreenRegion();
           Target t = new ImageTarget(new File(map_file));
        return screen_default.find(t);
    }



}