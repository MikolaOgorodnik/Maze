package maze;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        MazeUI mazeUI = new MazeUI(scan);

        mazeUI.start();

        scan.close();
    }
}
