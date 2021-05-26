package forms;

import utils.DBHandler;
import utils.FormConfig;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ArrivalsCheckForm extends JFrame{
    private JPanel mainPanel;
    private JTable arrivalTable;

    ArrivalsCheckForm(){
        setContentPane(mainPanel);
        FormConfig.setParams(
                this,
                "Приходы",
                1200,
                600,
                WindowConstants.DISPOSE_ON_CLOSE);

        DBHandler.openConnection();
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Товар", "Цена за штуку (реализация)","Цена закупки", "Прибыль за штуку", "Колличество","Дата поставки"});
        ResultSet resultSet = DBHandler.execQuery("SELECT *, Price-BuyPrice FROM arrival ORDER BY DateArrival");

        try {
            while (resultSet.next()){
                model.addRow(new String[]{
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(4),
                        resultSet.getString(5),

                });
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        arrivalTable.setModel(model);
        DBHandler.closeConnection();

    }
}
