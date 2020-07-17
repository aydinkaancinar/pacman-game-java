package com.KaanCinar.pacman;

//Kaan Cinar && Bogachan Arslan && Onder Soydal && Sinan Karabocuoglu
//ScoreBoard
//24.04.2018

import java.awt.*;

public class ScoreBoard{

    //attitudes
    public int score;
    public int lives;

    //constructer
    public ScoreBoard(){
        score = 0;
        lives = 3;
    }

    public void draw(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        float stroke = 12;
        BasicStroke strokeline = new BasicStroke(stroke);
        Rectangle myRect = new Rectangle(5,5,983,24);//creates a rectangle
        g2.setColor(Color.BLUE);
        g2.setStroke(strokeline);
        g2.draw(myRect);
        g2.setColor(Color.BLACK);
        g2.fill(myRect);
        g2.setColor(Color.WHITE);
        Font trb = new Font("Courier", Font.PLAIN, 20);//sets a different font
        g2.setFont(trb);
        g2.drawString("Score: "+score, 25,25);
        g2.drawString("THE PACMAN GAME", 407,25);
        g2.drawString("Lives: "+lives, 800,25);
    }

    public void died()
    {
        lives--;
    }

    public void getPoint(){
        score += 50;
    }
}
