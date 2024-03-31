package inputs;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Clavier implements KeyListener {
//Pour lire et prendre en compte les saisies du clavier


        @Override
        public void keyTyped(KeyEvent keyEvent) {

        }

        @Override
        public void keyPressed(KeyEvent keyEvent) {

            switch(keyEvent.getKeyCode()){
                case KeyEvent.VK_A:
                    System.out.println("Tu as appuyé sur a");
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent keyEvent) {


        }
}


