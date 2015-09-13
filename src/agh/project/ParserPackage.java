/**
 * Projekt Java 
 * Micha³ Zajdel 258965
 * Temat: 1A5. Kana³y satelitarne (www.lyngsat.com HOTBIRD pakiety) - zapis txt
 */

package agh.project;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Klasa parsujace pobrane pliki
 * 
 * @author Jakub Latala
 */
public class ParserPackage {
	
	public static void main(String[] args) throws IOException{	
		Parser par = new Parser();
		//par.run("asia.html");
		par.run("europe.html");
		
		ParserPackage parP = new ParserPackage("europePac.html");
		parP.LoadFile();
		
		for(int j=0;j<par.Name.size();j++){
			if(j!=0 && par.Name.elementAt(j)!=par.Name.elementAt(j-1))
				parP.ParsePackages(par.Name.elementAt(j));
		}
		for(int i=0;i<parP.satelita.size();i++)
			System.out.println(parP.Stopnie(i));
		System.out.println(parP.CheckboxSize());
		ParserPackage parP2 = new ParserPackage("EutelsatHotBird.html");		
		parP2.ParseChannels();
		parP2.SaveToFile("channels/Eutelsat Hot Bird 13B.txt");
	}
	
	/** nazwa satelity */
	private List<String> satelita = new ArrayList<String>();
	/** nazwa pakietu */
	private List<String> pakiet = new ArrayList<String>();
	/** adres strony pakietu */
	private List<String> link = new ArrayList<String>();
	/** data modyfikacji */
	private List<String> update = new ArrayList<String>();
	/** pozycja orbitalna */
	private List<String> stopnie = new ArrayList<String>();
	/** suma nazw do checkboxa w gui */
	private List<String> checkbox = new ArrayList<String>();

	/** nazwa kanalu */
	private List<String> ChannelName = new ArrayList<String>();
	/** cechy */
	private List<String> Features = new ArrayList<String>();
	/** szyfrowanie */
	private List<String> Encryption = new ArrayList<String>();
	/** numer kanalu */
	private List<String> ChNo = new ArrayList<String>();
	/** sid */
	private List<String> SID = new ArrayList<String>();
	/** vpid */
	private List<String> VPID = new ArrayList<String>();
	/** apid */
	private List<String> APID = new ArrayList<String>();
	/** czestotliwosc */
	private List<String> Frequency = new ArrayList<String>();
	/** suma nazw zwiazanych z kanalami */
	private List<String> Final = new ArrayList<String>();

	/** SD/clear */
	private String SD_cl;
	/** SD/encrypted */
	private String SD_en;
	/** HD/clear */
	private String HD_cl;
	/** HD/encrypted */
	private String HD_en;
	/** interactive */
	private String inter;

	/** nazwa pliku parsowanego */
	private String file;
	/** tytul strony */
	private String title;
	/** ostatnia aktualiazacja */
	private String upd;
	/** dokument obslugiwany przez jsoup */
	Document doc;

	/** logger */
	public static final Logger log = Logger.getLogger(ParserPackage.class);

	/** konstruktor pobierajacy nazwe pliku do parsowania */
	public ParserPackage(String file) {
		this.file = file;
	}

	/** metoda ladujaca plik do parsowania */
	public Document LoadFile() {
		File input = new File(file);
		try {
			doc = Jsoup.parse(input, "UTF-8");
		} catch (IOException e) {
			log.error("Could not find file " + file);
		}
		return doc;
	}

