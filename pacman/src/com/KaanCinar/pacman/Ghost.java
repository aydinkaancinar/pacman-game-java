package com.KaanCinar.pacman;

//Bogachan Arslan, Kaan Cinar, Onder Soydal, Sinan Karabocuoglu
//Pacman Midterm
//18.05.18


import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public abstract class Ghost {
    protected int xPos;
    protected int yPos;
    private int defaultX;
    private int defaultY;
    protected int SIZE;
    private int wallWidth=0;
    protected Color COLOR;
    public Shape boundary;
    private AffineTransform af;
    private Random r=new Random();
    private int velocity=1;
    private int lengthMoved=0;
    private boolean moving=false;
    private Shape[][] maze;
    public char direction='N';
    private boolean directionManuallySet=false;
    public int movementMode=0; // 0-->Scatter, 1-->Chase, 2-->Escape
    protected int timeToScatter;
    private final int timeToChase=15;
    private final int timeToEscape=15;
    private boolean oppositeDirection=false;
    private int secondsCounter=0;

    public Ghost(int initialX,int initialY,int vel,int squareSize,int width, Shape[][] grid){
        maze=grid;
        xPos=initialX;
        defaultX=initialX;
        defaultY=initialY;
        yPos=initialY;
        velocity=vel;
        SIZE=squareSize;
        wallWidth=width;
        af=AffineTransform.getTranslateInstance(0,0);
        boundary=new Rectangle2D.Double(initialX-SIZE/2,initialY-SIZE/2,SIZE,SIZE);

        //The timer that will keep track of time to switch between modes
        Timer timer=new Timer();
        TimerTask chronometer=new TimerTask(){
            @Override
            public void run() {
                secondsCounter++;
            }
        };
        timer.schedule(chronometer,0,1000);
    }

    public void drawGhost(Graphics2D g2,int pacmanX,int pacmanY,char pacmanDirection){
        g2.setStroke(new BasicStroke(0));
        g2.setColor((movementMode==1)?Color.RED:Color.GREEN);
        //g2.draw(boundary.getBounds());
        g2.setColor(COLOR);

        //To switch between modes if their relative times are up
        switch (movementMode){
            case 0:
                if(secondsCounter>=timeToScatter){ movementMode=1; secondsCounter=0; }
                break;
            case 1:
                if(secondsCounter>=timeToChase){ movementMode=0; secondsCounter=0; }
                break;
            case 2:
                g2.setColor(Color.BLUE);
                if(secondsCounter==6 || secondsCounter==8) g2.setColor(COLOR);
                if(secondsCounter>=timeToEscape){ movementMode=1; secondsCounter=0; }
        }

        if(moving) {
            for(int i=0;i<velocity;i++){
                boundary = af.createTransformedShape(boundary);
                lengthMoved++;
                xPos=(int)boundary.getBounds().getX()+SIZE/2;
                yPos=(int)boundary.getBounds().getY()+SIZE/2;
                if(lengthMoved==(/*SIZE+wallWidth*/52)) { moving=false; break;}
            }
        } else {
            lengthMoved=0;
            af=AffineTransform.getTranslateInstance(0,0);
            initiateMovement(pacmanX,pacmanY,pacmanDirection);
        }





        double scaleFactor=SIZE*3/8;
        //For circular head and horizontal rectangular body
        g2.fillArc((int)(xPos-scaleFactor),(int) (yPos-scaleFactor),(int)(2*scaleFactor),(int)(2*scaleFactor),0,180);
        g2.fillRect((int)(xPos-scaleFactor),yPos,(int)(2*scaleFactor),(int)(scaleFactor/2));

        //For triangular legs(?)
        int[] TX={(int)(xPos-scaleFactor),(int)(xPos-scaleFactor),(int)(xPos-scaleFactor),(int)(xPos-scaleFactor/2),xPos,(int)(xPos+scaleFactor/2),(int)(xPos+scaleFactor),(int)(xPos+scaleFactor)};
        int[] TY={(int)(yPos+scaleFactor/2),(int)(yPos+scaleFactor),(int)(yPos+scaleFactor),(int)(yPos+scaleFactor/2),(int)(yPos+scaleFactor),(int)(yPos+scaleFactor/2),(int)(yPos+scaleFactor),(int)(yPos+scaleFactor/2)};
        g2.fillPolygon(TX,TY,8);
        //g2.fillPolygon(legCoordinatesX(),legCoordinatesY(),8); TODO y u no work??

        //For Eyes
        g2.setColor(Color.WHITE);
        g2.fillOval((int)(xPos-2*scaleFactor/3),(int)(yPos-2*scaleFactor/3),(int)(2*scaleFactor/3),(int)(2*scaleFactor/3));
        g2.fillOval((xPos),(int)(yPos-2*scaleFactor/3),(int)(2*scaleFactor/3),(int)(2*scaleFactor/3));
        g2.setColor(Color.BLACK);
        g2.fillOval(eyePositionX(direction)[0],eyePositionY(direction),(int)(scaleFactor/3),(int)(scaleFactor/3));
        g2.fillOval(eyePositionX(direction)[1],eyePositionY(direction),(int)(scaleFactor/3),(int)(scaleFactor/3));
        //g2.fillOval((int)(xPos-scaleFactor/2),(int)(yPos-scaleFactor/3),(int)(scaleFactor/3),(int)(scaleFactor/3));
        //g2.fillOval((int)(xPos+scaleFactor/4),(int)(yPos-2*scaleFactor/3),(int)(scaleFactor/3),(int)(scaleFactor/3));
    }

    private int[] legCoordinatesX(){
        double scaleFactor=SIZE*3/8;
        double legShiftFactor=(lengthMoved/SIZE)*scaleFactor;
        double position6=(legShiftFactor>=(scaleFactor/2))? ((xPos-scaleFactor)+(legShiftFactor-(scaleFactor/2))) : ((xPos+scaleFactor/2)+legShiftFactor);
        int[] xPoints = {(int)(xPos-scaleFactor),(int)(xPos-scaleFactor),(int)(xPos-scaleFactor+legShiftFactor),(int)(xPos-scaleFactor/2+legShiftFactor),(int)(xPos+legShiftFactor),(int)position6,(int)(xPos+scaleFactor),(int)(xPos+scaleFactor)};
        return xPoints;
    }

    private int[] legCoordinatesY(){
        double scaleFactor=SIZE*3/8;
        double legShiftFactor=(lengthMoved/SIZE)*scaleFactor;
        double yshiftFactor=Math.abs(legShiftFactor-scaleFactor/2);
        int[] yPoints={(int)(yPos+scaleFactor/2),(int)(yPos+scaleFactor/2+yshiftFactor),(int)(yPos+scaleFactor),(int)(yPos+scaleFactor/2),(int)(yPos+scaleFactor),(int)(yPos+scaleFactor/2),(int)(yPos+scaleFactor/2+yshiftFactor),(int)(yPos+scaleFactor/2)};
        return yPoints;
    }

    private int[] eyePositionX(char direction){
        double scaleFactor=SIZE*3/8;
        int[] eyesX=new int[2];
        switch(direction){
            case 'S':
                eyesX[0]=(int)(xPos-scaleFactor/2);
                eyesX[1]=(int)(xPos+scaleFactor/6);
                break;
            case 'N':
                eyesX[0]=(int)(xPos-scaleFactor/2);
                eyesX[1]=(int)(xPos+scaleFactor/6);
                break;
            case 'E':
                eyesX[0]=(int)(xPos-scaleFactor/3);
                eyesX[1]=(int)(xPos+scaleFactor/3);
                break;
            case 'W':
                eyesX[0]=(int)(xPos-2*scaleFactor/3);
                eyesX[1]=(xPos);
                break;
        }
        return eyesX;
    }

    private int eyePositionY(char direction){
        double scaleFactor=SIZE*3/8;
        switch(direction){
            case 'S':
                return (int)(yPos-scaleFactor/3);
            case 'N':
                return (int)(yPos-2*scaleFactor/3);
            case 'E':
                return (int)(yPos-2*scaleFactor/3);
            case 'W':
                return (int)(yPos-2*scaleFactor/3);
        }
        return (int)(yPos-scaleFactor/3);
    }

    public abstract ArrayList<Character> determineDirectionOrder(int pacmanX, int pacmanY,char pacmanDirection);

    protected ArrayList<Character> generateRandomDirections(){
        ArrayList<Character> directions=new ArrayList<Character>();
        ArrayList<Character> possibilities=new ArrayList<Character>();
        possibilities.add('N');possibilities.add('S');possibilities.add('E');possibilities.add('W');
        for(int i=0;i<4;i++){
            int randomIndex=r.nextInt(possibilities.size());
            directions.add(possibilities.remove(randomIndex));
        }
        return directions;
    }

    private void initiateMovement(int pacX,int pacY,char pacDir){
        ArrayList<Character> directions=(movementMode==1)? determineDirectionOrder(pacX,pacY,pacDir) : generateRandomDirections();
        directions=addReverseToLast(directions);
        char selectedDirection='0';
        for(int i=0;i<directions.size();i++){
            Shape tempBoundary=boundary;
            char currentDir=directions.get(i);
            switch(currentDir){
                case 'N':
                    tempBoundary=createTranslatedShape(boundary,0,-(SIZE+wallWidth)/2);
                    break;
                case 'S':
                    tempBoundary=createTranslatedShape(boundary,0,(SIZE+wallWidth)/2);
                    break;
                case 'E':
                    tempBoundary=createTranslatedShape(boundary,(SIZE+wallWidth)/2,0);
                    break;
                case 'W':
                    tempBoundary=createTranslatedShape(boundary,-(SIZE+wallWidth)/2,0);
                    break;
            }
            if(moveSafe(tempBoundary)){
                selectedDirection=currentDir;
                break;
            }
        }
        int xVel=0;
        int yVel=0;
        if(directionManuallySet) directionManuallySet=false;
        else direction=(oppositeDirection)? reverseDirection() : selectedDirection;
        switch (direction){
            case 'N': xVel=0; yVel=-1; break;
            case 'S': xVel=0; yVel=1; break;
            case 'E': xVel=1; yVel=0;break;
            case 'W': xVel=-1; yVel=0; break;
        }
        af.translate(xVel,yVel);
        moving=true;
    }

    public void enterEscapeMode(){
        movementMode=2;
        oppositeDirection=true;
    }

    protected char reverseDirection(){
        oppositeDirection=false;
        switch (direction) {
            case 'N': return 'S';
            case 'S': return 'N';
            case 'E': return 'W';
            case 'W': return 'E';
        }
        return direction;
    }

    //To not reverse direction unless absolutely necessary, add the reverse of the current direction to the end of list
    //Note: For some reason removing the direction using .remove with the character as a parameter
    //raises an indexoutofbounds error so I had to manually locate index and then remove and add
    protected ArrayList<Character> addReverseToLast(ArrayList<Character> directions){
        int indexToRemove=0;
        for(int i=0;i<directions.size();i++){
            if(directions.get(i).equals(reverseDirection())) indexToRemove=i;
        }
        directions.add(directions.remove(indexToRemove));

        return directions;
    }

    private Shape createTranslatedShape(Shape s,int xShift,int yShift){
        AffineTransform newTransformation=AffineTransform.getTranslateInstance(xShift, yShift);
        return newTransformation.createTransformedShape(s);
    }

    private boolean intersects(Shape movable, Shape still){
        boolean intersects=false;
        if(still!=null && still.getBounds2D().intersects(movable.getBounds2D())){
            intersects=true;
        }
        return intersects;
    }

    private boolean moveSafe(Shape tempBoundary){ //TODO the safe move function doesn't check if movement shifts ghost out of the pane if no walls surround the game area
        for(Shape[] line:maze){
            for(Shape wall:line){
                if(intersects(tempBoundary,wall)) return false;
            }
        }
        return true;
    }

    //Ghost is eaten so is sent back to starting position
    public void ghostEaten(){
        boundary=new Rectangle2D.Double(defaultX-SIZE/2,defaultY-SIZE/2,SIZE,SIZE);
        xPos=defaultX;
        yPos=defaultY;
        movementMode=0;
        secondsCounter=0;
        moving=false;
    }

    public void teleport(int newX,int newY){
        boundary=new Rectangle2D.Double(newX-SIZE/2,newY-SIZE/2,SIZE,SIZE);
        xPos=newX;
        yPos=newY;
        moving=false;
    }

    public void setDirection(char dir){
        directionManuallySet=true;
        direction=dir;
    }

    public int getX(){
        return xPos;
    }
    public int getY(){
        return yPos;
    }
}
