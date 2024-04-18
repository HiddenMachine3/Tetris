package Game.IO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class Window extends JFrame {

    UI ui;

    public Window(int fieldW, int fieldH, int upperLimit, Canvas canvas) {
        setSize(fieldW * 40 + 15, (fieldH - upperLimit) * 40 + 40);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ui = new UI(canvas, new Dimension(fieldW * 40, (fieldH - upperLimit) * 40));
        ui.endScreen.setVisible(false);
        add(ui.panel);
    }

    public void setScoresTable(String[] names, int[] scores) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Name");
        model.addColumn("Score");

        for (int i = 0; i < names.length; i++) {
            model.addRow(new Object[]{names[i], scores[i]});
        }

        ui.scoresTable.setModel(model);
    }

    public String getEnteredName() {
        return ui.getEnteredName();
    }

    public void updateScore(int new_score) {
        ui.Score.setText("Score : " + new_score);
    }

    public void showDead() {
        ui.endScreen.setVisible(true);
    }

    public boolean canPlayAgain() {
        return ui.playAgainFlag;
    }

    public void playAgain() {
        ui.endScreen.setVisible(false);
        ui.playAgainFlag = false;
    }

    public void setOnSubmitNameAction(ActionListener action) {
        ui.setOnSubmitNameAction(action);
    }
}