	/**
	 * metoda parsujaca nazwy pakietow do listy
	 * 
	 * @return lista pelnych nazw pakietow
	 */
	public List<String> ParsePackages(String SateliteName) {

		// pobierz wszystkie tr zawierajace "SateliteName"
		Elements packages = LoadFile().select("table[width=720]").select(
				"tr ~ tr:contains(" + SateliteName + ")");

		int tmp = 0;
		for (Element src : packages) {
			// pobierz wartosci
			satelita.add(src.select("td[width=220]").text());
			pakiet.add(src.select("td[width=200]").text());
			link.add(src.select("td[width=200]").select("a").attr("href"));
			update.add(src.select("td[width=50]").text());
			stopnie.add(src.select("td[width=70]").text());
			checkbox.add(stopnie.get(tmp) + " Satelita : " + satelita.get(tmp)
					+ " -- Pakiet: " + pakiet.get(tmp)
					+ " -- Data modyfikacji: " + update.get(tmp));
			tmp++;
		}
		//log.info("Satelite parsed successfully");
		return checkbox;
	}

	/**
	 * metoda zwracajaca wybrany pakiet
	 * 
	 * @return nazwa wybranego pakietu
	 */
	public String Pakiet(int i) {
		return pakiet.get(i);
	}
	
	/**
	 * metoda zwracajaca stopnie
	 * 
	 * @return stopnie wybranego pakietu
	 */
	public String Stopnie(int i) {
		return link.get(i);
	}
	 
	/**
	 * metoda zwracajaca liste linkow do pakietow
	 * 
	 * @return lista linkow do pakietow
	 */
	public List<String> Link() {
		return link;
	}

	/**
	 * metoda zwracajaca liczbe elementow w liscie checkbox
	 * 
	 * @return liczba elementow listy pelnych nazw pakietow
	 */
	public int CheckboxSize() {
		return checkbox.size();
	}

