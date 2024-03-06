package view;

import javax.swing.*;
import java.awt.event.ActionListener;

public class AdminEditPerson extends JFrame{
    private JPanel panel1;
    private JTable table1;
    private JButton makeAdminButton;
    private JTextField personNameTextField;

    public JTable getTable1() {
        return table1;
    }

    public JButton getMakeAdminButton() {
        return makeAdminButton;
    }

    public JTextField getPersonNameTextField() {
        return personNameTextField;
    }

    public AdminEditPerson() {
        setDimension(600,300);
    }

    public void setDimension(int w, int h) {
        add(panel1);
        setBounds(300, 200, w, h);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void addVerificaButton1(ActionListener listener){this.makeAdminButton.addActionListener(listener);}
}
