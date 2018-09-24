package pacmanproject;

//Kaan Cinar && Bogachan Arslan && Onder Soydal && Sinan Karabocuoglu
//Player
//24.04.2018
/*This class has variables and methods of the pacman, the player character. It has move() method
* to chage variables. It takes user input via getInput() to change directions of pacman. The draw()
* function of this class draws the pacman in component class.*/

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Player {
//attitudes
int xpos; 
int ypos;
int diameter;
int angle = 30; //used for mouth movement
int theta = 30; //used for mouth movement
int close = 5; //used for mouth movement
public Rectangle2D border; //these borders are used for wall detection, ghost chase, and food eating
public Rectangle2D bor;
public Rectangle2D upBor;
public Rectangle2D downBor;
public Rectangle2D leftBor;
public Rectangle2D rightBor;
int xvel = 0; //velocity for movement
int yvel = 0;
char direction = 'E'; //shows direction
public boolean up,down,right,left; //booleans to move the pacman
public boolean u,d,r,l = true; //these booleans check if directions are empty

//constructer, initializes the variables
public Player(int x, int y, int size) {
  xpos = x;
  ypos = y;
  diameter = size;
  border = new Rectangle2D.Double(x, y, diameter, diameter);
  bor = new Rectangle2D.Double(x+14, y+14, 2, 2);
  upBor = new Rectangle2D.Double(x-1, y-13, diameter+2, 12);
  downBor = new Rectangle2D.Double(x-1, y+diameter+1, diameter+2, 12);
  leftBor = new Rectangle2D.Double(x-13, y-1, 12, diameter+2);
  rightBor = new Rectangle2D.Double(x+diameter+1, y-1, 12, diameter+2);
}

//draws pacman
public void draw(Graphics g){
  Graphics2D g2 = (Graphics2D) g;
  g2.setColor(Color.YELLOW);
  /* g2.draw(upBor);
   g2.draw(downBor);
   g2.draw(leftBor);
   g2.draw(rightBor);*/
  g2.fillArc((int) border.getBounds2D().getX(), (int) border.getBounds2D().getY(), diameter, diameter,angle-theta,300+2*theta);
    //g2.setColor(Color.RED); 
  // g2.draw(bor);
}

public int getX(){
  return (int)border.getBounds2D().getX();
}

public int getY(){
  return (int)border.getBounds2D().getY();
}

public char getDirection(){
  return direction;
}

//used to get input for directions
public void getInput(int i){
  up = false;
  down = false;
  right = false;
  left = false;
  if(i == 38){
    up = true;
    down = false;
  }
  if(i == 39){
    right = true;
    left = false;
  }
  if(i == 37){
    left = true;
    right = false;
  }
  if(i == 40){
    down = true;
    up = false;
  }
}

public Rectangle2D getBorderOfPac(){
  return border;
}

//changes variables
public void move(){
  //if in left portal, teleports pacman to right portal
  if((int) (border.getBounds2D().getX()+border.getBounds2D().getWidth())<0) 
  {
    leftBor.setRect(988-13-3, (int) leftBor.getBounds2D().getY()+yvel, leftBor.getBounds2D().getWidth(), leftBor.getBounds2D().getHeight());
    rightBor.setRect(988+diameter+1-3, (int) rightBor.getBounds2D().getY()+yvel, rightBor.getBounds2D().getWidth(), rightBor.getBounds2D().getHeight());
    upBor.setRect(987-3, (int) upBor.getBounds2D().getY()+yvel, upBor.getBounds2D().getWidth(), upBor.getBounds2D().getHeight());
    downBor.setRect(987-3, (int) downBor.getBounds2D().getY()+yvel, downBor.getBounds2D().getWidth(), downBor.getBounds2D().getHeight());
    border.setRect(988-3, (int) border.getBounds2D().getY()+yvel, diameter, diameter);
    bor.setRect(988-3+14, (int) bor.getBounds2D().getY()+yvel, 2, 2);
  }
  //if in right portal, teleports pacman to left portal
  if((int) (border.getBounds2D().getX())>992) 
  {
    leftBor.setRect(0-13+3-border.getBounds2D().getWidth(), (int) leftBor.getBounds2D().getY()+yvel, leftBor.getBounds2D().getWidth(), leftBor.getBounds2D().getHeight());
    rightBor.setRect(0+diameter+1+3-border.getBounds2D().getWidth(), (int) rightBor.getBounds2D().getY()+yvel, rightBor.getBounds2D().getWidth(), rightBor.getBounds2D().getHeight());
    upBor.setRect(-1+3-border.getBounds2D().getWidth(), (int) upBor.getBounds2D().getY()+yvel, upBor.getBounds2D().getWidth(), upBor.getBounds2D().getHeight());
    downBor.setRect(-1+3-border.getBounds2D().getWidth(), (int) downBor.getBounds2D().getY()+yvel, downBor.getBounds2D().getWidth(), downBor.getBounds2D().getHeight());
    border.setRect(0+3-border.getBounds2D().getWidth(), (int) border.getBounds2D().getY()+yvel, diameter, diameter);
    bor.setRect(0+3-bor.getBounds2D().getWidth()-14, (int) bor.getBounds2D().getY()+yvel, 2, 2);
  }
  //sets variables to zero
  xvel = 0;
  yvel = 0;
  if(theta<0||theta>30) close*=-1; //changes mouth movement
  theta-=close; //changes mouth movement
  //following if loops are used for movement in different directions
  if((right||direction=='E')&&r) {
    if(!u&&d) yvel=4;
    if(!d&&u) yvel=-4;
    direction = 'E';
    xvel=4;
    angle = 30;
  }
  if((left||direction =='W')&&l) {
    if(!u&&d) yvel=4;
    if(!d&&u) yvel=-4;
    direction = 'W';
    xvel=-4;
    angle = 210;
  }   
  if((up||direction=='N')&&u) {
    if(!l&&r) xvel=4;
    if(!r&&l) xvel=-4;
    direction = 'N';
    yvel=-4;
    angle = 120;
  }
  if((down||direction=='S')&&d) {
    if(!l&&r) xvel=4;
    if(!r&&l) xvel=-4;
    direction = 'S';
    yvel=4;
    angle = 300;
  }
  //in the end variables that store the positions are set
  leftBor.setRect((int) leftBor.getBounds2D().getX()+xvel, (int) leftBor.getBounds2D().getY()+yvel, leftBor.getBounds2D().getWidth(), leftBor.getBounds2D().getHeight());
  rightBor.setRect((int) rightBor.getBounds2D().getX()+xvel, (int) rightBor.getBounds2D().getY()+yvel, rightBor.getBounds2D().getWidth(), rightBor.getBounds2D().getHeight());
  upBor.setRect((int) upBor.getBounds2D().getX()+xvel, (int) upBor.getBounds2D().getY()+yvel, upBor.getBounds2D().getWidth(), upBor.getBounds2D().getHeight());
  downBor.setRect((int) downBor.getBounds2D().getX()+xvel, (int) downBor.getBounds2D().getY()+yvel, downBor.getBounds2D().getWidth(), downBor.getBounds2D().getHeight());
  border.setRect((int) border.getBounds2D().getX()+xvel, (int) border.getBounds2D().getY()+yvel, diameter, diameter);
  bor.setRect((int) bor.getBounds2D().getX()+xvel, (int) bor.getBounds2D().getY()+yvel, 2, 2);
}
}