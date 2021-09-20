package nz.ac.vuw.ecs.swen225.gp21.renderer;

import nz.ac.vuw.ecs.swen225.gp21.App.MainFrame;
import nz.ac.vuw.ecs.swen225.gp21.Persistency.XMLSaveLoad;
import nz.ac.vuw.ecs.swen225.gp21.domain.Game;
import nz.ac.vuw.ecs.swen225.gp21.domain.Position;
import nz.ac.vuw.ecs.swen225.gp21.domain.Tile;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Board {
    Graphics g;
    private JFrame mainFrame;
    private JComponent gameBoard;
    private ArrayList<RenderTile> renderTiles = new ArrayList();
    private Tile[][] maze;
    private Position chapPos;
    private Game game;
    public Board(Game game) {
        this.game = game;
        this.maze = game.getMaze();

        /**
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                tiles[i][j] = new RenderTile(i, j);
            }
        }
        **/
        //emulation code
        gameBoard = new JComponent() {
            protected void paintComponent(Graphics g) {
                redraw(g);
            }
        };
        mainFrame = new MainFrame("Chip's Challenge-Level " + "1");
        mainFrame.setLayout(null);
        int gameBoardWidth = mainFrame.getWidth() * 2 / 3;
        int tileSize = gameBoardWidth/9;
        int boardBorder = border(gameBoardWidth, mainFrame.getHeight());
        gameBoard.setBounds(boardBorder, boardBorder, gameBoardWidth - 2 * boardBorder, mainFrame.getHeight() - 3 * boardBorder);
        gameBoard.setBackground(new Color(190, 190, 190));
        gameBoard.setLayout(new GridLayout(9, 9));
        mainFrame.add(gameBoard);
        //mainFrame.setVisible(true);

    }
    //emulation code
    private int border(int width, int height) {
        int num = width<height? height : width;
        return (int)((0.05)*num);
    }

    public void redraw(Graphics g) {
        chapPos = game.getChap();
        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze.length; col++) {
                renderTiles.add( new RenderTile(col, row, maze[row][col].toString()));
            }
        }


        for(RenderTile t : renderTiles){
            t.draw(g);
        }
    }



    public JComponent getGameBoard(){
        return this.gameBoard;
    }
}
