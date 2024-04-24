package main;
import inputs.Clavier;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Panel extends JPanel {
    private float depX=100,depY=100;
    private float depDX=2.5f,depDY=2.5f;
    private int fps=0;
    private long lastCheck =0;
    private BufferedImage img,subImg;
     Random random;
    private List<Bee> bees; // Liste des abeilles

 private inputs.Mouse souris;
    public Panel(){
         souris = new inputs.Mouse(this);

         importImg();
          bees = new ArrayList<>();
         setPanelSize();
         addKeyListener(new inputs.Clavier());
         addMouseListener(souris);
         addMouseMotionListener(souris);
          initBees(); // Initialisation des abeilles
    }


  private void importImg(){
        InputStream is = getClass().getResourceAsStream("/bee_spritesheetv2.png");
        try{
            img = ImageIO.read(is);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

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

    private void updateRectangle(){
        depX+=depDX;
        if(depX >1280 || depX < 0)
            depDX*=-1;
        depY+=depDY;
        if(depY >600 || depY < 0)
            depDY*=-1;
    }

    private void initBees() {
        // Créez vos abeilles ici avec les positions initiales souhaitées
        ScoutBee scoutBee = new ScoutBee(100, 100);
        EmployeeBee employeeBee = new EmployeeBee(200, 200);
        ObserverBee observerBee = new ObserverBee(300, 300);

        // Ajoutez les abeilles à la liste
        bees.add(scoutBee);
        bees.add(employeeBee);
        bees.add(observerBee);
    }

    public void paint(Graphics g){
        // permet de faire tout ce qui est nécéssaire avant de dessiner
        // empeche les bugs
        super.paintComponent(g);
        for (Bee bee : bees) {
            bee.move(); // Déplacez l'abeille
            bee.paint(g); // Dessinez l'abeille
        }
        subImg = img.getSubimage(0*26,0*32,26,32);

        g.drawImage(subImg,(int)depX,(int)depY,null);
        updateRectangle();
    }
}


