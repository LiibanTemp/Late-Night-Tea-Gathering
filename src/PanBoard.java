
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

    Sprite sprPlayer, sprEnemy1, sprEnemy2, sprAttackR, sprAttackL, sprDeath, sprForce, sprGround;
    private Timer timer;
    private Image background, End;
    int nScroll, nScroll2;
    double dX, dX2, dY, dGravity;
    int nXstart, nYstart, nYstart2, nXstart2, nXstart3, nYstart3;
    int nScore;
    String sPSprite, sESprite, sASprite, sDSprite, sFSprite, sGSprite;
    String sHealth, sMP, sMPCool, sScore, sEHealth, sEHealth2;
    static int nDir, nADir;
    BufferedImage biPlayer, biEnemy, biAttack, biDeath, biForce, biEnd, biGround;
    private static Background bg1, bg2;
    static boolean bMove, bJump, bAttack, bLeft, bRight, bForce, bDeath, bDamage1;
    static boolean bDamage2, bHeal, bEDamage1, bEDamage2, bEDeath, bEDeath2;
    //http://stackoverflow.com/questions/16761630/font-createfont-set-color-and-size-java-awt-font
    Color White = new Color(128, 0, 0);
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

        nDir = 3; // right. 0 is forward 1 is left, and 2 is back - going toward me.
        bMove = false;
        bJump = false;
        bAttack = false;
        bLeft = false;
        bRight = false;
        bForce = false;
        bDamage1 = false;
        bDamage2 = false;
        bEDamage1 = false;
        bEDamage2 = false;
        nScore = 0;
        //nMP = 0;
        dX = 5;
        dX2 = 5;
        sHealth = "";
        sEHealth = "";
        sEHealth2 = "";
        sMP = "";
        sMPCool = "";
        sScore = "";

        sprPlayer = new Sprite(sPSprite, 350, 380, 64, 64, true);
        sprEnemy1 = new Sprite(sESprite, 0, 376, 60, 64, false);
        sprEnemy2 = new Sprite(sESprite, 550, 376, 60, 64, false);
        sprAttackR = new Sprite(sASprite, 360, 380, 120, 55, true);
        sprAttackL = new Sprite(sASprite, 200, 380, 150, 55, true);
        sprForce = new Sprite(sFSprite, 350, 240, 0, 0, true);
        sprDeath = new Sprite(sDSprite, 350, 380, 0, 0, true);
        sprGround = new Sprite(sGSprite, 0, 440, 765, 1, true);
        bg1 = new Background(0, 0);
        bg2 = new Background(765, 0);
        addKeyListener(new Movement());
        setFocusable(true);
        ImageIcon BG = new ImageIcon("Tea2.jpg");
        background = BG.getImage();
        ImageIcon Death = new ImageIcon("Died.jpg");
        End = Death.getImage();
        timer = new Timer(50, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {

        nXstart = sprEnemy1.x;
        nYstart = sprEnemy1.y;
        nYstart2 = sprPlayer.y;
        nXstart2 = sprPlayer.x;
        nXstart3 = sprEnemy2.x;
        nYstart3 = sprEnemy2.y;
        sprPlayer.Mana();
        sprPlayer.Health();
        sprEnemy1.EHealth();
        sprEnemy2.EHealth2();
        dGravity = 0.50;
        dY += dGravity;
        sprPlayer.y += dY;
        bg1.update();
        bg2.update();

        if (Sprite.nHealth > 0) {
            if (sprEnemy1.x < sprPlayer.x) {
                sprEnemy1.x += dX;
            } else if (sprEnemy1.x > sprPlayer.x) {
                sprEnemy1.x -= dX;
            }
            if (sprEnemy2.x > sprPlayer.x) {
               sprEnemy2.x -= dX2;
            } else if (sprEnemy2.x < sprPlayer.x) {
                sprEnemy2.x += dX2;
            }

            //Hit Detection Code
            if (sprPlayer.GetRect().intersects(sprGround.GetRect())) {
                sprPlayer.x = nXstart2;
                sprPlayer.y = nYstart2;
                dY = 0;
                bJump = true;
            } else {
                bJump = false;
            }
            if (sprEnemy1.GetRect().intersects(sprPlayer.GetRect())) {
                sprPlayer.y = nYstart2;
                sprEnemy1.x = nXstart;
                dY = 0;
                bJump = true;
                bDamage1 = true;

            } else {
                bDamage1 = false;
            }
            if (sprEnemy2.GetRect().intersects(sprPlayer.GetRect())) {
                sprPlayer.y = nYstart2;
                sprEnemy2.x = nXstart3;
                dY = 0;
                bJump = true;
                bDamage2 = true;
            } else {
                bDamage2 = false;
            }
            if (sprEnemy1.GetRect().intersects(sprPlayer.GetRect()) && sprPlayer.y < 380) {
                sprPlayer.y = nYstart2;
                sprPlayer.x = nXstart2;
                sprEnemy1.x = -400 + (int) (Math.random() * 300);
                dY = 0;
                bJump = true;
                nScore += 1;
                System.out.println("hit");
                bDamage1 = false;
            }
            if (sprEnemy2.GetRect().intersects(sprPlayer.GetRect()) && sprPlayer.y < 380) {
                sprPlayer.y = nYstart2;
                sprPlayer.x = nXstart2;
                sprEnemy2.x = 300 + (int) (Math.random() * 1300);
                dY = 0;
                bJump = true;
                nScore += 1;
                System.out.println("Hit");
                bDamage2 = false;
            }
            // So the enemys don't go through the each other enemy
            if (sprEnemy1.GetRect().intersects(sprEnemy2.GetRect())) {
                sprEnemy1.x = nXstart;
                sprEnemy2.x = nXstart3;
            }
            if (bForce) {
                sprEnemy1.x = 0;
                sprEnemy2.x = 700;
            }
            if (bAttack && sprEnemy1.GetRect().intersects(sprAttackR.GetRect()) && bRight && Sprite.nMP > 0) {
                bEDamage1 = true;
            
            }
            if (bAttack && sprEnemy1.GetRect().intersects(sprAttackL.GetRect()) && bLeft && Sprite.nMP > 0) {
                bEDamage1 = true;
           
            }
            if (bAttack && sprEnemy2.GetRect().intersects(sprAttackR.GetRect()) && bRight && Sprite.nMP > 0) {
                bEDamage2 = true;
            
            }
            if (bAttack && sprEnemy2.GetRect().intersects(sprAttackL.GetRect()) && bLeft && Sprite.nMP > 0) {
                bEDamage2 = true; 
           }

            if (Sprite.nEHealth <= 0) {
                sprEnemy1.x = -765 + (int) (Math.random() * 765);
                dX = 5 + (Math.random() * 20);
                // System.out.println(dX);
                nScore += 1;
                bEDeath = true;
                //bEDeath = false;            
            } else {
                bEDeath = false;
            }
            if (Sprite.nEHealth2 <= 0) {
                sprEnemy2.x = 765 + (int) (Math.random() * 765);
                dX2 = 5 + (Math.random() * 20);
                // System.out.println(dX);
                nScore += 1;
                bEDeath2 = true;
                //bEDeath = false;            
            } else {
                bEDeath2 = false;
            }

            //Sprite Updating
            if (nDir == 1) {//left attack
                nADir = 1;
            } else if (nDir == 3) {//right attack
                nADir = 0;
            }
            if (bMove) {
                biPlayer = sprPlayer.getSprite(nDir);
                if (bAttack == true && nADir == 1) {
                    biAttack = sprAttackL.getAttackSprite(nADir);
                } else if (bAttack == true && nADir == 0) {
                    biAttack = sprAttackR.getAttackSprite(nADir);
                }

            } else {
                biPlayer = sprPlayer.getStill();
                biEnemy = sprEnemy1.getStill();
                if (bAttack == true && nADir == 1) {
                    biAttack = sprAttackL.getAttackSprite(nADir);
                } else if (bAttack == true && nADir == 0) {
                    biAttack = sprAttackR.getAttackSprite(nADir);
                }
                biForce = sprForce.getForceSprite();
            }
            sHealth = "Health: " + Sprite.nHealth;
            sEHealth = Integer.toString(Sprite.nEHealth);
            sEHealth2 = Integer.toString(Sprite.nEHealth2);
        } else if (Sprite.nHealth <= 0) {
            sHealth = "Health: 0";
            biDeath = sprDeath.getStill();
        }

        //MpP Managing Code
        if (Sprite.nMP <= 0) {
            sMP = "MP: 0";
        } else {
            sMP = "MP: " + Sprite.nMP;
        }

        sMPCool = "MP Cooldown: " + Sprite.nMPCool;
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

        if (Sprite.nHealth > 0) {
            g2d.drawImage(biPlayer, sprPlayer.getX(), sprPlayer.getY(), null);
            g2d.drawImage(biEnemy, sprEnemy1.x, sprEnemy1.y, null);//Enemy 1
            g2d.drawImage(biEnemy, sprEnemy2.x, sprEnemy2.y, null);//Enemy 2

            g.drawString(sHealth, 0, 20);
            g.drawString(sEHealth, sprEnemy1.x, sprEnemy1.y - 10);
            g.drawString(sEHealth2, sprEnemy2.x, sprEnemy2.y - 10);
            if (Sprite.nMP > 0) {
                g.drawString(sMP, 0, 40);
            } else if (Sprite.nMP <= 0) {
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
                g2d.drawImage(biForce, sprPlayer.getX(), 240, null);
            }

        } else {
            g.setColor(Black);
            g2d.fillRect(0, 0, 765, 480);
            g2d.drawImage(biDeath, sprPlayer.getX(), 385, null);
            g2d.drawImage(End, 0, 150, null);
            g.setColor(White);
            g.drawString(sScore, 0, 20);
            //nMP = 0;
        }
    }

    private class Movement extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            bMove = false;
            bAttack = false;
            bForce = false;
            bHeal = false;
            bEDamage2 = false;
            bEDamage1 = false;
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
            } else if (code == KeyEvent.VK_W && bJump == true) {
                dY = -10;
            }
            if (code == KeyEvent.VK_SPACE && Sprite.nMP > 0) {//Attack
                bAttack = true;
            }
            if (code == KeyEvent.VK_F && Sprite.nMP > 0) {//Force
                bForce = true;
            }
            if (code == KeyEvent.VK_G && Sprite.nMP > 0) {//Healing
                bHeal = true;
            }
            if (code == KeyEvent.VK_F && Sprite.nMP == 0
                    || code == KeyEvent.VK_SPACE && Sprite.nMP == 0) {
                bForce = false;
                bAttack = false;
                bHeal = false;
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