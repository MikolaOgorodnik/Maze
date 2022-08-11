package maze.controller;

import maze.service.MazeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Controller
public class MazeWebController {
    private final Logger logger = LoggerFactory.getLogger(MazeWebController.class);

    private final String redirectToIndex = "redirect:/";

    private Optional<Integer> currentMaze = Optional.empty();

    @Autowired
    private MazeService mazeSvc;

    private final DateTimeFormatter formatter;

    @Autowired
    public MazeWebController(Environment env) {
        String dateFormatter = env.getProperty("localdatetime.format", "dd.MM.yyyy HH:mm:ss");
        formatter = DateTimeFormatter.ofPattern(dateFormatter);
    }

    public String getDateFormatted(LocalDateTime date) {
        return date.format(formatter);
    }

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        logger.info("Root mapping started.");

        String direction = "index";

        if (currentMaze.isPresent()) {
            model.addAttribute("grid", mazeSvc.getGrid(currentMaze.get()));
        } else if (!mazeSvc.getAll().isEmpty()) {
            model.addAttribute("mazesList", mazeSvc.getAll());
            direction = "mazes";
        }

        return direction;
    }

    @GetMapping("/index/{mazeId}")
    public String showMaze(@PathVariable int mazeId, Model model) {
        model.addAttribute("grid", mazeSvc.getGrid(mazeId));
        currentMaze = Optional.of(mazeId);
        return "index";
    }

    @GetMapping(value = "/mazes")
    public String showMazesList(Model model) {
        model.addAttribute("mazesList", mazeSvc.getAll());

        return "mazes";
    }

    @GetMapping(value = "/generate")
    public String showGenerateMaze(Model model) {

        model.addAttribute("height", MazeService.DEFAULT_HEIGHT);
        model.addAttribute("width", MazeService.DEFAULT_WIDTH);

        return "generate";
    }

    @PostMapping(value = "/generate")
    public String generateMaze(Model model, GenerationResponse generationResponse) {
        model.addAttribute("generationResponse", generationResponse);
        mazeSvc.generate(generationResponse.getHeight(), generationResponse.getWidth());

        model.addAttribute("mazesList", mazeSvc.getAll());

        return "mazes";
    }

    @GetMapping(value = "/solve")
    public String solveMaze(Model model) {
        if (currentMaze.isPresent()) {
            mazeSvc.solve(currentMaze.get());
            model.addAttribute("grid", mazeSvc.getGrid(currentMaze.get()));
        }
        return redirectToIndex;
    }

}
