package agh.project;
import org.jsoup.*;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;




public class Parser {
	
	//opcja z parsowaniem pliku
	public static void run(String file_path) throws IOException{
		File input = new File(file_path);
		org.jsoup.nodes.Document doc = Jsoup.parse(input, "UTF-8");
		
		//tytu³ bazy
		//Elements links = doc.select("title");
		
		//use regex
		//Elements links = doc.select("img[src~=k(ino).*\\.png]");
		
		//extract text "(.*){1,15}-(.*){1,5}"
		//Elements links = doc.select("a[href~=http://www\\.lyngsat\\.com/Intelsat-18\\.html]");
		Element table = doc.select("body").first();

		Iterator<Element> ite = table.select("table[width=720]").iterator();
		
		ite.next();
		ite.next();
		ite.next();
		ite.next();
		
		Iterator<Element> ite2 = ite.next().select("tr").iterator();
		ite2.next();
		
		
		
		do{
		Iterator<Element> ite3 = ite2.next().select("td").iterator();
		ite3.next();
		while(ite3.hasNext()){
		System.out.print(ite3.next().text());
		System.out.print(" ");
		}
		System.out.println();
		ite2.next();
		}while(ite2.hasNext());
		
		
		
		Vector<String> coordinates = new Vector<String>();
		Vector<String> name = new Vector<String>();
		Vector<String> last_change = new Vector<String>();
		String tmp;

		while(ite2.hasNext()){
			tmp = ite2.next().text().substring(2);
			String[] parts = tmp.split(" ");
			for(int i = 1;i<parts.length-3;i++){
				if(parts[i].contains(String.valueOf((char)176))){
					coordinates.add(parts[i]);
				}
				else{
					coordinates.add(parts[i-1]);
				}
				i++;
				name.add(parts[i].concat(parts[i+1]));
				i+=2;
				if(parts[i] != " "){
					last_change.add(parts[i]);
					i++;
				}
			}

		}
		
		/* use DOM
		Elements links = doc.getElementsByTag("table");
		for (Element link : links) {
			String linkHref = link.attr("bgcolor");
			//System.out.println(linkHref);
			String linkText = link.text();
			System.out.println(linkText);
		}
		*/
/*
		Vector<String> coordinates = new Vector<String>();
		Vector<String> name = new Vector<String>();
		Vector<String> last_change = new Vector<String>();

		String tmp_string = ite.next().text();
		System.out.println(tmp_string);
		String[] parts = tmp_string.split(" ");
		
		
		for(int i = 1;i<parts.length-3;i++){
			coordinates.add(parts[i]);
			i++;
			name.add(parts[i].concat(parts[i+1]));
			i++; i++;
			if(parts[i] != " "){
				last_change.add(parts[i]);
				i++;
			}
		}
		for(int i=0;i<coordinates.size();i++){		
			System.out.println(coordinates.elementAt(i));
			//i++;
			//System.out.print(" ");
			//System.out.println(name.elementAt(i));
		}*/
		/*
		System.out.println(parts[0]);
		System.out.println(parts[1]);
		System.out.println(parts[2]);
		System.out.println(parts[3]);
		System.out.println(parts[4]);
		System.out.println(parts[5]);
		System.out.println(parts[6]);
		System.out.println(parts[7]);
		System.out.println(parts[8]);
		System.out.println(parts[9]);
		System.out.println(parts[10]);*/
	}	
	
	/* opcja z parsowaniem Stringa
	public static void run(String html)
	{		
		org.jsoup.nodes.Document doc = Jsoup.parse(html);
		Elements links = doc.select("img[src~=(?i)\\.(png)]");
		System.out.println(links);
	}
	*/
}
//zapisac contetnt do pliku i parsowac dopiero plik lokalny