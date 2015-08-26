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
		
		
		SQLite dataBase = new SQLite(); //tworze obiekt klasy SQLite
		
		//sparsowane dane (przez klase Parse) wyciagam z wektorow coordinates, names i last_update
		//przekazuje je do funkcji tworzacej jeden wpis do bazy danych (jedna satelita), to wszystko w petli
		for(int i=0;i<par.coordinates.size();i++){
			dataBase.insertSatellites(par.coordinates.elementAt(i), par.names.elementAt(i), par.last_update.elementAt(i));
		}
		//selectSatellites zwraca liste satelit (cala baze danych)
		List<Satellites> satellite = dataBase.selectSatellites();
		
		//wyswietlam baze
		for(Satellites sat: satellite)
			System.out.println(sat);
		
		//poki co kasuje cala baze, aby przy kolejnym utworzeniu nie dopisywac do niej, tylko tworzyc nowa
		dataBase.deteleTables();
		dataBase.closeConnection();
	}
}

