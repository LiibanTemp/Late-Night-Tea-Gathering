
import java.awt.event.KeyEvent;

//http://www.kilobolt.com/day-5-background-and-sprites/unit-2-day-5-background-and-sprites
////import java.awt.event.KeyEvent;

public class Background {

    private int nbgX, nbgY, nspeedX;

    public Background(int x, int y) {
        nbgX = x;
        nbgY = y;
        nspeedX = 5;
    }

    public void update() {
        nbgX -= nspeedX;

        if (nbgX <= -765) {
            nbgX += 1530;
        }
        if(nbgX >= 760){
           nbgX -= 1530; 
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