package maze.controller;

import maze.model.Maze;
import maze.model.MazeImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping({ "/", "/index" })
public class MazeWebController {
    private final Logger logger = LoggerFactory.getLogger(MazeWebController.class);

    private Maze maze;

    private final String appname;

    private final DateTimeFormatter formatter;

    @Autowired
    public MazeWebController(Environment env){
        appname = env.getProperty("spring.application.name", "Maze");
        String dateFormatter = env.getProperty("localdatetime.format", "dd.MM.yyyy HH:mm:ss");
        formatter = DateTimeFormatter.ofPattern(dateFormatter);

    }

    public String getDateFormatted(LocalDateTime date) {
        return date.format(formatter);
    }

    @GetMapping
    public String index(Model model){
        logger.info("Root mapping started.");
        maze = new MazeImpl(15, 15);
        model.addAttribute("datetime", getDateFormatted(LocalDateTime.now()));
        model.addAttribute("appname", appname);
        model.addAttribute("username", "Mikola");
        if (maze.isMazeExists()) {
            model.addAttribute("isMazeExists", maze.isMazeExists());
            model.addAttribute("maze", maze.toString());
        }
        return "index";
    }

    @GetMapping("/generate")
    public String generate(Model model){
        logger.info("Generate mapping started.");
        model.addAttribute("appname", appname);
        return "generate";
    }
}
