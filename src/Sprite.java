
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
    private static final int ATTACK_TILE_SIZEX = 186;
    private static final int ATTACK_TILE_SIZEY = 55;
    int x, y, dx, dy, nDir, nGridX, nAGridX, nADir;
    int nGravity = 1;
    boolean bJump, isAnim, isEnemy;
    Rectangle p;
    String sFile;

    public Sprite(String _sFile, int _x, int _y, boolean _isAnim) {
        sFile = _sFile;
        x = 350;
        y = 380;
        dx = 0;
        dy = 0;
        bJump = false;
        nGridX = 0; // the first sprite sheet image.
        nAGridX = 0;
        try {
            biSpriteSheet = ImageIO.read(new File(sFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void move() {
        dy += nGravity;
        y += dy;
        x += dx;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public BufferedImage getSprite(int _nDir) {//Walk Sprite Animation
        nDir = _nDir;
        if (nGridX == 9) {
            nGridX = 0;
        }
        return biSpriteSheet.getSubimage(nGridX++ * TILE_SIZEX, nDir * TILE_SIZEY, TILE_SIZEX, TILE_SIZEY);
    }
    public BufferedImage getAttackSprite(int _nADir) {//Attack Sprite Animation
        nADir = _nADir;
        if (nAGridX == 6) {
            nAGridX = 0;
        }
        return biSpriteSheet.getSubimage(nAGridX++ * ATTACK_TILE_SIZEX, nADir * ATTACK_TILE_SIZEY, ATTACK_TILE_SIZEX, ATTACK_TILE_SIZEY);
    }

    public BufferedImage getPlayerStill() {
        return biSpriteSheet.getSubimage(0 * TILE_SIZEX, nDir * TILE_SIZEY, TILE_SIZEX, TILE_SIZEY);
    }
     public BufferedImage getEnemyStill() {
        return biSpriteSheet.getSubimage(0 * TILE_SIZEX, nDir * TILE_SIZEY, 75, TILE_SIZEY);
    }
}