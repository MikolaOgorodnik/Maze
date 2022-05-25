package maze;

import java.util.Scanner;

/**
 * Console representation of Maze Solver.
 * @author Nikolai Ogorodnik
 */
public class Main {
    /**
     * Main method. Obviously. )
     * @param args program arguments.
     */
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        MazeUI mazeUI = new MazeUI(scan);

        mazeUI.start();

        scan.close();
    }
}
