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
    private List<SourceFood> foodSource;
    private BufferedImage[] img2;
    private BufferedImage backgroundImage; // Image de fond vert


 private inputs.Mouse souris;
    public Panel(){
         souris = new inputs.Mouse(this);
        importBackgroundImage();
         importImg();
         importFleur();
          bees = new ArrayList<>();
         foodSource = SourceFood.generateRandomFoodSources(75, 1280, 600); // Générer 10 sources de nourriture
         initBees(15,10,5);
         setPanelSize();
         addKeyListener(new inputs.Clavier());
         addMouseListener(souris);
         addMouseMotionListener(souris);
    }

    private void importBackgroundImage() {
        InputStream is = getClass().getResourceAsStream("/background.png"); // Assurez-vous de remplacer "background.jpg" par le nom de votre fichier d'image
        try {
            backgroundImage = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
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
    private void importFleur() {
        img2 = new BufferedImage[72]; // Nombre total de fleurs dans votre fichier d'image
        try {
            InputStream is = getClass().getResourceAsStream("/flower.png");
            BufferedImage flowerSheet = ImageIO.read(is);
            int flowerWidth = 32; // Largeur d'une fleur dans votre fichier
            int flowerHeight = 32; // Hauteur d'une fleur dans votre fichier
            int rows = flowerSheet.getHeight() / flowerHeight;
            int cols = flowerSheet.getWidth() / flowerWidth;
            int index = 0;
            for (int i = 0; i < rows-1; i++) {
                for (int j = 0; j < cols-1; j++) {
                    img2[index] = flowerSheet.getSubimage(j * flowerWidth, i * flowerHeight, flowerWidth, flowerHeight);
                    index++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private BufferedImage getRandomFlowerImage() {
        Random random = new Random();
        int index = random.nextInt(img2.length);
        return img2[index];
    }

    private void setPanelSize(){
        Dimension size = new Dimension(1280,600);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }

    private void updateBees() {
        long currentTime = System.currentTimeMillis();

        for (Bee bee : bees) {
            bee.move();
            for (SourceFood food : foodSource){
                double distance = bee.calculateDistance(food.getPosX(), food.getPosY());
                if (distance<10) {
                    food.explore(bee); // Explore la source de nourriture uniquement si l'abeille est exactement à la même position
                    break; // Sortir de la boucle dès qu'une source de nourriture est explorée
                }

            }

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
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        for (SourceFood foodSource : foodSource) {
            int foodX = foodSource.getPosX(); // Récupérer la position X de la source de nourriture
            int foodY = foodSource.getPosY(); // Récupérer la position Y de la source de nourriture
            int flowerIndex = determineFlowerIndex(foodX, foodY); // Déterminer l'index de la fleur en fonction de sa position
            g.drawImage(img2[flowerIndex], foodX, foodY, null); // Dessiner la fleur avec son sprite spécifique
        }

        subImg = img.getSubimage(0*26,0*32,26,32);
        for (Bee bee : bees) {
            bee.paint(g); // Dessine chaque abeille
            // if (bee instanceof ScoutBee)
            g.drawImage(subImg,(int)bee.posX,(int)bee.posY,null);

            // if (bee instanceof ObserverBee)

            // if (bee instanceof EmployeeBee)
        }
        updateBees(); // Met à jour la position des abeilles


    }
    private int determineFlowerIndex(int posX, int posY) {

        int flowerWidth = 32;
        int flowerHeight = 32;

        // Nombre de colonnes dans la grille
        int numCols = 1280 / 600; // 1280 est la largeur du panel

        // Calculez l'indice de la colonne
        int colIndex = posX / flowerWidth;

        // Calculez l'indice de la ligne
        int rowIndex = posY / flowerHeight;

        // Calculez l'index de la fleur dans le tableau img2
        int flowerIndex = rowIndex * numCols + colIndex;

        // Assurez-vous que l'index calculé est dans les limites du tableau img2
        if (flowerIndex >= 0 && flowerIndex < img2.length) {
            return flowerIndex;
        } else {
            // Si l'index est en dehors des limites du tableau, retournez un indice par défaut
            return 0; // Ou tout autre indice par défaut selon votre besoin
        }
    }
}


