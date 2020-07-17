package com.KaanCinar.pacman;

//Kaan Cinar && Bogachan Arslan && Onder Soydal && Sinan Karabocuoglu
//VerticalGhost
//24.04.2018
import java.awt.*;
import java.util.ArrayList;

public class VerticalGhost extends Ghost {
    public VerticalGhost(int x, int y,int vel, int size,int width, Shape[][] grid){
        super(x,y,vel,size,width,grid);
        COLOR=Color.RED;
        timeToScatter=7;
    }

    @Override
    public ArrayList<Character> determineDirectionOrder(int pacmanX, int pacmanY,char pacmanDirection){
        int[] direction={pacmanX-xPos,pacmanY-yPos};
        ArrayList<Character> directions=new ArrayList<Character>();
        if(direction[0]==0 && direction[1]==0){
            directions.add('N');directions.add('S');directions.add('E');directions.add('W');
        } else {
            if (direction[1] > 0) { directions.add('S');directions.add('N'); }
            else if (direction[1] < 0) { directions.add('N');directions.add('S'); }

            if (direction[0] > 0) { directions.add('E');directions.add('W'); }
            else if (direction[0] < 0) { directions.add('W');directions.add('E'); }

            if(direction[0]==0) { directions.add('E'); directions.add('W'); }
            if(direction[1]==0) { directions.add('N'); directions.add('S'); }
        }
        return directions;

    }
}

