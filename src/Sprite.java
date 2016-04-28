import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite {
    
    private BufferedImage biSpriteSheet;
    private static final int TILE_SIZEX = 64;
    private static final int TILE_SIZEY = 64;

    int i;
    int x, y, dx, dy, nDir, nGridX;
    private final int SPEED = 15;
    boolean bJump, isAnim;
    int rows = 4;
    int cols = 9;
    Rectangle p;
    String sFile;
    public Sprite(String _sFile, int _x, int _y, boolean _isAnim) {
        sFile = _sFile;
        x = 350;
        y = 376;
        dx = 0;
        dy = 0;
        bJump = false;
        nGridX = 0; // the first sprite sheet image.
        
        try {
            biSpriteSheet = ImageIO.read(new File(sFile));
        } catch (IOException e) {
            e.printStackTrace();
        } 
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
    
    public void Jump(){
        y -= 100;
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
       
    public BufferedImage getSprite(int _nDir) {
        nDir = _nDir;
        if(nGridX ==9) nGridX = 0;       
        return biSpriteSheet.getSubimage(nGridX++ * TILE_SIZEX, nDir * TILE_SIZEY, TILE_SIZEX, TILE_SIZEY);
    }
    public BufferedImage getStill() {       
        return biSpriteSheet.getSubimage(0 * TILE_SIZEX, nDir * TILE_SIZEY, TILE_SIZEX, TILE_SIZEY);
    }

}
