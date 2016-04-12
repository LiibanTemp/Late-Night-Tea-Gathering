
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

    private Image img;
    private BufferedImage biSpriteSheet;
    int i;
    int x, y, bx, by, dx, dy, backgroundX, nYMain, nXMain, nY, nX;
    int vx, vy, nGrav, nTVelo;
    private final int SPEED = 15;
    ImageIcon p = new ImageIcon("sprite.png");
    int H = p.getIconHeight() / 4;
    int W = p.getIconWidth() / 9;
    //Booleans for character movement
//    private boolean movingLeft = false;
//    private boolean movingRight = false;
    boolean bJump;

    public void loadSprite(String sFile) {
        try {
            biSpriteSheet = ImageIO.read(new File("sprite.png"));;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    int rows = 4;
    int cols = 9;
    BufferedImage[] sprites = new BufferedImage[rows * cols];
    Rectangle r;

    public Player() {
        r = new Rectangle();
        x = 350;
        y = 376;
        vx = 0;
        vy = 0;
        dx = 0;
        dy = 0;
        nGrav = 10;
        nTVelo = 100;
        bJump = false;
    }

    public Rectangle getRect() {
        r.setBounds(nX, nY, W, H);
        return r;
    }

    public void move() {
        x += dx;
        y += vy;

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Image getImage() {
        img = p.getImage();
        return img;

    }

    public void keyPressed(KeyEvent w) {
        int code = w.getKeyCode();
        if (code == KeyEvent.VK_A) {
            //dx = -SPEED;
        } else if (code == KeyEvent.VK_D) {
            //dx = SPEED;
            //
        }
        if (code == KeyEvent.VK_SPACE) {
            y = -100;
        }
        if (code == KeyEvent.VK_W) {
            bJump = true;
        }
        while (bJump == true && y > 226) {
            y -= nGrav;
//            if (y <= nTVelo) {
//                vy += nGrav;
//                if (vy == -nTVelo) {
//                    
//                }
           // }
        }
    }

    public void keyReleased(KeyEvent w) {
        bJump = false;
        while(y < 376){
        y += nGrav;
        }
        dy = 0;
        dx = 0;
    }
}