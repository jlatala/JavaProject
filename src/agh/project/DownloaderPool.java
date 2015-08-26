package agh.project;

import java.io.IOException;

public class DownloaderPool implements Runnable{
	private Integer ID;
	boolean Done;
	public DownloaderPool(Integer _Id)
	{
		this.ID=_Id;
		Done = false;
	}
	public void run(){
		try{
		Downloader D = new Downloader(ID);
		}
		catch(IOException E)
		{
			
		}
	}
}
