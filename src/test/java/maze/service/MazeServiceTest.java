package maze.service;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Random;
import java.util.concurrent.Executor;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@EnableAsync
class MazeServiceTest {

    private final Logger logger = LoggerFactory.getLogger(MazeServiceTest.class);

    private final Random rnd = new Random();

    @Autowired
    private MazeService svc;

    @Autowired
    private Executor asyncExecutor;

    @Test
    @Order(2)
    void performanceTest() {
        int rndMin = 15;
        int rndMax = 55;


        for (int i = 0; i < 1_000_000/*Integer.MAX_VALUE*/; i++) {
            svc.generate(
                    rnd.nextInt(rndMax - rndMin) + rndMin,
                    rnd.nextInt(rndMax - rndMin) + rndMin
            );
        }

        for (int i = 0; i < svc.getAll().size(); i++) {
            var future = svc.getAll().get(i);
            if (future.isDone()) {
                svc.solve(i);
            } else {
                try {
                    Thread.sleep(2_000);
                    svc.solve(i);
                } catch (InterruptedException e) {
                    logger.error("Error while solving Maze {}: {}", i, e);
                }
            }
        }

        try {
            Thread.sleep(180_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        logger.info("List size: {}", svc.getAll().size());
    }

}