package main;
import inputs.Clavier;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;


public class Panel extends JPanel {
    private float depX=100,depY=100;
    private float depDX=0.5f,depDY=0.5f;
    private int fps=0;
    private long lastCheck =0;
    Random random;


 private inputs.Mouse souris;
    public Panel(){
         random = new Random();
         souris = new inputs.Mouse(this);
         setPanelSize();
         addKeyListener(new inputs.Clavier());
         addMouseListener(souris);
         addMouseMotionListener(souris);
    }
    //pour eviter les debordements
    private void setPanelSize(){
        Dimension size = new Dimension(1280,600);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }

    public void changeDEPx(int val){
        this.depX+=val;
        repaint();

    }
    public void changeDEPy(int val){
        this.depY+=val;
        repaint();
    }

    public void setRectPos(int x , int y){
        this.depX=x;
        this.depY=y;
        repaint();
    }



    public void paint(Graphics g){
        // permet de faire tout ce qui est nécéssaire avant de dessiner
        // empeche les bugs
        super.paintComponent(g);



        g.setColor(Color.YELLOW);
        g.fillRect((int)depX,(int)depY,200,50);
        updateRectangle();


    }
    private void updateRectangle(){
        depX+=depDX;
        if(depX >800 || depX < 0)
            depDX*=-1;
        depY+=depDY;
        if(depY >600 || depY < 0)
            depDY*=-1;
    }


}

