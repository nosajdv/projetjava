package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java. lang. Math;

class ScoutBee extends Bee {

    public ScoutBee(int posX, int posY) {
        super("Éclaireuse", posX, posY);
    }
    // Méthode pour déplacer l'éclaireuse sur le plateau
    public void move() {
     //  super.move();
    }
    // Méthode pour déplacer l'éclaireuse vers la ruche
    public void moveToRuche() {
        // Calculer les différences entre les coordonnées actuelles et la position (0, 0)
        int dx = 100 - posX;
        int dy = 120 - posY;

        // Déplacer l'abeille progressivement vers la position (0, 0)
        if (dx != 100) {
            posX += Math.signum(dx); // Ajouter ou soustraire 1 à la position X en fonction de la direction
        }
        if (dy != 120) {
            posY += Math.signum(dy); // Ajouter ou soustraire 1 à la position Y en fonction de la direction
        }
    }



}

class EmployeeBee extends Bee {
    public EmployeeBee(int posX, int posY) {
        super("Employée", posX, posY);
    }
    public int getPosXMax(){
        return posXMax;
    }

    public int getPosYMax(){
        return posYMax;
    }

    public void moveToRuche() {
        // Calculer les différences entre les coordonnées actuelles et la position (0, 0)
        int dx = 100 - posX;
        int dy = 120 - posY;

        // Déplacer l'abeille progressivement vers la position (0, 0)
        if (dx != 100) {
            posX += Math.signum(dx); // Ajouter ou soustraire 1 à la position X en fonction de la direction
        }
        if (dy != 120) {
            posY += Math.signum(dy); // Ajouter ou soustraire 1 à la position Y en fonction de la direction
        }
        statut=3;
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
        temp=0;
    }

    public void moveToRuche() {
        // Calculer les différences entre les coordonnées actuelles et la position (0, 0)
        int dx = 100 - posX;
        int dy = 120 - posY;

        // Déplacer l'abeille progressivement vers la position (0, 0)
        if (dx != 100) {
            posX += Math.signum(dx); // Ajouter ou soustraire 1 à la position X en fonction de la direction
        }
        if (dy != 120) {
            posY += Math.signum(dy); // Ajouter ou soustraire 1 à la position Y en fonction de la direction
        }
    }

    public int getPosXMax() {
        return posXMax;
    }

    public int getPosYMax() {
        return posYMax;
    }


    public void observe(List<EmployeeBee> employees) {
        boolean allEmployeesCollected = true; // Initialiser à true
        // Vérifie si toutes les employées ont un statut de 3
        for (EmployeeBee employee : employees) {
            if (employee.statut != 3) { // Si au moins une employée n'est pas rentrée
                allEmployeesCollected = false; // Mettre à false
              //  System.out.println("FAlsde");
                statut=3;
                break; // Sortir de la boucle car on sait déjà que toutes les employées ne sont pas rentrées
            }
        }

        if (allEmployeesCollected) {
            // Une fois que toutes les employées ont terminé la collecte, les observatrices se déplacent
            for (EmployeeBee employee : employees) {
                // Les observatrices se dirigent vers les positions posXMax et posYMax de chaque employée
                if(statut!=1 || statut==4) {
                    if(employee.posXMax!=0 && employee.posYMax!=0) {
                        moveTo(employee.getPosXMax(), employee.getPosYMax());

                    }
                }
            }
            statut = 0; // Mettre à jour le statut de l'observatrice à 0
        }
    }

    public void moveTo(int targetX, int targetY) {
        int dx = targetX - posX;
        int dy = targetY - posY;

        // Déplacer l'observatrice progressivement vers la position cible
        if (dx != 0) {
            posX += Math.signum(dx); // Ajouter ou soustraire 1 à la position X en fonction de la direction
        }
        if (dy != 0) {
            posY += Math.signum(dy); // Ajouter ou soustraire 1 à la position Y en fonction de la direction
        }

        statut=3;
    }







    public void deplace(List<SourceFood> foodSources) {
        if (foodSource != null) {
            int currentQuality = foodSource.getQuality();
            if (currentQuality < previousQuality) {
                System.out.println("La qualité de la source a diminué, recherche d'une autre source.");
                deplace(foodSources); // Réévaluation de la source
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


class BeeManager {
    private static List<Bee> allBees = new ArrayList<>();

    // Méthode pour ajouter une abeille à la liste globale
    public static void addBee(Bee bee) {
        allBees.add(bee);
    }

    // Méthode pour récupérer toutes les abeilles éclaireuses
    public static List<ScoutBee> getAllScoutBees() {
        List<ScoutBee> scoutBees = new ArrayList<>();
        for (Bee bee : allBees) {
            if (bee instanceof ScoutBee) {
                scoutBees.add((ScoutBee) bee);
            }
        }
        return scoutBees;
    }

    public void moveToRuche() {
       moveToRuche();
    }

    // Méthode pour récupérer toutes les abeilles employées
    public static List<EmployeeBee> getAllEmployeeBees() {
        List<EmployeeBee> employeeBees = new ArrayList<>();
        for (Bee bee : allBees) {
            if (bee instanceof EmployeeBee) {
                employeeBees.add((EmployeeBee) bee);
            }
        }
        return employeeBees;
    }

    // Méthode pour récupérer toutes les abeilles observatrices
    public static List<ObserverBee> getAllObserverBees() {
        List<ObserverBee> observerBees = new ArrayList<>();
        for (Bee bee : allBees) {
            if (bee instanceof ObserverBee) {
                observerBees.add((ObserverBee) bee);
            }
        }
        return observerBees;
    }
}
