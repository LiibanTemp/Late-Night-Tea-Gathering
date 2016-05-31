
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

    Sprite sprPlayer, sprEnemy1, sprEnemy2, sprAttack, sprDeath, sprForce, sprGround;
    private Timer timer;
    private Image background, End;
    double nScroll, nScroll2, nDx, nDy, nGravity;
    int nXstart, nYstart, nYstart2, nXstart2, nXstart3, nYstart3;
    int nHealth, nMP, nMPCool, nScore;
    String sPSprite, sESprite, sASprite, sDSprite, sFSprite, sGSprite;
    String sHealth, sMP, sMPCool, sScore;
    static int nDir, nADir;
    BufferedImage biPlayer, biEnemy, biAttack, biDeath, biForce, biEnd, biGround;
    private static Background bg1, bg2;
    static boolean bMove, bJump, bAttack, bExist, bLeft, bRight, bForce, bDeath;
    Rectangle rPlayer, rEnemy, rGround, rEnemy2, rAttackL, rAttackR;
    //http://stackoverflow.com/questions/16761630/font-createfont-set-color-and-size-java-awt-font
    Color White = new Color(128, 128, 128);
    Color Black = new Color(0, 0, 0);
    Font font = new Font("Verdana", Font.BOLD, 25);

    public PanBoard() {
        //Images
        sPSprite = "Walk (2).png";//Player
        sESprite = "Sanic.png";//Enemy
        sASprite = "Attack.png";//Attack
        sDSprite = "Death.png";//Death
        sFSprite = "Force.png";//Force
        sGSprite = "Ground.jpg";//Ground

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
        nHealth = 100;//500 for actual game, 100 for testing
        nMP = 200;//MP, Used to preform action
        nMPCool = 50;//MP Cooldown variable
        nScore = 0;
        sHealth = "";
        sMP = "";
        sMPCool = "";
        sScore = "";

        sprPlayer = new Sprite(sPSprite, 350, 380, 64, 64, true);
        sprEnemy1 = new Sprite(sESprite, 200, 376, 64, 64, false);
        sprEnemy2 = new Sprite(sESprite, 200, 376, 64, 64, false);
        sprAttack = new Sprite(sASprite, 350, 380, 120, 55, true);
        sprForce = new Sprite(sFSprite, 350, 380, 0, 0, true);
        sprDeath = new Sprite(sDSprite, 350, 380, 0, 0, true);
        sprGround = new Sprite(sGSprite,0, 430, 765, 1, true );
        bg1 = new Background(0, 0);
        bg2 = new Background(765, 0);
        addKeyListener(new Movement());
        setFocusable(true);
        ImageIcon BG = new ImageIcon("Tea2.jpg");
        background = BG.getImage();
        ImageIcon Death = new ImageIcon("Died.jpg");
        End = Death.getImage();
        timer = new Timer(30, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        //Color Black = new Color(0, 0, 0);

        nXstart = sprEnemy1.x;
        nYstart = sprEnemy1.y;
        nYstart2 = sprPlayer.y;
        nXstart2 = sprPlayer.x;
        nXstart3 = sprEnemy2.x;
        nYstart3 = sprEnemy2.y;
        nDx = 5;
        nGravity = 0.50;
        nDy += nGravity;
        sprPlayer.y += nDy;
        bg1.update();
        bg2.update();

        //Hit Detection Bounds
//        rPlayer.setBounds(sprPlayer.getX(), sprPlayer.getY(), 64, 50);
//        rEnemy.setBounds(sprEnemy1.x, sprEnemy1.y, 64, 64);
//        rEnemy2.setBounds(sprEnemy2.x, sprEnemy2.y, 64, 64);
//        rAttackR.setBounds(sprPlayer.getX() + 20, sprPlayer.getY(), 150, 55);
//        rAttackL.setBounds(sprPlayer.getX() - 120, sprPlayer.getY(), 120, 55);
//        rGround.setBounds(0, 430, 765, 1);

        //Hit Detection Code
        if (nHealth > 0) {
            if (sprEnemy1.x < sprPlayer.x) {
                sprEnemy1.x += nDx;
            } else if (sprEnemy1.x > sprPlayer.x) {
                sprEnemy1.x -= nDx;
            }
            if (sprEnemy2.x > sprPlayer.x) {
                sprEnemy2.x -= nDx;
            } else if (sprEnemy2.x < sprPlayer.x) {
                sprEnemy2.x += nDx;
            }
            if (sprPlayer.GetRect().intersects(sprGround.GetRect())) {
                sprPlayer.x = nXstart2;
                sprPlayer.y = nYstart2;
                nDy = 0;
                bJump = true;

            }
            if (sprEnemy1.GetRect().intersects(sprPlayer.GetRect())
                    || sprEnemy2.GetRect().intersects(sprPlayer.GetRect())) {
                sprPlayer.y = nYstart2;
                sprEnemy1.x = nXstart;
                sprEnemy2.x = nXstart3;
                nDy = 0;
                bJump = true;
                //bExist = false;
                nHealth -= 1;
            }
            if (sprEnemy1.GetRect().intersects(sprPlayer.GetRect()) && sprPlayer.y < 380) {
                sprPlayer.y = nYstart2;
                sprPlayer.x = nXstart2;
                sprEnemy1.x = -376 + (int) (Math.random() * 376);
                sprPlayer.dy = 0;
                bJump = true;
                nScore += 1;
                //nHealth += 1;
            }
            if (sprEnemy2.GetRect().intersects(sprPlayer.GetRect()) && sprPlayer.y < 380) {
                sprPlayer.y = nYstart2;
                sprPlayer.x = nXstart2;
                sprEnemy2.x = 376 + (int) (Math.random() * 1300);
                nDy = 0;
                bJump = true;
                nScore += 1;
                //nHealth += 1;
            }


            if (bForce) {
                sprEnemy1.x = 0;
                sprEnemy2.x = 700;
            }
            if (bAttack && sprEnemy1.GetRect().intersects(sprAttack.GetRect()) && bRight) {
                sprEnemy1.x = 0 + (int) (Math.random() * 376);
                nScore += 1;
            }
            if (bAttack && sprEnemy1.GetRect().intersects(sprAttack.GetRect()) && bLeft) {
                sprEnemy1.x = 0 + (int) (Math.random() * 376);
                nScore += 1;
            }
            if (bAttack && sprEnemy2.GetRect().intersects(sprAttack.GetRect()) && bRight) {
                sprEnemy2.x = 550 + (int) (Math.random() * 1300);
                nScore += 1;
            }
            if (bAttack && sprEnemy2.GetRect().intersects(sprAttack.GetRect()) && bLeft) {
                sprEnemy2.x = 550 + (int) (Math.random() * 1300);
                nScore += 1;
            }

            //Sprite Updating
            if (nDir == 1) {//left attack
                nADir = 1;
            } else if (nDir == 3) {//right attack
                nADir = 0;
            }
            if (bMove) {
                biPlayer = sprPlayer.getSprite(nDir);
                biAttack = sprAttack.getAttackSprite(nADir);
                //biForce = sprForce.getForceSprite();
                //biDeath = sprDeath.getDeathSprite();
            } else {
                biPlayer = sprPlayer.getStill();
                biEnemy = sprEnemy1.getStill();
                biGround = sprGround.getGround();
                biAttack = sprAttack.getAttackSprite(nADir);
                biForce = sprForce.getForceSprite();
            }
            sHealth = "Health: " + nHealth;

        } else if (nHealth <= 0) {
            sHealth = "Health: 0";
            biDeath = sprDeath.getStill();
        }

        //MpP Managing Code
        if (nMP <= 0) {
            sMP = "MP: 0";
            nMP = 0;
            nMPCool--;
        } else {
            sMP = "MP: " + nMP;
        }
        if (nMPCool <= 0) {//Problem area that needs to be fixed
            nMPCool = 50;
            nMP = 200;
        }
        sMPCool = "MP Cooldown: " + nMPCool;
        sScore = "Score: " + nScore;
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

        if (nHealth > 0) {
            g2d.drawImage(biPlayer, sprPlayer.getX(), sprPlayer.getY(), null);
            g2d.drawImage(biEnemy, sprEnemy1.x, sprEnemy1.y, null);//Enemy 1
            g2d.drawImage(biEnemy, sprEnemy2.x, sprEnemy2.y, null);//Enemy 2
            g2d.drawImage(biGround, sprGround.getX(), sprGround.getY(), null);


            g.drawString(sHealth, 0, 20);
            if (nMP > 0) {
                g.drawString(sMP, 0, 40);
            } else if (nMP <= 0) {
                g.drawString(sMPCool, 0, 40);
            }
            g.drawString(sScore, 0, 60);

            if (bAttack == true && nADir == 1) {//Attack Left
                g2d.drawImage(biAttack, sprPlayer.getX() - 120, sprPlayer.getY(), null);
                bLeft = true;
                bRight = false;
            }

            if (bAttack == true && nADir == 0) {//Attack Right
                g2d.drawImage(biAttack, sprPlayer.getX() + 20, sprPlayer.getY(), null);
                bLeft = false;
                bRight = true;
            }

            if (bForce) {//Force Animation
                g2d.drawImage(biForce, sprPlayer.getX(), sprPlayer.getY(), null);
            }

        } else {
            g.setColor(Black);
            g2d.fillRect(0, 0, 765, 480);
            g2d.drawImage(biDeath, sprPlayer.getX(), 385, null);
            g2d.drawImage(End, 0, 150, null);
            g.setColor(White);
            g.drawString(sScore, 0, 20);
            nMP = 0;
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
            } else if (code == KeyEvent.VK_W && bJump == true) {
                nDy = -15;
            }
            if (code == KeyEvent.VK_SPACE && nMP > 0) {//Attack
                bAttack = true;
                nMP -= 5;
            }
            if (code == KeyEvent.VK_F && nMP > 0) {//Force
                bForce = true;
                nMP -= 20;
            }
            if (code == KeyEvent.VK_G && nMP > 0) {//Healing
                nMP = 0;
                nHealth = 100;
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