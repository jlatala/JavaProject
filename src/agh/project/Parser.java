package agh.project;
import org.jsoup.*;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

/**
 * This class is responsible for parsing website with list of satellites.
 * @author Jakub Latala
 *
 */
public class Parser {
	public static Vector<Double> Coordinates = new Vector<Double>();
	public static Vector<String> EW = new Vector<String>();	
	public static Vector<String> Name = new Vector<String>();
	public static Vector<String> Last_Update = new Vector<String>();

	/**
	 * Parse website to vectors.
	 * @param file_path path to downloaded website source
	 * @throws IOException
	 */
	public static void run(String file_path) throws IOException{
		File input = new File(file_path);
		org.jsoup.nodes.Document doc = Jsoup.parse(input, "UTF-8");
		
		
		Element table = doc.select("body").first();

		Iterator<Element> ite = table.select("table[width=720]").iterator();
		
		ite.next();
		ite.next();
		ite.next();
		ite.next();
		
		Iterator<Element> ite2 = ite.next().select("tr").iterator();
		ite2.next();
		String tmp;
		
		
		do{
		Iterator<Element> ite3 = ite2.next().select("td").iterator();
		ite3.next();
		int i=1;
		while(ite3.hasNext()){
			tmp = ite3.next().text();
			if(!tmp.contains(String.valueOf((char)176)) && i==1){
				Coordinates.add(Coordinates.lastElement());
				EW.add(EW.lastElement());
				i=2;
			}
			switch(i){
			case 1: 
				{
					String EWtmp =new String(tmp.substring(tmp.indexOf("°")+1, tmp.indexOf("°")+2));
					Coordinates.add(Double.parseDouble(tmp.substring(0, tmp.indexOf("°")))); 
					EW.add(EWtmp);
				}
				break;
			case 2: Name.add(tmp); break;
			case 3: Last_Update.add(tmp); break;
			default: Last_Update.add(""); break;
			}
			i++;
			
		}
		}while(ite2.hasNext());
	}
}
