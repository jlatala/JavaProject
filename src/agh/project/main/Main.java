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

/**
 * This is Main class of SatelliteParser application.
 * @author Jakub Latala
 *
 */
public class Main {
		
	/**
	 * This is main function.
	 * @param args
	 * @throws IOException
	 */
		public static void main(String[] args) throws IOException {
		File f1 = new File("src\\agh\\project"); f1.mkdirs();
		File f2 = new File("channels"); f2.mkdirs();
		File f3 = new File("channelsSources"); f3.mkdirs();
		Log4j.log.info("Creating 'poperties' file");
		Properties.initProp();
		//Properties.deleteWebsite("***europe***");
		Log4j.log.info("Downloading websites sources");
		Downloader.readSettings();
		
		GUI form = new GUI();
		form.setVisible(true);
		form.setResizable(false);
	}
}

