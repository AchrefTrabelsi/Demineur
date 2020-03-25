package demineur.definitions;


import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Definitions {
	public static final double scale =1;
	public static final String sep = "\t";
	public static Stage ScoreWindow = null;
	public static int[][] Scores = {{Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE},
									{Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE},
									{Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE}
									};
	public static BorderPane root;
	public static Stage stage;
	public static final double SPLASH_STATE_TIME = 2.5;	
	public static final int BUTTON_WIDTH = (int) (80*scale); 
	public static final int BUTTON_HEIGHT = (int) (40*scale);
	public static final String PATH_HOVER_BACK_BUTTON = "HoverBackButton.png";
	public static final String PATH_HOVER_EASY_BUTTON = "HoverEasyButton.png";
	public static final String PATH_HOVER_MEDIUM_BUTTON = "HoverMediumButton.png";
	public static final String PATH_HOVER_HARD_BUTTON = "HoverHardButton.png";
	public static final String PATH_HOVER_PLAY_BUTTON = "HoverPlayButton.png";
	public static final String PATH_HOVER_SCORE_BUTTON = "HoverScoreButton.png";
	public static final String PATH_HOVER_QUIT_BUTTON = "HoverQuitButton.png";
	public static final String PATH_HOVER_RESUME_BUTTON = "HoverResumeButton.png";
	public static final String PATH_HOVER_PAUSE_BUTTON = "HoverPauseButton.png";
	public static final String PATH_HOVER_MENU_BUTTON = "HoverMenuButton.png";
	public static final String PATH_BACK_BUTTON = "BackButton.png";
	public static final String PATH_EASY_BUTTON = "EasyButton.png";
	public static final String PATH_MEDIUM_BUTTON = "MediumButton.png";
	public static final String PATH_HARD_BUTTON = "HardButton.png";
	public static final String PATH_PLAY_BUTTON = "PlayButton.png";
	public static final String PATH_SCORE_BUTTON = "ScoreButton.png";
	public static final String PATH_QUIT_BUTTON = "QuitButton.png";
	public static final String PATH_RESUME_BUTTON = "ResumeButton.png";
	public static final String PATH_PAUSE_BUTTON = "PauseButton.png";
	public static final String PATH_MENU_BUTTON = "MenuButton.png";
	public static final String PATH_EASY_BOARD = "Easymode.png";
	public static final String PATH_MID_BOARD = "Midmode.png";
	public static final String PATH_HARD_BOARD = "Hardmode.png";
	public static final String PATH_NUM_0 = "0.png";
	public static final String PATH_NUM_1 = "1.png";
	public static final String PATH_NUM_2 = "2.png";
	public static final String PATH_NUM_3 = "3.png";
	public static final String PATH_NUM_4 = "4.png";
	public static final String PATH_NUM_5 = "5.png";
	public static final String PATH_NUM_6 = "6.png";
	public static final String PATH_NUM_7 = "7.png";
	public static final String PATH_NUM_8 = "8.png";
	public static final String PATH_NUM_9 = "9.png";
	public static final String PATH_FLAG = "Flag1.png";
	public static final String PATH_MINE = "Mine0.png";
	public static final String PATH_LOGO = "logo.png";
	public static final double FPS = 60;
	public static final int SCREEN_WIDTH[] = {(int)(280*scale),(int)(400*scale),(int)(680*scale)};
	public static final int SCREEN_HEIGHT[] = {(int)(350*scale),(int)(490*scale),(int)(490*scale)};
	public static final int BOARD_SQUARE = (int) (20*scale);
	public static final int BOARD_HEIGHT[] = {BOARD_SQUARE*9,BOARD_SQUARE*16,BOARD_SQUARE*16};
	public static final int BOARD_WIDTH[] = {BOARD_SQUARE*9,BOARD_SQUARE*16,BOARD_SQUARE*30};
	public static final int MINE_NUMBER[] = {10,40,99};
	public static final int VISIBLE = 1;
	public static final int NOT_VISIBLE = 0;
	public static final int FLAG = -1;





}
