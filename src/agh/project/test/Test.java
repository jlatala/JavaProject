package agh.project.test;
import java.io.*;
import java.util.List;

import agh.project.Downloader;
import agh.project.Parser;
import agh.project.SQLite;
import agh.project.Satellites;
public class Test {

		// TODO Auto-generated method stub

		public static void main(String[] args) throws IOException {
		//String tmp = new String(Downloader.run());
		//System.out.println(tmp);
		Parser par = new Parser();
		par.run("asia.html"); 	//poki co parsuje z pliku lokalnego ktory wczesniej zapisalem
									// do naszego folderu, asia.html, europe.html itd.
		//Satellites s = new Satellites("35E","nazwa mojej sat", "021015");
		SQLite dataBase = new SQLite();
		
		for(int i=0;i<par.coordinates.size();i++){
			dataBase.insertSatellites(par.coordinates.elementAt(i), par.names.elementAt(i), par.last_update.elementAt(i));
		}
		List<Satellites> satellite = dataBase.selectSatellites();
		
		for(Satellites sat: satellite)
			System.out.println(sat);
		
		dataBase.deteleTables();
		dataBase.closeConnection();
	}
}

