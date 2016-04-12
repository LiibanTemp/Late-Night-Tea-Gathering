
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class PanBoard extends JPanel implements ActionListener {

    static boolean drawn = false;
    Rectangle rB, rP; // rectangles for the 3 sprites.
    private Player p;
    Sprite s;
    private Timer timer;
    private Image background;
    static String sName;
    Label JLabel;
    int nChange = 1, nX, nY, nDir = 3;
    String sFile;
    BufferedImage biSpriteSheet, biSprite;

    public PanBoard() {
        //sFile = "Walk8.png";
        nX = 0;
        //nY = 3;
        p = new Player();
        s = new Sprite();
        s.loadSprite(sFile);
        addKeyListener(new Movement());
        setFocusable(true);
        ImageIcon i1 = new ImageIcon("Castle2.jpg");
        background = i1.getImage();
        timer = new Timer(60, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        p.move();
        if (nX > 8) {
            nX = 0;
            // nDir++;
        }
        if (nDir > 3) {
            nDir = 0;
        }
        biSprite = s.getSprite(nX, nDir);
        System.out.println(nX + " " + nDir);
        repaint();
        //nX++;

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(background, 0, 0, null);
        //g2d.drawImage(biSprite, 0, 376, null);
        g2d.drawImage(biSprite, p.getX(), p.getY(), null);
    }

    private class Movement extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent w) {
            p.keyReleased(w);
            nX = 0;
        }

        @Override
        public void keyPressed(KeyEvent w) {
            p.keyPressed(w);
            int code = w.getKeyCode();
            if (code == KeyEvent.VK_A) {
                nDir = 1;
                nX++;
            } else if (code == KeyEvent.VK_D) {
                nDir = 3;
                nX++;
            } else if (code == KeyEvent.VK_W) {
                nDir = 0;
                nX++;
            } else if (code == KeyEvent.VK_S) {
                nDir = 2;
                nX++;
            }
        }
    }
}