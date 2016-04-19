
import java.awt.Component;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Player {
    
    int i;
    int x, y, bx, by, dx, dy, backgroundX, nYMain, nXMain, nY, nX;
    private final int SPEED = 15;
    boolean bJump;
    int rows = 4;
    int cols = 9;
    Rectangle r;
    
    public Player() {
        r = new Rectangle();
        x = 350;
        y = 376;
        dx = 0;
        dy = 0;
        bJump = false;
    }
    
    public void move() {
        x += dx;
        y += dy;
        if (bJump == true && y > 276) {
            dy = -7;
        }
        if (y <= 276) {
            bJump = false;
        }
        if (bJump == false) {
            dy = 7;
        }
        if (y > 376) {
            dy = 0;
            y = 376;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void keyPressed(KeyEvent w) {
        int code = w.getKeyCode();
        if (code == KeyEvent.VK_SPACE) {
            y = -100;
        }
        if (code == KeyEvent.VK_W) {
            bJump = true;

        }

    }

    public void keyReleased(KeyEvent w) {
        dx = 0;

    }
}