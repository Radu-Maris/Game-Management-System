package controller;

import view.AdminEditPerson;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class AdminController {
    private AdminEditPerson adminEditPerson;
    private String nume;
    private String password;
    private Integer indexAdmin;
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    JTable table1;
    private String data[][] = new String[100][100];
    private String column[]={"PID","NAME","IS_ADMIN"};
    public AdminController(AdminEditPerson adminEditPerson) {
        this.adminEditPerson = adminEditPerson;
        System.out.println("mere");

        adminEditPerson.addVerificaButton1(new Verifica());

        try{
            Class.forName("org.postgresql.Driver");
            connect = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/Proiect","postgres", "sql");
            statement = connect.createStatement();
            String sql = "select * from public.person order by person.pid";

            preparedStatement = connect.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            int i=0;
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                Integer isAdmin = resultSet.getInt("isAdmin");
                Integer pid = resultSet.getInt("pid");
                data[i][1] = name;
                data[i][2] = String.valueOf(isAdmin);
                data[i][0] = String.valueOf(pid);
                i++;
            }
        }catch(Exception event){
            event.printStackTrace();
            System.out.println("Erroare!");
        }

        table1 = adminEditPerson.getTable1();
        table1.setModel(new DefaultTableModel(
                data,
                column
        ));

    }

    class Verifica implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            nume = adminEditPerson.getPersonNameTextField().getText();
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
                while(resultSet.next()) {
                    indexNume1 = resultSet.getString("name");
                    indexAdmin = resultSet.getInt("isAdmin");
                }
                if(nume.isEmpty()){
                    JOptionPane.showMessageDialog(adminEditPerson, "campul nume nu este completat!");
                }
                else {
                    if (nume.equals(indexNume1)) {
                        if(indexAdmin == 0){
                            indexAdmin = 1;
                        }
                        else{
                            indexAdmin = 0;
                        }
                        preparedStatement = connect.prepareStatement("update public.person set isadmin = ? where name = ? ");
                        preparedStatement.setInt(1,indexAdmin);
                        preparedStatement.setString(2,indexNume1);
                        preparedStatement.executeUpdate();

                        sql = "select * from public.person order by person.pid";
                        preparedStatement = connect.prepareStatement(sql);
                        resultSet = preparedStatement.executeQuery();
                        int i=0;
                        while (resultSet.next()) {
                            String name = resultSet.getString("name");
                            Integer isAdmin = resultSet.getInt("isAdmin");
                            Integer pid = resultSet.getInt("pid");
                            data[i][1] = name;
                            data[i][2] = String.valueOf(isAdmin);
                            data[i][0] = String.valueOf(pid);
                            i++;
                        }
                    } else {
                        JOptionPane.showMessageDialog(adminEditPerson, "Nu exista!");
                    }
                }
            }
            catch(Exception event){
                event.printStackTrace();
                System.out.println("Erroare!");
            }
            table1 = adminEditPerson.getTable1();
            table1.setModel(new DefaultTableModel(
                    data,
                    column
            ));
        }
    }
}

