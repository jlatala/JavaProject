package agh.project.test;
import java.io.*;
import java.util.List;

import agh.project.Downloader;
import agh.project.Parser;
import agh.project.SQLite;
import agh.project.Satellites;
import agh.project.Properties;
import agh.project.GUI;

public class Test {

		public static void main(String[] args) throws IOException {
		
		System.out.println("Otwieram okno");
		GUI form = new GUI();
		form.setVisible(true);
		
		/*
		String s1 = "101.4";
		float i1 = Float.parseFloat(s1)
		System.out.println(i1);
		*/
		
		System.out.println("Tworze plik Properties");
		Properties.initProp();
		//Properties.deleteWebsite("***europe***");
		System.out.println("Pobieram strony");
		Downloader.readSettings();
		
		System.out.println("Parsuje");
		Parser par = new Parser();
		par.run("asia.html");
		//par.run("europe.html");
		//par.run("atlantic.html");
		//par.run("america.html");
		
		
		//Satellites s = new Satellites("35E","nazwa mojej sat", "021015");
		System.out.println("Tworze baze");
		SQLite dataBase = new SQLite();
		System.out.println("Zapisuje do bazy");
		for(int i=0;i<par.coordinates.size();i++){
			dataBase.insertSatellites(par.coordinates.elementAt(i), par.names.elementAt(i), par.last_update.elementAt(i));
		}
		System.out.println("Odczytuje z bazy");
		List<Satellites> satellite = dataBase.selectSatellites();
		System.out.println("Drukuje w oknie");
		for(Satellites sat: satellite)
			form.showSatList(sat);
		
		Float f = Float.parseFloat(par.coordinates.elementAt(0).substring(0, par.coordinates.elementAt(0).length() - 2));
		if(f>100.5)
			System.out.println("OK");
		System.out.println("Koniec");
		
		dataBase.deteleTables();
		dataBase.closeConnection();
		
		
	}
}

