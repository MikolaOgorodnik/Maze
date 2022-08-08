package maze.controller;

import maze.model.Maze;
import maze.service.MazeService;
import maze.service.MazeServiceImpl;
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

    //private Maze maze;

    private MazeService mazeSvc;

    private final DateTimeFormatter formatter;

    @Autowired
    public MazeWebController(Environment env) {
        mazeSvc = new MazeServiceImpl();
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
        int height = mazeSvc.DEFAULT_HEIGHT;
        int width = mazeSvc.DEFAULT_WIDTH;

        if (mazeSvc.getAll().size() == 0) {
            direction = "list";
        } else {
            model.addAttribute("height", height);
            model.addAttribute("width", width);
        }

        return direction;
    }

    @GetMapping(value = {"/generate"})
    public String showGenerateMaze(Model model) {

        model.addAttribute("height", mazeSvc.DEFAULT_HEIGHT);
        model.addAttribute("width", mazeSvc.DEFAULT_WIDTH);

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
