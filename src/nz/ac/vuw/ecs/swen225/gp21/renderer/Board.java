package nz.ac.vuw.ecs.swen225.gp21.renderer;
import nz.ac.vuw.ecs.swen225.gp21.domain.Game;
import nz.ac.vuw.ecs.swen225.gp21.domain.Position;
import nz.ac.vuw.ecs.swen225.gp21.domain.Tile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Board {
    private JComponent gameBoard;
    private RenderTile[][] renderTiles;
    private Tile[][] maze;
    private Position chapPos, lChapPos;
    private Game game;
    public static BufferedImage chap,empty,portal,lock,treasure,info,doorG,doorB,doorY,doorR,wall,keyY,keyG,keyB,keyR, acid, time;
    public Board(Game game) {
        this.game = game;
        this.maze = game.getMaze();
        renderTiles = new RenderTile[maze.length][maze.length];
        chapPos = game.getChap();
        lChapPos = game.getChap();
        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze.length; col++) {
                    renderTiles[col][row] = (new RenderTile(col, row, maze[row][col].toString(), maze[row][col]));
            }
        }

        gameBoard = new JComponent() {
            protected void paintComponent(Graphics g) {
                redraw(g);
            }
        };

        //preload all images.

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
            doorR = ImageIO.read(RenderTile.class.getResource("doorred.png"));
            wall = ImageIO.read(RenderTile.class.getResource("wall.png"));
            keyY = ImageIO.read(RenderTile.class.getResource("keyyellow.png"));
            keyG = ImageIO.read(RenderTile.class.getResource("keygreen.png"));
            keyB = ImageIO.read(RenderTile.class.getResource("keyblue.png"));
            keyR = ImageIO.read(RenderTile.class.getResource("keyred.png"));
            acid = ImageIO.read(RenderTile.class.getResource("acid.png"));
            time = ImageIO.read(RenderTile.class.getResource("timetile.png"));

        } catch (IOException e) {
            System.out.println("Unable to load images");
        }

    }
    public void redraw(Graphics g) {
        chapPos = game.getChap();
        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze.length; col++) {
                renderTiles[col][row].tile = maze[row][col];
                int xOffset = col - chapPos.getCol() + 4;
                int yOffset = row - chapPos.getRow() + 4;
                renderTiles[col][row].x = xOffset;
                renderTiles[col][row].y = yOffset;

                renderTiles[col][row].draw(g);

            }
        }
    }



    public JComponent getGameBoard(){
        return this.gameBoard;
    }
}
