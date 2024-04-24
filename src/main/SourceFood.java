package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class SourceFood {
    private int quality; // Qualité de la source de nourriture
    private int posX; // Position X de la source sur le plateau
    private int posY; // Position Y de la source sur le plateau
    private int explorationCount; // Compteur d'essais pour l'exploration de la source

    public SourceFood(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        this.explorationCount = 0;
        generateRandomQuality(); // Appel pour générer aléatoirement la qualité initiale
    }

    // Méthode pour générer aléatoirement la qualité initiale de la source de nourriture
    private void generateRandomQuality() {
        Random random = new Random();
        quality = random.nextInt(101); // Génère un entier aléatoire entre 0 et 100 (inclus)
    }

    // Méthode pour mettre à jour la qualité de la source de nourriture
    public void updateQuality(int newQuality) {
        this.quality = newQuality;
    }

    // Méthode pour incrémenter le compteur d'essais
    public void incrementExplorationCount() {
        this.explorationCount++;
    }

    // Méthode pour vérifier si la source de nourriture a été suffisamment explorée
    public boolean isExplored(int maxExplorationCount) {
        return explorationCount >= maxExplorationCount;
    }

    // Méthode pour qu'une abeille explore la source de nourriture
    public void explore(Bee bee) {
        // Simuler l'évaluation de la qualité de la source par l'abeille
        Random random = new Random();
        int evaluation = quality + random.nextInt(3) - 1; // Ajoute ou soustrait une valeur aléatoire entre -1 et 1
        if (evaluation > 0) {
            updateQuality(evaluation); // Met à jour la qualité de la source
            bee.setFoodSource(this); // L'abeille adopte cette source comme sa nouvelle source de nourriture
        }
        incrementExplorationCount(); // Incrémente le compteur d'essais de la source
    }

    // Méthode statique pour générer plusieurs instances de SourceFood à des positions aléatoires
    public static List<SourceFood> generateRandomFoodSources(int numSources, int maxX, int maxY) {
        List<SourceFood> foodSources = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < numSources; i++) {
            int posX = random.nextInt(maxX);
            int posY = random.nextInt(maxY);
            foodSources.add(new SourceFood(posX, posY));
        }
        return foodSources;
    }

    // Getters pour posX et posY
    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    // Getters pour la qualité
    public int getQuality(){
        return quality;
    }
}

