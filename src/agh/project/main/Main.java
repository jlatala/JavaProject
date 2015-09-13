package agh.project.main;
import java.io.*;
import java.util.List;

import agh.project.Downloader;
import agh.project.Parser;
import agh.project.ParserPackage;
import agh.project.SQLite;
import agh.project.Satellites;
import agh.project.Properties;
import agh.project.GUI;
import agh.project.Log4j;

public class Main {

		public static void main(String[] args) throws IOException {
		
		
		/*
		String s1 = "101.4";
		float i1 = Float.parseFloat(s1)
		System.out.println(i1);
		*/
		
		Log4j.log.info("Creating 'poperties' file");
		Properties.initProp();
		//Properties.deleteWebsite("***europe***");
		Log4j.log.info("Downloading websites sources");
		Downloader.readSettings();
		
		GUI form = new GUI();
		form.setVisible(true);
		form.setResizable(false);
		//System.out.println("Parsuje");
		//Parser par = new Parser();
		//par.run("asia.html");
		//par.run("europe.html");
		//par.run("atlantic.html");
		//par.run("america.html");
		
		//System.out.println("Tworze baze");
		//SQLite dataBase = new SQLite();
		
		//System.out.println("Zapisuje do bazy");
		//for(int i=0;i<par.Coordinates.size();i++){
		//	dataBase.insertSatellites(par.Name.elementAt(i), par.Coordinates.elementAt(i), par.EW.elementAt(i), par.Last_Update.elementAt(i));
		//}
		
		//System.out.println("Odczytuje z bazy");
		//List<Satellites> satellite = dataBase.selectSatellites("SELECT * From satellites"); //(form.generateSQLQuery());
		
		//System.out.println("Drukuje w oknie");
		//for(Satellites sat: satellite)
		//	form.showSatList(sat);

	//	System.out.println("Koniec");
		
		//dataBase.deleteTables();
		//dataBase.closeConnection();
		
		
		
		
	}
}