	/** metoda parsujaca kanaly z danych pakietow */
	public void ParseChannels() {
		Elements packages = ((LoadFile().select("td[width=728]"))); // tabela z
																	// programami
		Elements p = packages.select("table[border!=0]").select(
				"table[align!=center]"); // wyselekcjonowane tabele z programami
		Elements tr = p.select("tr"); // pobrane wiersze

		title = doc.title();// pobierz tutul strony
		upd = tr.select("td[colspan=10]").text();// pobierz ostatnia
													// aktualizacje
		
		Elements rowspan = tr.select("td:contains(DVB-S)");
		List<String> freq = new ArrayList<String>();
		List<Integer> count = new ArrayList<Integer>();

		// petle pobierajace czestotliwosc
		for (Element row : rowspan) {
			Iterator<Element> it = row.select("font[size]").iterator();
			String text = it.next().text();
			while (it.hasNext()) {
				text = text + ", " + it.next().text();
			}
			freq.add(text);

			if (row.attr("rowspan") != "") {
				count.add(Integer.parseInt(row.attr("rowspan")));
			} else {
				count.add(1);
			}
		}

		int temp = 0;
		for (int i = 0; i < count.size(); i++) {
			for (int y = temp; y < (temp + count.get(i)); y++)
				Frequency.add("Frequency: " + freq.get(i)); // pobierz
															// czestotliwosci
			temp = temp + count.get(i);
		}

		// petla pobierajaca dane atrybuty
		for (int i = 0; i < tr.size(); i++) {
			Iterator<Element> ite;
			if (tr.get(i)
					.select("td[bgcolor=#ffffbb],td[bgcolor=#ffcc99],td[bgcolor=#ddffdd],td[bgcolor=#99ff99],td[bgcolor=#ffaaff]")
					.hasText()) {
				ite = tr.get(i)
						.select("td[bgcolor=#ffffbb],td[bgcolor=#ffcc99],td[bgcolor=#ddffdd],td[bgcolor=#99ff99],td[bgcolor=#ffaaff]")
						.iterator();

				ChannelName.add("Channel Name: " + ite.next().text());

				Elements a = ite.next().select("img");
				List<String> b = new ArrayList<String>();
				for (int x = 0; x < a.size(); x++) {
					b.add(a.get(x).attr("title"));
				}

				Features.add("Features: " + b);
				Encryption.add("Encryption: " + ite.next().text());
				ChNo.add("ChNo: " + ite.next().text());
				SID.add("SID: " + ite.next().text());
				//VPID.add("VPID: " + ite.next().text());
				//APID.add("APID: " + ite.next().text());
			}
		}

		// petla skladajaca nazwy w calosc
		for (int i = 0; i < ChannelName.size()-1; i++) {
			Final.add((i + 1) + ". " + ChannelName.get(i)
					+ System.getProperty("line.separator") + Features.get(i)
					+ System.getProperty("line.separator") + Encryption.get(i)
					+ System.getProperty("line.separator") + ChNo.get(i)
					+ System.getProperty("line.separator") + SID.get(i)
					//+ System.getProperty("line.separator") + VPID.get(i)
					//+ System.getProperty("line.separator") + APID.get(i)
					//+ System.getProperty("line.separator") + Frequency.get(i)
					+ System.getProperty("line.separator"));
		}

		// pobieranie kanalow SD/clear
		Elements SD_clear = tr.select("td[bgcolor=#ffffbb]");
		SD_cl = "SD/clear: ";
		for (Element row : SD_clear) {
			if (row.select("font[face=Arial]").hasText())
				SD_cl = SD_cl + row.select("font[face=Arial]").text() + ", ";
		}

		// pobieranie kanalow SD/encrypted
		Elements SD_encrypted = tr.select("td[bgcolor=#ffcc99]");
		SD_en = "SD/encrypted: ";
		for (Element row : SD_encrypted) {
			if (row.select("font[face=Arial]").hasText())
				SD_en = SD_en + row.select("font[face=Arial]").text() + ", ";
		}

		// pobieranie kanalow HD/clear
		Elements HD_clear = tr.select("td[bgcolor=#ddffdd]");
		HD_cl = "HD/clear: ";
		for (Element row : HD_clear) {
			if (row.select("font[face=Arial]").hasText())
				HD_cl = HD_cl + row.select("font[face=Arial]").text() + ", ";
		}

		// pobieranie kanalow HD/encrypted
		Elements HD_encrypted = tr.select("td[bgcolor=#99ff99]");
		HD_en = "HD/encrypted: ";
		for (Element row : HD_encrypted) {
			if (row.select("font[face=Arial]").hasText())
				HD_en = HD_en + row.select("font[face=Arial]").text() + ", ";
		}

		// pobieranie kanalow interactive
		Elements interactive = tr.select("td[bgcolor=#ffaaff]");
		inter = "interactive: ";
		for (Element row : interactive) {
			if (row.select("font[face=Arial]").hasText())
				inter = inter + row.select("font[face=Arial]").text() + ", ";
		}

		//log.info("Channels parsed successfully");
	}

	/** metoda zapisujaca wyniki parsowania kanalow kanalow do plikow */
	public void SaveToFile(String file_name) {
		Date data = new Date();
		String sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(data);

		try {
			FileWriter writer = new FileWriter(file_name);
			writer.write("Date: " + sdf + System.getProperty("line.separator")); // data
			writer.write("Title: " + title
					+ System.getProperty("line.separator")); // tytul
			writer.write("Last update: " + upd
					+ System.getProperty("line.separator")
					+ System.getProperty("line.separator")); // aktualizacja

			for (String str : Final) {
				writer.write(str + System.getProperty("line.separator")); // kanaly
			}

			writer.write(System.getProperty("line.separator"));
			writer.write(SD_cl + System.getProperty("line.separator")
					+ SD_en
					+ System.getProperty("line.separator") // sd_clear/encrypted
					+ HD_cl + System.getProperty("line.separator") + HD_en
					+ System.getProperty("line.separator") // hd_clear/ecrypted
					+ inter);

			writer.close();

			log.info("File " + file_name + " created successfully");
		} catch (FileNotFoundException e) {
			log.error("File not found");
		} catch (IOException e) {
			log.error("File " + file_name + " did not create");
		}
	}
}
