package controller;

import view.MainView;
import view.SignInView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class SignInController {
    private SignInView signInView;
    private String nume;
    private String password;
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public SignInController(SignInView signInView) {
        this.signInView = signInView;
        System.out.println("mere");
        signInView.addVerificaButton(new Verifica());
        signInView.addVerificaButton2(new Verifica2());

    }

    class Verifica implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            nume = signInView.getTextField2().getText();
            password = signInView.getTextField1().getText();
            System.out.println(nume + " || " + password + ">");
            try {

                Class.forName("org.postgresql.Driver");
                connect = DriverManager
                        .getConnection("jdbc:postgresql://localhost:5432/Proiect", "postgres", "sql");
                statement = connect.createStatement();

                String sql = "select * from public.person where name=?";

                preparedStatement = connect.prepareStatement(sql);
                preparedStatement.setString(1, nume);
                resultSet = preparedStatement.executeQuery();

                String indexNume1 = null;
                String indexParola1 = null;
                while(resultSet.next()) {
                    indexNume1 = resultSet.getString("name");
                    indexParola1 = resultSet.getString("password");
                }
                System.out.println(indexNume1 + "<>" + indexParola1);
                if(nume.isEmpty() || password.isEmpty()){
                    JOptionPane.showMessageDialog(signInView, "unul dintre campuri nu este completat!");
                }
                else {
                    if (nume.equals(indexNume1)) {
                        JOptionPane.showMessageDialog(signInView, "Exista cineva cu acest nume deja. Alege alt nume!");
                    } else {
                        preparedStatement = connect
                                .prepareStatement("insert into public.person (name,password,isadmin)values (?, ?, 0 )");

                        preparedStatement.setString(1, nume);
                        preparedStatement.setString(2, password);
                        preparedStatement.executeUpdate();
                        JOptionPane.showMessageDialog(signInView, "Cont creeat!");
                    }
                }
            }
            catch(Exception event){
                event.printStackTrace();
                System.out.println("Erroare!");
            }
        }
    }

    class Verifica2 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                MainView mainView = new MainView();
                mainView.setVisible(true);
                signInView.setVisible(false);
                MainController mainController = new MainController(mainView);
            }
            catch(Exception event){
                event.printStackTrace();
                System.out.println("Erroare!");
            }
        }
    }
}
