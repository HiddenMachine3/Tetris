package Game.GameMechanics;

import java.awt.*;
import java.util.Random;

public class Shape {
    public position[] blockPositions;
    public position pos = new position (2, 2);

    int color;

    public Shape(position[] blockPositions, float r, float g, float b) {
        this.blockPositions = blockPositions;
        color = new Color (r,g,b).getRGB();
    }

    public position[] getRotatedVersion(int number) {
        position[] new_positions = new position[blockPositions.length];

        for (int i = 0; i < new_positions.length; i++)
            new_positions[i] = blockPositions[i].copy ();


        number = number % 4;
        for (int i = 0; i < number; i++) {
            rotateCW90 (new_positions);
        }

        return new_positions;
    }

    public void rotateCW(int number) {
        number = number % 4;
        for (int i = 0; i < number; i++) {
            rotateCW90 (blockPositions);
        }
    }

    void rotateCW90(position[] blockPositions) {
        for (int i = 0; i < blockPositions.length; i++) {
            int oldX = blockPositions[i].x;
            int oldY = blockPositions[i].y;

            int newX = -oldY;
            int newY = oldX;

            blockPositions[i].x = newX;
            blockPositions[i].y = newY;
        }
    }

    public static Shape[] shapes =
            {
                    make (new int[]{//T block
                            +0, -1,
                            -1, +0,
                            +0, +0,
                            +1, +0
                    }),
                    make (new int[]{//line block
                            +0, -1,
                            +0, +0,
                            +0, +1,
                            +0, +2,
                    }),
                    make (new int[]{//Z block
                            -1, -1,
                            -1, +0,
                            +0, +0,
                            +0, +1,
                    }),
                    make (new int[]{//Z block reverse
                            +1, -1,
                            +1, +0,
                            +0, +0,
                            +0, +1,
                    }),
                    make (new int[]{//square block
                            -1, -1,
                            +0, -1,
                            +0, +0,
                            -1, +0,
                    }),
                    make (new int[]{//L block
                            -1, -1,
                            -1, +0,
                            -1, +1,
                            +0, +1,
                    }),
                    make (new int[]{//L block reverse
                            +0, -1,
                            +0, +0,
                            +0, +1,
                            -1, +1,
                    })};

    static Shape make(int[] positions) {
        position[] piece_pos = new position[positions.length / 2];
        for (int i = 0; i < positions.length - 1; i += 2)
            piece_pos[i / 2] = new position (positions[i], positions[i + 1]);
        Random random = new Random ();
        return new Shape (piece_pos, random.nextFloat (), random.nextFloat (), random.nextFloat ());
    }
}
