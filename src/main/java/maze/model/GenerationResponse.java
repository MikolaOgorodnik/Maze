package maze.model;

import java.io.Serial;
import java.io.Serializable;

public class GenerationResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 2L;

    private int y;
    private int x;

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public String toString() {
        return "GenerationResponse{" +
                "y=" + y +
                ", x=" + x +
                '}';
    }
}
