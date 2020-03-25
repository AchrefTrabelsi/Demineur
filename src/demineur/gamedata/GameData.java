package demineur.gamedata;

import utilities.FileManager;

public class GameData {
	public  Display screen;
	public AssetManager assets;
	public StateMachine machine;
	public FileManager file;
	public GameData(int width,int height)
	{
		file = new FileManager();
		screen = new Display(width,height);
		assets= new AssetManager();
		machine = new StateMachine();

	}

}
