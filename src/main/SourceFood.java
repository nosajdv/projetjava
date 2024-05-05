package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


class SourceFood {
    private int qualite; // Qualité de la source de nourriture
    private int posX; // Position X de la source sur le plateau
    private int posY; // Position Y de la source sur le plateau
    public int posXMax=0;
    public int posYMax=0;
    private int explorationCount; // Compteur d'essais pour l'exploration de la source
    protected long ExplorationTmps; // Temps de la dernière exploration
    private boolean visited;
    protected Bee exploreBee;
    private Bee exploreBee1;
    private Bee exploreBee2;
    protected String statut;
    private List<Bee> tabBee = new ArrayList<>();

    public SourceFood(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        this.explorationCount = 0;
        this.visited = false; // Initialise la fleur comme non visitée
        this.exploreBee = null;
        this.statut="Pas marquée";
        randomQualite(); // Appel pour générer aléatoirement la qualité initiale
    }


    public int getPosXMax(){
        return posXMax;
    }

    public int getPosYMax(){
        return posYMax;
    }

    public String getStatut(){
        return statut;
    }
    // Méthode pour générer aléatoirement la qualité initiale de la source de nourriture
    private void randomQualite() {
        Random random = new Random();
        qualite = random.nextInt(101); // Génère un entier aléatoire entre 0 et 100 (inclus)
    }

    // Méthode pour mettre à jour la qualité de la source de nourriture
    public void updateQualitee(int newQuality) {
        this.qualite = newQuality;
    }

    // Méthode pour incrémenter le compteur d'essais
    public void incrementeExploration() {
        this.explorationCount++;
    }

    // Méthode pour mettre à jour le statut de la source à (posXMax, posYMax) à "marqué"
    public static boolean PlateauEcMAX(List<EclaireuseBee> bees) {
        for (EclaireuseBee bee : bees) {
            if (bee.statut == 0) {
              //  System.out.println("NO");
                return false;
            }
        }
        return true;
    }



    // Méthode pour qu'une abeille explore la source de nourriture
    public void explore(Bee bee) {
        if (bee.type.equals("Observatrice")) {
            if(bee.statut==3){
                return;
            }
            if (bee.visiteeSources.size() >= 3) {
                bee.statut = 3;
                return;
            }
            if (bee.statut == 1 || bee.visiteeSources.contains(this) || exploreBee1 != null) {
                    bee.statut = 0;
                return;
            }
            double distance = bee.calculateDistance(posX, posY);
            // Si l'abeille est à une distance inférieure à 15 (ou une autre valeur appropriée)
            Random random = new Random();
            int evaluation = qualite + random.nextInt(3) - 1; // Ajoute ou soustrait une valeur aléatoire entre -1 et 1
            if (distance < 15 || statut !="Marquée") {
                updateQualitee(evaluation); // Met à jour la qualité de la source
                ExplorationTmps = System.currentTimeMillis(); // Mettre à jour le temps de la dernière exploration
                exploreBee1 = bee;
                bee.addVisitedSource(this);
                bee.statut = 1;
                incrementeExploration();
            }
        }

        // Simuler l'évaluation de la qualité de la source par l'abeille éclaireuse
        if (bee.type == "Éclaireuse") {
            List<EclaireuseBee> allBees = BeeManager.getAllEclaireuseBees();
            if (bee.statut == 1 || bee.visiteeSources.contains(this) || exploreBee != null) {
                return;
            }
            double distance = bee.calculateDistance(posX, posY); // Calculer la distance entre l'abeille et la source de nourriture
            if (distance < 15) { // Vous pouvez ajuster cette valeur selon votre besoin
                Random random = new Random();
                int evaluation = qualite + random.nextInt(3) - 1; // Ajoute ou soustrait une valeur aléatoire entre -1 et 1
                if (evaluation > 1|| statut !="Marquée") {
                    updateQualitee(evaluation); // Met à jour la qualité de la source
                    ExplorationTmps = System.currentTimeMillis(); // Mettre à jour le temps de la dernière exploration
                    exploreBee = bee;
                    bee.addVisitedSource(this);
                    statut="Marquée";
                    bee.statut = 1;
                    incrementeExploration();
                }
                     //Verification si toute les abbeiles sont ont une source de nourriture
                if (PlateauEcMAX(allBees) == true) {
                    for (EclaireuseBee b : allBees) {
                        b.statut = 3; // Déclenche le retour à la ruche pour les abeilles éclaireuses
                    }
                }


            }
        }


        // Vérifiez d'abord si l'abeille est de type "Employée"
        if (bee.type.equals("Employée")) {
            // Bloc de code pour les abeilles employées
            List<EmployeeBee> allEBees = BeeManager.getAllEmployeeBees();
            tabBee.add(bee);

            if(bee.statut==3){
                return;
            }
            // Vérifiez si l'employée a déjà visité trois fleurs
            if (bee.statut == 1 || bee.visiteeSources.contains(this) || exploreBee2 != null) {
                if (bee.visiteeSources.size() >= 3 && bee.statut!=5) {
                    return;
                } else {
                    bee.statut = 0;
                }
                return;
            }

            // Simuler l'évaluation de la qualité de la source par l'abeille
            double distance = bee.calculateDistance(posX, posY);
            if (distance < 15) {
                Random random = new Random();
                int evaluation = qualite + random.nextInt(3) - 1;
                if (evaluation > 1 || statut !="Marquée") {
                    updateQualitee(evaluation);
                    ExplorationTmps = System.currentTimeMillis();
                    exploreBee2 = bee;
                    if(evaluation>bee.temp) {
                        bee.posXMax = posX;
                        bee.posYMax = posY;
                        bee.temp = evaluation;
                    }
                    bee.addVisitedSource(this);
                    if(bee.visiteeSources.size()==3) {
                        bee.statut = 1;
                    }else{
                        bee.statut = 0;
                    }
                    incrementeExploration();
                }


            }
            if (bee.visiteeSources.size()==3) {
                    bee.statut = 3; // Déclenche le retour à la ruche pour les abeilles éclaireuses
            }
        }

        if (explorationCount >= 5) {
            // Supprimer la source de nourriture de la liste globale
            FoodManager.removeFoodSource(this);
        }
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
    public int getQualite(){
        return qualite;
    }

}
class FoodManager {
    private static List<SourceFood> allFoodSources = new ArrayList<>();

    // Méthode pour ajouter une source de nourriture à la liste globale
    public static void addFoodSource(SourceFood foodSource) {
        allFoodSources.add(foodSource);
    }
    public static void removeFoodSource(SourceFood foodSource) {
        allFoodSources.remove(foodSource);
    }
    // Méthode pour récupérer toutes les sources de nourriture
    public static List<SourceFood> getAllFoodSources() {
        return allFoodSources;
    }

    // Autres méthodes de gestion des sources de nourriture peuvent être ajoutées ici
}
