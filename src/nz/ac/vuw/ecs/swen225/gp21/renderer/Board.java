package nz.ac.vuw.ecs.swen225.gp21.renderer;
import nz.ac.vuw.ecs.swen225.gp21.domain.Game;
import nz.ac.vuw.ecs.swen225.gp21.domain.Position;
import nz.ac.vuw.ecs.swen225.gp21.domain.Tile;

import javax.swing.*;
import java.awt.*;

public class Board {
    private JComponent gameBoard;
    private RenderTile[][] renderTiles;
    private Tile[][] maze;
    private Position chapPos, lChapPos;
    private Game game;
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

    }
    public void redraw(Graphics g) {
        chapPos = game.getChap();
        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze.length; col++) {
                renderTiles[col][row].x = col- chapPos.getCol() + 4;
                renderTiles[col][row].y = row - chapPos.getRow() + 4;
                renderTiles[col][row].draw(g);
            }
        }
    }



    public JComponent getGameBoard(){
        return this.gameBoard;
    }
}
