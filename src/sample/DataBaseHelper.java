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

    public void closeConnection() throws Exception{
            con.close();

    }

    private void openConnection() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore?useUnicode=true&characterEncoding=utf8", "root", "2337");
    }

    public boolean addbook(int isbn, String tilte, String pubname, int pubyear, int price, int quan, int threshold, String cat) {

        try {


            openConnection();
            Statement stmt = con.createStatement();
            stmt.executeQuery("CALL Add_new_book(" + isbn + ",\"" + tilte + "\",\"" + pubname + "\",\""
                    + pubyear + "\"," + price + "," + quan + "," + threshold + ",\"" + cat + "\");");

            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


        return true;

    }

    public boolean addAuthor(int isbn, String authorName) {

        try {

            openConnection();
            Statement stmt = con.createStatement();
            stmt.executeQuery("CALL Add_book_authors(" + isbn + ",\"" + authorName + "\");");
            closeConnection();
        } catch (Exception e) {
            return false;
        }


        return true;

    }

    public boolean modifyBook(int isbn, int quan) {
        try {

         openConnection();
         Statement stmt = con.createStatement();
            stmt.executeQuery("CALL modify_book_quantity(" + isbn + "," + quan + ");");
closeConnection();
        } catch (Exception e) {
            return false;
        }


        return true;
    }

    public boolean placeOrder(int isbn, String name, int quan) {
        try {

      openConnection();
      Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore?useUnicode=true&characterEncoding=utf8", "root", "2337");
            Statement stmt = con.createStatement();
            stmt.executeQuery("CALL place_book_order(\"" + name + "\"," + isbn + "," + quan + ");");
closeConnection();
        } catch (Exception e) {
            return false;
        }


        return true;
    }

    public boolean confirmOrder(int isbn, String pname) {
        try {

          openConnection();
          Statement stmt = con.createStatement();
            stmt.executeQuery("CALL confirm_order(\"" + pname + "\"," + isbn + ");");
closeConnection();
        } catch (Exception e) {
            return false;
        }


        return true;
    }

    public boolean promote(String un) {
        try {

        openConnection();
        Statement stmt = con.createStatement();
            stmt.executeQuery("CALL promote_user(\"" + un + "\");");
closeConnection();
        } catch (Exception e) {
            return false;
        }


        return true;
    }

    public boolean addNewPublisher(String authName) {
        try {

        openConnection();
        Statement stmt = con.createStatement();
            stmt.executeQuery("CALL Add_new_publishers(\"" + authName + "\");");

closeConnection();
        } catch (Exception e) {
            MassageController.getInstance().show("ERR");
                e.printStackTrace();
            return false;
        }


        return true;

    }

    public boolean addAddressToAuth(String name, String address) {

        try {

         openConnection();
         Statement stmt = con.createStatement();
            stmt.executeQuery("CALL add_publisher_address(\"" + name + "\",\"" + address + "\");");
closeConnection();
        } catch (Exception e) {
            return false;
        }


        return true;
    }

    public boolean addPhoneToAuth(String name, String phone) {
        try {

        openConnection();
        Statement stmt = con.createStatement();
            stmt.executeQuery("CALL add_publisher_phone(\"" + name + "\",\"" + phone + "\");");
closeConnection();
        } catch (Exception e) {
            return false;
        }


        return true;
    }

    public boolean addAuthor2(String name) {
        try {

        openConnection();
        Statement stmt = con.createStatement();
            stmt.executeQuery("CALL Add_new_authors(\"" + name + "\");");
            closeConnection();
        } catch (Exception e) {
            MassageController.getInstance().show(e.toString());
            e.printStackTrace();
            return false;
        }


        return true;
    }


    public ResultSet totalSalesPrevMonth() {
        try {

           openConnection();
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

            openConnection();
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
          openConnection();Statement stmt = con.createStatement();
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

            openConnection();
            Statement stmt = con.createStatement();
            System.out.println("-------------"+title+" "+ authorName+" "+ publisherName+" "+ category+" "+pubYear+" "+priceMax+" "+priceMin);
            
            stmt.executeQuery(buildSearchQuery(title, authorName, publisherName, category, pubYear, priceMin, priceMax));
            ResultSet rs = stmt.getResultSet();

            return rs;
        } catch (Exception e) {
            MassageController.getInstance().show(e.toString());
        }
        return null;
    }

    public ResultSet profileInfo(String userName) {
        try {
            openConnection();
            Statement stmt = con.createStatement();
            stmt.executeQuery("CALL Retreive_user_info (\"" + userName + "\");");

            return stmt.getResultSet();
        } catch (Exception e) {
            MassageController.getInstance().show(e.toString());
        }
        return null;
    }

    public void updateUserInfo(String userName, String oldPass, String newPass, String address, String phone) {
        try {
            openConnection();
            Statement stmt = con.createStatement();
            stmt.executeQuery("CALL update_user_info (\"" + userName + "\",\"" + oldPass + "\",\"" + newPass + "\",\"" + address + "\",\"" +
                    phone + "\");");
            con.close();
        } catch (Exception e) {
            MassageController.getInstance().show(e.toString());
        }
    }

    public  int signin(String usrName , String password){

        try {
            openConnection();
            Statement stmt = con.createStatement();
            CallableStatement st = con.prepareCall("{ ? = call check_login(\""+usrName+"\",\""+password+"\")}");
            int count = 0;
            st.registerOutParameter(1, Types.INTEGER);
            st.execute();
            count = st.getInt(1);
            System.out.println(count);
            con.close();
            return count;
        } catch (Exception e) {
            MassageController.getInstance().show(e.toString());
        }
        return -1;

    }
    public boolean signUp(String userName, String pass, String firstN, String lastN, String email, String phone, String address) {
        try {
            openConnection();
            Statement stmt = con.createStatement();
            stmt.executeQuery("CALL Signup (\"" + userName + "\",\"" + pass + "\",\"" + firstN + "\",\"" + lastN + "\",\"" + email +
                    "\",\"" + address + "\",\"" + phone + "\");");
            con.close();
            return true;
        } catch (Exception e) {
            MassageController.getInstance().show(e.toString());
            return false;
        }
    }
    
    public String buildSearchQuery (String title, String authorName, String publisherName, String category, Integer pubYear, Integer priceMin, Integer priceMax){
		if(title!=null){
			title="'"+title+"'";
		}
		if(authorName!=null){
			authorName="'"+authorName+"'";
		}
		if(publisherName!=null){
			publisherName="'"+publisherName+"'";
		}
		if(category!=null){
			category="'"+category+"'";
		}
    	String query="CALL Search_for_book (" + title + "," + authorName + "," + publisherName + "," + category + "," +
                pubYear + "," + priceMax + "," +priceMin + ");";
    	return query;
    	
    }

}

