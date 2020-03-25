package demineur.States;

import demineur.definitions.Definitions;
import demineur.gamedata.GameData;
import utilities.Clock;

public class SplashState extends State {
	private GameData data;
	private boolean loaded = false;
	public SplashState(GameData data)
	{
		this.data = data;
		data.assets.LoadImage("Logo", Definitions.PATH_LOGO);
	}

	@Override
	public void Update() {
		if(!loaded)
		{
			Clock timer = new Clock();
			timer.Start();
			data.assets.LoadImage("HoverPauseButton", Definitions.PATH_HOVER_PAUSE_BUTTON);
			data.assets.LoadImage("HoverMenuButton", Definitions.PATH_HOVER_MENU_BUTTON);
			data.assets.LoadImage("HoverResumeButton", Definitions.PATH_HOVER_RESUME_BUTTON);
			data.assets.LoadImage("PauseButton", Definitions.PATH_PAUSE_BUTTON);
			data.assets.LoadImage("MenuButton", Definitions.PATH_MENU_BUTTON);
			data.assets.LoadImage("ResumeButton", Definitions.PATH_RESUME_BUTTON);
			data.assets.LoadImage("Board2", Definitions.PATH_HARD_BOARD);
			data.assets.LoadImage("Board1", Definitions.PATH_MID_BOARD);
			data.assets.LoadImage("Board0", Definitions.PATH_EASY_BOARD);
			data.assets.LoadImage("Flag", Definitions.PATH_FLAG);
			data.assets.LoadImage("Mine", Definitions.PATH_MINE);
			data.assets.LoadImage("0", Definitions.PATH_NUM_0);
			data.assets.LoadImage("1", Definitions.PATH_NUM_1);
			data.assets.LoadImage("2", Definitions.PATH_NUM_2);
			data.assets.LoadImage("3", Definitions.PATH_NUM_3);
			data.assets.LoadImage("4", Definitions.PATH_NUM_4);
			data.assets.LoadImage("5", Definitions.PATH_NUM_5);
			data.assets.LoadImage("6", Definitions.PATH_NUM_6);
			data.assets.LoadImage("7", Definitions.PATH_NUM_7);
			data.assets.LoadImage("8", Definitions.PATH_NUM_8);
			data.assets.LoadImage("9", Definitions.PATH_NUM_9);
			data.assets.LoadImage("BackButton", Definitions.PATH_BACK_BUTTON);
			data.assets.LoadImage("PlayButton", Definitions.PATH_PLAY_BUTTON);
			data.assets.LoadImage("ScoreButton", Definitions.PATH_SCORE_BUTTON);
			data.assets.LoadImage("QuitButton", Definitions.PATH_QUIT_BUTTON);
			data.assets.LoadImage("HardButton", Definitions.PATH_HARD_BUTTON);
			data.assets.LoadImage("MediumButton", Definitions.PATH_MEDIUM_BUTTON);
			data.assets.LoadImage("EasyButton", Definitions.PATH_EASY_BUTTON);
			data.assets.LoadImage("HoverBackButton", Definitions.PATH_HOVER_BACK_BUTTON);
			data.assets.LoadImage("HoverPlayButton", Definitions.PATH_HOVER_PLAY_BUTTON);
			data.assets.LoadImage("HoverScoreButton", Definitions.PATH_HOVER_SCORE_BUTTON);
			data.assets.LoadImage("HoverQuitButton", Definitions.PATH_HOVER_QUIT_BUTTON);
			data.assets.LoadImage("HoverHardButton", Definitions.PATH_HOVER_HARD_BUTTON);
			data.assets.LoadImage("HoverMediumButton", Definitions.PATH_HOVER_MEDIUM_BUTTON);
			data.assets.LoadImage("HoverEasyButton", Definitions.PATH_HOVER_EASY_BUTTON);
			data.file.CreateFile();
			data.file.LoadScores();
			timer.Pause();
			double time = timer.ElapsedTimeinSeconds();
			if(time<Definitions.SPLASH_STATE_TIME)
			{
				try {
					Thread.sleep((long) ((Definitions.SPLASH_STATE_TIME-time)*1000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			data.machine.AddState(new MainMenuState(data));
			loaded=true;
		}
		
	}

	@Override
	public void Draw() {
		data.screen.ClearScreen();
		data.screen.GetGfx().fillRect(0, 0, 800, 600);
		data.screen.AddImage(data.assets.GetImage("Logo"), 0, 0, 800, 600);
		
	}

	@Override
	public void Init() {
		
	}

}
