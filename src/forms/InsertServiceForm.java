package forms;

import exceptions.SQLSyntaxException;
import utils.DBHandler;
import utils.FormConfig;
import utils.ServiceTable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InsertServiceForm extends JFrame{
    private JPanel panel;
    private JTextField titleField;
    private JTextField costField;
    private JTextField descriptionField;
    private JButton applyButton;
    private JButton backButton;
    private JTextField categoryField;
    private JComboBox ndsComboBox;

    public InsertServiceForm(JTable table, String serviceTitle, String windowTitle){
        FormConfig.setParams(
                this,
                windowTitle,
                600,
                600,
                WindowConstants.DISPOSE_ON_CLOSE);
        setContentPane(panel);

        ndsComboBox.addItem("0%");
        ndsComboBox.addItem("10%");
        ndsComboBox.addItem("20%");

        if(!serviceTitle.equals("")&&!serviceTitle.contains("\'")){
            DBHandler.openConnection();

            ResultSet resultSet = DBHandler.execQuery("SELECT * FROM product WHERE Title='"+serviceTitle+"'");

            try {
                while (resultSet.next()){
                    titleField.setText(resultSet.getString(2));
                    costField.setText(resultSet.getString(3));
                    descriptionField.setText(resultSet.getString(4));
                    categoryField.setText(resultSet.getString(5));

                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            DBHandler.closeConnection();
        }

        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                String nds = (String) ndsComboBox.getSelectedItem();
                double price = 0;
                int quantity = 0;
                String category = categoryField.getText();
                String barcode = descriptionField.getText();


                boolean numbersAreOK = true;

                try {
                    price = Double.parseDouble(costField.getText());


                }catch(NumberFormatException exception){
                    exception.printStackTrace();
                    JOptionPane.showMessageDialog(
                            null,
                            "Введен неверный формат данных",
                            "Внимание",
                            JOptionPane.WARNING_MESSAGE
                    );
                    numbersAreOK = false;
                }

                if (ServiceTable.checkUpdateConditions(title,price,quantity) && numbersAreOK){
                    DBHandler.openConnection();
                    if(!serviceTitle.equals("")&&!serviceTitle.contains("\'")){
                        DBHandler.execQuery("UPDATE product SET Title='"+title+
                                "', Price = "+price+
                                ", Category = '"+category+
                                "', Barcode="+barcode+
                                ", NDS="+nds+" WHERE Title='"+serviceTitle+"'");
                    }else{
                        DBHandler.execQuery("INSERT INTO product (Title, Price, Barcode, Category, NDS) VALUES (" +
                                "'"+title+"', "+price+", "+barcode+",'"+category+"','"+nds+"')");
                    }

                    ServiceTable.refreshTable(table, 0);
                    DBHandler.closeConnection();

                    dispose();
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
