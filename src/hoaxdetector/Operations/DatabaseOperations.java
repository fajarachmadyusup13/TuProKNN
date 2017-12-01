/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hoaxdetector.Operations;

import hoaxdetector.Models.News;
import hoaxdetector.Utils.Limits;
import hoaxdetector.Utils.Operations;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ghost
 */
public class DatabaseOperations implements Operations.DatabaseOperations{

    @Override
    public Connection establishConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Hoax_Detector", "root", "");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }

    

    @Override
    public void updateData(Connection con, News news) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<News> getDataTraining(Connection con) {
        ArrayList<News> ns = new ArrayList<>();
        try {
            Statement stmt = con.createStatement(); 
            ResultSet rs = stmt.executeQuery("SELECT * FROM Data_Training WHERE newsID BETWEEN '"+ Limits.trainingFirstLim +"' AND '"+ Limits.trainingLastLim +"' ORDER BY newsID");
            while (rs.next()) {
                News n = new News(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6));
                ns.add(n);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ns;
    }
    
    @Override
    public ArrayList<News> getRangedDataTraining(Connection con, String start, String end) {
        ArrayList<News> ns = new ArrayList<>();
        try {
            Statement stmt = con.createStatement(); 
            ResultSet rs = stmt.executeQuery("SELECT * FROM Data_Training WHERE newsID BETWEEN '"+ start +"' AND '"+ end +"' ORDER BY newsID");
            while (rs.next()) {
                News n = new News(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6));
                ns.add(n);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ns;
    }

    @Override
    public ArrayList<News> getDataTest(Connection con) {
        ArrayList<News> ns = new ArrayList<>();
        try {
            Statement stmt = con.createStatement(); 
            ResultSet rs = stmt.executeQuery("SELECT * FROM Data_Training WHERE newsID BETWEEN '"+ Limits.testFirstLim +"' AND '"+ Limits.testLastLim +"' ORDER BY newsID");
            while (rs.next()) {
                News n = new News(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6));
                ns.add(n);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ns;
    }

    
    
}
