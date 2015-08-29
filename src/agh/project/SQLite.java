package agh.project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

//tu sie dzieje magia
public class SQLite {
 
    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:AsiaSatellites.db";
 
    private Connection conn;
    private Statement stat;
    
    //inicjalizacja bazy danych SQLtie
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
    //tworzenie naszej tabeli
    public boolean createTables()  {
        String createSatellites = "CREATE TABLE IF NOT EXISTS Satellites (Name varchar(255), Coordinates double, EW varchar(255), Last_Update varchar(255))";
        try {
            stat.execute(createSatellites);  
        } catch (SQLException e) {
            System.err.println("Error during creating table");
            e.printStackTrace();
            return false;
        }
        return true;
    }
    //usuwanie tabeli, analogicznie do tworzenia
    public boolean deleteTables()  {
        String deleteSatellites = "DELETE FROM satellites";
        try {
            stat.execute(deleteSatellites);  
        } catch (SQLException e) {
            System.err.println("Error during deleting table");
            e.printStackTrace();
            return false;
        }
        return true;
    }
    //dodawanie jednego rekordu
    public boolean insertSatellites(String Name, Double Coordinates, String EW, String Last_Updt) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement(
                    "insert into satellites values (?, ?, ?, ?);");
            prepStmt.setString(1, Name);
            prepStmt.setDouble(2, Coordinates);
            prepStmt.setString(3, EW);
            prepStmt.setString(4, Last_Updt);            
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Error during inserting satellite");
            e.printStackTrace();
            return false;
        }
        return true;
    }
    //zwracanie bazy z postaci listy
    public List<Satellites> selectSatellites() {
        List<Satellites> satellites = new LinkedList<Satellites>();
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM satellites WHERE Coordinates > 155 AND Coordinates < 170"); // WHERE coordinates.substring(0, coordinates.length() - 2) > 100.5");
            String Name, EW, Last_Updt;
            Double Coordinates;
            while(result.next()) {
                Name = result.getString("Name");
                Coordinates = result.getDouble("Coordinates");
                EW = result.getString("EW");   
                Last_Updt = result.getString("Last_Update");
                satellites.add(new Satellites(Name, Coordinates, EW, Last_Updt));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return satellites;
    }
    //konczenie pracy z baza
    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.err.println("Error during closing connection");
            e.printStackTrace();
        }
    }
}