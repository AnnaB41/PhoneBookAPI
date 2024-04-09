package helpers;

import models.ContactModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseReader {

    static String url = "jdbc:mysql://localhost:3306/phonebook"; //путь к базе данных
    static String username = "root"; // доступ к базе данных
    static String password = "301177";

    public static ContactModel readContactFromDatabase(String id) throws SQLException {
       ContactModel contactModel = null;

        Connection connection = DriverManager.getConnection(url, username, password); // драйвер, который отвечает за запрос к базам данных
        System.out.println("Connection successful!");

        String query = "select * from contacts where id = ?"; //prepared sstatment, вопрос означает, что будут подстваляться данные

        PreparedStatement preparedStatement = connection.prepareStatement(query); // передаем наш ззапрос
        preparedStatement.setString(1, id); // 1 это индекс, который преперед стетмент устанавливает
        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next()){
            contactModel = new ContactModel();
            contactModel.setId(resultSet.getString("id"));
            contactModel.setName(resultSet.getString("name"));
            contactModel.setLastName(resultSet.getString("lastname"));
            contactModel.setEmail(resultSet.getString("email"));
            contactModel.setPhone(resultSet.getString("phone"));
            contactModel.setAddress(resultSet.getString("address"));
            contactModel.setDescription(resultSet.getString("description"));

        } else{
            System.out.println("No contacts");
        }

        preparedStatement.close();
        connection.close();
        return contactModel;
    }

    //////////////////////////////////////////

    public static List<ContactModel> readAllContactsFromDatabase() throws SQLException {
        List<ContactModel> contacts = new ArrayList<>();

        Connection connection = DriverManager.getConnection(url, username, password); // драйвер, который отвечает за запрос к базам данных
        System.out.println("Connection successful!");

        String query = "select * from contacts";

        Statement statement = connection.createStatement(); // передаем наш ззапрос

        ResultSet resultSet = statement.executeQuery(query);

        while(resultSet.next()){
            ContactModel contactModel = new ContactModel();

            contactModel.setId(resultSet.getString("id"));
            contactModel.setName(resultSet.getString("name"));
            contactModel.setLastName(resultSet.getString("lastname"));
            contactModel.setEmail(resultSet.getString("email"));
            contactModel.setPhone(resultSet.getString("phone"));
            contactModel.setAddress(resultSet.getString("address"));
            contactModel.setDescription(resultSet.getString("description"));
            contacts.add(contactModel);

        }

        statement.close();
        connection.close();
        return contacts;
    }

    public static void main(String[] args) throws SQLException {
        ContactModel contact = readContactFromDatabase("4611b2d3-1da9-45f1-a41b-60a18f6b92f4");
        System.out.println("Contact name : " + contact.getName());
        System.out.println("An email : " + contact.getEmail());

//        List<ContactModel> cont = readAllContactsFromDatabase();
//        for(ContactModel contactModel : cont){
//            System.out.println("Record: " + contactModel.toString());
//        }

    }}

