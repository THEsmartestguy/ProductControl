package forms;

import utils.DBHandler;
import utils.FormConfig;
import utils.ServiceTable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

public class ArrivalForm extends JFrame{
    private JPanel mainPanel;
    private JTextField arrivalField;
    private JButton applyButton;
    private JButton backButton;
    private JTextField buyPriceField;

    public ArrivalForm(JTable table, String serviceTitle, String windowTitle, Object Price, Object Title){
        setContentPane(mainPanel);
        FormConfig.setParams(
                this,
                windowTitle,
                600,
                300,
                WindowConstants.DISPOSE_ON_CLOSE);


        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DBHandler.openConnection();

                int buyPrice = Integer.parseInt(buyPriceField.getText());
                int quantity = Integer.parseInt(arrivalField.getText());
                long curTime = System.currentTimeMillis();
                String curStringDate = new SimpleDateFormat("yyyy-MM-dd H:m:s").format(curTime);
                System.out.println(curStringDate);

                DBHandler.execQuery("INSERT INTO arrival (Title, Price, Quantity, DateArrival, BuyPrice) VALUES ('"+Title+"',"+Price+","+quantity+",'"+curStringDate+"',"+buyPrice+")");
                DBHandler.execQuery("UPDATE product SET Quantity=Quantity+"+quantity+" WHERE Title='"+Title+"'");
                ServiceTable.refreshTable(table, 0);
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
