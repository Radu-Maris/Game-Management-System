package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView extends JFrame{
    private JTextField textField1;
    private JTextField passwordField1;
    private JButton loginButton;
    private JPanel basePanel;
    private JButton signUpButton;

    public JTextField getTextField1() {
        return textField1;
    }

    public JTextField getPasswordField1() {
        return passwordField1;
    }

    public void setDimension(int w, int h) {
        add(basePanel);
        setBounds(300, 200, w, h);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    public MainView() {

        setDimension(600,300);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("in view: " + textField1.getText());
            }
        });


    }
    public void addVerificaButton(ActionListener listener){this.loginButton.addActionListener(listener);}
    public void addVerificaButton2(ActionListener listener){
        this.signUpButton.addActionListener(listener);
    }
}
