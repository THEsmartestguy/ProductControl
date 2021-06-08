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

public class ArrivalsCheckForm extends JFrame{
    private JPanel mainPanel;
    private JTable arrivalTable;
    private JButton backButton;
    private JButton deleteButton;

    ArrivalsCheckForm(JTable serviceTable){
        setContentPane(mainPanel);
        FormConfig.setParams(
                this,
                "Приходы",
                1200,
                600,
                WindowConstants.DISPOSE_ON_CLOSE);

        refresh();

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(arrivalTable.getSelectedRow()>=0){
                    if (JOptionPane.showConfirmDialog(null, "Вы действительно хотите удалить проданный товар?", "Внимание",
                            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        String quantity= arrivalTable.getValueAt(arrivalTable.getSelectedRow(),5).toString();
                        String Title= arrivalTable.getValueAt(arrivalTable.getSelectedRow(),1).toString();
                        DBHandler.openConnection();
                        DBHandler.execQuery("DELETE FROM arrival WHERE idarrival = "+arrivalTable.getValueAt(arrivalTable.getSelectedRow(),0)+"");
                        DBHandler.execQuery("UPDATE product SET Quantity=Quantity-"+quantity+" WHERE Title='"+Title+"'");
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
        arrivalTable.getTableHeader().setReorderingAllowed(false);

        DBHandler.openConnection();
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Номер прихода", "Товар", "Цена за штуку (реализация)","Цена закупки", "Прибыль за штуку", "Колличество","Дата поставки"});
        ResultSet resultSet = DBHandler.execQuery("SELECT *, Price-BuyPrice FROM arrival ORDER BY DateArrival");

        try {
            while (resultSet.next()){
                model.addRow(new String[]{
                        resultSet.getString(1),
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
