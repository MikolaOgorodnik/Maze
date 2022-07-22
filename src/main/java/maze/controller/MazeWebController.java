package maze.controller;

import maze.model.Maze;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class MazeWebController {
    private final Logger logger = LoggerFactory.getLogger(MazeWebController.class);

    private Maze maze;

    private final DateTimeFormatter formatter;

    @Autowired
    public MazeWebController(Environment env) {
        maze = new Maze();
        String dateFormatter = env.getProperty("localdatetime.format", "dd.MM.yyyy HH:mm:ss");
        formatter = DateTimeFormatter.ofPattern(dateFormatter);
    }

    public String getDateFormatted(LocalDateTime date) {
        return date.format(formatter);
    }

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        logger.info("Root mapping started.");

        model.addAttribute("datetime", getDateFormatted(LocalDateTime.now()));
        model.addAttribute("username", "Mikola");

        String direction = "generate";
        int height = maze.isMazeExists() ? maze.getHeight() : Maze.DEFAULT_HEIGHT;
        int width = maze.isMazeExists() ? maze.getWidth() : Maze.DEFAULT_WIDTH;

        if (maze.isMazeExists()) {
            model.addAttribute("isMazeExists", true);
            model.addAttribute("grid", maze.getGrid());
            direction = "index";
        } else {
            model.addAttribute("height", height);
            model.addAttribute("width", width);
        }

        return direction;
    }

    @GetMapping(value = {"/generate"})
    public String showGenerateMaze(Model model) {

        int height;
        int width;

        height = maze.isMazeExists() ? maze.getHeight() : Maze.DEFAULT_HEIGHT;
        width = maze.isMazeExists() ? maze.getWidth() : Maze.DEFAULT_WIDTH;

        model.addAttribute("height", height);
        model.addAttribute("width", width);

        return "generate";
    }

    @PostMapping(value = "/generate")
    public String generateMaze(Model model, GenerationResponse generationResponse) {
        model.addAttribute("generationResponse", generationResponse);
        maze = new Maze(generationResponse.getHeight(), generationResponse.getWidth());

        return "redirect:/";
    }

    @GetMapping(value = "/solve")
    public String solveMaze() {

        if (maze.isMazeExists() ) {
            maze.solveMaze();
        }

        return "redirect:/";
    }

}
