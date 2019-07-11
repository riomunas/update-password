/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ssg.update.password;

import com.mysql.jdbc.Connection;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.commons.lang3.StringUtils;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rio
 */
public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        String user = "";
        String pass = "";
        String host = "";
        String name = "";

        for (String s : args) {
            if (s.startsWith("-u")) user = s.substring(2);
            if (s.startsWith("-p")) pass = s.substring(2);
            if (s.startsWith("-h")) host = s.substring(2);
            if (s.startsWith("-db")) name = s.substring(3);
        }

        if (StringUtils.isEmpty(user) || StringUtils.isEmpty(pass) || StringUtils.isEmpty(host)) {
            System.out.println("Parameter belum terisi...");
            System.out.println("java -jar update-password-1.0-SNAPSHOT-jar-with-dependencies.jar -u<user> -p<password> -h<host> -db<db>");
            System.exit(1);
        }
        Class.forName("com.mysql.jdbc.Driver");  
        Connection conn =(Connection) DriverManager.getConnection("jdbc:mysql://"+host+":3306/"+name+"?autoReconnect=true&useSSL=false",user,pass);
        
        String query = "SELECT * FROM ic_mas_user o WHERE o.loker <> ''";
        Statement st = conn.createStatement();  
        
        // execute the query, and get a java resultset
        ResultSet rs = st.executeQuery(query);
        
        //tampung sid dan user_id
        List data = new ArrayList();
        while (rs.next()) {
            data.add(new IcMasUser(rs.getString("sid"), rs.getString("user_id")));
        }
        st.close();
        
        //update password
        String queryUpdate = " update ic_mas_user set password = ? where sid = ? ";

        PreparedStatement preparedStmt = conn.prepareStatement(queryUpdate);
        for (IcMasUser icMasUser : (List<IcMasUser>)data) {
            preparedStmt.setString(1, new Md5PasswordEncoder().encodePassword(icMasUser.getUserID(), icMasUser.getUserID()));
            preparedStmt.setString(2, icMasUser.getSid());
            preparedStmt.addBatch();
        }
        preparedStmt.executeBatch();

        // execute the java preparedstatement
        conn.close();
    }
}
