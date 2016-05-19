
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

    Sprite sPlayer, sEnemy, sAttack, s;
    private Timer timer;
    private Image background;
    int nScroll, nScroll2, nEnemyX, nEnemyX2, nEnemyY, nEnemyY2, nDx, nDy;
    int nXstart, nYstart, nYstart2, nXstart2, nXstart3, nYstart3;
    String sPSprite, sESprite, sASprite;
    static int nDir, nADir;
    BufferedImage biPlayer, biEnemy, biAttack;
    private static Background bg1, bg2;
    static boolean bMove, bJump, bAttack, bExist, bLeft, bRight;
    Rectangle rPlayer, rEnemy, rGround, rEnemy2, rAttackL, rAttackR;
    ArrayList<Sprite> alSprite = new ArrayList<>();
    
    public PanBoard() {
        //Images
        sPSprite = "Walk (2).png";
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
        bLeft = false;
        bRight = false;
        bExist = true;
        nEnemyY = 376;
        nEnemyY2 = 376;
        sPlayer = new Sprite(sPSprite, 350, 380, true);
//        sEnemy = new Sprite(sESprite, 200, 405, false);
        sAttack = new Sprite(sASprite, 350, 380, true);
        s = new Sprite(sESprite, 200, 405, false);
        alSprite.add(s);
        bg1 = new Background(0, 0);
        bg2 = new Background(765, 0);
        addKeyListener(new Movement());
        setFocusable(true);
        ImageIcon i1 = new ImageIcon("Tea2.jpg");
        background = i1.getImage();
        timer = new Timer(30, this);
        timer.start();
        nEnemyX2 = 700;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        nXstart = nEnemyX;
        nYstart = nEnemyY;
        nYstart2 = sPlayer.y;
        nXstart2 = sPlayer.x;
        nYstart3 = nEnemyX2;
        nXstart3 = nEnemyY2;
        nDx = 5;
        nEnemyX += nDx;
        sPlayer.move();
        s.move();
        bg1.update();
        bg2.update();

        //Hit Detection Bounds
        rPlayer.setBounds(sPlayer.getX(), sPlayer.getY(), 32, 50);
        rEnemy.setBounds(nEnemyX, nEnemyY, 60, 64);
        rEnemy2.setBounds(nEnemyX2, nEnemyY2, 60, 64);
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
            nEnemyX2 = nXstart3;
            sPlayer.dy = 0;
            bJump = true;
            bExist = false;
            //System.out.println("why");
        }
        if (rEnemy.intersects(rPlayer)) {
            sPlayer.y = nYstart2;
            nEnemyX = nXstart;
            sPlayer.dy = 0;
            bJump = true;
            bExist = false;
        }
        if (rEnemy.intersects(rEnemy2)) {
            nEnemyX = nXstart;
            nEnemyX2 = nXstart3;
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
            nEnemyX = -376 + (int)(Math.random()) * 376;
        }
        if (bAttack && rEnemy.intersects(rAttackL) && bLeft) {
            nEnemyX = 0;
            nEnemyX = -376 + (int)(Math.random()) * 376;
        }
        if (bAttack && rEnemy2.intersects(rAttackR) && bRight) {
            nEnemyX2 = 700;
            nEnemyX2 = 376 + (int)(Math.random() * 1300);
        }
        if (bAttack && rEnemy2.intersects(rAttackL) && bLeft) {
            nEnemyX2 = 700;
            nEnemyX2 = 376 + (int)(Math.random() * 1300);
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
        } else {
            biPlayer = sPlayer.getPlayerStill();
            biEnemy = s.getEnemyStill();
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
        g2d.drawImage(biEnemy, nEnemyX2, nEnemyY2, null);//Enemy 2
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

    }

    private class Movement extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            bMove = false;
            bAttack = false;
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();
            if (code == KeyEvent.VK_A) {
                nDir = 1;
                bMove = true;
            } else if (code == KeyEvent.VK_D) {
                nDir = 3;
                bMove = true;
            } else if (code == KeyEvent.VK_W && bJump) {
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