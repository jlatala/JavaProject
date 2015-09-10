package agh.project.test;
import java.io.*;
import java.util.List;

import agh.project.Downloader;
import agh.project.Parser;
import agh.project.SQLite;
import agh.project.Satellites;
import agh.project.Properties;
public class Test {

		// TODO Auto-generated method stub

		public static void main(String[] args) throws IOException {
		Properties.initProp();
		//Properties.deleteWebsite("***europe***");
		
		Downloader.readSettings();
		
			
		Parser asia = new Parser();
		asia.run("asia.html");
		asia.run("europe.html");
		asia.run("atlantic.html");
		asia.run("america.html");
		
		
		//Satellites s = new Satellites("35E","nazwa mojej sat", "021015");
		SQLite dataBase = new SQLite();
		
		for(int i=0;i<asia.coordinates.size();i++){
			dataBase.insertSatellites(asia.coordinates.elementAt(i), asia.names.elementAt(i), asia.last_update.elementAt(i));
		}
	/*	for(int i=0;i<europe.coordinates.size();i++){
			dataBase.insertSatellites(europe.coordinates.elementAt(i), europe.names.elementAt(i), europe.last_update.elementAt(i));
		}
		for(int i=0;i<atlantic.coordinates.size();i++){
			dataBase.insertSatellites(atlantic.coordinates.elementAt(i), atlantic.names.elementAt(i), atlantic.last_update.elementAt(i));
		}
		for(int i=0;i<america.coordinates.size();i++){
			dataBase.insertSatellites(america.coordinates.elementAt(i), america.names.elementAt(i), america.last_update.elementAt(i));
		}*/
		List<Satellites> satellite = dataBase.selectSatellites();
		
		for(Satellites sat: satellite)
			System.out.println(sat);
		//System.out.println(par.coordinates.elementAt(0).substring(0, par.coordinates.elementAt(0).length() - 2));
		dataBase.deteleTables();
		dataBase.closeConnection();
		
		
	}
}

