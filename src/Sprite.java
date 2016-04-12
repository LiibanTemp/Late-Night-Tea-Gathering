
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite {

    private BufferedImage biSpriteSheet;
    private static final int TILE_SIZEX = 576 / 9;
    private static final int TILE_SIZEY = 256 / 4;

    public void loadSprite(String sFile) {
        try {
            biSpriteSheet = ImageIO.read(new File("Walk8.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getSprite(int xGrid, int nDir) {
        return biSpriteSheet.getSubimage(xGrid * TILE_SIZEX, nDir * TILE_SIZEY, TILE_SIZEX, TILE_SIZEY);
    }
}
