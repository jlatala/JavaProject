package agh.project;
import agh.project.SQLite;
public class DataBase {

	public static SQLite Content;
	
	public static void Close()
	{
		Content.deleteTables();
		Content.closeConnection();
	}
	
}
