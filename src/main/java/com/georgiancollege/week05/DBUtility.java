package com.georgiancollege.week05;

import java.sql.*;
import java.util.ArrayList;

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

    // static method to get data from database
    public static ArrayList<Book> getBooksFromDB(){
        ArrayList<Book> books = new ArrayList<>();

        // string to hold sql select statement
        String sql = "SELECT books.book_id, books.book_name, books.author, books.genre, books.price, books.is_available, COUNT(sales.date_sold) AS 'units_sold'\n" +
                "FROM books\n" +
                "INNER JOIN sales\n" +
                "ON books.book_id = sales.book_id\n" +
                "GROUP BY books.book_id;";

        // establish a connection and run the query
        try(
                Connection conn = DriverManager.getConnection(connectURL, user, pass);
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
                ){
            // loop to the resultset until we have the last row and store each of them in a book object
            while (resultSet.next()){
                int bookId = resultSet.getInt("book_id");
                String bookName = resultSet.getString("book_name");
                String author = resultSet.getString("author");
                String genre = resultSet.getString("genre");
                Double price = resultSet.getDouble("price");
                Boolean isAvailable = resultSet.getBoolean("is_available");
                int unitsSold = resultSet.getInt("units_sold");

                // create a book object for the variables
                Book book = new Book(bookId, bookName, author, genre, price, isAvailable, unitsSold);

                books.add(book);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return books;
    }
}
