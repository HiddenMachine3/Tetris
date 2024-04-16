package Game.IO;

import Game.GameMechanics.Block;
import Game.GameMechanics.BlockType;

import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

public class Canvas extends JComponent {

    Block[][] playField;
    int width, height, upperLimit;

    public Canvas(Block[][] playField, int width, int height, int upperLimit) {
        this.playField = playField;
        this.width = width;
        this.height = height;
        this.upperLimit = upperLimit;
    }

    @Override
    public void paintComponent(Graphics gg) {
        Graphics2D g = (Graphics2D) gg;
        for (int y = upperLimit; y < height; y++) {
            for (int x = 0; x < width; x++) {
                BlockType type = playField[x][y].type;
                if (type != BlockType.EMPTY) {
                    switch (type) {
                        case MOVABLE:
                        case OBSTACLE:
                            g.setColor (new Color (playField[x][y].color));
                            break;
                        case DESTROYED:
                            g.setColor (Color.RED);
                            break;
                    }

                    g.fillRect (x * 40, (y - upperLimit) * 40, 40, 40);
                }
            }
        }
    }

}
