package agh.project;
import org.jsoup.*;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

public class Parser {
	//3 wektory w ktorych przechowuje 3 kolumny ze strony, pozniej uzywane w main do tworzenia
	//wpisow do bazy danych
	public static Vector<String> coordinates = new Vector<String>();
	public static Vector<String> names = new Vector<String>();
	public static Vector<String> last_update = new Vector<String>();

	public static void run(String file_path) throws IOException{
		File input = new File(file_path);
		org.jsoup.nodes.Document doc = Jsoup.parse(input, "UTF-8");
		
		//tytu³ bazy
		//Elements links = doc.select("title");
		
		//use regex
		//Elements links = doc.select("img[src~=k(ino).*\\.png]");
		
		//extract text "(.*){1,15}-(.*){1,5}"
		//Elements links = doc.select("a[href~=http://www\\.lyngsat\\.com/Intelsat-18\\.html]");
		
		
		//TA CZESC POLEGA NA PRZESKAKIWANIU PO KOLEJNYCH TAGACH PLIKU HTML I WYCIAGANIU ODPOWIEDNICH 
		//DANYCH (TEKSTU), A NASTEPNIE ZAPISANIU ICH DO NASZYCH ATRYBUTOW (WEKTOROW KLASY).
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
				coordinates.add(coordinates.lastElement());
				i=2;
			}
			switch(i){
			case 1: coordinates.add(tmp); break;
			case 2: names.add(tmp); break;
			case 3: last_update.add(tmp); break;
			default: last_update.add(""); break;
			}
			i++;
			
		}
		}while(ite2.hasNext());
		
		/*
		for(int i=0;i<coordinates.size();i++){		
			System.out.print(coordinates.elementAt(i));
			System.out.print(" ");
			System.out.print(names.elementAt(i));
			System.out.print(" ");
			System.out.println(last_change.elementAt(i));
			
		}
		*/
	}
}
