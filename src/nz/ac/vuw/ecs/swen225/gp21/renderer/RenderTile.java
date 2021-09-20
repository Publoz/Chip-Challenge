package nz.ac.vuw.ecs.swen225.gp21.renderer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;


public class RenderTile {
    int x, y;
    String imgType;
    BufferedImage img = null;
    public RenderTile(int xpos, int ypos, String imgType) {
        this.imgType = imgType;
        this.x = xpos;
        this.y = ypos;


        try {
            if(imgType == null){
                img = ImageIO.read(RenderTile.class.getResource("tile.png"));
            } else if (imgType != null){
                if(imgType == ""){
                    img = ImageIO.read(RenderTile.class.getResource("key.png"));
                } else {
                    img = ImageIO.read(RenderTile.class.getResource(imgType + ".png"));
                }
            }
        } catch (IOException e) {
        }

    }

    public void draw(Graphics g){
        g.drawImage(img, this.x * 60, this.y * 60, 60, 60, new ImageObserver() {
            @Override
            public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                return false;
            }
        });
    }
}
