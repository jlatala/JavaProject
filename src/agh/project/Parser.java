package agh.project;
import org.jsoup.*;
import org.jsoup.select.Elements;

public class Parser {
	
	public static void run(String html)
	{		
		org.jsoup.nodes.Document doc = Jsoup.parse(html);
		Elements links = doc.select("tr > td > b");
		System.out.println(links);
	}

}
