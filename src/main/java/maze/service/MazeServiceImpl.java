package maze.service;

import maze.model.Cell;
import maze.model.Maze;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;

@Service
public class MazeServiceImpl implements MazeService {

    private final Logger logger = LoggerFactory.getLogger(MazeServiceImpl.class);
    private final List<CompletableFuture<Maze>> mazes = new CopyOnWriteArrayList<>();

    @Override
    @Async
    public void generate(int height, int width) {
        mazes.add(CompletableFuture.completedFuture(new Maze(height, width)));
    }

    @Override
    @Async
    public void solve(int mazeId) {
        var futureMaze = mazes.get(mazeId);

        try {
            var maze = !futureMaze.isDone() ? futureMaze.get(2, TimeUnit.SECONDS) : futureMaze.get();
            maze.solveMaze();
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            logger.error("Error while solving Maze {}: {}", mazeId, e);
        }

    }

    public Cell[][] getGrid(int mazeId) {
        var futureMaze = mazes.get(mazeId);
        Cell[][] result = new Cell[0][0];

        try {
            result = !futureMaze.isDone() ? futureMaze.get(2, TimeUnit.SECONDS).getGrid() : futureMaze.get().getGrid();
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            logger.error("Error while solving Maze {}: {}", mazeId, e);
        }

        return result;
    }




    @Override
    public List<CompletableFuture<Maze>> getAll() {
        return mazes;
    }
}
