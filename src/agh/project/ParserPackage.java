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
 * This class is responsible for parsing downloaded files.
 * 
 * @author Jakub Latala
 */
public class ParserPackage {
	

	
	/** name of satellite */
	public List<String> satellite = new ArrayList<String>();
	/** name of package */
	public List<String> pack = new ArrayList<String>();
	/** url of package */
	public List<String> link = new ArrayList<String>();
	/** last update */
	public List<String> update = new ArrayList<String>();
	/** coordinates */
	public List<String> coordinate = new ArrayList<String>();
	/** all parameters as string */
	public List<String> sum = new ArrayList<String>();

	/** name of channel */
	public List<String> ChannelName = new ArrayList<String>();
	/** features */
	public List<String> Features = new ArrayList<String>();
	/** encryption */
	public List<String> Encryption = new ArrayList<String>();
	/** ChNo */
	public List<String> ChNo = new ArrayList<String>();
	/** SID */
	public List<String> SID = new ArrayList<String>();
	/** VPID */
	public List<String> VPID = new ArrayList<String>();
	/** APID */
	public List<String> APID = new ArrayList<String>();
	/** frequency */
	public List<String> Frequency = new ArrayList<String>();
	/** all channels as string */
	public List<String> Final = new ArrayList<String>();

	/** SD/clear */
	public String SD_cl;
	/** SD/encrypted */
	public String SD_en;
	/** HD/clear */
	public String HD_cl;
	/** HD/encrypted */
	public String HD_en;
	/** interactive */
	public String inter;

	/** name of parsing file */
	public String file;
	/** title of website */
	public String title;
	/** last update */
	public String upd;
	/** document used by JSOUP */
	Document doc;

	

	/** Constructor, sets name of file */
	public ParserPackage(String file) {
		this.file = file;
	}
	
	/**
	 * Default constructor
	 */
	public ParserPackage(){;}
	
	/**
	 * Sets name of file.
	 * @param file
	 */
	public void setFilePath(String file){
		this.file = file;
	}

	/** Load a file. */
	public Document LoadFile() {
		File input = new File(file);
		try {
			doc = Jsoup.parse(input, "UTF-8");
		} catch (IOException e) {
			//Log4j.log.error("Could not find file " + file);
		}
		return doc;
	}

	/**
	 * Parse list of packages.
	 * @param SateliteName name of satellite
	 * @return
	 */
	public List<String> ParsePackages(String SateliteName) {

		// pobierz wszystkie tr zawierajace "SateliteName"
		Elements packages = LoadFile().select("table[width=720]").select(
				"tr ~ tr:contains(" + SateliteName + ")");

		int tmp = 0;
		for (Element src : packages) {
			// pobierz wartosci
			satellite.add("http://www.lyngsat.com/"+src.select("td[width=220]").text().replaceAll(" ", "-").replaceAll("&", "and").replaceAll("/", "-")+".html");
			pack.add(src.select("td[width=200]").text());
			link.add(src.select("td[width=200]").select("a").attr("href"));
			update.add(src.select("td[width=50]").text());
			coordinate.add(src.select("td[width=70]").text());
			sum.add(coordinate.get(tmp) + " satellite : " + satellite.get(tmp)
					+ " -- pack: " + pack.get(tmp)
					+ " -- Data modyfikacji: " + update.get(tmp));
			tmp++;
		}
		//log.info("Satelite parsed successfully");
		return sum;
	}

	/**
	 * Return package
	 * @param i ID of package
	 * @return
	 */
	public String Pack(int i) {
		return pack.get(i);
	}
	 
	/**
	 * Returns list of urls.
	 * @return
	 */
	public List<String> Link() {
		return link;
	}

	/** Parse channels from packages. */
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
		try{
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
		}
		catch(java.util.NoSuchElementException e){
			//Log4j.log.error(e);
		}
		catch(java.lang.IndexOutOfBoundsException e){
			//Log4j.log.error(e);
		}
		
		
		try{
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
		}
		catch(java.lang.IndexOutOfBoundsException e){
			//Log4j.log.error(e);
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

	/** Save channels informations to text files. */
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

			Log4j.log.info("File " + file_name + " created successfully");
		} catch (FileNotFoundException e) {
			Log4j.log.error("File not found");
		} catch (IOException e) {
			Log4j.log.error("File " + file_name + " did not create");
		}
	}
}
