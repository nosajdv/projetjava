package main;

import javax.swing.*;

public class Window extends JFrame {
    private JFrame jframe;
    public Window(Panel p){
        jframe = new JFrame();


        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setTitle("Simulation Abeilles");
        jframe.setLocationRelativeTo(null);
        jframe.add(p);
        jframe.setResizable(false);
        jframe.pack();
        jframe.setVisible(true); // tjrs en bas
    }


}
