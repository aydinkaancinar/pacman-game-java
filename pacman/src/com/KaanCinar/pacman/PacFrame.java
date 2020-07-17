package com.KaanCinar.pacman;

//Kaan Cinar && Bogachan Arslan && Onder Soydal && Sinan Karabocuoglu
//PacFrame
//24.04.2018
/*PacFrame extends JFrame and implements KeyListener and ActionListener. PacFrame provides
 * a frame for the game while having a Timer to change variables in time, and it also checks
 * inputs taken from keyboard. In this class also game finishes or game stops.*/

//imports
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.Timer;

public class PacFrame extends JFrame implements KeyListener,ActionListener{

    PacComp kcomp;
    private static Timer t;
    boolean stopped = true;

    //during initialization frame is set, keylistener and action listener are set
    public PacFrame() {
        setLayout(new BorderLayout());
        kcomp = new PacComp();
        getContentPane().add(kcomp, BorderLayout.CENTER); //game is in center
        addKeyListener(this);
        ActionListener listener = this;
        final int DELAY = 20;
        t = new Timer(DELAY, listener);
        setTitle("PAC-MAN");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(994,738));
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void keyPressed(KeyEvent e) {
        //inputs have a use while game is not finished
        if(!(kcomp.gameOver||kcomp.win)){
            //if game is not stopped and key code is space game stops
            if (!stopped&&e.getKeyCode() == KeyEvent.VK_SPACE){
                kcomp.stopped = true;
                stopped = true;
                t.stop();
                kcomp.reDraw();
            }
            //if game is stopped and keycode is space game continues
            else if (stopped&&e.getKeyCode() == KeyEvent.VK_SPACE){
                kcomp.stopped = false;
                stopped = false;
                t.start();
                kcomp.reDraw();
            }
            kcomp.pac.getInput(e.getKeyCode());
        }
    }
    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e){}
    public void actionPerformed(ActionEvent event){
        kcomp.reDraw();
        if(kcomp.scores.score>=12150) { //if scores excede 12150 game is won
            kcomp.win =true;
            t.stop();
            kcomp.reDraw();
        }
        if(kcomp.scores.lives<=0) { //if there is no life game is lost
            kcomp.scores.lives = 0;
            kcomp.gameOver =true;
            t.stop();
            kcomp.reDraw();
        }
    }
}
