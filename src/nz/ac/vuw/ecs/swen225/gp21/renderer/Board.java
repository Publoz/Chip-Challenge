package nz.ac.vuw.ecs.swen225.gp21.renderer;

import nz.ac.vuw.ecs.swen225.gp21.App.GUI;
import nz.ac.vuw.ecs.swen225.gp21.App.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;

public class Board {
    Graphics g;
    private JFrame mainFrame;
    private JComponent gameBoard;
    private Tile[][] tiles = new Tile[9][9];

    public Board() {

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                tiles[i][j] = new Tile(i, j);
            }
        }

        gameBoard = new JComponent() {
            protected void paintComponent(Graphics g) {
                redraw(g);
            }
        };
        mainFrame = new MainFrame("Chip's Challenge-Level " + "1");
        mainFrame.setLayout(null);
        int gameBoardWidth = mainFrame.getWidth() * 2 / 3;
        int tileSize = gameBoardWidth/9;
        System.out.println(tileSize);
        int boardBorder = border(gameBoardWidth, mainFrame.getHeight());
        gameBoard.setBounds(boardBorder, boardBorder, gameBoardWidth - 2 * boardBorder, mainFrame.getHeight() - 3 * boardBorder);
        gameBoard.setBackground(new Color(190, 190, 190));
        gameBoard.setLayout(new GridLayout(9, 9));

        mainFrame.add(gameBoard);
        mainFrame.setVisible(true);

    }

    public void redraw(Graphics g) {
        for(Tile[] ts : tiles){
            for(Tile t : ts){
                t.draw(g);
            }
        }
    }

    private int border(int width, int height) {
        int num = width<height? height : width;
        return (int)((0.05)*num);
    }

    public static void main(String[] args) throws FontFormatException, IOException {
        new Board();
    }
}
