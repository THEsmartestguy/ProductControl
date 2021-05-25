package forms;

import utils.DBHandler;
import utils.FormConfig;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignUpClientForm extends JFrame{
    private JPanel panel;
    private JComboBox clientsComboBox;
    private JTextField dateTextField;
    private JButton applyButton;
    private JButton backButton;
    private JLabel titleLabel;
    private JLabel durationLabel;

    public SignUpClientForm(String title, int duration){
        FormConfig.setParams(
                this,
                "Запись клиента на прием",
                600,
                600,
                WindowConstants.DISPOSE_ON_CLOSE);
        setContentPane(panel);

        titleLabel.setText(title);
        durationLabel.setText(duration/60+" мин");

        DBHandler.openConnection();
        ResultSet resultSet = DBHandler.execQuery("SELECT * FROM client");
        try{
            while (resultSet.next()){
                clientsComboBox.addItem(resultSet.getString(3)+" "+
                        resultSet.getString(2)+" "+
                        resultSet.getString(4));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        DBHandler.closeConnection();

        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(dateTextField.getText().length()==16 &&
                        dateTextField.getText().charAt(4)=='-' &&
                        dateTextField.getText().charAt(7)=='-' &&
                        dateTextField.getText().charAt(13)==':'){
                    DBHandler.openConnection();
                    ResultSet resultSet;
                    int clientID = 0;
                    int serviceID = 0;

                    String selectedString = clientsComboBox.getSelectedItem().toString();
                    resultSet = DBHandler.execQuery("SELECT ID FROM client WHERE LastName='"+
                            selectedString.substring(0,selectedString.indexOf(" "))+"'");
                    try{
                        while (resultSet.next()){
                            clientID=resultSet.getInt(1);
                        }
                    }catch(SQLException exception){
                        exception.printStackTrace();
                    }
                    resultSet = DBHandler.execQuery("SELECT ID FROM service WHERE Title='"+title+"'");
                    try{
                        while (resultSet.next()){
                            serviceID=resultSet.getInt(1);
                        }
                    }catch(SQLException exception){
                        exception.printStackTrace();
                    }

                    DBHandler.execQuery("INSERT INTO clientservice(ClientID,ServiceID,StartTime) VALUES ("+
                            clientID+","+serviceID+",'"+dateTextField.getText()+"')");
                    DBHandler.closeConnection();
                    dispose();
                }else{
                    JOptionPane.showMessageDialog(
                            null,
                            "Формат даты должен соответствовать следующему паттерну: ГГГГ-ММ-ДД чч:мм",
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
}
