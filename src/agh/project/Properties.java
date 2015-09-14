package agh.project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Vector;

/**
 * This class manage with properties.txt file.
 * @author Jakub Latala
 */
public class Properties {
	
	static final String name="";
	public static String region[] = {"asia", "europe", "atlantic", "america"};
	
	/**
	 * Initialize properties.txt file
	 * @throws IOException
	 */
	public static void initProp() throws IOException{	
		createProp();
		for(int i=0;i<4;i++){
			addWebsite(region[i], "", "http://www.lyngsat.com/", 3, 3);
		}
		for(int i=0;i<4;i++){
			addWebsite(region[i], "Pac", "http://www.lyngsat.com/packages/", 3, 3);
		}
	}
	
	/**
	 * Creates properties.txt file
	 * @throws IOException
	 */
	public static void createProp() throws IOException{
		deleteProp();
		FileWriter wr = new FileWriter("src/agh/project/properties.txt");
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("src/agh/project/properties.txt", true)));
		out.println("List of websites to download with their parameters.");
		out.close();
		wr.close();
	}
	
	/**
	 * Delete properties.txt file
	 */
	public static void deleteProp(){
		File prop = new File("src/agh/project/properties.txt");
		prop.delete();
	}
	
	/**
	 * Adds website with parameters to properties.txt file
	 * @param regName name of region
	 * @param regNamePac for Packages websites
	 * @param url lyngsat url
	 * @param timeout timeout
	 * @param attempts number of tries
	 * @throws IOException
	 */
	public static void addWebsite(String regName, String regNamePac, String url, int timeout, int attempts) throws IOException{
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("src/agh/project/properties.txt", true)))) {
			out.println();
		    out.println("***"+regName+regNamePac+"***");		    
		    out.println("url "+url+regName+".html");
		    out.println("timeout "+timeout);
		    out.print("try "+attempts);
			
		    out.close();
		}catch (IOException e) {
			Log4j.log.error(e, e);
		}
	}
	
	/**
	 * Delete website with parameters from properties.txt file
	 * @param url lyngsat url
	 * @throws IOException
	 */
	public static void deleteWebsite(String url) throws IOException{
		File inputFile = new File("src/agh/project/properties.txt");
		File tempFile = new File("tmpFile.txt");

		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

		String currentLine;

		while((currentLine = reader.readLine()) != null) {
		    // trim newline when comparing with lineToRemove
		    String trimmedLine = currentLine.trim();
		    if(trimmedLine.equals(url)) {
		    	for(int i=0;i<4;i++){ reader.readLine();};
		    	continue;
		    }
		    writer.write(currentLine + System.getProperty("line.separator"));
		}
		// regions.remove(url); PAMIETAJ o usunieciu regionu z wektora
		writer.close(); 
		reader.close(); 
		deleteProp();
		tempFile.renameTo(inputFile);
	}
	
}
