package controller;

import view.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class AdminDecisionController {
    private AdminMainView adminMainView;
    private String nume;
    private String password;
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public AdminDecisionController (AdminMainView adminMainView) {
        this.adminMainView = adminMainView;
        System.out.println("mere");
        adminMainView.addVerificaButton1(new Verifica());
        adminMainView.addVerificaButton2(new Verifica2());

    }

    class Verifica implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                AdminEditPerson adminEditPerson = new AdminEditPerson();
                adminEditPerson.setVisible(true);
                adminMainView.setVisible(false);
                AdminController adminController = new AdminController(adminEditPerson);
            }
            catch(Exception exeption){
                throw exeption;
            }
        }
    }

    class Verifica2 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                GamesView gamesView = new GamesView();
                gamesView.setVisible(true);
                adminMainView.setVisible(false);
                AdminGamesController adminGamesController = new AdminGamesController(gamesView);
            }
            catch(Exception event){
                event.printStackTrace();
                System.out.println("Erroare!");
            }
        }
    }
}
