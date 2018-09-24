package pacmanproject;

//Kaan Cinar && Bogachan Arslan && Onder Soydal && Sinan Karabocuoglu
//Map
//24.04.2018


import java.util.Random;
import java.awt.Shape;
import java.awt.Point;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Map{

//constants
int WIDTH = 1010;
int HEIGHT = 710;
final int SQUARE = 48;
final int RECT = 4;
final int ARR_FIRST = 2*((WIDTH-RECT)/(SQUARE+RECT))+1;
final int ARR_SECOND = 2*((HEIGHT-RECT)/(SQUARE+RECT))+1;
int xpos;
int ypos;
//random generator
Random rgen = new Random();

//walls
Shape [][] obstacle = new Shape [ARR_FIRST][ARR_SECOND];

//constructor
public Map(int xCo, int yCo){
  //fill wals array
  xpos = xCo;
  ypos = yCo;
  for(int i = 0; i < ARR_FIRST; i++){
    for(int j = 0; j < ARR_SECOND; j++){
      int x;
      int y;
      if(i%2==0){x =xpos+(i/2)*(SQUARE+RECT);}
      else{x =xpos+(i/2)*SQUARE + ((i/2)+1)*RECT;}
      if(j%2==0){y =ypos+(j/2)*(SQUARE+RECT);}
      else{y =ypos+(j/2)*SQUARE + ((j/2)+1)*RECT;}
      
      //Corner
      if( i%2==0 && j%2==0){
       obstacle[i][j] = new Rectangle(x,y,RECT,RECT);
      }
      //VERTICAL
      else if(i%2!=0 && j%2==0){
        obstacle[i][j] = new Rectangle(x,y,SQUARE,RECT);
      }
      //HORIZONTAL
      else if(i%2==0 && j%2!=0){
        obstacle[i][j] = new Rectangle(x,y,RECT,SQUARE);
        
      }
    }
  }
  
  //opening paths by removing unncessarry walls.
  
  for(int i = 0; i < ARR_FIRST; i++){
    for(int j = 0; j < ARR_SECOND; j++){
      //vertical column
      if((i==1||i==3||i==ARR_FIRST-2||i==ARR_FIRST-4||((i==11||i==ARR_FIRST-12)&&j!=ARR_SECOND-3))&&j!=0&&j!=ARR_SECOND-1 && 
         (( j!=ARR_SECOND/2+1&& j!=ARR_SECOND/2-1) ||(i > 1 && i < ARR_FIRST-2))){
        obstacle[i][j] = null;
      }
      //below
      if((i>3&&i<ARR_FIRST-4)&& j == ARR_SECOND-4){
        obstacle[i][j] = null;
      }
      //horizontal row
      if((j==9||j==1||j==ARR_SECOND-2)&&i!=0&&i!=ARR_FIRST-1){
        obstacle[i][j] = null;
      }
      //up-center row
      if(i==ARR_FIRST/2 && j == ARR_SECOND-3){
        obstacle[i][j] = null;
      } 
      //center
      if(i>(ARR_FIRST/2-2)&&i<(ARR_FIRST/2+2)&&j<(ARR_SECOND/2+2)&&j>(ARR_SECOND/2-2)){
        obstacle[i][j] = null;
      }
      if(i == ARR_FIRST/2 && j == ARR_SECOND/2 - 3){
        obstacle[i][j] = null;
      }
      //wall side
      if( (i==2||i==ARR_FIRST-3) && (j==10||j==5||j==ARR_SECOND-6||j==ARR_SECOND-10)){
        obstacle[i][j] = null;
      }
      //Side path
      if((j==3||j==7) && ((i>4&&i<11)||(i>ARR_FIRST-12&&i<ARR_FIRST-5))){
        obstacle[i][j]=null;
      }
      if((j==4||j==6) && (i == 5||i==ARR_FIRST-6)){
        obstacle[i][j]=null;
      }
      if((i==8||i==ARR_FIRST-9) && (j==5)){
        obstacle[i][j]=null;
      }
      if((i==0||i==2||i==ARR_FIRST-3||i==ARR_FIRST-1) && j==ARR_SECOND/2){
        obstacle[i][j] = null;
      }
      //up center
      if(i>ARR_FIRST/2-6 && i<ARR_FIRST/2+6 && j == 7){
        obstacle[i][j] = null;
      }
      if( (i==ARR_FIRST/2-6||i==ARR_FIRST/2+6) && ((j<7 && j > 1) || (j>9 && j<19))){
        obstacle[i][j] = null;
      }
      if(i>ARR_FIRST/2-4 && i<ARR_FIRST/2+4 && j == 5){
        obstacle[i][j] = null;
      }
      if( (i==ARR_FIRST/2-4||i==ARR_FIRST/2+4) && ((j<5 && j > 1) || (j>11 && j<15))){
        obstacle[i][j] = null;
      }
      if(i>ARR_FIRST/2-2 && i<ARR_FIRST/2+2 && j == 3){
        obstacle[i][j] = null;
      }
      if( i==ARR_FIRST/2 && ( j==2 || j==6)){
        obstacle[i][j] = null;
      }
      if(i==ARR_FIRST/2 && j == ARR_SECOND/2+5){
        obstacle[i][j] = null;
      }
      if(i>ARR_FIRST/2-4 && i<ARR_FIRST/2+4 && j == ARR_SECOND/2+4){
        obstacle[i][j] = null;
      }
      if(((i>ARR_FIRST/2-6 && i<ARR_FIRST/2-2) || (i>ARR_FIRST/2+2 &&i<ARR_FIRST/2+6)) && j == ARR_SECOND/2+6){
        obstacle[i][j] = null;
      }
      if((i == ARR_FIRST/2 || i == ARR_FIRST/2-2 || i == ARR_FIRST/2+2) && (j == ARR_SECOND/2+7)){
        obstacle[i][j] = null;
      }
      if(i>ARR_FIRST/2-6 && i<ARR_FIRST/2+6 && j == ARR_SECOND/2+8){
        obstacle[i][j] = null;
      }
      if((i==ARR_FIRST/2-6 || i==ARR_FIRST/2+6) && j == ARR_SECOND/2+9){
        obstacle[i][j] = null;
      }
      if((i==ARR_FIRST/2-3 || i==ARR_FIRST/2+3) && (j == ARR_SECOND/2+2||j == ARR_SECOND/2-2)){
        obstacle[i][j] = null;
      }
      if((i==ARR_FIRST/2-2 || i==ARR_FIRST/2+2) && (j == ARR_SECOND/2+3||j == ARR_SECOND/2-3)){
        obstacle[i][j] = null;
      }
      if((i==ARR_FIRST/2-10 || i==ARR_FIRST/2+10) && (j == ARR_SECOND/2-3||j == ARR_SECOND/2+1
                                                        ||j == ARR_SECOND/2+5||j == ARR_SECOND/2+9)){
        obstacle[i][j] = null;
      }
      if((i==ARR_FIRST/2-14 || i==ARR_FIRST/2+14) && (j == ARR_SECOND/2-1||j == ARR_SECOND/2+3
                                                        ||j == ARR_SECOND/2+7)){
        obstacle[i][j] = null;
      }
      if(((i>ARR_FIRST/2-14 && i<ARR_FIRST/2-10)||(i>ARR_FIRST/2+10 && i<ARR_FIRST/2+14)) && (j == ARR_SECOND/2-2||j == ARR_SECOND/2
                                                                                                ||j == ARR_SECOND/2+2||j == ARR_SECOND/2+4||j == ARR_SECOND/2+6
                                                                                                ||j == ARR_SECOND/2+8)){
        obstacle[i][j] = null;
      }   
    }
    //end of all removals
  }
  
  //fill all corners
  for(int i = 0; i < ARR_FIRST; i++){
    for(int j = 0; j < ARR_SECOND; j++){
      
      //find location for corners
      int x;
      int y;
      if(i%2==0){x =xpos+(i/2)*(SQUARE+RECT);}
      else{x =xpos+(i/2)*SQUARE + ((i/2)+1)*RECT;}
      if(j%2==0){y =ypos+(j/2)*(SQUARE+RECT);}
      else{y =ypos+(j/2)*SQUARE + ((j/2)+1)*RECT;}
      
      //Corner
      if( i%2==0 && j%2==0){
        obstacle[i][j] = new Rectangle(x,y,RECT,RECT);
      }
    }
  }
  
  
  
}

public boolean intersects(Shape s) //used to determine if the bomb intersects with a shape
{
  boolean intersect = false; 
  for(int i = 0; i < ARR_FIRST; i++){
    for(int j = 0; j < ARR_SECOND; j++){
      if(obstacle[i][j]!=null){
        Rectangle r = (Rectangle) obstacle[i][j];
        if(s.intersects(r)){
          intersect = true; //if intersection happens, returns true
        }
      }
    }
  }
  
  return intersect;
}



//paint
public void draw(Graphics g){
  
  Graphics2D g2 = (Graphics2D) g;
  
  //draw background
  g2.setColor(Color.BLACK);
  g2.fillRect(xpos,ypos,WIDTH-16,HEIGHT-28);
  
  //draw walls
  g2.setColor(new Color(29,28,229));
  for(int i = 0; i < ARR_FIRST; i++){
    for(int j = 0; j < ARR_SECOND; j++){
      if(obstacle[i][j]!=null){
        Rectangle r = (Rectangle) obstacle[i][j];
        g2.draw(r);
        g2.fillRect((int)r.getX(),(int)r.getY(),(int)r.getWidth(),(int)r.getHeight());
      }
    }
  }
  
  
}


//find relative x , y
public Point setRelativeLoc(int i, int j){
  int a;
  int b;
  if(i%2==0){a =(i/2)*(SQUARE+RECT);}
  else{a =(i/2)*SQUARE + ((i/2)+1)*RECT;}
  if(j%2==0){b =(j/2)*(SQUARE+RECT);}
  else{b =(j/2)*SQUARE + ((j/2)+1)*RECT;}
  return new Point(a,b);
}

}