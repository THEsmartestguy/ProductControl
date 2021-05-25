package utils;

import java.sql.*;

/// <summary>
/// Класс отвечает за работу с подключением к БД
/// (открытие подключения, закрытие, выполнение запросов)
/// </summary>

public class DBHandler {
    private static Connection connection;

    public static boolean openConnection(){
        try {
            connection = DriverManager.getConnection(
                    Configuration.DBURL,
                    Configuration.DBUSER,
                    Configuration.DBPASSWORD);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public static boolean closeConnection(){
        try {
            connection.close();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    /// <summary>
    /// Функция выполняет запрос к БД
    /// </summary>
    /// <params>
    /// String query - строка, содержащая запрос к БД
    /// </params>
    /// <returns>
    /// ResultSet - если запарос содержит SELECT, null - в остальных случаях
    /// </returns>

    public static ResultSet execQuery(String query){
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(query);
            System.out.println(preparedStatement);
            if(query.contains("SELECT"))
                resultSet = preparedStatement.executeQuery();
            else
                preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return resultSet;
    }
}
