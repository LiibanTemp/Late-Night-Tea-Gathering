
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

    Sprite sPlayer, sEnemy, sAttack, sDeath, sForce;
    private Timer timer;
    private Image background, End;
    int nScroll, nScroll2, nEnemyX, nEnemyX2, nEnemyY, nDx, nDy, nHealth;
    int nXstart, nYstart, nYstart2, nXstart2;
    String sPSprite, sESprite, sASprite, sDSprite, sFSprite, sHealth;
    static int nDir, nADir;
    BufferedImage biPlayer, biEnemy, biAttack, biDeath, biForce, biEnd;
    private static Background bg1, bg2;
    static boolean bMove, bJump, bAttack, bExist, bLeft, bRight, bForce, bDeath;
    Rectangle rPlayer, rEnemy, rGround, rEnemy2, rAttackL, rAttackR;
    //http://stackoverflow.com/questions/16761630/font-createfont-set-color-and-size-java-awt-font
    Color White = new Color(128, 128, 128);
    Font font = new Font ("Verdana", Font.BOLD, 25);

    public PanBoard() {
        //Images
        sPSprite = "Walk (2).png";//Player
        sESprite = "Sanic.png";//Enemy
        sASprite = "Attack.png";//Attack
        sDSprite = "Death.png";//Death
        sFSprite = "Force.png";//Force

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
        bLeft = false;
        bRight = false;
        //bExist = true;
        bForce = false;
        nEnemyY = 376;
        nHealth = 100;//500 for actual game, 100 for testing
        sHealth = "";

        sPlayer = new Sprite(sPSprite, 350, 380, true);
        sEnemy = new Sprite(sESprite, 200, 405, false);
        sAttack = new Sprite(sASprite, 350, 380, true);
        sForce = new Sprite(sFSprite, 350, 380, true);
        sDeath = new Sprite(sDSprite, 350, 380, true);
        bg1 = new Background(0, 0);
        bg2 = new Background(765, 0);
        addKeyListener(new Movement());
        setFocusable(true);
        ImageIcon i1 = new ImageIcon("Tea2.jpg");
        background = i1.getImage();
        ImageIcon i2 = new ImageIcon("Died.jpg");
        End = i2.getImage();
        timer = new Timer(60, this);
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
        rPlayer.setBounds(sPlayer.getX(), sPlayer.getY(), 64, 50);
        rEnemy.setBounds(nEnemyX, nEnemyY, 64, 64);
        rEnemy2.setBounds(nEnemyX2, nEnemyY, 64, 64);
        rAttackR.setBounds(sPlayer.getX() + 20, sPlayer.getY(), 150, 55);
        rAttackL.setBounds(sPlayer.getX() - 120, sPlayer.getY(), 150, 55);
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
            //bExist = false;
            nHealth -= 1;
        }
        if (rEnemy.intersects(rPlayer)) {
            sPlayer.y = nYstart2;
            sPlayer.x = nXstart2;
            sPlayer.dy = 0;
            bJump = true;
            //bExist = false;
            nHealth -= 1;
        }
        if (bForce) {
            nEnemyX = 0;
            nEnemyX2 = 700;
        }
        if (rGround.intersects(rPlayer)) {
            sPlayer.y = 380;
            sPlayer.dy = 0;
            bJump = true;
        } else {
            bJump = false;
        }
        if (bAttack && rEnemy.intersects(rAttackR) && bRight) {
            nEnemyX = 0;
            nEnemyX = -376 + (int) (Math.random() * 376);
        }
        if (bAttack && rEnemy.intersects(rAttackL) && bLeft) {
            nEnemyX = 0;
            nEnemyX = -376 + (int) (Math.random() * 376);
        }
        if (bAttack && rEnemy2.intersects(rAttackR) && bRight) {
            nEnemyX2 = 700;
            nEnemyX2 = 376 + (int) (Math.random() * 1300);
        }
        if (bAttack && rEnemy2.intersects(rAttackL) && bLeft) {
            nEnemyX2 = 700;
            nEnemyX2 = 376 + (int) (Math.random() * 1300);
        }

        //Sprite Updating
        if (nDir == 1) {//left attack
            nADir = 1;
        } else if (nDir == 3) {//right attack
            nADir = 0;
        }
        if (bMove) {
            biPlayer = sPlayer.getSprite(nDir);
            biAttack = sAttack.getAttackSprite(nADir);
            //biForce = sForce.getForceSprite();
            //biDeath = sDeath.getDeathSprite();
        } else {
            biPlayer = sPlayer.getStill();
            biEnemy = sEnemy.getStill();
            biAttack = sAttack.getAttackSprite(nADir);
            biForce = sForce.getForceSprite();
            //biDeath = sDeath.getDeathSprite();
        }
        if (nHealth <= 0) {
            sHealth = "Health: 0";
            biDeath = sDeath.getStill();           
        }else {
            sHealth = "Health: " + nHealth;
        }
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g.drawImage(background, bg1.getnScroll(), 0, this);
        g.drawImage(background, bg2.getnScroll2(), 0, this);
        g.setColor(White);
        g.setFont(font);
        g.drawString(sHealth, 0, 20);
        //g.drawRect(0, 0, 75, 20);
        if (nHealth > 0) {
            g2d.drawImage(biPlayer, sPlayer.getX(), sPlayer.getY(), null);
            g2d.drawImage(biEnemy, nEnemyX, nEnemyY, null);//Enemy 1
            g2d.drawImage(biEnemy, nEnemyX2, nEnemyY, null);//Enemy 2
            if (bAttack == true && nADir == 1) {//Attack Left
                g2d.drawImage(biAttack, sPlayer.getX() - 120, sPlayer.getY(), null);
                bLeft = true;
                bRight = false;
            }
            if (bAttack == true && nADir == 0) {//Attack Right
                g2d.drawImage(biAttack, sPlayer.getX() + 20, sPlayer.getY(), null);
                bLeft = false;
                bRight = true;
            }
            if (bForce) {//Force Animation
                g2d.drawImage(biForce, sPlayer.getX(), sPlayer.getY(), null);
            }
        } else {
            g2d.drawImage(biDeath, sPlayer.getX(), sPlayer.getY() + 5, null);
            g2d.drawImage(End, 0, 150, null);
        }
    }

    private class Movement extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            bMove = false;
            bAttack = false;
            bForce = false;
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
            if (code == KeyEvent.VK_F) {
                bForce = true;
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