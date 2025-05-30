package main;
import inputs.Clavier;
import inputs.Mouse;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public class Panel extends JPanel {
    private BufferedImage img, img1, img3, subImg;
    private List<Bee> bees;
    private List<SourceNourriutre> sourceNourriutres;
    private BufferedImage[] img2;
    private BufferedImage backgroundImage;
    private Mouse souris;
    String qualiteText;
    String statutText;
    private BufferedImage rucheImage;
    int phase = 0;
    int fin;

    public Panel() {
        souris = new Mouse(this);
        chargerToutesLesImages();
        bees = new ArrayList<>();
        sourceNourriutres = SourceNourriutre.generateRandomFoodSources(50, 1280, 600);
        initBees(15, 10, 5);
        setPanelSize();
        addKeyListener(new Clavier());
        addMouseListener(souris);
        addMouseMotionListener(souris);
    }

    /**
     * Charge toutes les images nécessaires pour l'application
     */
    private void chargerToutesLesImages() {
        rucheImage = chargerImage("/res/Ruchev2.png");
        backgroundImage = chargerImage("/res/backgroundV4.png");
        img = chargerImage("/res/bee_spritesheetv2.png");
        img1 = chargerImage("/res/beeV2.png");
        img3 = chargerImage("/res/beeV3.png");
        img2 = chargerFleurs("/res/flowerv2.png");
    }

    /**
     * Méthode générique pour charger une image depuis les ressources
     * @param chemin Le chemin relatif de l'image dans le dossier res
     * @return L'image chargée ou une image vide si échec
     */
    private BufferedImage chargerImage(String chemin) {
        try (InputStream is = getClass().getResourceAsStream(chemin)) {
            if (is == null) {
                throw new IOException("Ressource introuvable: " + chemin);
            }
            BufferedImage image = ImageIO.read(is);
            if (image == null) {
                throw new IOException("Échec du chargement de l'image: " + chemin);
            }
            return image;
        } catch (IOException e) {
            e.printStackTrace();
            // Crée une image vide comme solution de repli
            System.err.println("Utilisation d'une image vide pour: " + chemin);
            return new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        }
    }

    /**
     * Charge la feuille de sprites des fleurs et la découpe
     * @param chemin Chemin vers l'image contenant toutes les fleurs
     * @return Tableau d'images de fleurs
     */
    private BufferedImage[] chargerFleurs(String chemin) {
        BufferedImage[] fleurs = new BufferedImage[83];
        try (InputStream is = getClass().getResourceAsStream(chemin)) {
            if (is == null) {
                throw new IOException("Ressource introuvable: " + chemin);
            }
            
            BufferedImage feuilleFleurs = ImageIO.read(is);
            if (feuilleFleurs == null) {
                throw new IOException("Échec du chargement de la feuille de fleurs: " + chemin);
            }

            int largeurFleur = 32;
            int hauteurFleur = 32;
            int lignes = feuilleFleurs.getHeight() / hauteurFleur;
            int colonnes = feuilleFleurs.getWidth() / largeurFleur;
            int index = 0;
            
            for (int i = 0; i < lignes - 1; i++) {
                for (int j = 0; j < colonnes - 1; j++) {
                    if (index < fleurs.length) {
                        fleurs[index] = feuilleFleurs.getSubimage(
                            j * largeurFleur, 
                            i * hauteurFleur, 
                            largeurFleur, 
                            hauteurFleur
                        );
                        index++;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Initialise avec des images vides comme solution de repli
            for (int i = 0; i < fleurs.length; i++) {
                fleurs[i] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
            }
        }
        return fleurs;
    }


    private void setPanelSize() {
        Dimension size = new Dimension(1280, 600);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }

    public boolean VerifSourceFood(List<SourceNourriutre> f){
        boolean test=true;
        for (SourceNourriutre food : f) {
           if(food.statut=="Pas explorée");
           test=false;
        }
        return test;
    }


    private void updateBees() {
        long currentTime = System.currentTimeMillis();
        observe(BeeManager.getAllEmployeeBees(),BeeManager.getAllObserveratriceBees());


        phase=employeeObserveVerif(BeeManager.getAllEmployeeBees(),BeeManager.getAllObserveratriceBees());

        //On vérifie si toutes les sources sont explorée
       if(VerifSourceFood(FoodManager.getAllFoodSources()))
        RucheDepEclaireuse(BeeManager.getAllEclaireuseBees());

        for (Bee bee : bees) {
            if (bee.statut == 3) {
                bee.moveToRuche();
            }else if(bee.statut==5){
                    bee.moveTo(bee.posXMax, bee.posYMax);
            }else{
                bee.move();
                for (SourceNourriutre food : sourceNourriutres) {
                    food.explore(bee);
                }
            }

        }
        if(phase==1){
            for(EmployeeBee employee : BeeManager.getAllEmployeeBees()){
                employee.statut=5;
              //  System.out.println(employee.posXMax+" et "+employee.posYMax);
                employee.moveTo(employee.posXMax, employee.posYMax);
            }
            for(ObservatriceBee observe : BeeManager.getAllObserveratriceBees()){
                observe.statut=5;
             //   System.out.println(observe.posXMax+" et "+observe.posYMax);
                observe.moveTo(observe.posXMax, observe.posYMax);
            }
        }
        int test=finSimulaton(FoodManager.getAllFoodSources());
        if(test>0){
            System.out.println("Meilleur source qualité:" + test +"... fin de de simulation");
        }
    }
    public int finSimulaton(List<SourceNourriutre> sources){
       int qualmax=-9999;
        for(SourceNourriutre source: sources){
            if(source.statut=="Pas marqué"){
              return -1;
            }
            if(source.getQualite()>qualmax){
                qualmax=source.getQualite();
            }
        }
        return qualmax;
    }


    private void initBees(int nbS, int nbE, int nbO) {
        // Liste pour stocker les abeilles
        bees = new ArrayList<>();
        int a = 100, b = 120;
        // Créez et ajoutez les abeilles ScoutBee
        for (int i = 0; i < nbS; i++) {
            EclaireuseBee Eclaireuse = new EclaireuseBee(a, b);
            bees.add(Eclaireuse);
            BeeManager.addBee(Eclaireuse);

        }


        a = 100;
        b = 120;
        // Créez et ajoutez les abeilles EmployeeBee
        for (int i = 0; i < nbE; i++) {
            EmployeeBee employeeBee = new EmployeeBee(a, b);
            bees.add(employeeBee);
            BeeManager.addBee(employeeBee);

        }
        a = 100;
        b = 120;
        // Créez et ajoutez les abeilles ObserverBee
        for (int i = 0; i < nbO; i++) {
            ObservatriceBee observatriceBee = new ObservatriceBee(a, b);
            bees.add(observatriceBee);
            BeeManager.addBee(observatriceBee);
            a = a - 20;
            b = b - 20;
        }
    }
    public void RucheDepEclaireuse(List<EclaireuseBee> scout){
        boolean allEclaireuseatRuche=true;
        for (EclaireuseBee scouts : scout) {
            if(!scouts.isAtRuche()) { // Si au moins une employée n'est pas rentrée
                allEclaireuseatRuche = false; // Mettre à false
                break; // Sortir de la boucle car on sait déjà que toutes les employées ne sont pas rentrées
            }
        }
        if(allEclaireuseatRuche){
            for(EclaireuseBee scouts : scout){
                scouts.statut=0;
            }
        }
    }

    public int employeeObserveVerif(List<EmployeeBee> employe,List<ObservatriceBee> observatrice){
        boolean allEmployeatRuche=true;
        boolean allObserveatRuche=true;

        for (EmployeeBee employee : employe) {
            if(!employee.isAtRuche()) { // Si au moins une observatrice n'est pas rentrée
                allEmployeatRuche = false; // Mettre à false
                break; // Sortir de la boucle car on sait déjà que toutes les employées ne sont pas rentrées
            }
        }
        for (ObservatriceBee observe : observatrice) {
            if(!observe.isAtRuche()) { // S// i au moins une employée n'est pas rentrée
                allObserveatRuche = false; // Mettre à false
                break; // Sortir de la boucle car on sait déjà que toutes les employées ne sont pas rentrées
            }
        }
        if(allObserveatRuche&&allEmployeatRuche ){
         return 1;
        }
        return 0;
    }
    public void observe(List<EmployeeBee> employees, List<ObservatriceBee> observe) {
        boolean allEmployeesCollected = true; // Initialiser à true
        for (EmployeeBee employee : employees) {
            if (employee.statut != 3) { // Si au moins une employée n'est pas rentrée
                allEmployeesCollected = false; // Mettre à false
                //  System.out.println("FAlsde");
                break; // Sortir de la boucle car on sait déjà que toutes les employées ne sont pas rentrées
            }
        }

        if (allEmployeesCollected) {
            for (ObservatriceBee observer : observe) {
                for (EmployeeBee employee : employees) {
                    // Les observatrices se dirigent vers les positions posXMax et posYMax de chaque employée
                    if(observer.visiteeSources.size()>=3) {
                        return;
                    }
                    if (observer.statut != 1 && observer.statut != 4 && employee.prise!=1) {
                        observer.moveTo(employee.posXMax, employee.posYMax);
                        observer.posXMax=employee.posXMax;
                        observer.posYMax=employee.posYMax;
                        observer.statut=4;
                        employee.prise=1;
                    }
                }
            }
            // Mettre à jour le statut de l'observatrice à 0
            for (ObservatriceBee observer : observe) {
                observer.statut = 0;
            }
        }


}
    public void paint(Graphics g){
        // permet de faire tout ce qui est nécéssaire avant de dessiner
        // empeche les bugs
        super.paintComponent(g);
        // Dessiner l'image de la ruche à la position (0, 0)
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        // Dessiner toutes les sources de nourriture
        for (SourceNourriutre sourceNourriutre : sourceNourriutres) {
            int foodX = sourceNourriutre.getPosX(); // Récupérer la position X de la source de nourriture
            int foodY = sourceNourriutre.getPosY(); // Récupérer la position Y de la source de nourriture
            int flowerIndex = determineFlowerIndex(foodX, foodY); // Déterminer l'index de la fleur en fonction de sa position
            g.drawImage(img2[flowerIndex], foodX, foodY, null); // Dessiner la fleur avec son sprite spécifique
            //Affichage de la qualité
            qualiteText = "Qualité: " + sourceNourriutre.getQualite();
            g.setColor(Color.darkGray);
            g.drawString(qualiteText, foodX, foodY - 10); // Afficher la qualité au-dessus de la source de nourriture
            statutText = "Statut: " + sourceNourriutre.getStatut();
            g.drawString(statutText, foodX, foodY - 20); // Afficher le statut au-dessus de la qualité
        }

        g.drawImage(rucheImage, 0, 0, null);

           //dessin de toute les images
        for (Bee bee : bees) {
            if (bee instanceof EclaireuseBee)
                subImg = img.getSubimage(0*26,0*32,26,32);
            if (bee instanceof ObservatriceBee)
                subImg = img3.getSubimage(0*26,0*32,26,32);
            if (bee instanceof EmployeeBee)
                subImg = img1.getSubimage(0*26,0*32,26,32);
               bee.paint(g); // Dessine chaque abeille
            g.drawImage(subImg,(int)bee.posX,(int)bee.posY,null);
        }
        fin=finSimulaton(FoodManager.getAllFoodSources());
        if(fin>0){
            // Créer une police plus grande
            Font font = new Font("Arial", Font.BOLD, 30);
            g.setFont(font);
            // Définir la couleur du texte
            g.setColor(Color.RED);
            // Définir le texte à afficher
            String message = "Meilleure source qualité: " + fin + "... fin de la simulation";
            // Obtenir les dimensions du texte
            FontMetrics metrics = g.getFontMetrics(font);
            int textWidth = metrics.stringWidth(message);
            // Calculer les coordonnées pour centrer le texte
            int x = (getWidth() - textWidth) / 2;
            int y = getHeight() / 2;
            // Dessiner le texte
            g.drawString(message, x, y);
        }
        updateBees(); // Met à jour la position des abeilles
    }
    private int determineFlowerIndex(int posX, int posY) {

        int fleurLargeur = 32;
        int fleurTaille = 32;

        // Nombre de colonnes dans la grille
        int numCols = 1280 / 600; // 1280 est la largeur du panel

        // Calculez l'indice de la colonne
        int colIndex = posX / fleurLargeur;

        // Calculez l'indice de la ligne
        int ligneIndex = posY / fleurTaille;

        // Calculez l'index de la fleur dans le tableau img2
        int fleurIndex = ligneIndex * numCols + colIndex;

        // Assurez-vous que l'index calculé est dans les limites du tableau img2
        if (fleurIndex >= 0 && fleurIndex < img2.length) {
            return fleurIndex;
        } else {
            // Si l'index est en dehors des limites du tableau, retournez un indice par défaut
            return 0; // Ou tout autre indice par défaut selon votre besoin
        }
    }
}
