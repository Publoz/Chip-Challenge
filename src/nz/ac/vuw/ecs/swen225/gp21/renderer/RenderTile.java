package nz.ac.vuw.ecs.swen225.gp21.renderer;

import nz.ac.vuw.ecs.swen225.gp21.domain.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;


public class RenderTile {
    int x, y;
    String imgType;
    Tile tile;
    private BufferedImage img,chap,empty,portal,lock,treasure,info,doorG,doorB,doorY,wall,keyY,keyG,keyB,spider;

    public RenderTile(int xpos, int ypos, String imgType, Tile t) {
        this.imgType = imgType;
        this.tile = t;
        this.x = xpos;
        this.y = ypos;
        //preload all images instead of on board update.
        try {
            empty = ImageIO.read(RenderTile.class.getResource("empty.png"));
            chap = ImageIO.read(RenderTile.class.getResource("chap.png"));
            portal = ImageIO.read(RenderTile.class.getResource("portal.png"));
            lock = ImageIO.read(RenderTile.class.getResource("lock.png"));
            treasure = ImageIO.read(RenderTile.class.getResource("treasure.png"));
            info = ImageIO.read(RenderTile.class.getResource("info.png"));
            doorG = ImageIO.read(RenderTile.class.getResource("doorgreen.png"));
            doorB = ImageIO.read(RenderTile.class.getResource("doorblue.png"));
            doorY = ImageIO.read(RenderTile.class.getResource("dooryellow.png"));
            wall = ImageIO.read(RenderTile.class.getResource("wall.png"));
            keyY = ImageIO.read(RenderTile.class.getResource("keyyellow.png"));
            keyG = ImageIO.read(RenderTile.class.getResource("keygreen.png"));
            keyB = ImageIO.read(RenderTile.class.getResource("keyblue.png"));

        } catch (IOException e) {
        }
    }

    public void draw(Graphics g){
        img = empty;
        if(tile.hasTreasure()){img = treasure;}
        if(tile instanceof Door){
            if(((Door) tile).getColour().equals("g")){ img = doorG; }
            if(((Door) tile).getColour().equals("b")){ img = doorB; }
            if(((Door) tile).getColour().equals("y")){ img = doorY; }
            if(tile.canMoveHere()){ img = empty; }
        }
        if(tile instanceof Wall){ img = wall; }
        if(tile instanceof ExitLock){ img = lock; }
        if(tile instanceof Exit){ img = portal; }
        if(tile instanceof Info) { img = info;}
        if(tile.hasKey()){
            if(tile.getKey().equals("g")){ img = keyG; }
            if(tile.getKey().equals("b")){ img = keyB; }
            if(tile.getKey().equals("y")){ img = keyY; }
        }
        if(tile.getActor() != null){img = chap;}


        g.drawImage(img, this.x * 60, this.y * 60, 60, 60, new ImageObserver() {
            @Override
            public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                return false;
            }
        });
    }
}
