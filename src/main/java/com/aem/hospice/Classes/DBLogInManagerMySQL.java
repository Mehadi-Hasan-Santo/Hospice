package com.aem.hospice.Classes;

import com.aem.hospice.PopUp.AlertBox;
import java.sql.*;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DBLogInManagerMySQL implements LogInManager,DatabaseManager{
    public static Connection MakeConnection() {
        String url = "jdbc:mysql://localhost:3306/hospice?autoReconnect=true&useSSL=false";
        String username = "root";
        String password = "password";
        try {
            return DriverManager.getConnection(url,username,password);

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static Boolean LogInValidate(String uid, String pass){
        try{
            String sql = "Select * from hospice.login where uid=\""+uid+"\";";
            ResultSet rs = DBLogInManagerMySQL.MakeConnection().createStatement().executeQuery(sql);
            while(rs.next()){
                if( Objects.equals(rs.getString("password"),pass )) {return true;}
            }
            return false;

        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }
    public static String GenerateUid(String table, String uidcolname, int login){
        return GenerateUid(table,uidcolname,login,"1234");
    }
    public static String GenerateUid(String table, String uidcolname, int login, String password){
        try {
            String sql = "Select * from " + table + ";";
            ResultSet rs = DBLogInManagerMySQL.MakeConnection().createStatement().executeQuery(sql);
            String generated_uid = null;
            while (rs.next()) {
                generated_uid = rs.getString(uidcolname);
            }
            //if(generated_uid==null) generated_uid="10100";
            int temp = Integer.parseInt(generated_uid) + 1;
            generated_uid = String.valueOf(temp);
            if (login == 1) {
                sql = "insert into login values ('" + generated_uid + "','"+password+"');";
                DBLogInManagerMySQL.MakeConnection().createStatement().execute(sql);
            }
            return generated_uid;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static void ChangePassword(String uid, String oldpassword , String newpassword){
        if(LogInValidate(uid,oldpassword)){
            String query = "UPDATE login set password=? WHERE uid =? ;";
            try {
                PreparedStatement pstmt =  DBLogInManagerMySQL.MakeConnection().prepareStatement(query);
                pstmt.setString(2, uid);
                pstmt.setString(1, newpassword);
                pstmt.executeUpdate();
            } catch(Exception e){
                e.printStackTrace();
            }

        }
        else {
            AlertBox.display("Wrong Password", "Try Again");
        }


    }
    public static void ChangePasswordAdminPrevilage(String uid,String newpassword){

            String query = "UPDATE login set password=? WHERE uid =? ;";
            try {
                PreparedStatement pstmt =  DBLogInManagerMySQL.MakeConnection().prepareStatement(query);
                pstmt.setString(2, uid);
                pstmt.setString(1, newpassword);
                pstmt.executeUpdate();
            } catch(Exception e){
                e.printStackTrace();
            }


    }
    public static boolean isValidPassword(String password)
    {
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";
        Pattern p = Pattern.compile(regex);
        if (password == null) {
            return false;
        }
        Matcher m = p.matcher(password);
        return m.matches();
    }
//    public static ObservableList<ProvidedService> FetchBillFromProvidedService(int type, String coln, String uid5){
//        ObservableList<ProvidedService> list = FXCollections.observableArrayList();
//        try {
//            Connection conn = DBLogInManagerMySQL.MakeConnection();
//            Statement mysta = conn.createStatement();
//            String sql = "SELECT * from providedservice WHERE s_type= '" + type + "' AND " + coln + "= '" + uid5 + "' ;";
//            ResultSet rs = mysta.executeQuery(sql);
//            while (rs.next()) {
//                e+= rs.getDouble("bill");
//                p+= rs.getDouble("paid");
//                d+=rs.getDouble("bill")-rs.getDouble("paid");
//                list.add(new ProvidedService(rs.getString("ps_uid")));
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//    }

}