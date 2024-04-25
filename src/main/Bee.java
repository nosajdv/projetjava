package main;

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

    public Bee(String type, int posX, int posY) {
        this.type = type;
        this.posX = posX;
        this.posY = posY;
    }

    public String getType() {
        return type;
    }

    public SourceFood getFoodSource() {
        return foodSource;
    }

    public void setFoodSource(SourceFood foodSource) {
        this.foodSource = foodSource;
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

    public void changePosy(int val) {
        this.posY += val;

    }

    public void setPos(int x, int y) {
        this.posX = x;
        this.posY = y;

    }

    protected void updateBee() {
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


    // Méthode abstraite pour que l'abeille explore une source de nourriture
    public abstract void explore(List<SourceFood> foodSources);

    // Méthode pour choisir aléatoirement une source de nourriture
    protected void chooseRandomFoodSource(List<SourceFood> foodSources) {
        Random random = new Random();
        int index = random.nextInt(foodSources.size());
        this.foodSource = foodSources.get(index);
    }


    // Méthode pour simuler le déplacement de l'abeille
    public void move() {
        updateBee();
        // Mettre à jour la position de l'abeille et redessiner
        setPos((int) posX, (int) posY);
    }


}
