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

    private Connection con;

    private DataBaseHelper() {
    }

    public void closeConnection() {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void openConnection() throws SQLException {
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore?useUnicode=true&characterEncoding=utf8", "root", "2337");
    }

    public boolean addbook(int isbn, String tilte, String pubname, int pubyear, int price, int quan, int threshold, String cat) {

        try {

            Class.forName("com.mysql.jdbc.Driver");
            openConnection();
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
            openConnection();
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

    public boolean confirmOrder(int isbn, String pname) {
        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore?useUnicode=true&characterEncoding=utf8", "root", "2337");
            Statement stmt = con.createStatement();
            stmt.executeQuery("CALL confirm_order(" + pname + "," + isbn + ");");
            con.close();
        } catch (Exception e) {
            return false;
        }


        return true;
    }

    public boolean promote(String un) {
        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore?useUnicode=true&characterEncoding=utf8", "root", "2337");
            Statement stmt = con.createStatement();
            stmt.executeQuery("CALL promote_user(" + un + ");");
            con.close();
        } catch (Exception e) {
            return false;
        }


        return true;
    }

    public boolean addNewAuthor(String authName) {
        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore?useUnicode=true&characterEncoding=utf8", "root", "2337");
            Statement stmt = con.createStatement();
            stmt.executeQuery("CALL add_publisher(" + authName + ");");
            con.close();
        } catch (Exception e) {
            return false;
        }


        return true;

    }

    public boolean addAddressToAuth(String name, String address) {

        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore?useUnicode=true&characterEncoding=utf8", "root", "2337");
            Statement stmt = con.createStatement();
            stmt.executeQuery("CALL add_address(" + name + "," + address + ");");
            con.close();
        } catch (Exception e) {
            return false;
        }


        return true;
    }

    public boolean addPhoneToAuth(String name, String phone) {
        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore?useUnicode=true&characterEncoding=utf8", "root", "2337");
            Statement stmt = con.createStatement();
            stmt.executeQuery("CALL add_phone(" + name + "," + phone + ");");
            con.close();
        } catch (Exception e) {
            return false;
        }


        return true;
    }

    public boolean addAuthor2(String name) {
        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore?useUnicode=true&characterEncoding=utf8", "root", "2337");
            Statement stmt = con.createStatement();
            stmt.executeQuery("CALL add_author(" + name + "," + name + ");");
            con.close();
        } catch (Exception e) {
            return false;
        }


        return true;
    }


    public ResultSet totalSalesPrevMonth() {
        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore?useUnicode=true&characterEncoding=utf8", "root", "2337");
            Statement stmt = con.createStatement();
            stmt.executeQuery("CALL retreive_total_sales();");//TODO
            ResultSet rs = stmt.getResultSet();
            return rs;
        } catch (Exception e) {
            MassageController.getInstance().show(e.toString());
            return null;
        }


    }

    public ResultSet top5CustomersInlast3Monthes() {
        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore?useUnicode=true&characterEncoding=utf8", "root", "2337");
            Statement stmt = con.createStatement();
            stmt.executeQuery("CALL retreive_top_customers();");//TODO
            ResultSet rs = stmt.getResultSet();
            return rs;
        } catch (Exception e) {
            MassageController.getInstance().show(e.toString());
            return null;
        }


    }

    public ResultSet top10salesInLastThreeMonthes() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore?useUnicode=true&characterEncoding=utf8", "root", "2337");
            Statement stmt = con.createStatement();
            stmt.executeQuery("CALL retreive_top_sales();");//TODO
            ResultSet rs = stmt.getResultSet();
            return rs;
        } catch (Exception e) {
            MassageController.getInstance().show(e.toString());
            return null;
        }

    }

    public ResultSet searchBook(String title, String authorName, String publisherName, String category, Integer pubYear, Integer priceMin, Integer priceMax) {
        try {

            Class.forName("com.mysql.jdbc.Driver");
            openConnection();
            Statement stmt = con.createStatement();
            stmt.executeQuery("CALL Search_for_book (" + title + "," + authorName + "," + publisherName + "," + category + "," +
                    pubYear + "," + priceMin + "," + priceMax + ");");
            ResultSet rs = stmt.getResultSet();
            return rs;
        } catch (Exception e) {
            MassageController.getInstance().show(e.toString());
        }
        return null;
    }

    public ResultSet profileInfo(String userName) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            openConnection();
            Statement stmt = con.createStatement();
            stmt.executeQuery("CALL Retreive_user_info (" + userName + ");");
            return stmt.getResultSet();
        } catch (Exception e) {
            MassageController.getInstance().show(e.toString());
        }
        return null;
    }

    public void updateUserInfo(String userName, String oldPass, String newPass, String address, String phone) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            openConnection();
            Statement stmt = con.createStatement();
            stmt.executeQuery("CALL update_user_info (" + userName + "," + oldPass + "," + newPass + "," + address + "," +
                    phone + ");");
            con.close();
        } catch (Exception e) {
            MassageController.getInstance().show(e.toString());
        }
    }

    public void signUp(String userName, String pass, String firstN, String lastN, String email, String phone, String address) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            openConnection();
            Statement stmt = con.createStatement();
            stmt.executeQuery("CALL Signup (" + userName + "," + pass + "," + firstN + "," + lastN + "," + email +
                    "," + phone + "," + address + ");");
            con.close();
        } catch (Exception e) {
            MassageController.getInstance().show(e.toString());
        }
    }

}

