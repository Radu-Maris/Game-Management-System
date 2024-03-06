package controller;

import view.UserMainView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class UserController {
    private UserMainView userMainView;
    private MainController mainController;
    private String nume;
    private String numeleUser;
    private Integer gid;
    private Integer pid;
    private Integer nrDeOre;

    private String data[][] = new String[100][100];
    private String column[]={"NAME","GAME","HOURS"};
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    JTable table1;


    JComboBox cb;
    public UserController (UserMainView userMainView,String numeleGlobal,Integer indexPid) throws Exception{
        this.userMainView = userMainView;
        System.out.println("mere");
        pid = indexPid;
        numeleUser = numeleGlobal;
        cb = userMainView.getComboBox1();
        String[] gameNameIndex = new String[100];
        String[] gid = new String[100];
        try{
            Class.forName("org.postgresql.Driver");
            connect = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/Proiect","postgres", "sql");
            statement = connect.createStatement();
            String sql = "select * from public.games";
            preparedStatement = connect.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            int i=0;
            while (resultSet.next()) {
                gameNameIndex[i] = resultSet.getString("gname");
                gid[i] = resultSet.getString("gid");
                i++;
            }
        }
        catch(Exception e) {
            throw e;
        }
        cb.setModel(new DefaultComboBoxModel(gameNameIndex));

        userMainView.addVerificaButton(new Verifica());

        nume = numeleGlobal;

        try{
            Class.forName("org.postgresql.Driver");
            connect = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/Proiect","postgres", "sql");
            statement = connect.createStatement();
            String sql = "select * from public.person join time t on person.pid = t.pid join games g on g.gid = t.gid where person.name=?";


            preparedStatement = connect.prepareStatement(sql);
            preparedStatement.setString(1, numeleGlobal);
            resultSet = preparedStatement.executeQuery();
            int i=0;
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String gameName = resultSet.getString("gname");
                String nrOfHours = resultSet.getString("hoursplayed");
                data[i][0] = name;
                data[i][1] = gameName;
                data[i][2] = nrOfHours;
                i++;
            }
        } catch (Exception e) {
            throw e;
        }

        table1 = userMainView.getTable1();
        table1.setModel(new DefaultTableModel(
                data,
                column
        ));
    }

    class Verifica implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (userMainView.getTextField1().isValid()){
                    nrDeOre = Integer.valueOf(userMainView.getTextField1().getText());
                }
                String sql = "select * from public.games where games.gname=?";

                preparedStatement = connect.prepareStatement(sql);
                preparedStatement.setString(1, String.valueOf(cb.getSelectedItem()));
                resultSet = preparedStatement.executeQuery();
                if(resultSet.next()) {
                    gid = resultSet.getInt("gid");
                }
                sql = "select * from public.person join time t on person.pid = t.pid join games g on g.gid = t.gid where person.name=?";

                preparedStatement = connect.prepareStatement(sql);
                preparedStatement.setString(1, nume);
                resultSet = preparedStatement.executeQuery();

                sql = "insert into public.time values(?,?,?)";

                preparedStatement = connect.prepareStatement(sql);
                preparedStatement.setInt(1, pid);
                preparedStatement.setInt(2, gid);
                preparedStatement.setInt(3, nrDeOre);
                preparedStatement.executeUpdate();

                sql = "select * from public.person join time t on person.pid = t.pid join games g on g.gid = t.gid where person.name=?";

                preparedStatement = connect.prepareStatement(sql);
                preparedStatement.setString(1, nume);
                resultSet = preparedStatement.executeQuery();
                int i=0;
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String gameName = resultSet.getString("gname");
                    String nrOfHours = resultSet.getString("hoursplayed");
                    data[i][0] = name;
                    data[i][1] = gameName;
                    data[i][2] = nrOfHours;
                    i++;
                }

            }
            catch(Exception event){
                event.printStackTrace();
                System.out.println("Erroare!");
            }
            table1 = userMainView.getTable1();
            table1.setModel(new DefaultTableModel(
                    data,
                    column
            ));
        }
    }


}
