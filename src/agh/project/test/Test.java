package agh.project.test;
import java.io.*;
import java.util.List;

import agh.project.DownloaderPool;
import agh.project.Parser;
import agh.project.SQLite;
import agh.project.Satellites;
public class Test {

		// TODO Auto-generated method stub
		public static void main(String[] args) throws IOException {
		DownloaderPool.run();
		while(!DownloaderPool.Done);

		//System.out.println(DownloaderPool.DownloadedContent[1]);
		
		SQLite dataBase = new SQLite();
		//System.out.println(DownloaderPool.Done);
		SQLite.run(dataBase);
	//poki co parsuje z pliku lokalnego ktory wczesniej zapisalem
									// do naszego folderu, asia.html, europe.html itd.
		

		List<Satellites> satellite = dataBase.selectSatellites();
		
		for(Satellites sat: satellite)
			System.out.println(sat);
		dataBase.deleteTables();
		dataBase.closeConnection();
	}
}

