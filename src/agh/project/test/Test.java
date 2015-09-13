package agh.project.test;
import java.io.*;
import java.util.List;

import agh.project.DownloaderPool;
import agh.project.GUI;
import agh.project.Parser;
import agh.project.SQLite;
import agh.project.Satellites;
public class Test {

	
		public static String HugeText;
		public static SQLite DataBase;
		// TODO Auto-generated method stub
		public static void main(String[] args) throws IOException {
			
		HugeText = new String();
		//DownloaderPool.run();
		//while(!DownloaderPool.Done);

		//System.out.println(DownloaderPool.DownloadedContent[1]);
		
		//DataBase = new SQLite();

		//System.out.println(DownloaderPool.Done);
		//SQLite.run(DataBase);
	//poki co parsuje z pliku lokalnego ktory wczesniej zapisalem
									// do naszego folderu, asia.html, europe.html itd.
		

		//List<Satellites> satellite = DataBase.selectSatellites();
		
		//for(Satellites sat: satellite)
			//HugeText+=sat+"\n";
		//System.out.println(HugeText);
		//HugeText = "Benjamin";
		//while (HugeText ==null);
		GUI G1 = new GUI();
		G1.setVisible(true);
		G1.setResizable(false);
		
		

		
	}
}

