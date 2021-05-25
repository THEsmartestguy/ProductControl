package forms;

import utils.DBHandler;
import utils.FormConfig;
import utils.ServiceTable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SellForm extends JFrame{
    private JPanel mainPanel;
    private JTextField sellField;
    private JButton backButton;
    private JButton applyButton;

    public SellForm(JTable table, String serviceTitle, String windowTitle, Object Price){
    setContentPane(mainPanel);
        FormConfig.setParams(
                this,
                windowTitle,
                300,
                300,
                WindowConstants.DISPOSE_ON_CLOSE);

        if(!serviceTitle.equals("")) {
            DBHandler.openConnection();
            System.out.println(serviceTitle);

        }

        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean numbersAreOK = true;
                    int quantity = Integer.parseInt(sellField.getText());
                    long curTime = System.currentTimeMillis();
                String curStringDate = new SimpleDateFormat("yyyy-MM-dd H:m:s").format(curTime);
                System.out.println(curStringDate);


                    DBHandler.execQuery("INSERT INTO productsale (Product_idProduct, Quantity, DateSale, Price) VALUES ("+Integer.parseInt(serviceTitle)+","+quantity+",'"+curStringDate+"',"+Price+")");

                DBHandler.closeConnection();

                dispose();
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