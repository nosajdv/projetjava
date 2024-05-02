package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class SourceFood {
    private int quality; // Qualité de la source de nourriture
    private int posX; // Position X de la source sur le plateau
    private int posY; // Position Y de la source sur le plateau
    private int explorationCount; // Compteur d'essais pour l'exploration de la source
    protected long lastExplorationTime; // Temps de la dernière exploration
    protected long currentTime; // Temps actuel du système
    protected long explorationStartTime; // Temps de début de l'exploration
    private boolean visited;
    private Bee exploringBee;
    protected String statut;

    public SourceFood(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        this.explorationCount = 0;
        this.visited = false; // Initialise la fleur comme non visitée
        this.exploringBee = null;
        this.statut="Pas marquer";
        generateRandomQuality(); // Appel pour générer aléatoirement la qualité initiale
    }
    public void startExploration() {
        explorationStartTime = System.currentTimeMillis();
    }

    public String getStatut(){
        return statut;
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


    // Méthode pour qu'une abeille explore la source de nourriture
    public void explore(Bee bee) {
        // Simuler l'évaluation de la qualité de la source par l'abeille
        if (bee.statut == 1 || bee.visitedSources.contains(this) || exploringBee!= null  ) {
            return;
        }

        double distance = bee.calculateDistance(posX, posY); // Calculer la distance entre l'abeille et la source de nourriture
        if (distance < 15) { // Vous pouvez ajuster cette valeur selon votre besoin
            Random random = new Random();
            int evaluation = quality + random.nextInt(3) - 1; // Ajoute ou soustrait une valeur aléatoire entre -1 et 1
            if (evaluation > 1) {
                updateQuality(evaluation); // Met à jour la qualité de la source
                lastExplorationTime = System.currentTimeMillis(); // Mettre à jour le temps de la dernière exploration

                exploringBee = bee;
                bee.statut=1;
                bee.addVisitedSource(this);
                incrementExplorationCount();
            }
        }
        // Incrémente le compteur d'essais de la source
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
    public static void resetVisited(List<SourceFood> foodSources) {
        for (SourceFood food : foodSources) {
            food.visited = false; // Réinitialise l'état de visite de chaque fleur
        }
    }
}

