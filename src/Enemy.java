
import javax.swing.*;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Enemy {

    //private Image img;
    //int k;
    int x, y, dx, dy;
    //static int X, Y, I, BX, BY, nHit;
    int nWidth, nLength, EH, EW, nEXP, nSpawn;
    //private final int nSpeed = 10;
    ImageIcon e1 = new ImageIcon("Sanic.png");
    //ImageIcon e2 = new ImageIcon("Sanic.png");
    int imgWidth = e1.getIconWidth();
    int imgHeight = e1.getIconHeight();
   // int imgHeight = e2.getIconWidth();
    Image arnEnemy[] = new Image[3];
    int arnHit[] = new int[50];
    //Rectangle e;


    public Enemy() {
        //e = new Rectangle();
        dx = 0;
        dy = 0;
        x = 200;
        y = 405;
        nEXP = 5;
        //nHit = 1;
        arnEnemy[0] = e1.getImage();
        //arnEnemy[1] = e2.getImage();
    }

    public void move() {
      //  EH = imgHeight + y;
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

//    public Image getImage() {
//        img = arnEnemy[k];
//        return img;
//
//    }

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
