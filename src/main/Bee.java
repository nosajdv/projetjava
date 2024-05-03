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
    public int posXMax=0;
    public int posYMax=0;
    protected float posDX = 2.5f, posDY = 2.5f;
    public int statut=0;
    protected long lastExplorationTime; // Temps de la dernière exploration
    protected long currentTime; // Temps actuel du système
    protected long explorationStartTime; // Temps de début de l'exploration
    protected List<SourceFood> visitedSources = new ArrayList<>();
    protected List<SourceFood> visitedSourcesEm = new ArrayList<>();
    protected List<SourceFood> visitedSourcesEc = new ArrayList<>();

    public void addVisitedSourceEc(SourceFood source) {
        visitedSourcesEc.add(source);
    }

    public void addVisitedSourceEm(SourceFood source) {
        visitedSourcesEm.add(source);
    }

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
    // Méthode pour déplacer l'éclaireuse vers la ruche
    public void moveToRuche() {
        // Calculer les différences entre les coordonnées actuelles et la position (0, 0)
        int dx = 130 - posX;
        int dy = 200 - posY;

        // Déplacer l'abeille progressivement vers la position (0, 0)
        if (dx != 130) {
            posX += Math.signum(dx); // Ajouter ou soustraire 1 à la position X en fonction de la direction
        }
        if (dy != 200) {
            posY += Math.signum(dy); // Ajouter ou soustraire 1 à la position Y en fonction de la direction
        }
    }
    public void moveTo(int ddx, int ddy){
        // Calculer les différences entre les coordonnées actuelles et la position (0, 0)
        int dx = ddx - posX;
        int dy = ddy - posY;

        // Déplacer l'abeille progressivement vers la position (0, 0)
        if (dx != ddx) {
            posX += Math.signum(dx); // Ajouter ou soustraire 1 à la position X en fonction de la direction
        }
        if (dy != ddy) {
            posY += Math.signum(dy); // Ajouter ou soustraire 1 à la position Y en fonction de la direction
        }

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
            if(test<2000){
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
