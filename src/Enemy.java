
import javax.swing.*;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Enemy {

    int x, y, dx, dy;
    int nWidth, nLength, EH, EW, nSpawn;
    static int nEXP, nHP;
    ImageIcon e1 = new ImageIcon("Sanic.png");
    int imgWidth = e1.getIconWidth();
    int imgHeight = e1.getIconHeight();

    public Enemy() {
        dx = 0;
        dy = 0;
        x = 200;
        y = 405;
        nEXP = 5;
        nHP = 20;
    }

    public void move() {
        EW = imgWidth + x - 30;
        x += dx;
        y += dy;
    }

    public int getX() {
        return x - 30;
    }

    public int getY() {
        return y - 30;
    }

    public void keyPressed(KeyEvent w) {
        int code = w.getKeyCode();
        if (code == KeyEvent.VK_D) {
            dx = -5;
        }
        if (code == KeyEvent.VK_A) {
            dx = 5;
        }
    }

    public void keyReleased(KeyEvent w) {
        dx = 0;

    }
}
