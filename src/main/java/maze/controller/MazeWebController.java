package maze.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Date;

@EnableWebMvc
@Controller
@RequestMapping({ "/", "/index" })
public class MazeWebController {
    private String appMode;

    @Autowired
    public MazeWebController(Environment environment){
        appMode = environment.getProperty("app-mode");
    }


    @GetMapping
    public String index(Model model){
        model.addAttribute("datetime", new Date());
        model.addAttribute("username", "Mikola");
        model.addAttribute("mode", appMode);

        return "index";
    }
}
