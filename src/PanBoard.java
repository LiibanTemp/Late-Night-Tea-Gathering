
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class PanBoard extends JPanel implements ActionListener {

    Sprite sPlayer, sEnemy, sAttack;
    private Timer timer;
    private Image background;
    int nScroll, nScroll2, nEnemyX, nEnemyX2, nEnemyY, nDx, nDy;
    int nXstart, nYstart, nYstart2, nXstart2;
    String sPSprite, sESprite, sASprite;
    static int nDir, nADir;
    BufferedImage biPlayer, biEnemy, biAttack;
    private static Background bg1, bg2;
    static boolean bMove, bJump, bAttack;
    Rectangle rPlayer, rEnemy, rGround, rEnemy2, rAttackL, rAttackR;

    public PanBoard() {
        //Images
        sPSprite = "Walk (2) copy.png";
        sESprite = "Sanic.png";
        sASprite = "Attack.png";
        
        //Rectangles
        rPlayer = new Rectangle();
        rEnemy = new Rectangle();
        rEnemy2 = new Rectangle();
        rGround = new Rectangle();
        rAttackL = new Rectangle();
        rAttackR = new Rectangle();
        
        nDir = 3; // right. 0 is forward 1 is left, and 2 is back - going toward me.
        bMove = false;
        bJump = false;
        bAttack = false;
        nEnemyY = 376;
        sPlayer = new Sprite(sPSprite, 350, 380, true);
        sEnemy = new Sprite(sESprite, 200, 405, false);
        sAttack = new Sprite(sASprite, 350, 380, true);
        bg1 = new Background(0, 0);
        bg2 = new Background(765, 0);
        addKeyListener(new Movement());
        setFocusable(true);
        ImageIcon i1 = new ImageIcon("Tea2.jpg");
        background = i1.getImage();
        timer = new Timer(10, this);
        timer.start();
        nEnemyX2 = 700;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        nXstart = nEnemyX;
        nYstart = nEnemyY;
        nYstart2 = sPlayer.y;
        nXstart2 = sPlayer.x;
        nDx = 5;
        sPlayer.move();
        sEnemy.move();
        bg1.update();
        bg2.update();
        
        //Hit Detection Bounds
        rPlayer.setBounds(sPlayer.getX(), sPlayer.getY(), 32, 50);
        rEnemy.setBounds(nEnemyX, nEnemyY, 75, 64);
        rEnemy2.setBounds(nEnemyX2, nEnemyY, 75, 64);
        rAttackR.setBounds(sPlayer.getX() + 20, sPlayer.getY(), 186, 55);
        rAttackL.setBounds(sPlayer.getX() - 120, sPlayer.getY(), 186, 55);
        rGround.setBounds(0, 430, 765, 1);
        
        //Hit Detection Code
        if (nEnemyX < sPlayer.x) {
            nEnemyX += nDx;
        } else if (nEnemyX > sPlayer.x) {
            nEnemyX -= nDx;
        }
        if (nEnemyX2 > sPlayer.x) {
            nEnemyX2 -= nDx;
        } else if (nEnemyX2 < sPlayer.x) {
            nEnemyX2 += nDx;
        }
        if (rEnemy2.intersects(rPlayer)) {
            sPlayer.y = nYstart2;
            sPlayer.x = nXstart2;
            sPlayer.dy = 0;
            bJump = true;
            nEnemyX2 = 700;
        }
        if (rEnemy.intersects(rPlayer)) {
            sPlayer.y = nYstart2;
            sPlayer.x = nXstart2;
            sPlayer.dy = 0;
            bJump = true;
            nEnemyX = 0;
        }
        if (rEnemy.intersects(rEnemy2)) {
            nEnemyX = 0;
            nEnemyX2 = 700;
        }
        if (rGround.intersects(rPlayer)) {
            sPlayer.y = 380;
            bJump = true;
        } else {
            bJump = false;
        }
        if(rEnemy.intersects(rAttackR) || rEnemy.intersects(rAttackL)){
            nEnemyX = 0;
        }
        if(rEnemy2.intersects(rAttackR) || rEnemy2.intersects(rAttackL)){
            nEnemyX2 = 700;
        }
        
        //Sprite Updating
        if(nDir == 1){//left attack
            nADir = 1;
        } else if(nDir == 3){//right attack
            nADir = 0;
        }
        if (bMove) {
            biPlayer = sPlayer.getSprite(nDir);
        } else {
            biPlayer = sPlayer.getPlayerStill();
            biEnemy = sEnemy.getEnemyStill();
            biAttack = sAttack.getAttackSprite(nADir);
        } 
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g.drawImage(background, bg1.getnScroll(), 0, this);
        g.drawImage(background, bg2.getnScroll2(), 0, this);
        g2d.drawImage(biPlayer, sPlayer.getX(), sPlayer.getY(), null);
        g2d.drawImage(biEnemy, nEnemyX, nEnemyY, null);//Enemy 1
        g2d.drawImage(biEnemy, nEnemyX2, nEnemyY, null);//Enemy 2
        if(bAttack == true && nADir == 1){//Attack Left
           g2d.drawImage(biAttack, sPlayer.getX() - 120, sPlayer.getY(), null); 
        }
        if(bAttack == true && nADir == 0){//Attack Right
           g2d.drawImage(biAttack, sPlayer.getX() + 20, sPlayer.getY(), null); 
        }

    }

    private class Movement extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            bMove = false;
            bAttack = false;
        }

        @Override
        public void keyPressed(KeyEvent e) {
            // why split up the keyPressed function???
            int code = e.getKeyCode();
            if (code == KeyEvent.VK_A) {
                nDir = 1;
                bMove = true;
            } else if (code == KeyEvent.VK_D) {
                nDir = 3;
                bMove = true;
            } else if (code == KeyEvent.VK_W) {
                sPlayer.dy = -15;
            }
            if (code == KeyEvent.VK_SPACE) {
                bAttack = true;
            }
        }
    }

    public static Background getBg1() {
        return bg1;
    }

    public static Background getBg2() {
        return bg2;
    }

    public static boolean bMove() {
        return bMove;
    }

    public static int nDir() {
        return nDir;
    }
}