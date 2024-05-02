package main;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.*;
import javax.swing.JPanel;


abstract class Bee extends JPanel {
    protected String type; // Type de l'abeille (éclaireuse, employée, observatrice)
    protected SourceFood foodSource; // Source de nourriture de l'abeille
    public int posX; // Position X de l'abeille sur le plateau
    protected int posY; // Position Y de l'abeille sur le plateau
    protected float posDX = 2.5f, posDY = 2.5f;
    public int statut;
    protected long lastExplorationTime; // Temps de la dernière exploration
    protected long currentTime; // Temps actuel du système
    protected long explorationStartTime; // Temps de début de l'exploration
    protected List<SourceFood> visitedSources = new ArrayList<>();

    public void addVisitedSource(SourceFood source) {
        visitedSources.add(source);
    }

    public Bee(String type, int posX, int posY) {
        this.type = type;
        this.posX = posX;
        this.posY = posY;
    }



    public String getType() {
        return type;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void changePosx(int val) {
        this.posX += val;
    }

    public double calculateDistance(int targetX, int targetY) {
        double dx = this.posX - targetX;
        double dy = this.posY - targetY;
        return Math.sqrt(dx * dx + dy * dy);
    }


    public void changePosy(int val) {
        this.posY += val;

    }

    public void setPos(int x, int y) {
        this.posX = x;
        this.posY = y;

    }

    // Méthode pour démarrer l'exploration d'une source de nourriture
    public void startExploration() {
        explorationStartTime = System.currentTimeMillis();
    }

    // Méthode pour mettre à jour le mouvement de l'abeille
    protected void updateBee() {
        for (SourceFood source : visitedSources) {
            long test =System.currentTimeMillis() - source.lastExplorationTime;
            //System.out.println(test);
            if(test<1000){
                return;
            }
        }

        if (statut == 1) {
                return;
        }

        // Si l'abeille n'est pas en exploration ou si moins de 10 secondes se sont écoulées, continuez le mouvement
        // Générer des valeurs aléatoires pour le déplacement
        Random random = new Random();
        float randomDX = (random.nextFloat() - 0.5f) * 2; // Valeur aléatoire entre -1 et 1
        float randomDY = (random.nextFloat() - 0.5f) * 2; // Valeur aléatoire entre -1 et 1

        // Ajouter les valeurs aléatoires au déplacement actuel
        posX += posDX + randomDX;
        posY += posDY + randomDY;

        // Gérer les rebonds sur les bords de l'écran
        if (posX > 1280 || posX < 0)
            posDX *= -1;
        if (posY > 600 || posY < 0)
            posDY *= -1;



    }


    // Méthode pour simuler le déplacement de l'abeille
    public void move() {
            updateBee();
        // Mettre à jour la position de l'abeille et redessiner
        setPos((int) posX, (int) posY);
    }

}
