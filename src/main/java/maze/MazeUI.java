package maze;

import java.io.*;
import java.util.Scanner;

/**
 * This class provides main menu for console representation of Maze Solver.
 * @author Nikolai Ogorodnik
 */
public class MazeUI {

    private Maze maze;
    private final Scanner scan;

    /**
     * Constructs main menu for console representation of Maze Solver.
     * @param scan Scanner object.
     */
    public MazeUI(Scanner scan) {
        this.scan = scan;
    }

    private void showMaze() {
        String result = String.valueOf(maze);
        System.out.println(result);
    }

    private void generateMaze() {
        System.out.println("Please, enter the size of a maze");

        scan.nextLine();

        String[] dimensions = scan.nextLine().split("\\s");

        int height = 0;
        int width = 0;

        if (dimensions.length == 2) {
            height = Integer.parseInt(dimensions[0]);
            width = Integer.parseInt(dimensions[1]);
        } else if (dimensions.length == 1) {
            height = width = Integer.parseInt(dimensions[0]);
        }

        try {
            maze = new Maze(height, width);
            maze.fillGrid();
            showMaze();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void saveMaze() {
        scan.nextLine();
        String fileName = scan.nextLine();
        try {
            serialize(maze, fileName);
        } catch (IOException e) {
            System.out.println("Error saving file");
            e.printStackTrace();
        }
    }

    private void loadMaze() {
        scan.nextLine();
        String fileName = scan.nextLine();
        try {
            maze = (Maze) deserialize(fileName);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Cannot load the maze. It has an invalid format");
        }
    }

    private void solveMaze() {
        System.out.println(maze.showSolution());
    }

    private void showMainMenu() {
        System.out.println("=== Menu ===");
        System.out.println("1. Generate a new maze");
        System.out.println("2. Load a maze");

        if (maze != null && maze.isMazeExists()) {
            System.out.println("3. Save the maze");
            System.out.println("4. Display the maze");
            System.out.println("5. Find the escape.");
        }

        System.out.println("0. Exit");
    }

    /**
     *
     */
    public void start() {

        int selectedMenuIdx = -1;

        while (selectedMenuIdx != 0) {
            showMainMenu();
            selectedMenuIdx = scan.nextInt();

            switch (selectedMenuIdx) {
                case 1:
                    generateMaze();
                    break;
                case 2:
                    loadMaze();
                    break;
                case 3:
                    saveMaze();
                    break;
                case 4:
                    showMaze();
                    break;
                case 5:
                    solveMaze();
                    break;
                case 0:
                    System.out.println("Bye!");
                    break;
                default:
                    System.out.println("Incorrect option. Please try again");
            }
        }
    }

    /**
     * Serialize the given object to the file.
     *
     * @param obj Object to be serialized.
     * @param fileName Name of a file for serialization.
     * @throws IOException when given Object couldn't be serialized.
     */
    public static void serialize(Object obj, String fileName) throws IOException {
        try (
                FileOutputStream fos = new FileOutputStream(fileName);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(obj);
        }
    }

    /**
     * Deserialize to an object from the file.
     *
     * @param fileName Name of a file for deserialization.
     * @return Deserialized Object.
     * @throws IOException when given Object couldn't be deserialized.
     * @throws ClassNotFoundException when the given object cannot be cast to the specified type.
     */
    public static Object deserialize(String fileName) throws IOException, ClassNotFoundException {
        try (
                FileInputStream fis = new FileInputStream(fileName);
                BufferedInputStream bis = new BufferedInputStream(fis);
                ObjectInputStream ois = new ObjectInputStream(bis)) {
            return ois.readObject();
        }
    }
}
