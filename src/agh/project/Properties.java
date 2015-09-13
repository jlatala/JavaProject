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
 * This class 
 * @author Jakub Latala
 * @version 1.0
 */
public class Properties {
	
	static final String name="";
	public static String region[] = {"asia", "europe", "atlantic", "america"};
	
	public static void initProp() throws IOException{	
		createProp();
		for(int i=0;i<4;i++){
			addWebsite(region[i], "", "http://www.lyngsat.com/", 3, 3);
		}
		for(int i=0;i<4;i++){
			addWebsite(region[i], "Pac", "http://www.lyngsat.com/packages/", 3, 3);
		}
	}
	
	public static void createProp() throws IOException{
		deleteProp();
		FileWriter wr = new FileWriter("src/agh/project/properties.txt");
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("src/agh/project/properties.txt", true)));
		out.println("List of websites to download with their parameters.");
		out.close();
		wr.close();
	}
	
	public static void deleteProp(){
		File prop = new File("src/agh/project/properties.txt");
		prop.delete();
	}
	
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
