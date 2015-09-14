package agh.project;

import java.io.IOException;
import java.io.File;

/**
 * This class is responsible for multithreading downloading.
 * @author Jakub Latala
 *
 */
public class DownloaderPool{

	public static boolean Done = false;
	public static String[] DownloadedContent;
	
	/**
	 * Multithreading downloading
	 * @param pac object of ParserPackage class
	 * @throws IOException
	 */
	public static void run(ParserPackage pac) throws IOException{

		DownloadedContent = new String[pac.link.size()];
        Runnable[] runners = new Runnable[pac.link.size()];
        Thread[] threads = new Thread[pac.link.size()];
        Boolean[] donethread = new Boolean[pac.link.size()];
        
        for(int i=0; i<pac.link.size(); i++) {
            //files[i] = new File("local_website"+i+".html");
            DownloadedContent[i] = new String();
        }
        for(int i=0; i<pac.link.size(); i++) {
            runners[i] = new Downloader(i, pac.satellite);
        }
 
        for(int i=0; i<pac.link.size(); i++) {
            threads[i] = new Thread(runners[i]);
        }
 
        for(int i=0; i<pac.link.size(); i++) {
            threads[i].start();
        }
        
        while (!Done)
        {
        	Boolean tmp = false;
            for(int i=0; i<pac.link.size(); i++) {
                donethread[i] = threads[i].isAlive();
                tmp = tmp || donethread[i];
            }
            Done = !tmp;
        }

	}
}
