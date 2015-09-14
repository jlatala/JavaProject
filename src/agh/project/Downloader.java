package agh.project;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Scanner;

/**
 * This class is responsible for reading from propersiet.txt file and downloading website sources.
 * @author Jakub Latala
 *
 */
public class Downloader implements Runnable{
	
	static String Name;
	static URL Website;
	static Integer Timeout;
	static Integer Tries;
	static List<String> urlStr;
	Integer Instance;
	
	/**
	 * Constructor for multithreading downloading
	 * @param _Instance instance of Downloader
	 * @param s lyngsat url
	 */
	Downloader(Integer _Instance, List<String> s)
	{
		Instance = _Instance;
		urlStr = s;
	}
	
	/**
	 * Download website source
	 * @return string path
	 * @throws IOException
	 */
	public static String download() throws IOException{
		try{
		Reader reader=null;
		InputStream stream = null;
		BufferedReader bufferedreader;
		URLConnection connection = null;
		String content = null, tmp = null;
		
		connection = Website.openConnection();
		connection.setConnectTimeout(Timeout*1000);

		do{
			try{
				stream = connection.getInputStream();
			}
			catch(java.net.SocketTimeoutException e)
			{
				Log4j.log.error("Connection Timeout "+Timeout+" "+Tries);
				Log4j.log.error(e, e);
				Tries--;
				if (Tries==0)
				{
					return"";
				}
			}
		}
		while(Tries>0 && Tries<3);
	
		String path = Name+".html";
		reader = new InputStreamReader(stream);
		bufferedreader = new BufferedReader(reader);
		BufferedWriter writer = new BufferedWriter(new FileWriter(path));
		String line;
		while ((line=bufferedreader.readLine())!=null)
		{
			writer.write(line);
			writer.newLine();
		}
		reader.close();
		writer.close();
		return path;
		}
		catch(java.net.UnknownHostException e){
			Log4j.log.error("Unknown website");
			Log4j.log.error(e, e);
			return "";
		}
	}
	
	/**
	 * Read from properties.txt file
	 * @throws IOException
	 */
	public static void readSettings() throws IOException
	{
		File properties = new File("src/agh/project/properties.txt");
		Scanner read=null;
		try
		{
			read = new Scanner(properties);			
			String line;
			int i=0;
			while ((line = read.nextLine())!=null)
			{			
				if (line.startsWith("***"))
				{
					Name = line.substring("***".length(),line.length()-3);
					line = read.nextLine();
					Website = new URL(line.substring("url".length()+1));
					line = read.nextLine();
					Timeout = new Integer(line.substring("timeout".length()+1));
					line = read.nextLine();
					Tries = new Integer(line.substring("try".length()+1));
					download();
					i++;
				}
			}	
		}
		catch (java.net.MalformedURLException e)
		{
			Log4j.log.error("Malformed URL");
			Log4j.log.error(e, e);
		}
		catch (FileNotFoundException e){
			Log4j.log.error("File not found");
			Log4j.log.error(e, e);
		}
		catch (java.util.NoSuchElementException e){
			//Log4j.log.error(e, e);
			read.close();
		}

	}
	
	/**
	 * Method for multithreading downloading
	 */
	@Override
	public void run() {
		try{
			Reader reader=null;
			InputStream stream = null;
			BufferedReader bufferedreader;
			URLConnection connection = null;
			String content = null, tmp = null;
			
			URL url = new  URL(urlStr.get(Instance));
			connection = url.openConnection();
			//connection.setConnectTimeout(3000);

			
			try{
				stream = connection.getInputStream();
			}
			catch(java.net.SocketTimeoutException e)
			{
				Log4j.log.error("Cannot download channel website");
				Log4j.log.error(e, e);
			}
			catch(java.io.FileNotFoundException e)
			{
				//Log4j.log.error(e);
			}
			
		
			String path = "channelsSources/"+urlStr.get(Instance).substring("http://www.lyngsat.com/".length());
			reader = new InputStreamReader(stream);
			bufferedreader = new BufferedReader(reader);
			BufferedWriter writer = new BufferedWriter(new FileWriter(path));
			String line;
			while ((line=bufferedreader.readLine())!=null)
			{
				writer.write(line);
				writer.newLine();
			}
			reader.close();
			writer.close();
			Log4j.log.info("Download succeed, "+path);
			}
			catch(java.net.UnknownHostException e){
				Log4j.log.error("Unknown website");
				Log4j.log.error(e, e);
				
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}		
	}
	
	
	
}
