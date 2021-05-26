package forms;

import utils.DBHandler;
import utils.FormConfig;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

public class ProductSoldInDay extends JFrame{
    private JPanel mainPanel;
    private JTable productSoldInDayTable;
    private JButton applyButton;
    private JTextField dateField;
    private JComboBox comboBoxQuartal;
    private JLabel yearValue;
    private JButton backButton;

    public void yearLabel(){
        DBHandler.openConnection();
        ResultSet resultSet = DBHandler.execQuery("SELECT SUM(productsale.Price*Quantity) FROM productsale WHERE DateSale BETWEEN '"+ Calendar.getInstance().get(Calendar.YEAR) +"-01-01' AND '"+ Calendar.getInstance().get(Calendar.YEAR) +"-12-01' ORDER BY DateSale ");
        try {
            while (resultSet.next()) {
                yearValue.setText(resultSet.getString(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        DBHandler.closeConnection();
    }

    ProductSoldInDay(){
        setContentPane(mainPanel);
        FormConfig.setParams(
                this,
                "Выручка",
                600,
                600,
                WindowConstants.DISPOSE_ON_CLOSE);
        yearLabel();

        comboBoxQuartal.addItem("Квартал 1");
        comboBoxQuartal.addItem("Квартал 2");
        comboBoxQuartal.addItem("Квартал 3");
        comboBoxQuartal.addItem("Квартал 4");

        comboBoxQuartal.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                int start=0;
                int end=0;

                if ("Квартал 1".equals(comboBoxQuartal.getSelectedItem())) {
                    start=1;
                    end=3;
                }else if ("Квартал 2".equals(comboBoxQuartal.getSelectedItem())){
                    start=4;
                    end=6;
                }else if ("Квартал 3".equals(comboBoxQuartal.getSelectedItem())){
                    start=7;
                    end=9;
                }else{
                    start=10;
                    end=12;
                }
                DBHandler.openConnection();
                DefaultTableModel model = new DefaultTableModel();
                model.setColumnIdentifiers(new String[]{"Дата продажи", "Сумма продажи"});

                ResultSet resultSet = DBHandler.execQuery("SELECT productsale.*, SUM(productsale.Price*Quantity) FROM productsale WHERE DateSale BETWEEN '"+ Calendar.getInstance().get(Calendar.YEAR) +"-"+start+"-01' AND '"+ Calendar.getInstance().get(Calendar.YEAR) +"-"+end+"-30' ORDER BY DateSale ");

                try {
                    while (resultSet.next()) {
                        model.addRow(new String[]{
                                (String) comboBoxQuartal.getSelectedItem(),
                                resultSet.getString(8),

                        });
                    }
                    System.out.println(resultSet);
                    productSoldInDayTable.setModel(model);
                    DBHandler.closeConnection();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                DBHandler.closeConnection();
            }

        });



        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DBHandler.openConnection();
                DefaultTableModel model = new DefaultTableModel();
                model.setColumnIdentifiers(new String[]{"Дата продажи", "Сумма продажи"});

                    ResultSet resultSet = DBHandler.execQuery("SELECT productsale.*, SUM(productsale.Price*Quantity) FROM productsale WHERE MONTH('" + dateField.getText() + "-01') = MONTH(DateSale) AND YEAR('" + dateField.getText() + "-01') = YEAR(DateSale) ORDER BY DateSale ");

                    try {
                        while (resultSet.next()) {
                            model.addRow(new String[]{
                                    dateField.getText(),
                                    resultSet.getString(8),

                            });
                        }
                        System.out.println(resultSet);
                        productSoldInDayTable.setModel(model);
                        DBHandler.closeConnection();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                DBHandler.closeConnection();
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
