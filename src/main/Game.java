package main;

public class Game implements Runnable {
    private Window notreJeu;
    private Panel notrePanel;
    private Thread notreThread;
    private final int FPS_SET = 60;

    public Game() {
        notrePanel = new Panel();
        notreJeu = new Window(notrePanel);
        // Demande a l'ordinateur de récupérer les saisies
        notrePanel.requestFocus();
        loop();
    }

    private void loop() {
        notreThread = new Thread(this);
        notreThread.start();
    }

    @Override
    public void run() {
        // Nanoseconde
        double tmpFPS = 1000000000.0 / FPS_SET;
        long lastFrame = System.nanoTime();
        long now = System.nanoTime();
        int fps = 0;
        long lastCheck = System.currentTimeMillis();
        while (true) {
            now = System.nanoTime();
            if (now - lastFrame >= tmpFPS) {
                notrePanel.repaint(); // Redessiner le panneau
                lastFrame = now;
                fps++;
            }
            // Vérification des FPS
            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS :" + fps);
                fps = 0;
            }
        }
    }
}
