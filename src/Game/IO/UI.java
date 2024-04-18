package Game.IO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.TableColumn; // Add missing import statement

public class UI {
    public JPanel panel;
    private Canvas canvas1;
    public JLabel Score;
    public JButton playAgain;
    private JTextField enterYourNameHereTextField;
    private JButton saveScoreButton;
    public JPanel endScreen;
    public JTable scoresTable;
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
        enterYourNameHereTextField.addKeyListener(new java.awt.event.KeyAdapter() {
           public void keyPressed(java.awt.event.KeyEvent evt) {
               if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                   saveScoreButton.doClick();
               }
           }
        });
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Name");
        model.addColumn("Score");
        scoresTable.setModel(model);
        
        scoresTable.getTableHeader().setVisible(true);
        

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
