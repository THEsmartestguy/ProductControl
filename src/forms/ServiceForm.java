package forms;

import utils.DBHandler;
import utils.FormConfig;
import utils.ServiceTable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServiceForm extends JFrame{
    private JPanel panel;
    private JTable serviceTable;
    private JButton adminButton;
    private JPasswordField adminPasswordField;
    private JButton insertButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton productSoldButton;
    private JButton soldInMonthButton;
    private JButton sellButton;
    private JButton arrivalButton;
    private JButton arrivalCheckButton;
    private JButton refreshButton;

    public ServiceForm(){
        setContentPane(panel);
        FormConfig.setParams(
                this,
                "Магазин одежды",
                1200,
                600,
                WindowConstants.EXIT_ON_CLOSE);

        serviceTable.getTableHeader().setReorderingAllowed(false);
        ServiceTable.refreshTable(serviceTable, 0);



        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(adminPasswordField.isEnabled()){
                    if(adminPasswordField.getText().equals("0000")){
                        adminPasswordField.setEnabled(false);
                        adminPasswordField.setText("");
                        adminButton.setText("Выйти");

                        insertButton.setVisible(true);
                        updateButton.setVisible(true);
                        deleteButton.setVisible(true);
                        productSoldButton.setVisible(true);
                        soldInMonthButton.setVisible(true);
                        sellButton.setVisible(true);
                        arrivalButton.setVisible(true);
                        arrivalCheckButton.setVisible(true);

                    }else{
                        JOptionPane.showMessageDialog(
                                null,
                                "Код введен неверно. Режим администратора недоступен.",
                                "Внимание!",
                                JOptionPane.WARNING_MESSAGE);
                    }
                }else{
                    adminPasswordField.setEnabled(true);
                    adminButton.setText("Войти");

                    insertButton.setVisible(false);
                    updateButton.setVisible(false);
                    deleteButton.setVisible(false);
                    productSoldButton.setVisible(false);
                    soldInMonthButton.setVisible(false);
                    sellButton.setVisible(false);
                    arrivalButton.setVisible(false);
                    arrivalCheckButton.setVisible(false);

                }


            }
        });

        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InsertServiceForm insertServiceForm = new InsertServiceForm(serviceTable,"", "Добавление товара");
                insertServiceForm.setVisible(true);
                insertServiceForm.pack();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(serviceTable.getSelectedRow()>=0){
                    InsertServiceForm insertServiceForm = new InsertServiceForm(
                            serviceTable,
                            serviceTable.getValueAt(serviceTable.getSelectedRow(), 1).toString(),
                            "Редактирование товара"
                    );
                    insertServiceForm.setVisible(true);
                    insertServiceForm.pack();
                }else{
                    JOptionPane.showMessageDialog(
                            null,
                            "Необходимо выбрать строку для редактирования",
                            "Внимание",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(serviceTable.getSelectedRow()>=0){
                    if (JOptionPane.showConfirmDialog(null, "Вы действительно хотите удалить товар?", "Внимание",
                            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        DBHandler.openConnection();
                        DBHandler.execQuery("DELETE FROM product WHERE Title='"+serviceTable.getValueAt(serviceTable.getSelectedRow(),1)+"'");
                        DBHandler.closeConnection();
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

        productSoldButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductSoldForm productSoldForm = new ProductSoldForm(serviceTable);
                productSoldForm.setVisible(true);
                productSoldForm.pack();
            }
        });

        soldInMonthButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductSoldInDay productSoldInMonth = new ProductSoldInDay();
                productSoldInMonth.setVisible(true);
                productSoldInMonth.pack();
            }
        });

        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(serviceTable.getSelectedRow()>=0){
                    SellForm sellForm = new SellForm(
                            serviceTable,
                            serviceTable.getValueAt(serviceTable.getSelectedRow(), 0).toString(),
                            "Продажа", serviceTable.getValueAt(serviceTable.getSelectedRow(),2),
                            serviceTable.getValueAt(serviceTable.getSelectedRow(),1)
                    );
                    sellForm.setVisible(true);
                    sellForm.pack();
                }else{
                    JOptionPane.showMessageDialog(
                            null,
                            "Необходимо выбрать строку для продажи",
                            "Внимание",
                            JOptionPane.WARNING_MESSAGE
                    );
            }
        }
        });

        arrivalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(serviceTable.getSelectedRow()>=0){
                    ArrivalForm arrivalForm = new ArrivalForm(
                            serviceTable,
                            serviceTable.getValueAt(serviceTable.getSelectedRow(), 0).toString(),
                            "Приход", serviceTable.getValueAt(serviceTable.getSelectedRow(),2),
                            serviceTable.getValueAt(serviceTable.getSelectedRow(),1)
                    );
                    arrivalForm.setVisible(true);
                    arrivalForm.pack();
                }else{
                    JOptionPane.showMessageDialog(
                            null,
                            "Необходимо выбрать строку для поставки на приход",
                            "Внимание",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
            }
        });

        arrivalCheckButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrivalsCheckForm arrivalsCheckForm = new ArrivalsCheckForm(serviceTable);
                arrivalsCheckForm.setVisible(true);
                arrivalsCheckForm.pack();
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ServiceTable.refreshTable(serviceTable, 0);
            }
        });

    }
}
