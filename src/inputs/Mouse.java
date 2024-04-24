package inputs;

import main.Panel;


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouse implements MouseListener, MouseMotionListener {
    private Panel p;

    public Mouse(Panel p){
        this.p=p;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        System.out.println("Clack");
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        System.out.println("Click");
    }
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
       // p.setRectPos(mouseEvent.getX(),mouseEvent.getY());
    }
}
