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
    private float depX=0,depY=0;
    private float depDX=2.5f,depDY=2.5f;
    private int fps=0;
    private long lastCheck =0;
    private BufferedImage img,subImg;
     Random random;
    private List<Bee> bees; // Liste des abeilles
    private List<SourceFood> foodSources;

 private inputs.Mouse souris;
    public Panel(){
         souris = new inputs.Mouse(this);

         importImg();
          bees = new ArrayList<>();
         initBees(15,10,5);
         setPanelSize();
         addKeyListener(new inputs.Clavier());
         addMouseListener(souris);
         addMouseMotionListener(souris);
    }

    private void drawFood(Graphics g) {
        for (SourceFood foodSource : foodSources) {
            int foodX = foodSource.getPosX(); // Récupérer la position X de la source de nourriture
            int foodY = foodSource.getPosY(); // Récupérer la position Y de la source de nourriture
            int foodQuality = foodSource.getQuality(); // Récupérer la qualité de la source de nourriture

            // Dessiner la nourriture en fonction de sa qualité et de sa position
            g.setColor(Color.GREEN); // Couleur de la nourriture (par exemple, vert)
            int foodSize = foodQuality / 10; // Taille de la nourriture en fonction de sa qualité
            g.fillRect(foodX, foodY, foodSize, foodSize); // Dessiner un rectangle représentant la nourriture
        }
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
    private void updateBees() {
        for (Bee bee : bees) {
            bee.move(); // Met à jour la position de chaque abeille
        }
    }

    private void initBees(int nbS, int nbE, int nbO) {
        // Liste pour stocker les abeilles
        bees = new ArrayList<>();


        int a=100,b=100;
        // Créez et ajoutez les abeilles ScoutBee
        for (int i = 0; i < nbS; i++) {
            ScoutBee scoutBee = new ScoutBee(a, b);
            bees.add(scoutBee);
            a=a-10;
            b=b-10;
        }

         a=200;
         b=200;
        // Créez et ajoutez les abeilles EmployeeBee
        for (int i = 0; i < nbE; i++) {
            EmployeeBee employeeBee = new EmployeeBee(a, b);
            bees.add(employeeBee);
            a=a-10;
            b=b-10;
        }
        a=300;
        b=300;
        // Créez et ajoutez les abeilles ObserverBee
        for (int i = 0; i < nbO; i++) {
            ObserverBee observerBee = new ObserverBee(a, b);
            bees.add(observerBee);
            a=a-20;
            b=b-20;
        }
    }




    public void paint(Graphics g){
        // permet de faire tout ce qui est nécéssaire avant de dessiner
        // empeche les bugs
        super.paintComponent(g);
       // drawFood(g);
        subImg = img.getSubimage(0*26,0*32,26,32);
        for (Bee bee : bees) {
            bee.paint(g); // Dessine chaque abeille
            g.drawImage(subImg,(int)bee.posX,(int)bee.posY,null);
        }
        updateBees(); // Met à jour la position des abeilles


       // updateRectangle();
    }
}


