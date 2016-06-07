
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
    private static final int ATTACK_KNIGHT_TILE_SIZEX = 186;
    private static final int ATTACK_KNIGHT_TILE_SIZEY = 55;
    private static final int FORCE_KNIGHT_TILE_SIZEX = 64;
    private static final int FORCE_KNIGHT_TILE_SIZEY = 60;
    int x, y, nDir, nGridX, nAttackGridX, nADir, nDeathGridX, nForceGridX;
    int nX, nY, nWidth, nHeight;
    Rectangle r;
    public static int nMP, nHealth, nEHealth, nMPCool, nScore;
    int nGravity = 1;
    boolean bJump, isAnim, isEnemy;
    String sFile;

    public Sprite(String _sFile, int _x, int _y, int _nWidth, int _nHeight, boolean _isAnim) {
        sFile = _sFile;
        x = _x;
        y = _y;
        nHeight = _nHeight;
        nWidth = _nWidth;
        r = new Rectangle();
        nHealth = 10000;//500 for actual game, may be higher, 100 for testing
        nEHealth = 100;
        nMP = 200;//MP, Used to preform action, 600 for actual game
        nMPCool = 50;//MP Cooldown variable
        nGridX = 0; // the first sprite sheet image.
        nAttackGridX = 0;
        nForceGridX = 0;
        nDeathGridX = 0;
        try {
            biSpriteSheet = ImageIO.read(new File(sFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int Health() {
        if (PanBoard.bDamage1) {
            nHealth -= 1;
        }
        if (PanBoard.bDamage2) {
            nHealth -= 1;
        }if(PanBoard.bEDeath){
            nHealth = 100;
        }
        return nHealth;
        
    }

    public int EHealth() {
        if (PanBoard.bEDamage1) {
            nEHealth -= 5;
        }
        if (nEHealth == 0) {
            nEHealth = 0;
            //nEHealth = 100;
    }
     return nEHealth;
}
public int Mana() {
        if (nMP <= 0) {
            nMP = 0;
            nMPCool--;
        }
        if (nMPCool <= 0) {//Problem area that needs to be fixed
            nMPCool = 50;
            nMP = 200;
        }
        if (PanBoard.bAttack == true) {
            nMP -= 5;
        }
        if (PanBoard.bForce == true) {
            nMP -= 20;
        }
        if (PanBoard.bHeal == true) {
            nMP = 0;
            nHealth += 100;
        }
        return nMP;
    }

    public Rectangle GetRect() {
        r.setBounds(x, y, nWidth, nHeight);
        return r;
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
        if (nAttackGridX == 6) {
            nAttackGridX = 0;
        }
        return biSpriteSheet.getSubimage(nAttackGridX++ * ATTACK_KNIGHT_TILE_SIZEX, nADir * ATTACK_KNIGHT_TILE_SIZEY, ATTACK_KNIGHT_TILE_SIZEX, ATTACK_KNIGHT_TILE_SIZEY);
    }

    public BufferedImage getForceSprite() {//Force Sprite Animation
        if (nForceGridX == 7) {
            nForceGridX = 0;
        }
        return biSpriteSheet.getSubimage(nForceGridX++ * FORCE_KNIGHT_TILE_SIZEX, nDir * FORCE_KNIGHT_TILE_SIZEY, FORCE_KNIGHT_TILE_SIZEX, FORCE_KNIGHT_TILE_SIZEY);
    }

    public BufferedImage getStill() {
        return biSpriteSheet.getSubimage(0 * TILE_SIZEX, nDir * TILE_SIZEY, TILE_SIZEX, TILE_SIZEY);
    }

    public BufferedImage getGround() {
        return biSpriteSheet.getSubimage(0, 1, 765, 1);
    }
}