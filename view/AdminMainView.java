package view;

import javax.swing.*;
import java.awt.event.ActionListener;

public class AdminMainView extends JFrame{
    private JPanel panel1;
    private JButton EditButton;
    private JButton EditButton1;

    public AdminMainView() {
        setDimension(600,300);
    }

    public void setDimension(int w, int h) {
        add(panel1);
        setBounds(300, 200, w, h);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void addVerificaButton1(ActionListener listener){this.EditButton.addActionListener(listener);}
    public void addVerificaButton2(ActionListener listener){this.EditButton1.addActionListener(listener);}
}
