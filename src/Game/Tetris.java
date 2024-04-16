package Game;

import Game.GameMechanics.*;
import Game.IO.Canvas;
import Game.IO.Window;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static Game.GameMechanics.Shape.shapes;

public class Tetris implements Runnable {

    private Thread game;
    final int width = 10, height = 20;
    volatile Block[][] playField;
    Shape current_piece;
    int upperLimit = 3;
    boolean dead = false;
    long stepSize = 500;
    int score;
    Window window;
    Canvas canvas;

    public void start() {
        game = new Thread (this, "tetris");
        game.start ();
    }

    @Override
    public void run() {
        initialize ();
        mainUILoop ();
    }

    private void mainUILoop() {
        while (true) {
            score = 0;
            mainGameLoop ();
            died ();

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    playField[i][j] = new Block (BlockType.EMPTY, 0);
                }
            }
        }
    }

    private void mainGameLoop() {
        while (!dead) {
            update ();

            renderScreen ();

            delay ();
        }
    }

    private void died() {
        window.showDead ();
        while (dead) {
            if (window.canPlayAgain ()) {

                score = 0;
                dead = false;
                window.playAgain ();
                break;
            }
            Thread.yield ();
        }

    }

    private void initialize() {
        current_piece = shapes[(int) (Math.random () * shapes.length)];
        playField = new Block[width][height];
        canvas = new Canvas (playField, width, height, upperLimit);
        window = new Window (width, height, upperLimit, canvas);



        window.setOnSubmitNameAction((onSubmitAction)->{
            String name = window.getEnteredName();
            System.out.println(name+" "+score);
        });

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                playField[i][j] = new Block (BlockType.EMPTY, 0);
            }
        }
        KeyAdapter movement = new KeyAdapter () {
            int horizontal_movement = 0;
            int vertical_movement = 0;

            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode ();

                if (keyCode == KeyEvent.VK_SPACE) {//Rotation
                    position[] rotated_positions = current_piece.getRotatedVersion (1);
                    if (isMovable (rotated_positions)) {
                        clearPositions ();
                        current_piece.rotateCW (1);
                        placePiece ();
                        renderScreen ();
                    }
                }

                if (keyCode == KeyEvent.VK_RIGHT)//Horizontal Movement
                    horizontal_movement = 1;
                if (keyCode == KeyEvent.VK_LEFT)
                    horizontal_movement = -1;
                if (keyCode == KeyEvent.VK_DOWN)//Move Down
                    vertical_movement = 1;

                if (horizontal_movement != 0 || vertical_movement != 0) {
                    if (isMovable (horizontal_movement, vertical_movement)) {
                        move (horizontal_movement, vertical_movement);
                        renderScreen ();
                    }
                    horizontal_movement = 0;
                    vertical_movement = 0;
                }
            }
        };
        window.addKeyListener (movement);
        window.updateScore (score);
        window.setVisible (true);
    }

    private void update() {

        if (isMovable (0, 1)) {
            move (0, 1);/*Moving piece downwards*/
        } else {
            /*if the piece is unable to move,then, a background piece is created in its place*/
            for (position blockPosition : current_piece.blockPositions) {
                position pos = position.add (current_piece.pos, blockPosition);

                playField[pos.x][pos.y].type = BlockType.OBSTACLE;
                if (pos.y <= upperLimit)
                    dead = true;
            }

            current_piece = shapes[(int) (Math.random () * shapes.length)];
            current_piece.pos.x = 2;
            current_piece.pos.y = 2;
        }

        checkIfLineCompleted ();
    }

    private void renderScreen() {
        canvas.repaint ();
    }

    private void move(int incX, int incY) {
        /*If piece is able to move, we cleanup its previous position on the field , and place the piece in the new position*/
        clearPositions ();                                              /*Clearing the previous position of the object*/

        current_piece.pos.y += incY;                                         /*incrementing the position of the object*/
        current_piece.pos.x += incX;

        placePiece ();

    }

    private boolean isMovable(int incX, int incY) {
        position new_pos = new position (current_piece.pos.x + incX, current_piece.pos.y + incY);

        for (position block_pos : current_piece.blockPositions) {
            int new_x = block_pos.x + new_pos.x;
            int new_y = block_pos.y + new_pos.y;
            if (new_x < 0 || new_x >= width || new_y < 0 || new_y >= height) {
                return false;
            } else if (playField[new_x][new_y].type == BlockType.OBSTACLE) {
                return false;
            }
        }
        return true;
    }

    private boolean isMovable(position[] new_BlockPositions) {
        for (position new_block_pos : new_BlockPositions) {
            int new_x = new_block_pos.x + current_piece.pos.x;
            int new_y = new_block_pos.y + current_piece.pos.y;
            if (new_x < 0 || new_x >= width || new_y < 0 || new_y >= height) {
                return false;
            } else if (playField[new_x][new_y].type == BlockType.OBSTACLE) {
                return false;
            }
        }
        return true;
    }

    private void clearPositions() {
        for (int i = 0; i < current_piece.blockPositions.length; i++) {
            position pos = position.add (current_piece.pos, current_piece.blockPositions[i]);
            playField[pos.x][pos.y].type = BlockType.EMPTY;
        }
    }

    private void placePiece() {
        for (int i = 0; i < current_piece.blockPositions.length; i++) {                  /*placing the object in the playField*/
            position pos = position.add (current_piece.pos, current_piece.blockPositions[i]);
            playField[pos.x][pos.y].type = BlockType.MOVABLE;
            playField[pos.x][pos.y].setRGB (current_piece);
        }
    }

    void checkIfLineCompleted() {
        ArrayList<Integer> rowNumbers = new ArrayList<> ();
        for (int y = height - 1; y > 0; y--) {
            boolean isLineFilled = true;
            for (int x = 0; x < width; x++) {
                if (playField[x][y].type != BlockType.OBSTACLE) {
                    isLineFilled = false;
                    break;
                }
            }
            if (isLineFilled) {
                rowNumbers.add (y);
            }
        }

        int numberOfRows = rowNumbers.size ();

        if (numberOfRows > 0) {
            score += numberOfRows * 10;
            window.updateScore (score);

            destroyedAnimation (rowNumbers);

            //Removing the needed blocks
            for (int y : rowNumbers)
                for (int x = 0; x < width; x++)
                    playField[x][y].type = BlockType.EMPTY;

            renderScreen ();

            //moving the blocks down after the needed blocks are destroyed
            moveDown (rowNumbers.get (0), numberOfRows);
        }
    }


    void moveDown(int rowEnd, int numberOfRows) {
        for (int i = 0; i < numberOfRows; i++, delay (200L)) {
            for (int y = rowEnd; y > 0; y--) {

                boolean isCurrentRowFilled = false;

                for (int x = 0; x < width; x++) {
                    if (playField[x][y].type != BlockType.MOVABLE) {
                        playField[x][y].type = playField[x][y - 1].type;
                        playField[x][y].setRGB (playField[x][y - 1].color);
                        playField[x][y - 1].type = BlockType.EMPTY;
                    }
                }
                for (int x = 0; x < width; x++) {
                    if (playField[x][y].type == BlockType.OBSTACLE) {
                        isCurrentRowFilled = true;
                        break;
                    }
                }

                if (y == rowEnd && isCurrentRowFilled)
                    rowEnd--;
            }
        }
    }

    long goal = 0L;

    void delay() {
        while (System.currentTimeMillis () <= goal)
            Thread.yield ();
        goal = System.currentTimeMillis () + stepSize;
    }

    void delay(long customDelaySize) {
        long goal = System.currentTimeMillis () + customDelaySize;
        while (System.currentTimeMillis () <= goal)
            Thread.yield ();
    }

    void destroyedAnimation(ArrayList<Integer> rowNumbers) {
        for (int y : rowNumbers)
            for (int x = 0; x < width; x++)
                playField[x][y].type = BlockType.DESTROYED;

        renderScreen ();

        delay (100);
        for (int y : rowNumbers)
            for (int x = 0; x < width; x++)
                playField[x][y].type = BlockType.EMPTY;

        renderScreen ();

        delay (100);
        for (int y : rowNumbers)
            for (int x = 0; x < width; x++)
                playField[x][y].type = BlockType.DESTROYED;

        renderScreen ();
        delay (100);
    }

}
