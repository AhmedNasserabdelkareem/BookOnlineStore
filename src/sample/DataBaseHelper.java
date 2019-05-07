package sample;

import java.sql.*;


/**
 * Created by sharaf on 05/05/2019.
 */
public class DataBaseHelper {
    private static DataBaseHelper ourInstance = new DataBaseHelper();

    public static DataBaseHelper getInstance() {
        return ourInstance;
    }

    private DataBaseHelper() {
    }

    public boolean addbook(int isbn, String tilte, String pubname, int pubyear, int price, int quan, int threshold, String cat) {

        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore?useUnicode=true&characterEncoding=utf8", "root", "2337");
            Statement stmt = con.createStatement();
            stmt.executeQuery("CALL Add_new_book(" + isbn + "," + tilte + "," + pubname + "," + pubyear + "," + price + "," + quan + "," + threshold + "," + cat + ");");
//            //stmt.executeQuery("SELECT * FROM book WHERE (book.Title=NULL)and ( book.PubID in (	SELECT publisher.PublisherID from publisher where ( publisher.Name=NULL)  )   and ( book.categoryID in (SELECT category.categoryID from category where (category.Name=NULL)))and (book.ISBN in (select book_authors.ISBN from book_authors where book_authors.AuthorID in (select authors.authorId from authors where  (authors.Name=NULL))))) ;");
//            ResultSet rs= stmt.getResultSet();
//            ResultSetMetaData rsmd = rs.getMetaData();
//            int columnsNumber = rsmd.getColumnCount();
//            while(rs.next())
//                for (int i = 1; i <= columnsNumber; i++) {
//                    String columnValue = rs.getString(i);
//                    System.out.print(columnValue + " " + rsmd.getColumnName(i)+"\n");
//                }
            con.close();
        } catch (Exception e) {
            return false;
        }


        return true;

    }

    public boolean addAuthor(int isbn, String authorName) {

        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore?useUnicode=true&characterEncoding=utf8", "root", "2337");
            Statement stmt = con.createStatement();
            stmt.executeQuery("CALL Add_book_authors(" + isbn + "," + authorName + ");");
            con.close();
        } catch (Exception e) {
            return false;
        }


        return true;

    }

    public boolean modifyBook(int isbn, int quan) {
        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore?useUnicode=true&characterEncoding=utf8", "root", "2337");
            Statement stmt = con.createStatement();
            stmt.executeQuery("CALL modify_book_quantity(" + isbn + "," + quan + ");");
            con.close();
        } catch (Exception e) {
            return false;
        }


        return true;
    }

    public boolean placeOrder(int isbn, String name, int quan) {
        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore?useUnicode=true&characterEncoding=utf8", "root", "2337");
            Statement stmt = con.createStatement();
            stmt.executeQuery("CALL place_book_order(" + name + "," + isbn + "," + quan + ");");
            con.close();
        } catch (Exception e) {
            return false;
        }


        return true;
    }
}

