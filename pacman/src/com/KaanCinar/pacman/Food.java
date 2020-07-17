package com.KaanCinar.pacman;

//Kaan Cinar && Bogachan Arslan && Onder Soydal && Sinan Karabocuoglu
//Food
//24.04.2018
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Food{
    //attitudes
    public int xpos;
    public int ypos;
    public Rectangle2D bor;
    public boolean eaten;
    public boolean powerUp;

    //constructer
    public Food(int x, int y){
        xpos = x;
        ypos = y;
        eaten = false;
        bor = new Rectangle2D.Double(xpos+23,ypos+23+34,10,10);

    }

    //methods

    public int getX(){
        return xpos;
    }

    public int getY(){
        return ypos;
    }

    public Rectangle2D getBorderOfFood(){
        return bor;
    }
    public void draw(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        if(!eaten) {
            g2.setColor(Color.YELLOW);
            g2.fillOval((int)bor.getBounds2D().getX(),(int)bor.getBounds2D().getY(),(int)bor.getBounds2D().getWidth(),(int)bor.getBounds2D().getHeight());
        }
    }

    public void setPowerUp(){
        powerUp = true;
        bor = new Rectangle2D.Double(xpos+18,ypos+18+34,20,20);
    }
}