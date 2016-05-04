
import java.awt.event.KeyEvent;

//http://www.kilobolt.com/day-5-background-and-sprites/unit-2-day-5-background-and-sprites
////import java.awt.event.KeyEvent;

public class Background {

    public int nbgX, nbgY, nspeedX, nScroll, nScroll2;

    public Background(int x, int y) {
        nScroll = x;
        nScroll2 = x + 765;
        nspeedX = 5;
    }

    public void update() {
        if (PanBoard.bMove()){
        if (PanBoard.nDir() == 1) {
            nScroll += nspeedX;
            nScroll2 += nspeedX;
        } else if (PanBoard.nDir() == 3) {
            nScroll -= nspeedX;
            nScroll2 -= nspeedX;
        }
        
        if (nScroll <= -765) {
            nScroll += 765;
            nScroll2 += 765;
        
        } else if (nScroll2 >= 765) {
            nScroll -= 765;
            nScroll2 -= 765;
        }
        }else{
            nScroll -= 0;
            nScroll2 -= 0;
        }
    }

    public int getnScroll() {
        return nScroll;
    }
    public int getnScroll2() {
        return nScroll2;
    }
    public void setnScroll(int nScroll) {
        this.nScroll = nScroll;
    }
    public void setnScroll2(int nScroll2) {
        this.nScroll2 = nScroll2;
    }
}