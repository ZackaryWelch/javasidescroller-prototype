package sidescroller;

import java.awt.Rectangle;
import java.util.List;
import javax.swing.JLabel;

public class Gravity
{
    JLabel guy = SideScroller.guy;
    List<Rectangle> grassRectangles = SideScroller.grassRectangles;
    
    public void climbingAndFallingFor()
    {
        for(int x = 1; x < SideScroller.frame.getWidth()/16; x++)
        {
            if(guy.getY()+31 > grassRectangles.get(x).getY() && SideScroller.wPressed
                && guy.getX() < (16*x)-1  && guy.getX() > (16*x)-17)
            {
                 guy.setLocation(grassRectangles.get(x).x, grassRectangles.get(x).y-32);
            }
            if(guy.getY()+32 < grassRectangles.get(x).getY() && guy.getX() > ((x)*16)-1 && guy.getX() > (16*x)-17)
            {
                guy.setLocation(grassRectangles.get(x).x, grassRectangles.get(x).y-32);
            }
        }
    }
}