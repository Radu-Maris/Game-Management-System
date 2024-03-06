package view;

import javax.swing.*;
import java.awt.event.ActionListener;

public class GamesView extends JFrame{
    private JPanel panel1;
    private JTable table1;
    private JTextArea textArea1;
    private JTextPane textPane1;
    private JButton InsertButton;

    public JTable getTable1() {
        return table1;
    }

    public JTextArea getTextArea1() {
        return textArea1;
    }

    public JTextPane getTextPane1() {
        return textPane1;
    }

    public JButton getINSERTButton() {
        return InsertButton;
    }

    public GamesView() {
        setDimension(600,300);
    }

    public void setDimension(int w, int h) {
        add(panel1);
        setBounds(300, 200, w, h);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void addVerificaButton1(ActionListener listener){this.InsertButton.addActionListener(listener);}
}
