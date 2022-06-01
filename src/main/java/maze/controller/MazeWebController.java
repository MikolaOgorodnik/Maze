package maze.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
@RequestMapping({ "/", "/index" })
public class MazeWebController {
    private final Logger logger = LoggerFactory.getLogger(MazeWebController.class);

    private String appMode;

    @Autowired
    public MazeWebController(Environment environment){
        appMode = environment.getProperty("app-mode");
    }

    @GetMapping
    public String index(Model model){
        logger.info("Root mapping started.");
        model.addAttribute("datetime", new Date());
        model.addAttribute("appName", "Maze");
        model.addAttribute("username", "Mikola");
        model.addAttribute("mode", appMode);

        return "index";
    }
}
