package pacmanproject;

//Kaan Cinar && Bogachan Arslan && Onder Soydal && Sinan Karabocuoglu
//PacComp
//24.04.2018
/*This class collects draw() methods from visible objects in the game and draws them using 
* paintComponent() method. This class also updates certain variables in the game that are
* dependent on time. Opening and closing windows in the game are managed by usage of 
* boolean variables in this class.*/
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import javax.swing.JComponent;
import java.awt.Dimension;

public class PacComp extends JComponent //component class for storing the Tanks, moving them at once
{  
public Player pac; //the object that represents pacman
public Map map; //the object that displayes and arranges the walls
public ScoreBoard scores; //the object that shows the score and life
Food[][] foods; //food class that stores info about foods
public boolean win = false; //boolean dependent on winning
public boolean gameOver = false; //boolean dependent on loosing
public boolean stopped = true; //boolean that stops and starts the game
Ghost[] ghosts; //array that stores the ghosts
Ghost aghost;//variables to store ghosts
Ghost dghost;
Ghost vghost;
Ghost hghost;
int counter = 0; //counter to track time
boolean dead = false; //boolean that shows if pacman died
boolean ghostEat = false; //boolean that shows ghosts are escaping

public PacComp() //in constructor variables are initialized
{
  this.setSize(new Dimension(992,692));
  foods = new Food[20][14];
  for(int i=0;i<19;i++)
    for(int j=0;j<13;j++)
  {
    foods[i][j] = new Food(i*52,j*52);
  }
  foods[0][0].setPowerUp();
  foods[0][12].setPowerUp();
  foods[18][0].setPowerUp();
  foods[18][12].setPowerUp();
  foods[3][2] = null;
  foods[4][2] = null;
  foods[14][2] = null;
  foods[15][2] = null;
  pac = new Player(481,568,30);
  map = new Map(0,35);
  scores = new ScoreBoard();
  ghosts=new Ghost[4];
  aghost=new AmbushGhost((int)map.setRelativeLoc(16,12).getX()+map.SQUARE/2+4,(int)map.setRelativeLoc(16,12).getY()+map.SQUARE/2+36+4,4,map.SQUARE-2,map.RECT,map.obstacle);
  dghost=new DistanceGhost((int)map.setRelativeLoc(20,12).getX()+map.SQUARE/2+4,(int)map.setRelativeLoc(20,12).getY()+map.SQUARE/2+36+4,5,map.SQUARE-2,map.RECT,map.obstacle);
  hghost=new HorizontalGhost((int)map.setRelativeLoc(18,10).getX()+map.SQUARE/2+4,(int)map.setRelativeLoc(18,10).getY()+map.SQUARE/2+36+4,5,map.SQUARE-2,map.RECT,map.obstacle);
  vghost=new VerticalGhost((int)map.setRelativeLoc(18,14).getX()+map.SQUARE/2+4,(int)map.setRelativeLoc(18,14).getY()+map.SQUARE/2+36+4,4,map.SQUARE-2,map.RECT,map.obstacle);
  
  ghosts[0]=aghost;
  ghosts[1]=dghost;
  ghosts[2]=hghost;
  ghosts[3]=vghost;
}

public void getState(boolean b) //takes start/stop state from PacFrame
{
  stopped = b;
}

public void paintComponent(Graphics g) //draws every object
{
  map.draw(g); //draws the map
  for(int i=0;i<19;i++)//draws the foods
    for(int j=0;j<13;j++)
  {
    if(foods[i][j]!=null) foods[i][j].draw(g);
  }
  pac.draw(g); //draws pacman
  scores.draw(g); //draws scores
  for(Ghost gh:ghosts){//draws ghosts
    gh.drawGhost((Graphics2D)g,pac.getX(),pac.getY(),pac.getDirection());
    if(gh.getX()<=0) gh.setDirection('E');
    if(gh.getX()>=994) gh.setDirection('W');
  }
  
  if(stopped) {//draws stop screen
    g.setColor(Color.BLACK);
    g.fillRect((994-300)/2,(692-150)/2,300,150);
    g.setColor(new Color(29,28,229));
    g.drawRect((994-300)/2,(692-150)/2,300,150);
    g.setColor(Color.WHITE);
    g.drawString("PRESS SPACEBAR",(994-300)/2+65,(692-20)/2);
    g.drawString("TO START/STOP",(994-300)/2+71,(692-20)/2+20);
  }
  if(win) { //draws win screen
    g.setColor(Color.BLACK);
    g.fillRect((994-300)/2,(692-150)/2,300,150);
    g.setColor(new Color(29,28,229));
    g.drawRect((994-300)/2,(692-150)/2,300,150);
    g.setColor(Color.WHITE);
    g.drawString("YOU WIN!",(994-200)/2+53,(692-20)/2);
  }
  if(gameOver) {//draws game over screen
    g.setColor(Color.BLACK);
    g.fillRect((994-300)/2,(692-150)/2,300,150);
    g.setColor(new Color(29,28,229));
    g.drawRect((994-300)/2,(692-150)/2,300,150);
    g.setColor(Color.WHITE);
    g.drawString("GAME OVER",(994-200)/2+53,(692-20)/2);
  }
}

public void reDraw() //repaints and sets variables dependent on time
{
  //if any point in the map is intersecting any border of pacman these if statements change booleans
  if(map.intersects(pac.rightBor)) pac.r=false; 
  else pac.r = true;
  if(map.intersects(pac.leftBor)) pac.l = false;
  else pac.l = true;
  if(map.intersects(pac.upBor)) pac.u = false;
  else pac.u = true;
  if(map.intersects(pac.downBor)) pac.d = false;
  else pac.d = true;
  
  //if pacman is not dead if there is any intersection between pacman and ghosts
  //and if ghosts are in chase mode life decreases else if ghosts are in escape
  //mode ghosts are eaten
  if(!dead){
    for(Ghost g:ghosts)
      if(g.boundary.intersects(pac.bor)) {
      if(!ghostEat){
        scores.died();
      dead = true;
      }
      else {
        g.ghostEaten();
      }
    }
  }
  //this checks if ghosts are in escape mode
  if(ghosts[0].movementMode == 1) ghostEat = false;
  //this two if statement gives some time to pacman for recovery after it loses a life
  if(dead) counter++;
  if(counter==50) {dead = false; counter = 0;}
  //this loops check all foods and if they intersect with pacman, if they intersect player gets 
  //score, and if pacman eats super food then ghosts run away
  for(int i=0;i<19;i++)
    for(int j=0;j<13;j++)
  {
    if(foods[i][j]!=null) if(pac.getBorderOfPac().intersects(foods[i][j].getBorderOfFood())){
      if(foods[i][j].powerUp) for(Ghost g:ghosts) {g.enterEscapeMode(); ghostEat = true;}
      foods[i][j] = null;
      scores.getPoint();
    }
  }
  pac.move(); //changes variables in pacman
  repaint(); //repaints...
}
}