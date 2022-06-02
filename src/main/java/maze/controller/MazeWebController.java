package maze.controller;

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
        model.addAttribute("datetime", getDateFormatted(LocalDateTime.now()));
        model.addAttribute("appname", appname);
        model.addAttribute("username", "Mikola");

        return "index";
    }
}
