package utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.JFileChooser;

import demineur.definitions.Definitions;

public class FileManager {
	private String myDocuments;
	private File score[] = {null,null,null};
	private String[][] filecontent = {{"","","","","","","","","",""},
										{"","","","","","","","","",""},
										{"","","","","","","","","",""}
										};
	public FileManager()
	{
		myDocuments =new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
	}
	public void CreateFile()
	{
		File dir = new File(myDocuments+"\\Demineur");
		dir.mkdir();
		for(int i=0;i<3;i++)
		{
			score[i] = new File(myDocuments+"\\Demineur"+"\\"+"Score"+i+".txt");
			try {
				score[i].createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	public void LoadScores()
	{
		for(int S=0;S<3;S++)
		{
			FileReader r =null;
			try {
				r=new FileReader(score[S]);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			BufferedReader reader = new BufferedReader(r);
			try {
				String line = reader.readLine();
				boolean reset = false;
				int i=0;
				while(line!=null && i<10)
				{
					String[] num =line.split(Definitions.sep);
					if(num.length==2)
					try {
						Definitions.Scores[S][i] = Integer.parseInt(num[1]);
						if(Definitions.Scores[S][i]>10000)
						{
							Definitions.Scores[S][i] = Integer.MAX_VALUE;
							reset=true;
							line="";
						}
					}catch (NumberFormatException e) {
						e.printStackTrace();
						line="";
						Definitions.Scores[S][i]=Integer.MAX_VALUE;
						reset=true;
					}
					else
					{
						line="";
						reset=true;
					}
					filecontent[S][i]=line;
					i++;
					if(reset) i--;
					line=reader.readLine();
				}
			} catch (IOException  e) {
				e.printStackTrace();
			} 
			try {
				reader.close();
				r.close();
			} catch (IOException e) {
				e.printStackTrace();
		}
		
		Functions.Sort(filecontent[S], 0, 9, (x,y) -> {
			int o,p;
			if(x!="")
				o = Integer.parseInt(((String) x).split(Definitions.sep)[1]);
			else
				o=Integer.MAX_VALUE;
			if(y!="")
				p = Integer.parseInt(((String) y).split(Definitions.sep)[1]);
			else
				p=Integer.MAX_VALUE;
			if(o<p) 
				return true;
			else
				return false;
			
		});
		Arrays.sort(Definitions.Scores[S]);
		}		
	}
	public void SaveScores()
	{
		for(int S=0;S<3;S++)
		{
			try {
				FileWriter write =new FileWriter(score[S]);
				for(int i=0;i<10;i++)
				{
					if(filecontent[S][i]!="")
					{
						write.write(filecontent[S][i]+System.lineSeparator());
					}
				}
				write.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	public String[] GetScores(int diff)
	{
		return filecontent[diff];
		
	}
	public void AddScoreAt(String score,int pos,int diff)
	{
		for(int i=9;i>pos;i--)
		{
			filecontent[diff][i]=filecontent[diff][i-1];
		}
		filecontent[diff][pos]=score;
	}
	public void AddDirectlyScoreAt(String score,int pos,int diff)
	{
		filecontent[diff][pos]=score;
	}


}
