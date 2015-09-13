package agh.project;
import org.jsoup.*;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

public class Parser {
	public Vector<Double> Coordinates = new Vector<Double>();
	public Vector<String> EW = new Vector<String>();	
	public Vector<String> Name = new Vector<String>();
	public Vector<String> Last_Update = new Vector<String>();

	public void run(Integer _Instance) throws IOException{
		
		
		//while(DownloaderPool.DownloadedContent[_Instance].isEmpty());
		//System.out.println(DownloaderPool.DownloadedContent[_Instance]);
		if(DownloaderPool.DownloadedContent[_Instance]=="")
		{
			return;
		}
		String input = new String(DownloaderPool.DownloadedContent[_Instance]);
		org.jsoup.nodes.Document doc = Jsoup.parse(input, "UTF-8");
		
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
