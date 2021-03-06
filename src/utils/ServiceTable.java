package utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceTable {

    public static void refreshTable(JTable table, int limit){
        DBHandler.openConnection();
        ResultSet resultSet;
        if (limit>0)
            resultSet = DBHandler.execQuery("SELECT * FROM product LIMIT "+ limit);
        else
            resultSet = DBHandler.execQuery("SELECT * FROM product");
        setTable(resultSet,table);
    }
    public static void refreshTable(JTable table, int start, int end){
        DBHandler.openConnection();
        ResultSet resultSet;

        resultSet = DBHandler.execQuery("SELECT * FROM product WHERE ID BETWEEN "+ start + " AND "+ end);

        setTable(resultSet, table);
    }
    private static void setTable(ResultSet resultSet, JTable table){

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID товара","Название товара", "Стоимость реализации", "Остатки","Штрихкод", "НДС", "Категория"});

        try {
            while (resultSet.next()){
                model.addRow(new String[]{
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(6),
                        resultSet.getString(4),
                        resultSet.getString(7),
                        resultSet.getString(5),

                });
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        table.setModel(model);
        DBHandler.closeConnection();
    }
    public static int numberOfRows(){
        DBHandler.openConnection();
        ResultSet resultSet;
        int rows = 0;
        resultSet = DBHandler.execQuery("SELECT * FROM product");
        try {
            while (resultSet.next()){
                rows++;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        DBHandler.closeConnection();

        return rows;
    }
    public static boolean checkUpdateConditions(JTable table,String title, double cost, int duration, int checkupd){
        boolean check = true;

        if(title.length()<1 || title.length()>100){
            check = false;
            JOptionPane.showMessageDialog(
                    null,
                    "Название услуги должно содержать от 1 до 100 символов",
                    "Внимание",
                    JOptionPane.WARNING_MESSAGE
            );

        }

        if(cost<0 || duration<0 ){
            check = false;
            JOptionPane.showMessageDialog(
                    null,
                    "Значения в полях 'Стоимость' должны быть положительными",
                    "Внимание",
                    JOptionPane.WARNING_MESSAGE
            );
        }

        for(int count =0; count<table.getRowCount(); count++){

            if(table.getValueAt(count, 1).toString().equals(title)){
                if (checkupd == count){
                    continue;
                }
                    check=false;
                JOptionPane.showMessageDialog(
                        null,
                        "Название услуги должно быть уникальным",
                        "Внимание",
                        JOptionPane.WARNING_MESSAGE
                );
                break;
            }
        }



        return check;
    }
}
