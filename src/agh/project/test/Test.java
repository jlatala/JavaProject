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
		
		for(int i=0;i<par.Coordinates.size();i++){
			dataBase.insertSatellites(par.Name.elementAt(i), par.Coordinates.elementAt(i), par.EW.elementAt(i), par.Last_Update.elementAt(i));
		
		//sparsowane dane (przez klase Parse) wyciagam z wektorow coordinates, names i last_update
		//przekazuje je do funkcji tworzacej jeden wpis do bazy danych (jedna satelita), to wszystko w petli
		}
		//selectSatellites zwraca liste satelit (cala baze danych)
		List<Satellites> satellite = dataBase.selectSatellites();
		
		//wyswietlam baze
		for(Satellites sat: satellite)
			System.out.println(sat);
		//System.out.println(par.coordinates.elementAt(0).substring(0, par.coordinates.elementAt(0).length() - 2));
		dataBase.deleteTables();
		dataBase.closeConnection();
	}
}

