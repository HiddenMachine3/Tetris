package Game.GameMechanics;

public class Block {

    public BlockType type;
    public int color;

    public Block(BlockType type, int color) {
        this.type = type;
        this.color = color;
    }

    public void setRGB(Shape another) {
        color = another.color;
    }

    public void setRGB(int color) {
        this.color = color;
    }

    public Block copy() {
        return new Block (type, color);
    }
}
