
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Player {

    int i, a;
    int x, y, bx, by, dx, dy, backgroundX, nYMain, nXMain, nY, nX;
    private final int SPEED = 15;
    boolean left, right, up, down;
    boolean arImage[][] = new boolean[9][4];

    int rows = 4;
    int cols = 9;
    //BufferedImage[] sprites = new BufferedImage[rows * cols];
    Rectangle r;

    public Player() {
        r = new Rectangle();
        x = 0;
        y = 376;
        dx = 0;
        dy = 0;
        left = false;
        right = false;
        up = false;
        down = false;
    }
    public void move() {
        x += dx;
        y += dy;

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void keyPressed(KeyEvent w) {
        int code = w.getKeyCode();
        if (code == KeyEvent.VK_A) {
            dx = -SPEED;
        } else if (code == KeyEvent.VK_D) {
            dx = SPEED;
        } else if (code == KeyEvent.VK_W) {
            dy =- SPEED;
        } else if (code == KeyEvent.VK_S) {
            dy = SPEED;
        }
    }

    public void keyReleased(KeyEvent w) {
        dy = 0;
        dx = 0;

    }
}