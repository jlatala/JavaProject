package agh.project;

import java.io.IOException;
import java.io.File;
public class DownloaderPool{

	public static boolean Done = false;
	public static String[] DownloadedContent;
	public static void run() throws IOException{

		Downloader.readSettings();
		//DownloadedContent[] = new String[Downloader.Website.size()];
		//File[] files = new File[Downloader.Website.size()];
		DownloadedContent = new String[Downloader.Website.size()];
        Runnable[] runners = new Runnable[Downloader.Website.size()];
        Thread[] threads = new Thread[Downloader.Website.size()];
        Boolean[] donethread = new Boolean[Downloader.Website.size()];
        
        for(int i=0; i<Downloader.Website.size(); i++) {
            //files[i] = new File("local_website"+i+".html");
            DownloadedContent[i] = new String();
        }
        for(int i=0; i<Downloader.Website.size(); i++) {
            runners[i] = new Downloader(i);
        }
 
        for(int i=0; i<Downloader.Website.size(); i++) {
            threads[i] = new Thread(runners[i]);
        }
 
        for(int i=0; i<Downloader.Website.size(); i++) {
            threads[i].start();
        }
        
        while (!Done)
        {
        	Boolean tmp = false;
            for(int i=0; i<Downloader.Website.size(); i++) {
                donethread[i] = threads[i].isAlive();
                tmp = tmp || donethread[i];
            }
            Done = !tmp;
        }

	}
}
