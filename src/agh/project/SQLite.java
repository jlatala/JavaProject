package agh.project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

 
public class SQLite {
 
    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:Satellites.db";
 
    private Connection conn;
    private Statement stat;
 
    public SQLite() {
        try {
            Class.forName(SQLite.DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("Lack of driver JDBC");
            e.printStackTrace();
        }
 
        try {
            conn = DriverManager.getConnection(DB_URL);
            stat = conn.createStatement();
        } catch (SQLException e) {
            System.err.println("Cannot open connection");
            e.printStackTrace();
        }
 
        createTables();
    }
 
    public boolean createTables()  {
        String createSatellites = "CREATE TABLE IF NOT EXISTS satellites (coordinates varchar(255), names varchar(255), last_update varchar(255))";
        try {
            stat.execute(createSatellites);  
        } catch (SQLException e) {
            System.err.println("Error during creating table");
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public boolean deteleTables()  {
        String deleteSatellites = "DELETE FROM satellites";
    	//String deleteSatellites = "DROP TABLE IF EXISTS satellites";
        try {
            stat.execute(deleteSatellites);  
        } catch (SQLException e) {
            System.err.println("Error during deleting table");
            e.printStackTrace();
            return false;
        }
        return true;
    }
 
    public boolean insertSatellites(String coordinate, String name, String last_updt) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement(
                    "insert into satellites values (?, ?, ?);");
            prepStmt.setString(1, coordinate);
            prepStmt.setString(2, name);
            prepStmt.setString(3, last_updt);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Error during inserting satellite");
            e.printStackTrace();
            return false;
        }
        return true;
    }
 
    public List<Satellites> selectSatellites() {
        List<Satellites> satellites = new LinkedList<Satellites>();
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM satellites WHERE SUBSTRING(coordinates, 0, coordinates.length()-2) > 120");
            String coordinate, name, last_updt;
            while(result.next()) {
                coordinate = result.getString("coordinates");
                name = result.getString("names");
                last_updt = result.getString("last_update");               
                satellites.add(new Satellites(coordinate, name, last_updt));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return satellites;
    }
 
    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.err.println("Error during closing connection");
            e.printStackTrace();
        }
    }
}