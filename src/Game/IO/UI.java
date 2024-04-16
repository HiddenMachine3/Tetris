package Game.IO;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UI {
    public JPanel panel;
    private Canvas canvas1;
    public JLabel Score;
    public JButton playAgain;
    private JTextField enterYourNameHereTextField;
    private JButton saveScoreButton;
    boolean playAgainFlag = false;

    public UI(Canvas canvas, Dimension canvas_dimension) {
        this.canvas1 = canvas;
        canvas1.setPreferredSize (canvas_dimension);
        Score.setText ("Score : " + 0);
        playAgain.addActionListener (new ActionListener () {
            @Override
            public void actionPerformed(ActionEvent e) {
                playAgainFlag = true;
            }
        });
    }

    public String getEnteredName(){
        return enterYourNameHereTextField.getText();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public void setOnSubmitNameAction(ActionListener action) {
        saveScoreButton.addActionListener(action);
    }
}
