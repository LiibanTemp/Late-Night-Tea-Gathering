
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
    Sprite p, e;
    //private Enemy en;
    private Timer timer;
    private Image background;
    static String sName;
    Label JLabel;
    int nChange = 1, nScroll, nScroll2, nPY, nX, nY, nDx, nDy;
    String sFile, sFile2;
    static int nDir;
    BufferedImage biSpriteSheet, biSprite, biSprite2;
    private static Background bg1, bg2;
    static boolean bMove, bJump;
    Rectangle P, E, G;

    public PanBoard() {

        sFile = "Walk (2).png";
        sFile2 = "Sanic.png";
        P = new Rectangle();
        E = new Rectangle();
        G = new Rectangle();
        //nSpriteX = 0; // this variable is used to get the proper image from the spritesheet. I will use nDir
        nDir = 3; // right. 0 is forward 1 is left, and 2 is back - going toward me.
        bMove = false;
        bJump = false;
        nY += nDy;
        nX += nDx;
        nDx = 5;
        nY = 376;
        p = new Sprite(sFile, 350, 376, true);
        e = new Sprite(sFile2, 200, 405, false);
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
        p.move();
        e.move();
        bg1.update();
        bg2.update();
        P.setBounds(p.getX(), p.getY(), 64, 64);
        E.setBounds(nX, nY, 75, 64);
        //Ground Hit detection rectangle
        G.setBounds(0, 438, 765, 1);
        if(E.intersects(P)){
        }
        if(G.intersects(P)){
            System.out.println("Hit");
        }
        if (bMove) {
            biSprite = p.getSprite(nDir);
        } else {
            biSprite = p.getStill();
            biSprite2 = e.getStill();
        }
        
        //nPY = p.getY();
        //System.out.println(nPY);
        repaint();
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g.drawImage(background, bg1.getnScroll(), 0, this);
        g.drawImage(background, bg2.getnScroll2(), 0, this);
        g2d.drawImage(biSprite, p.getX(), p.getY(), null);
        g2d.drawImage(biSprite2, nX, nY, null);
        
        
    }

    private class Movement extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            bMove = false;
            if(p.getX() <= 276){
                bJump = false;
            }
            //e.keyReleased(w);
            //nSpriteX = 0;
        }

        @Override
        public void keyPressed(KeyEvent e) {
            // why split up the keyPressed function???
            int code = e.getKeyCode();
            if (code == KeyEvent.VK_A) {
                nDir = 1;
                nX += nDx;
                bMove = true;
            } else if (code == KeyEvent.VK_D) {
                nDir = 3;
                nX -= nDx;
                bMove = true;
            }
            if (code == KeyEvent.VK_W) {
                //bMove = true;
                bJump = true;
                //nDir = 1;
            }
            if (bJump) {
                p.Jump();
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
