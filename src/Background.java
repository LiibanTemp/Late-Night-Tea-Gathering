
import java.awt.event.KeyEvent;

//http://www.kilobolt.com/day-5-background-and-sprites/unit-2-day-5-background-and-sprites
////import java.awt.event.KeyEvent;

public class Background {

    private int nbgX, nbgY, nspeedX;

    public Background(int x, int y) {
        nbgX = x;
        nbgY = y;
        nspeedX = 10;
    }
 //Test code for Scrolling background with Character movement    
//    public void keyPressed(KeyEvent w) {
//        int code = w.getKeyCode();
//        if (code == KeyEvent.VK_A) {
//            nspeedX = -10;
//        } else if (code == KeyEvent.VK_D) {
//            nspeedX = 10;
//        }
//    }

    public void update() {
        nbgX -= nspeedX;

        if (nbgX <= -730) {
            nbgX += 1460;
        }
        if(nbgX >= 730){
           nbgX -= 1460; 
        }
    }

    public int getBgX() {
        return nbgX;
    }

    public int getBgY() {
        return nbgY;
    }

    public int getSpeedX() {
        return nspeedX;
    }

    public void setBgX(int nbgX) {
        this.nbgX = nbgX;
    }

    public void setBgY(int nbgY) {
        this.nbgY = nbgY;
    }

    public void setSpeedX(int nspeedX) {
        this.nspeedX = nspeedX;
    }
}