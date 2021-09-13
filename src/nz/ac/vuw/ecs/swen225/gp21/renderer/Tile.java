package nz.ac.vuw.ecs.swen225.gp21.renderer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;


public class Tile {
    int x, y;
    BufferedImage img = null;
    public Tile(int xpos, int ypos) {
        this.x = xpos;
        this.y = ypos;

        try {
            img = ImageIO.read(Tile.class.getResource("tile.png"));
        } catch (IOException e) {
        }

    }

    public void draw(Graphics g){
        g.drawImage(img, this.x*66, this.y*66, new ImageObserver() {
            @Override
            public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                return false;
            }
        });
    }
}
