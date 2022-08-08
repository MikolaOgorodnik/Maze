package maze.service;

import maze.model.Maze;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface MazeService {

    CompletableFuture<Maze> generate();

    void solve();

    List<Maze> getAll();

}
