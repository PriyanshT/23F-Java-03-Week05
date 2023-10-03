package com.georgiancollege.week05;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Formatter;

public class FakeBooksData {
    // create insert statements for random data
    public static void createSQL(){
        SecureRandom secureRandom = new SecureRandom();

        try(
                Formatter formatter = new Formatter("salesData.sql");
                ){
            // create a loop to get random 500 data
            for (int i = 0; i < 500; i++) {
                // Example - INSERT INTO sales(book_id, date_sold) VALUES (1, '2023-10-03');
                int bookId = secureRandom.nextInt(1, 13);
                LocalDate dateSold = LocalDate.now().minusDays(secureRandom.nextInt(365));
                formatter.format("INSERT INTO sales(book_id, date_sold) VALUES (%d, '%s');\n", bookId, dateSold);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        createSQL();
//    }
}
