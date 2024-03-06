package controller;

import view.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainController extends ClasaAbstracta implements Interfata {
    private MainView mainView;
    private UserMainView userMainView;
    private String nume;
    private String password;
    private Integer pid;
    private Integer isAdmin;
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public MainController(MainView mainView) {
        this.mainView = mainView;
        mainView.addVerificaButton(new Verifica());
        mainView.addVerificaButton2(new Verifica2());
        afiseazaBuna();
    }

    @Override
    public void afiseazaBuna() {
        JOptionPane.showMessageDialog(mainView, "Developed by Maris Radu-Ioan");
    }

    class Verifica implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            nume = mainView.getTextField1().getText();
            password = mainView.getPasswordField1().getText();
            System.out.println(nume + " || " + password + ">");
            try {
                Class.forName("org.postgresql.Driver");
                connect = DriverManager
                        .getConnection("jdbc:postgresql://localhost:5432/Proiect", "postgres", "sql");
                statement = connect.createStatement();

                String sql = "select * from public.person where name=? and person.password=?";

                preparedStatement = connect.prepareStatement(sql);
                preparedStatement.setString(1, nume);
                preparedStatement.setString(2,password);
                resultSet = preparedStatement.executeQuery();
                //writeResultSet(resultSet);
                String indexNume = null;
                String indexParola = null;
                Integer indexPid = 0;
                Integer indexAdmin = 0;
                while(resultSet.next()) {
                    indexNume = resultSet.getString("name");
                    indexParola = resultSet.getString("password");
                    indexPid = resultSet.getInt("pid");
                    indexAdmin = resultSet.getInt("isAdmin");
                }
                if(nume.isEmpty() || password.isEmpty()){
                    JOptionPane.showMessageDialog(mainView, "unul dintre campuri nu este completat!");
                }
                else {
                    if (nume.equals(indexNume) && password.equals(indexParola)) {
                       if(indexAdmin==0){
                        UserMainView userMainView = new UserMainView();
                        userMainView.setVisible(true);
                        mainView.setVisible(false);
                        UserController userController = new UserController(userMainView,nume,indexPid);
                       }
                       else{
                           AdminMainView adminMainView = new AdminMainView();
                           adminMainView.setVisible(true);
                           mainView.setVisible(false);
                           AdminDecisionController adminDecisionController = new AdminDecisionController(adminMainView);
                       }
                    } else {
                        System.out.println("Nume sau parola incorecta");
                        JOptionPane.showMessageDialog(mainView, "Nume sau parola incorecta!");
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
                SignInView signInView = new SignInView();
                signInView.setVisible(true);
                mainView.setVisible(false);
                SignInController signInController = new SignInController(signInView);
            }
            catch(Exception event){
                event.printStackTrace();
                System.out.println("Erroare!");
            }
        }
    }

    private void writeResultSet(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            String pid = resultSet.getString("pid");
            String name = resultSet.getString("name");
            String password = resultSet.getString("password");
            String isAdmin = resultSet.getString("isadmin");
            System.out.println("player id: " + pid);
            System.out.println("name: " + name);
            System.out.println("password: " + password);
            System.out.println("isAdmin: " + isAdmin);
        }
    }

    public String getName() {
        return nume;
    }

    public String getPassword() {
        return password;
    }
}
