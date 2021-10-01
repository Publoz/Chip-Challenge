package nz.ac.vuw.ecs.swen225.gp21.renderer;


import javax.imageio.ImageIO;

import nz.ac.vuw.ecs.swen225.gp21.domain.Tile;
import nz.ac.vuw.ecs.swen225.gp21.domain.Time;
import nz.ac.vuw.ecs.swen225.gp21.domain.Wall;
import nz.ac.vuw.ecs.swen225.gp21.domain.Acid;
import nz.ac.vuw.ecs.swen225.gp21.domain.Chap;
import nz.ac.vuw.ecs.swen225.gp21.domain.Door;
import nz.ac.vuw.ecs.swen225.gp21.domain.Exit;
import nz.ac.vuw.ecs.swen225.gp21.domain.ExitLock;
import nz.ac.vuw.ecs.swen225.gp21.domain.Info;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;

public class RenderTile {
    int x, y;
    String imgType;
    Tile tile;
    public BufferedImage img;
    public String lasMoveDir;
    public boolean hasEnemy;

    /**
     * RenderTile is responsible for holding a image for a tile of the maze.
     * Board is constructed using a grid of RenderTiles.
     *
     * @author barevcosm
     *
     */

    public RenderTile(int xpos, int ypos, String imgType, Tile t) {
        this.imgType = imgType;
        this.tile = t;
        this.x = xpos;
        this.y = ypos;
    }

    /**
     * Chooses the correct image for a tile.
     * Same tile types may use different images depending on state

     * @param g instance
     */

    public void draw(Graphics g) {
        img = Board.empty;
        if (tile != null) {
            if (tile instanceof Door) {
                if (((Door) tile).getColour().equals("g")) {
                    img = Board.doorG;
                }
                if (((Door) tile).getColour().equals("b")) {
                    img = Board.doorB;
                }
                if (((Door) tile).getColour().equals("y")) {
                    img = Board.doorY;
                }
                if (((Door) tile).getColour().equals("r")) {
                    img = Board.doorR;
                }
            }
            if (tile.hasTreasure()) {
                img = Board.treasure;
            }
            if (tile instanceof Wall) {
                img = Board.wall;
            }
            if (tile instanceof ExitLock) {
                img = Board.lock;
            }
            if (tile instanceof Exit) {
                img = Board.portal;
            }
            if (tile instanceof Info) {
                img = Board.info;
            }
            if (tile instanceof Time) {
                img = Board.time;
            }
            if (tile instanceof Acid) {
                img = Board.acid;
            }
            if (tile.hasKey()) {
                if (tile.getKey().equals("g")) {
                    img = Board.keyG;
                }
                if (tile.getKey().equals("b")) {
                    img = Board.keyB;
                }
                if (tile.getKey().equals("y")) {
                    img = Board.keyY;
                }
                if (tile.getKey().equals("r")) {
                    img = Board.keyR;
                }
            }
            if (tile.getActor() instanceof Chap) {
                img = Board.chap;
                if (lasMoveDir == "w") {
                    img = Board.chapup;
                }
                if (lasMoveDir == "a") {
                    img = Board.chapleft;
                }
                if (lasMoveDir == "d") {
                    img = Board.chapright;
                }
            }
            if (tile.getActor() != null && !(tile.getActor() instanceof Chap)) {
                img = tile.getActor().getImage();
            }
        }

        g.drawImage(img, this.x * 60, this.y * 60, 60, 60, new ImageObserver() {
            @Override
            public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                return false;
            }
        });
    }
}