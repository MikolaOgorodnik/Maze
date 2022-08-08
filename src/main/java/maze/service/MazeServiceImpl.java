package maze.service;

import maze.model.Maze;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class MazeServiceImpl implements MazeService {
    public static final int DEFAULT_HEIGHT = 15;

    public static final int DEFAULT_WIDTH = 15;
    List<Maze> mazes;

    @Override
    @Async
    public CompletableFuture<Maze> generate() {
        return null;
    }

    @Override
    @Async
    public void solve() {

    }

    @Override
    public List<Maze> getAll() {
        return mazes;
    }
}
