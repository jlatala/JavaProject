package agh.project;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Downloader {

	static URL Website;
	static Integer Timeout;
	static Integer Tries;
	
	public static String run() throws IOException{
		try{
		Reader reader=null;
		InputStream stream = null;
		BufferedReader bufferedreader;
		URLConnection connection = null;
		String content = null, tmp = null;
		readSettings();
		connection = Website.openConnection();
		connection.setConnectTimeout(Timeout*1000);

		do{
			try{
				stream = connection.getInputStream();
			}
			catch(java.net.SocketTimeoutException A)
			{
				System.out.println("Connection Timeout "+Timeout+" "+Tries);
				Tries--;
				if (Tries==0)
				{
					return"";
				}
			}
		}
		while(Tries>0 && Tries<3);
	
		String path = "local_website.html";
		reader = new InputStreamReader(stream);
		bufferedreader = new BufferedReader(reader);
		BufferedWriter writer = new BufferedWriter
		(new FileWriter(path));
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
		catch(java.net.UnknownHostException A){
			System.out.println("Unknown website");
			return "";
		}
	}
	
	public static void readSettings()
	{
		File properties = new File("src/agh/project/properties.txt");
		Scanner read=null;
		try
		{
			read = new Scanner(properties);
			String line;
			while ((line = read.nextLine())!=null)
			{
				if (line.startsWith("url"))
				{
					Website = new URL(line.substring("url".length()+1));
				}
				else if (line.startsWith("timeout"))
				{
					Timeout = new Integer(line.substring("timeout".length()+1));
				}
				else if (line.startsWith("try"))
				{
					Tries = new Integer(line.substring("try".length()+1));
				}
			    System.out.println(line);
			}	
		}
		catch (java.net.MalformedURLException A)
		{
			System.out.println("Malformed URL");
		}
		catch (FileNotFoundException A){
			System.out.println("File not found");
		}
		catch (java.util.NoSuchElementException A){
			read.close();
		}

	}
	
}
