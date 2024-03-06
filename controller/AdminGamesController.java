package controller;
import view.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class AdminGamesController {
    private GamesView gamesView;
    private String nume;
    private Integer an;
    //private Integer pid;
    private Integer indexAdmin = 0;
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    JTable table1;
    private String data[][] = new String[100][100];
    private String column[]={"GAME_ID","GAME_NAME","RELEASE_YEAR"};
    public AdminGamesController(GamesView gamesView) {
        this.gamesView = gamesView;
        System.out.println("mere");



        try{
            Class.forName("org.postgresql.Driver");
            connect = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/Proiect","postgres", "sql");
            statement = connect.createStatement();
            String sql = "select * from public.games order by games.gid";

            preparedStatement = connect.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            int i=0;
            while (resultSet.next()) {
                String gname = resultSet.getString("gname");
                Integer year = resultSet.getInt("grelaseyear");
                Integer gid = resultSet.getInt("gid");
                data[i][1] = gname;
                data[i][2] = String.valueOf(year);
                data[i][0] = String.valueOf(gid);
                i++;
            }
        }catch(Exception event){
            event.printStackTrace();
            System.out.println("Erroare!");
        }

        table1 = gamesView.getTable1();
        table1.setModel(new DefaultTableModel(
                data,
                column
        ));
        gamesView.addVerificaButton1(new Verifica());
    }

    class Verifica implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            nume = gamesView.getTextArea1().getText();
            an = Integer.valueOf(gamesView.getTextPane1().getText());
            try {
                Class.forName("org.postgresql.Driver");
                connect = DriverManager
                        .getConnection("jdbc:postgresql://localhost:5432/Proiect","postgres", "sql");
                statement = connect.createStatement();

                String sql = "select * from public.games where gname=?";

                preparedStatement = connect.prepareStatement(sql);
                preparedStatement.setString(1, nume);
                resultSet = preparedStatement.executeQuery();

                String indexNume1 = null;
                while(resultSet.next()) {
                    indexNume1 = resultSet.getString("gname");
                    indexAdmin = resultSet.getInt("grelaseyear");
                }
                if(nume.isEmpty()){
                    JOptionPane.showMessageDialog(gamesView, "campul nume nu este completat!");
                }
                else {
                    if (nume.equals(indexNume1)) {
                        JOptionPane.showMessageDialog(gamesView, "Exista deja!");
                    }
                    else {
                        if(an>2022 || an<1900){
                            JOptionPane.showMessageDialog(gamesView, "Nu se poate introduce acest an!");
                        }
                        else {
                            preparedStatement = connect.prepareStatement("insert into public.games (gname,grelaseyear)values(?,?)");
                            preparedStatement.setString(1, nume);
                            preparedStatement.setInt(2, an);
                            preparedStatement.executeUpdate();

                            sql = "select * from public.games order by games.gid";
                            preparedStatement = connect.prepareStatement(sql);
                            resultSet = preparedStatement.executeQuery();
                            int i = 0;
                            while (resultSet.next()) {
                                String gname = resultSet.getString("gname");
                                Integer year = resultSet.getInt("grelaseyear");
                                Integer gid = resultSet.getInt("gid");
                                data[i][1] = gname;
                                data[i][2] = String.valueOf(year);
                                data[i][0] = String.valueOf(gid);
                                i++;
                            }
                        }
                }
                }
            }
            catch(Exception event){
                event.printStackTrace();
                System.out.println("Erroare!");
            }
            table1 = gamesView.getTable1();
            table1.setModel(new DefaultTableModel(
                    data,
                    column
            ));
        }
    }
}

