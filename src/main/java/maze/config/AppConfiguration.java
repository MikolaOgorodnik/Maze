package maze.config;

import maze.service.MazeService;
import maze.service.MazeServiceImpl;
import org.springframework.context.annotation.Bean;

public class AppConfiguration {

    @Bean
    public MazeService getSvc() {
        return new MazeServiceImpl();
    }
}
