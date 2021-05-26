package forms;

import utils.DBHandler;
import utils.FormConfig;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductSoldForm extends JFrame{
    private JPanel mainPanel;
    private JTable productSoldTable;
    private JButton Month;
    private JButton Day;
    private JButton Year;
    private JButton backButton;

    ProductSoldForm(){
        setContentPane(mainPanel);
        FormConfig.setParams(
                this,
                "Проданные товары",
                1200,
                600,
                WindowConstants.DISPOSE_ON_CLOSE);
        DBHandler.openConnection();
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID Продажи", "Товар", "Количество", "Дата продажи","Цена за штуку","Сумма продажи"});
        ResultSet resultSet = DBHandler.execQuery("SELECT productsale.*, productsale.Price*Quantity FROM productsale ORDER BY DateSale");

        try {
            while (resultSet.next()){
                model.addRow(new String[]{
                        resultSet.getString(1),
                        resultSet.getString(7),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(8),

                });
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        productSoldTable.setModel(model);
        DBHandler.closeConnection();

        Day.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DBHandler.openConnection();
                DefaultTableModel model = new DefaultTableModel();
                model.setColumnIdentifiers(new String[]{"ID Продажи", "Товар", "Количество", "Дата продажи","Цена за штуку","Сумма продажи"});
                ResultSet resultSet = DBHandler.execQuery("SELECT productsale.*, productsale.Price*Quantity FROM productsale ORDER BY DateSale");

                try {
                    while (resultSet.next()){
                        model.addRow(new String[]{
                                resultSet.getString(1),
                                resultSet.getString(7),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getString(6),
                                resultSet.getString(8),

                        });
                    }
                    productSoldTable.setModel(model);
                    DBHandler.closeConnection();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        Month.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DBHandler.openConnection();
                DefaultTableModel model = new DefaultTableModel();
                model.setColumnIdentifiers(new String[]{"ID Продажи", "Товар", "Количество", "Дата продажи","Цена за штуку","Сумма продажи"});
                ResultSet resultSet = DBHandler.execQuery("SELECT productsale.*, productsale.Price*Quantity FROM productsale WHERE MONTH(DateSale) = MONTH(NOW()) AND YEAR(DateSale) = YEAR(NOW()) ORDER BY DateSale");

                try {
                    while (resultSet.next()){
                        model.addRow(new String[]{
                                resultSet.getString(1),
                                resultSet.getString(7),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getString(6),
                                resultSet.getString(8),

                        });
                    }
                    productSoldTable.setModel(model);
                    DBHandler.closeConnection();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        Year.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DBHandler.openConnection();
                DefaultTableModel model = new DefaultTableModel();
                model.setColumnIdentifiers(new String[]{"ID Продажи", "Товар", "Количество", "Дата продажи","Цена за штуку","Сумма продажи"});
                ResultSet resultSet = DBHandler.execQuery("SELECT productsale.*, productsale.Price*Quantity FROM productsale WHERE YEAR(DateSale) = YEAR(NOW()) ORDER BY DateSale");

                try {
                    while (resultSet.next()){
                        model.addRow(new String[]{
                                resultSet.getString(1),
                                resultSet.getString(7),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getString(6),
                                resultSet.getString(8),

                        });
                    }
                    productSoldTable.setModel(model);
                    DBHandler.closeConnection();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

}

