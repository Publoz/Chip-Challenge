package nz.ac.vuw.ecs.swen225.gp21.recorder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;


public class RecordAndPlay {

    private Stack<String> allMoves = new Stack<>();

    public Stack<String> getAllMoves() {
        return allMoves;
    }

    public void addMoves(String moves) {
        this.allMoves.add(moves);
    }

    public RecordAndPlay() {

    }

    /**
     * @param test the object that holds all the moves to be saved
     */
    public static void save(RecordAndPlay test) {
        //create a xml file and write all the string in to the file
        try (BufferedWriter out = new BufferedWriter(new FileWriter("./src/nz/ac/vuw/ecs/swen225/gp21/recorder/saved/gameMoves1.xml"))) {
            for (String moves : test.getAllMoves()) {
                out.write(moves + "\n");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String args[]) {

        //test that adds all the String of moves and calls on save method
        RecordAndPlay test = new RecordAndPlay();
        test.addMoves("N 1");
        test.addMoves("S 2");
        test.addMoves("W 3");
        test.addMoves("E 4");
        RecordAndPlay.save(test);
    }

}
