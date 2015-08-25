package agh.project.test;
import java.io.*;

import agh.project.Downloader;
import agh.project.DownloaderPool;
import agh.project.Parser;

public class Test {

		// TODO Auto-generated method stub

		public static void main(String[] args) throws IOException {
		//Downloader D = new Downloader(1);
		//String tmp = new String(Downloader.run(1));
		//Parser.run(tmp);
		//System.out.println("Salata ladny");
		//String tmp = new String(Downloader.run());
		//System.out.println(tmp);
			Downloader.readSettings();
			System.out.println(Downloader.Website.size());
	    Runnable[] runners = new Runnable[10];
	    Thread[] threads = new Thread[10];
	 
	        for(int i=0; i<Downloader.Website.size(); i++) {
	            runners[i] = new DownloaderPool(i);
	        }
	 
	        for(int i=0; i<Downloader.Website.size(); i++) {
	            threads[i] = new Thread(runners[i]);
	        }
	 
	        for(int i=0; i<Downloader.Website.size(); i++) {
	            threads[i].start();
	        }
		//Parser.run("asia.html");
	}
}

