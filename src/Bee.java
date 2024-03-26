import java.awt.Color;
public class Bee extends Displayable{
    int dx,dy;
    double vitesse;

    Bee(int dx, int dy, int x, int y, double vitesse, Color couleur) {
        super(x, y,couleur);
        this.dx = dx;
        this.dy = dy;
        this.vitesse = vitesse;
    }

    void move() {
        x = (int) (x + (dx * vitesse) / Math.sqrt(dx * dx + dy * dy));
        y = (int) (y + (dy * vitesse) / Math.sqrt(dx * dx + dy * dy));
    }


}
