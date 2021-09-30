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
    public BufferedImage img;
    public RenderTile(int xpos, int ypos, String imgType, Tile t) {
        this.imgType = imgType;
        this.tile = t;
        this.x = xpos;
        this.y = ypos;
    }

    public void draw(Graphics g){
        img = Board.empty;
        if(tile != null){
            if(tile instanceof Door){
                if(((Door) tile).getColour().equals("g")){ img = Board.doorG; }
                if(((Door) tile).getColour().equals("b")){ img = Board.doorB; }
                if(((Door) tile).getColour().equals("y")){ img = Board.doorY; }
                if(((Door) tile).getColour().equals("r")){ img = Board.doorR; }
            }
            if(tile.hasTreasure()){img = Board.treasure;}
            if(tile instanceof Wall){ img = Board.wall; }
            if(tile instanceof ExitLock){ img = Board.lock; }
            if(tile instanceof Exit){ img = Board.portal; }
            if(tile instanceof Info) { img = Board.info;}
            if(tile instanceof Time) {img = Board.time;}
            if(tile instanceof Acid) {img = Board.acid;}
            if(tile.hasKey()){
                if(tile.getKey().equals("g")){ img = Board.keyG; }
                if(tile.getKey().equals("b")){ img = Board.keyB; }
                if(tile.getKey().equals("y")){ img = Board.keyY; }
                if(tile.getKey().equals("r")){ img = Board.keyR; }
            }
            if(tile.getActor() instanceof Chap) {img = Board.chap;}
            if(tile.getActor() != null && !(tile.getActor() instanceof Chap)){img = tile.getActor().getImage();}
        }

        g.drawImage(img, this.x * 60, this.y * 60, 60, 60, new ImageObserver() {
            @Override
            public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                return false;
            }
        });
    }
}
