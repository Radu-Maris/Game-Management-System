package view;

import javax.swing.*;
import java.awt.event.ActionListener;

public class SignInView extends JFrame{
    private JPanel panelSignIn;
    private JTextField textField1;
    private JTextField textField2;
    private JButton signInNowButton;
    private JButton goBackButton;

    public JTextField getTextField1() {
        return textField1;
    }

    public JTextField getTextField2() {
        return textField2;
    }

    public JButton getGoBackButton() {
        return goBackButton;
    }

    public void setDimension(int w, int h) {
        add(panelSignIn);
        setBounds(300, 200, w, h);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    public SignInView(){
        setDimension(600,300);
    }

    public void addVerificaButton(ActionListener listener){this.signInNowButton.addActionListener(listener);}
    public void addVerificaButton2(ActionListener listener){this.goBackButton.addActionListener(listener);}
}
