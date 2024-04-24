package main;

import java.util.List;
import java.util.Random;

class ScoutBee extends Bee {

    public ScoutBee(int posX, int posY) {
        super("Éclaireuse", posX, posY);
    }

    @Override
    public void explore(List<SourceFood> foodSources) {
        // Autres actions spécifiques aux éclaireuses
        chooseRandomFoodSource(foodSources);

        // Afficher un message pour indiquer la source de nourriture choisie par l'éclaireuse
        System.out.println("Éclaireuse en position (" + posX + ", " + posY + ") a choisi la source de nourriture en position (" +
                foodSource.getPosX() + ", " + foodSource.getPosY() + ")");
    }

    // Méthode pour déplacer l'éclaireuse sur le plateau
    public void move() {
        super.move();
    }
}

class EmployeeBee extends Bee {
    public EmployeeBee(int posX, int posY) {
        super("Employée", posX, posY);
    }

    @Override
    public void explore(List<SourceFood> foodSources) {
        if (foodSource != null) {
            // Logique pour évaluer la qualité de la source
            if (foodSource.getQuality() < 50) {
                System.out.println("Mauvaise qualité, recherche d'une autre source.");
                explore(foodSources); // Réévaluation de la source
            } else {
                System.out.println("Bonne qualité de la source.");
            }
        }
    }

    // Méthode pour déplacer l'employée sur le plateau
    public void move() {

        super.move();
    }
}

class ObserverBee extends Bee {
    private int previousQuality;

    public ObserverBee(int posX, int posY) {
        super("Observatrice", posX, posY);
        previousQuality = Integer.MIN_VALUE; // Initialiser à une valeur non valide
    }

    @Override
    public void explore(List<SourceFood> foodSources) {
        if (foodSource != null) {
            int currentQuality = foodSource.getQuality();
            if (currentQuality < previousQuality) {
                System.out.println("La qualité de la source a diminué, recherche d'une autre source.");
                explore(foodSources); // Réévaluation de la source
            } else {
                System.out.println("La qualité de la source est stable ou en augmentation.");
                previousQuality = currentQuality; // Mettre à jour la qualité précédente
            }
        }
    }

    // Méthode pour déplacer l'observatrice sur le plateau
    public void move() {
        super.move();
    }
}

