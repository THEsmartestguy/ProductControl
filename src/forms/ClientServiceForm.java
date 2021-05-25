package forms;

import utils.DBHandler;
import utils.FormConfig;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientServiceForm extends JFrame{
    private JPanel panel;
    private JTable clientServiceTable;
    private JButton backButton;

    public ClientServiceForm(){
        setContentPane(panel);
        FormConfig.setParams(
                this,
                "Ближайшие посещения",
                600,
                600,
                WindowConstants.DISPOSE_ON_CLOSE);

        DefaultTableModel clientServiceModel = new DefaultTableModel();

        clientServiceModel.setColumnIdentifiers(new String[]{"Клиент", "Услуга", "Дата и время записи"});

        DBHandler.openConnection();

        String currentTime="0000-00-00 00:00";
        String tomorrowTime="0000-00-00 00:00";

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        currentTime=formatter.format(new Date());

        Date date = new Date();
        date.setDate(date.getDate()+2);
        date.setHours(0);
        date.setMinutes(0);
        tomorrowTime = formatter.format(date);
        System.out.println(tomorrowTime);

        ResultSet resultSet = DBHandler.execQuery("SELECT LastName, FirstName, Patronymic, Title, StartTime " +
                "FROM client, service, clientservice " +
                "WHERE client.ID=clientID AND service.ID=serviceID AND StartTime>'"+currentTime+"' AND StartTime<'"+tomorrowTime+"'");
        try{
            while (resultSet.next()){
                clientServiceModel.addRow(new String[]{
                        resultSet.getString(1)+" "+resultSet.getString(2)+" "+resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)
                });
            }
        }catch(SQLException exception){
            exception.printStackTrace();
        }

        DBHandler.closeConnection();

        clientServiceTable.setModel(clientServiceModel);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
