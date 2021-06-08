package forms;

import utils.DBHandler;
import utils.FormConfig;
import utils.ServiceTable;

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
    private JButton deleteButton;

    ProductSoldForm(JTable serviceTable){
        setContentPane(mainPanel);
        FormConfig.setParams(
                this,
                "Проданные товары",
                1200,
                600,
                WindowConstants.DISPOSE_ON_CLOSE);

        productSoldTable.getTableHeader().setReorderingAllowed(false);

        refresh();

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

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(productSoldTable.getSelectedRow()>=0){
                    if (JOptionPane.showConfirmDialog(null, "Вы действительно хотите удалить проданный товар?", "Внимание",
                            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        String quantity= productSoldTable.getValueAt(productSoldTable.getSelectedRow(),2).toString();
                        String Title= productSoldTable.getValueAt(productSoldTable.getSelectedRow(),1).toString();
                        DBHandler.openConnection();
                        DBHandler.execQuery("DELETE FROM productsale WHERE idProductSale = "+productSoldTable.getValueAt(productSoldTable.getSelectedRow(),0)+"");
                        DBHandler.execQuery("UPDATE product SET Quantity=Quantity+"+quantity+" WHERE Title='"+Title+"'");
                        DBHandler.closeConnection();
                        refresh();
                        ServiceTable.refreshTable(serviceTable, 0);
                    }
                }else{
                    JOptionPane.showMessageDialog(
                            null,
                            "Необходимо выбрать строку для удаления",
                            "Внимание",
                            JOptionPane.WARNING_MESSAGE
                    );
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


    private void refresh(){
        DBHandler.openConnection();
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Номер продажи", "Товар", "Количество", "Дата продажи","Цена за штуку","Сумма продажи"});
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
    }

}

