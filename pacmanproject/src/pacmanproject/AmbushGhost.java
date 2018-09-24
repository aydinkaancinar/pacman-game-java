package pacmanproject;

//Kaan Cinar && Bogachan Arslan && Onder Soydal && Sinan Karabocuoglu
//AmbushGhost
//24.04.2018

import java.awt.*;
import java.util.ArrayList;

public class AmbushGhost extends Ghost {
  private int ambushDistance=4;

  public AmbushGhost(int initialX, int initialY, int vel, int squareSize,int width, Shape[][] grid) {
      super(initialX, initialY, vel, squareSize, width, grid);
      COLOR=Color.ORANGE;
      timeToScatter=3;
  }

  @Override
  public ArrayList<Character> determineDirectionOrder(int pacmanX, int pacmanY,char pacmanDirection) {
      int targetX=pacmanX;
      int targetY=pacmanY;
      switch (pacmanDirection){
          case 'N':
              targetY-=ambushDistance;break;
          case 'S':
              targetY+=ambushDistance;break;
          case 'E':
              targetX+=ambushDistance;break;
          case 'W':
              targetX-=ambushDistance;break;
      }
      int[] direction={targetX-xPos,targetY-yPos};
      ArrayList<Character> directions=new ArrayList<Character>();
      if(direction[0]==0 && direction[1]==0){
          //stop() //This method will terminate the actions of ghost if pacman is caught. For now we'll keep moving the ghost
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
