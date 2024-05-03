package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


class SourceFood {
    private int quality; // Qualité de la source de nourriture
    private int posX; // Position X de la source sur le plateau
    private int posY; // Position Y de la source sur le plateau
    public int posXMax;
    public int posYMax;
    private int explorationCount; // Compteur d'essais pour l'exploration de la source
    protected long lastExplorationTime; // Temps de la dernière exploration
    protected long currentTime; // Temps actuel du système
    protected long explorationStartTime; // Temps de début de l'exploration
    private boolean visited;
    private Bee exploringBee;
    protected String statut;
    protected int  tempQual;
    private List<Bee> visitedSources = new ArrayList<>();
    private List<SourceFood> visitedSource2 = new ArrayList<>();

    int inctest=0;
    int count=0;
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

    // Méthode pour mettre à jour le statut de la source à (posXMax, posYMax) à "marqué"
    public static boolean PlateauEcMAX(List<ScoutBee> bees) {
        for (ScoutBee bee : bees) {
            if (bee.statut == 0) {
                System.out.println("NO");
                return false;
            }
        }
        return true;
    }

    // Méthode pour qu'une abeille explore la source de nourriture
    public void explore(Bee bee) {
        // Simuler l'évaluation de la qualité de la source par l'abeille éclaireuse
        if(bee.type=="Éclaireuse") {
            List<ScoutBee> allBees = BeeManager.getAllScoutBees();
            if (bee.statut == 1 || bee.visitedSources.contains(this) || exploringBee != null) {
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
                bee.addVisitedSource(this);

                bee.statut=1;
                incrementExplorationCount();

            }

            if (PlateauEcMAX(allBees) == true) {
                System.out.println("Enfin");
                for (ScoutBee b : allBees) {
                    b.statut = 3; // Déclenche le retour à la ruche pour les abeilles éclaireuses
                }
            }


        }

        } else if (bee.type == "Employée") {
        // Vérifiez si l'employée a déjà visité trois fleurs
            if (bee.statut == 1 || bee.visitedSources.contains(this) || exploringBee != null) {
                bee.statut=0;
                System.out.println(count);
                if (bee.visitedSources.size() >= 3) { // Correction de la condition pour vérifier si trois fleurs ont été visitées
                    System.out.println(bee.visitedSources.size());
                    bee.statut = 3;
                    /* changer le statut de source à (posXMax,posYMax) à marqué */

                }
                return;
            }


            // Simuler l'évaluation de la qualité de la source par l'abeille
            double distance = bee.calculateDistance(posX, posY);
            if (distance < 15) { // Vous pouvez ajuster cette valeur selon votre besoin
                Random random = new Random();
                int evaluation = quality + random.nextInt(3) - 1; // Ajoute ou soustrait une valeur aléatoire entre -1 et 1
                if (evaluation > 1) {
                    updateQuality(evaluation); // Met à jour la qualité de la source
                    lastExplorationTime = System.currentTimeMillis(); // Mettre à jour le temps de la dernière exploration
                    exploringBee = bee;
                    bee.addVisitedSource(this); // Ajouter cette source à la liste des sources visitées par l'abeille
                    if (tempQual < evaluation) {
                        System.out.println("NEW MAX");
                        tempQual = evaluation;
                        posXMax = posY;
                        posYMax = posX;
                    }
                    count++;
                    bee.statut = 1;
                    incrementExplorationCount();
                }

                // Si l'employée a visité trois fleurs, mettre son statut à 3
                if (count >= 3) {
                    System.out.println("Done");
                    bee.statut = 3;
                }
            }

    }
 else if(bee.type=="Observatrice"){
             return;
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

