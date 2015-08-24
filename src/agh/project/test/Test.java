package agh.project.test;
import java.io.*;

import agh.project.Downloader;
import agh.project.Parser;
public class Test {


		// TODO Auto-generated method stub

		public static void main(String[] args) throws IOException {
	
		String tmp = new String(Downloader.run());
		Parser.run(tmp);
		System.out.println("Salata ladny chlopak");
	}
}

