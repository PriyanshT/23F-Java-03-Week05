package com.georgiancollege.week05;

import java.sql.*;

public class DBUtility {
    // variables for username, password and connection url
    private static String user = DBCreds.findUser();
    private static String pass = DBCreds.findPass();
    private static String connectURL = "jdbc:mysql://172.31.22.43:3306/" + user;

    // static method to insert data to db
    public static int insertBookIntoDB(Book book) throws SQLException {
        int bookId = -1;
        ResultSet resultSet = null;

        // store the sql statement to insert data
        String sql = "INSERT INTO books(book_name, author, genre, price, is_available) VALUES (?, ?, ?, ?, ?);";

        // try with resource block
        // anything inside the () will be automatically closed if the exception occurs or not
        try(
                Connection conn = DriverManager.getConnection(connectURL, user, pass);
                PreparedStatement preparedStatement = conn.prepareStatement(sql, new String[]{"bookId"});
                )
        {
            // run prepared statement and attach data instead of ?
            preparedStatement.setString(1, book.getBookName());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getGenre());
            preparedStatement.setDouble(4, book.getPrice());
            preparedStatement.setBoolean(5, book.isAvailable());

            // execute the query
            preparedStatement.executeUpdate();

            // add result set
            resultSet = preparedStatement.getGeneratedKeys();

            while (resultSet.next()){
                bookId = resultSet.getInt(1);
            }

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(resultSet != null){
                resultSet.close();
            }
        }

        return bookId;
    }
}
