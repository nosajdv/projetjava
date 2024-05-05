package main;

import java.util.ArrayList;
import java.util.List;
import java. lang. Math;
import java.util.Random;

class EclaireuseBee extends Bee {

    public EclaireuseBee(int posX, int posY) {
        super("Éclaireuse", posX, posY);
    }
    // Méthode pour déplacer l'éclaireuse sur le plateau
    public void move() {
       super.move();
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
    int prise=0;
    public EmployeeBee(int posX, int posY) {
        super("Employée", posX, posY);
    }
    public int getPosXMax(){
        return posXMax;
    }

    public int getPosYMax(){
        return posYMax;
    }
    public boolean isAtRuche() {
        return (posX == 100 && posY == 120);
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
        if(statut==5&&(posX==posXMax&&posY==posYMax))statut=3;

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
      //  statut=3;
    }

    // Méthode pour déplacer l'employée sur le plateau
    public void move() {
       super.move();
    }
}

class ObservatriceBee extends Bee {

    public ObservatriceBee(int posX, int posY) {
        super("Observatrice", posX, posY);
        temp=0;
        statut=3;
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
        if(statut==5&&(posX==posXMax&&posY==posYMax))statut=3;

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
    public static List<EclaireuseBee> getAllEclaireuseBees() {
        List<EclaireuseBee> scoutBees = new ArrayList<>();
        for (Bee bee : allBees) {
            if (bee instanceof EclaireuseBee) {
                scoutBees.add((EclaireuseBee) bee);
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
    public static List<ObservatriceBee> getAllObserveratriceBees() {
        List<ObservatriceBee> observatriceBees = new ArrayList<>();
        for (Bee bee : allBees) {
            if (bee instanceof ObservatriceBee) {
                observatriceBees.add((ObservatriceBee) bee);
            }
        }
        return observatriceBees;
    }
}
