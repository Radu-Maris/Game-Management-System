package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;

public class UserMainView extends JFrame{
    private JPanel panel2;
    private JButton INSERTButton;
    private JTable table1;
    private JTextField textField1;
    private JComboBox comboBox1;

    public JButton getINSERTButton() {
        return INSERTButton;
    }

    public JTextField getTextField1() {
        return textField1;
    }

    public JTable getTable1() {
        return table1;
    }

    public JComboBox getComboBox1() {
        return comboBox1;
    }

    public void setTable1(JTable table1) {
        this.table1 = table1;
    }

    public UserMainView() {
        setDimension(600,300);

    }

    public void setDimension(int w, int h) {
        add(panel2);
        setBounds(300, 200, w, h);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void addVerificaButton(ActionListener listener){this.INSERTButton.addActionListener(listener);}

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
