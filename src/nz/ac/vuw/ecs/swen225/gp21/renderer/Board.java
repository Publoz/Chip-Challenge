package nz.ac.vuw.ecs.swen225.gp21.renderer;

import nz.ac.vuw.ecs.swen225.gp21.domain.Game;
import nz.ac.vuw.ecs.swen225.gp21.domain.Position;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JComponent;

import nz.ac.vuw.ecs.swen225.gp21.domain.Tile;

public class Board {
    private JComponent gameBoard;
    private RenderTile[][] renderTiles;
    private Tile[][] maze;
    private Position chapPos, lChapPos;
    private Game game;
    private Clip moveClip, pickupClip, doorClip, hurtClip, portalClip;
    AudioInputStream moveStream, pickupStream, doorStream, hurtStream, portalStream;
    public static BufferedImage chap, empty, portal, lock, treasure, info, doorG, doorB, doorY, doorR, wall, keyY, keyG,
            keyB, keyR, acid, time, chapleft, chapright, chapup;

    /**
     * Board is responsible for loading and storing resources used by RenderTiles.
     * Board then constructs the display component for GUI using these resources.
     *
     * @author barevcosm
     *
     */

    public Board(Game game) {
        this.game = game;
        this.maze = game.getMaze();
        renderTiles = new RenderTile[maze[0].length][maze.length];
        chapPos = game.getChap();
        lChapPos = game.getChap();
        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[0].length; col++) {
                renderTiles[col][row] = (new RenderTile(col, row,
                        maze[row][col].toString(), maze[row][col]));
            }
        }

        gameBoard = new JComponent() {
            protected void paintComponent(Graphics g) {
                redraw(g);
            }
        };

        // preload all images.

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

            chapleft = ImageIO.read(RenderTile.class.getResource("chapleft.png"));
            chapright = ImageIO.read(RenderTile.class.getResource("chapright.png"));
            chapup = ImageIO.read(RenderTile.class.getResource("chapback.png"));

            // preload sounds
            moveClip = AudioSystem.getClip();
            moveStream = AudioSystem.getAudioInputStream(RenderTile.class.getResource("move.wav"));
            moveClip.open(moveStream);
            pickupClip = AudioSystem.getClip();
            pickupStream = AudioSystem.getAudioInputStream(RenderTile.class.getResource("pickup.wav"));
            pickupClip.open(pickupStream);
            doorClip = AudioSystem.getClip();
            doorStream = AudioSystem.getAudioInputStream(RenderTile.class.getResource("door.wav"));
            doorClip.open(doorStream);
            hurtClip = AudioSystem.getClip();
            hurtStream = AudioSystem.getAudioInputStream(RenderTile.class.getResource("hurt.wav"));
            hurtClip.open(hurtStream);
            portalClip = AudioSystem.getClip();
            portalStream = AudioSystem.getAudioInputStream(RenderTile.class.getResource("portal.wav"));
            portalClip.open(portalStream);

        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            System.out.println("Unable to load images");
        }

    }

    /**
     * Responsible for updating tile images on game update.
     * Responsible for calling sound effects on move

     * @param g instance
     */

    public void redraw(Graphics g) {
        lChapPos = chapPos;

        if (chapPos != game.getChap()) {
            chapPos = game.getChap();
        }
        if (lChapPos != chapPos) {
            playSound("move");
            if (renderTiles[chapPos.getCol()][chapPos.getRow()].img == treasure
                    || renderTiles[chapPos.getCol()][chapPos.getRow()].img == keyG
                    || renderTiles[chapPos.getCol()][chapPos.getRow()].img == keyR
                    || renderTiles[chapPos.getCol()][chapPos.getRow()].img == keyB
                    || renderTiles[chapPos.getCol()][chapPos.getRow()].img == keyY) {
                playSound("pickup");
            }
            if (renderTiles[chapPos.getCol()][chapPos.getRow()].img == doorG
                    || renderTiles[chapPos.getCol()][chapPos.getRow()].img == doorB
                    || renderTiles[chapPos.getCol()][chapPos.getRow()].img == doorR
                    || renderTiles[chapPos.getCol()][chapPos.getRow()].img == doorY
                    || renderTiles[chapPos.getCol()][chapPos.getRow()].img == lock) {
                playSound("door");
            }
        }

        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[0].length; col++) {
                renderTiles[col][row].tile = maze[row][col];
                int xoffset = col - chapPos.getCol() + 4;
                int yoffset = row - chapPos.getRow() + 4;
                renderTiles[col][row].x = xoffset;
                renderTiles[col][row].y = yoffset;
                renderTiles[col][row].lasMoveDir = game.getLastMoveDir();

                renderTiles[col][row].draw(g);

            }
        }
    }

    /**
     * Plays sound file corresponding to string.

     * @param s sound file to be played
     */

    public void playSound(String s) {
        if (s.equals("move")) {
            moveClip.stop();
            moveClip.setFramePosition(0);
            moveClip.start();
        } else if (s.equals("pickup")) {
            pickupClip.stop();
            pickupClip.setFramePosition(0);
            pickupClip.start();
        } else if (s.equals("door")) {
            doorClip.stop();
            doorClip.setFramePosition(0);
            doorClip.start();
        } else if (s.equals("portal")) {
            portalClip.stop();
            portalClip.setFramePosition(0);
            portalClip.start();
        } else if (s.equals("hurt")) {
            hurtClip.stop();
            hurtClip.setFramePosition(0);
            hurtClip.start();
        }

    }

    /**
     * Board Component.

     * @return the game component used by GUI
     */

    public JComponent getGameBoard() {
        return this.gameBoard;
    }
}