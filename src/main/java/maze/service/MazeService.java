package maze.service;

import maze.model.Cell;
import maze.model.Maze;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface MazeService {
    int DEFAULT_HEIGHT = 15;

    int DEFAULT_WIDTH = 15;

    @Async
    void generate(int height, int width);

    @Async
    void solve(int mazeId);

    Cell[][] getGrid(int mazeId);

    List<CompletableFuture<Maze>> getAll();

}
