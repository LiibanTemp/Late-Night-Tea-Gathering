
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class PanBoard extends JPanel implements ActionListener {

    static boolean drawn = false;
    Rectangle rB, rP;
    //private Player p;
    //private Enemy e;
    Sprite sPlayer, sEnemy;
    private Timer timer;
    private Image background;
    static String sName;
    Label JLabel;
    int nChange = 1, nScroll, nScroll2, nPY, nX, nY, nDx, nDy;
    int nXstart, nYstart;
    String sFile, sFile2;
    static int nDir;
    BufferedImage biSpriteSheet, biPlayer, biEnemy;
    private static Background bg1, bg2;
    static boolean bMove, bJump;
    Rectangle rPlayer, rEnemy, rGround;

    public PanBoard() {

        sFile = "Walk (2).png";
        sFile2 = "Sanic.png";
        rPlayer = new Rectangle();
        rEnemy = new Rectangle();
        rGround = new Rectangle();
        //nSpriteX = 0; // this variable is used to get the proper image from the spritesheet. I will use nDir
        nDir = 3; // right. 0 is forward 1 is left, and 2 is back - going toward me.
        bMove = false;
        bJump = false;
        //nY += nDy;
//        nX += nDx;
//        nDx = 5;
        nY = 376;
        sPlayer = new Sprite(sFile, 350, 380, true);
        sEnemy = new Sprite(sFile2, 200, 405, false);
        bg1 = new Background(0, 0);
        bg2 = new Background(765, 0);
        addKeyListener(new Movement());
        setFocusable(true);
        ImageIcon i1 = new ImageIcon("Tea2.jpg");
        background = i1.getImage();
        timer = new Timer(30, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        nXstart = nX;
        nYstart = nY;
        nDx = 5;
        sPlayer.move();
        sEnemy.move();
        bg1.update();
        bg2.update();
        rPlayer.setBounds(sPlayer.getX(), sPlayer.getY(), 32, 50);
        rEnemy.setBounds(nX, nY, 60, 64);
        //Ground Hit detection rectangle
        rGround.setBounds(0, 430, 765, 1);
        if (nX <= sPlayer.x){
            nX+=nDx; 
       } else if (nX >= sPlayer.x) {
           nX-=nDx;
       }
        if (rEnemy.intersects(rPlayer) && nX < sPlayer.x) {
            nX = nXstart;
            nY = nYstart; 
           //nX -= nDx;
            //nDx =0;
            System.out.println("Hit");
        } else if (rEnemy.intersects(rPlayer) && nX > sPlayer.x) {
            nX = nXstart;
            nY = nYstart;
            //nX += nDx;
        }
        if (rGround.intersects(rPlayer)) {
            sPlayer.y = 380;
            bJump = true;
            sPlayer.dy = 0;
            //System.out.println("Hit");
        } else {
            bJump = false;
        }
        if (bMove) {
            biPlayer = sPlayer.getSprite(nDir);
        } else {
            biPlayer = sPlayer.getPStill();
            biEnemy = sEnemy.getEStill();
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
        g2d.drawImage(biEnemy, nX, nY, null);


    }

    private class Movement extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            bMove = false;
        }

        @Override
        public void keyPressed(KeyEvent e) {
            // why split up the keyPressed function???
            int code = e.getKeyCode();
            if (code == KeyEvent.VK_A) {
                nDir = 1;
                //nX += nDx;
                bMove = true;
            } else if (code == KeyEvent.VK_D) {
                nDir = 3;
                //nX -= nDx;
                bMove = true;

            } else if (code == KeyEvent.VK_W && bJump) {
                sPlayer.dy = -15;
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
