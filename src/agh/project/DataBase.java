package agh.project;
import java.sql.Statement;

import agh.project.SQLite;
public class DataBase {

	public static SQLite Content;
	 
	public static void Close()
	{
		Content.deleteTables();
		Content.closeConnection();
	}
	
	public static Double findMin(String A)
	{
		
		
		return null;
		
	}
	
}
