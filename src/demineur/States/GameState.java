package demineur.States;

import java.awt.MouseInfo;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import demineur.definitions.Definitions;
import demineur.gamedata.GameData;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import utilities.Clock;

public class GameState extends State {
	private int Mousex,Mousey;
	private boolean updating;
	private boolean Flag;
	private Rectangle MainMenuButton;
	private Rectangle PauseButton;
	private Rectangle ResumeButton;


	private GameData data;
	private int[][] Board;
	private int[][] Visible;
	private  int boardoriginx; 
	private  int boardoriginy ;
	private  int difficulty;
	private final int sqnbrx[]= {9,16,30};
	private final int sqnbry[] = {9,16,16};
	private int flagnbr = 0;
	private boolean gameover = false;
	private Boolean Paused =false;
	private final double bcst[] = {20* Definitions.scale,30* Definitions.scale,40* Definitions.scale};
	private final double bmcst[] = {0* Definitions.scale,-20* Definitions.scale,-40* Definitions.scale};
	private String MenuButton,PauseandResumeButton;
	private int opacity = 0;
	private int step = 1;
	private Clock clock;
	private boolean won =false;
	private int Score = 0;
	private boolean firstclick=false;
	private static boolean resize = true;
	private EventHandler<MouseEvent> filter;
	public GameState(GameData data,int diff)
	{
		clock = new Clock();
		difficulty=diff;
		MainMenuButton = new Rectangle(bmcst[difficulty]+Definitions.SCREEN_WIDTH[difficulty]*3/4 -Definitions.BUTTON_WIDTH/2, 20* Definitions.scale, Definitions.BUTTON_WIDTH,Definitions.BUTTON_HEIGHT);
		PauseButton =new Rectangle(bcst[difficulty]+Definitions.SCREEN_WIDTH[difficulty]*1/4 -Definitions.BUTTON_WIDTH*5/8, 20* Definitions.scale, Definitions.BUTTON_WIDTH*5/4,Definitions.BUTTON_HEIGHT );
		ResumeButton = new Rectangle(bcst[difficulty]+Definitions.SCREEN_WIDTH[difficulty]*1/4 -Definitions.BUTTON_WIDTH*13/16, 20* Definitions.scale, Definitions.BUTTON_WIDTH*13/8,Definitions.BUTTON_HEIGHT);
		MenuButton = "MenuButton";
		PauseandResumeButton = "PauseButton";
		boardoriginx = (Definitions.SCREEN_WIDTH[difficulty]-Definitions.BOARD_WIDTH[difficulty])/2 ;
		boardoriginy = Definitions.SCREEN_HEIGHT[difficulty]-Definitions.BOARD_HEIGHT[difficulty]-10;
		updating=false;
		Board = new int[sqnbry[difficulty]][sqnbrx[difficulty]];
		Visible = new int[sqnbry[difficulty]][sqnbrx[difficulty]];
		for(int i=0;i<sqnbry[difficulty];i++)
		{
			Arrays.fill(Board[i], 0);
			Arrays.fill(Visible[i], Definitions.NOT_VISIBLE);
		}
		int Minenum =Definitions.MINE_NUMBER[difficulty];
		int x;
		Random rand = new Random();
		this.data = data;
		while(Minenum>0)
		{
			for(int i=0;i<sqnbry[difficulty] &&  Minenum>0;i++)
			{
				for(int j=0;j<sqnbrx[difficulty] && Minenum>0;j++)
				{
					if(Board[i][j]==-1)
						continue;
					x= rand.nextInt(81);
					if(x<10 && Minenum>0)
					{
						Board[i][j] = -1;
						Minenum--;
					}
					
				}
			}
		}
		for (int i=0; i<sqnbry[difficulty] ; i++)
		{
			for(int j=0;j<sqnbrx[difficulty];j++)
			{
				if(Board[i][j]!=-1)
				{
					int mn=0;
					for(int k=-1; k<2;k++)
					{
						for(int o=-1;o<2;o++)
						{
							if(i+k>-1 && j+o>-1 && sqnbry[difficulty]>i+k && sqnbrx[difficulty]>j+o)
							if(Board[i+k][j+o]==-1)
							{
								mn++;
							}
						}
					}
					Board[i][j]=mn;
				}
			}
		}
		filter = new EventHandler<javafx.scene.input.MouseEvent>() {
		    public void handle(javafx.scene.input.MouseEvent e) {
				if(!updating)
				{
					updating=true;
					Mousex=(int) e.getX();
					Mousey=(int) e.getY();
					if(e.getButton()==MouseButton.PRIMARY)
					{
						Flag = false;
					}
					else if(e.getButton()==MouseButton.SECONDARY)
					{
						Flag = true;
					}
					if(Definitions.ScoreWindow!=null)
					{
						updating = false;
						Definitions.ScoreWindow.toFront();
					}
				}
		    }};


	}
	@Override
	public void Update() {
		if(updating  && MainMenuButton.contains(Mousex,Mousey))
		{
			data.screen.GetCanvas().removeEventFilter(javafx.scene.input.MouseEvent.MOUSE_CLICKED,filter);
			data.machine.AddState(new MainMenuState(data));
			updating = false;
		}
		if(clock.ElapsedTimeinSeconds()>9999)
		{
			clock.Pause();
			gameover=true;
		}
		if(updating)
		{
			if(Paused && ResumeButton.contains(Mousex,Mousey))
			{
				if(!gameover && firstclick)
				{
					clock.Resume();
				}
				Paused=false;
				updating = false;
			}
			else if(!Paused && PauseButton.contains(Mousex,Mousey))
			{
				if(!gameover)
				{
					clock.Pause();
				}
				Paused=true;
				updating = false;
			}

		}
		double x=MouseInfo.getPointerInfo().getLocation().x-Definitions.stage.getX()-7;
		double y=MouseInfo.getPointerInfo().getLocation().y-Definitions.stage.getY()-30;
		if(MainMenuButton.contains(x, y))
		{
			MenuButton = "HoverMenuButton";
			if(Paused)
			{
				PauseandResumeButton = "ResumeButton";
				
			}
			else
			{
				PauseandResumeButton = "PauseButton";
			}
		}
		else
		{
			MenuButton = "MenuButton";
			if(Paused )
			{
				if(ResumeButton.contains(x,y))
				{
					PauseandResumeButton = "HoverResumeButton";
				}
				else
				{
					PauseandResumeButton = "ResumeButton";
				}
				
			}
			else
			{
				if(PauseButton.contains(x,y))
				{
					PauseandResumeButton = "HoverPauseButton";
				}
				else
				{
					PauseandResumeButton = "PauseButton";
				}

			}
		}
		if(gameover && updating)
		{
			resize=false;
			data.machine.AddState(new GameState(data,difficulty));
		}
		if(updating &&!gameover && !Paused )
		{
			int boardx=(int) (Mousex-boardoriginx)/Definitions.BOARD_SQUARE;
			int boardy=(int) (Mousey-boardoriginy)/Definitions.BOARD_SQUARE;
			if(boardx>-1 && boardx <sqnbrx[difficulty] && boardy>-1 && boardy<sqnbry[difficulty])
			{
				if(!firstclick)
				{
					firstclick=true;
					clock.Start();
				}
				if(Flag)
				{
					if(Visible[boardy][boardx]==Definitions.NOT_VISIBLE)
					{
						if(flagnbr<Definitions.MINE_NUMBER[difficulty])
						{
							if(Board[boardy][boardx] == -1)
							{
								Score++;
							}
							else
							{
								Score--;
							}
							flagnbr++;
							Visible[boardy][boardx] = Definitions.FLAG;
						}
					}
					else if(Visible[boardy][boardx]==Definitions.FLAG)
					{
						if(Board[boardy][boardx] != -1)
						{
							Score++;
						}
						else
						{
							Score--;
						}

						flagnbr--;
						Visible[boardy][boardx] = Definitions.NOT_VISIBLE;
					}
					Flag=false;
				}
				else
				{
					reveal(boardx,boardy);
				}
			}
		}
		updating = false;
		if(Score == Definitions.MINE_NUMBER[difficulty] && !gameover)
		{
			clock.Pause();
			won = true;
			gameover = true;
			int score = (int) clock.ElapsedTimeinSeconds();
			if(Definitions.Scores[difficulty][9]>score )
			{
				ScoreWindow();
			}
		}
	}

	private void ScoreWindow()
	{
		CountDownLatch donesignal = new CountDownLatch (1);
		Platform.runLater(new Runnable() {
			@Override
			public void run()
			{
				int s = (int) clock.ElapsedTimeinSeconds();
				Stage newwindow = new Stage();
				newwindow.getIcons().add(data.assets.GetImage("Icon"));
				newwindow.setOnCloseRequest(event -> {
					Definitions.ScoreWindow=null;
				});
				newwindow.setResizable(false);
				Definitions.ScoreWindow = newwindow;
				VBox layout = new VBox();
				layout.setMinHeight(200*Definitions.scale);
				layout.setMinWidth(240*Definitions.scale);
		        final int MAX_CHARS = 15 ;
		        Label score = new Label();
		        score.setText("New Score : "+s);
		        score.setTextAlignment(TextAlignment.CENTER);
		        score.setFont(new Font(20*Definitions.scale));
		        TextField  textArea = new TextField ();
		        textArea.setMaxWidth(120*Definitions.scale);
		        textArea.setTextFormatter(new TextFormatter<String>(change -> 
		        {
		        	if(change.getControlNewText().length() > MAX_CHARS)
		        	{
		        		return null;
		        	}
		        	String txt = change.getControlNewText();
		        	for(int i=0; i<txt.length();i++)
		        	{
		        		if(!((txt.charAt(i)<='z' && txt.charAt(i)>='a') ||(txt.charAt(i)<='Z' && txt.charAt(i)>='A')||(txt.charAt(i)<='9' && txt.charAt(i)>='1')) )
		        		{
		        			return null;
		        		}
		        	}
		        	return change;
		            
		        }));
		        Button ok = new Button();
		        ok.setText("Confirm");
		        layout.getChildren().add(score);
		        layout.getChildren().add(textArea);
		        layout.getChildren().add(ok);
		        layout.setAlignment(Pos.CENTER);
		        layout.setSpacing(30*Definitions.scale);
		        ok.setOnAction(event ->
		        {		        	
		        	String name ;
		        	name= textArea.getText();
		        	Definitions.ScoreWindow.close();
		        	Definitions.ScoreWindow=null;
		        	UpdateScore(name+Definitions.sep+s);		        	
		        }
		        );
				Scene scene = new Scene(layout);				
				newwindow.setScene(scene);
				newwindow.show();
				donesignal.countDown();
			}
		});
		try {
			donesignal.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
	private void UpdateScore(String name)
	{
		int score = (int) clock.ElapsedTimeinSeconds();
		int i;
		for(i=0;Definitions.Scores[difficulty][i]!=Integer.MAX_VALUE && Definitions.Scores[difficulty][i]<=score;i++);
		if(Definitions.Scores[difficulty][i]==Integer.MAX_VALUE)
		{
			Definitions.Scores[difficulty][i]=score;
			data.file.AddDirectlyScoreAt(name, i,difficulty);
		}
		else
		{
			for(int j=9;j>i;j--)
			{
				Definitions.Scores[difficulty][j]= Definitions.Scores[difficulty][j-1];
				
			}
			Definitions.Scores[difficulty][i] = score;
			data.file.AddScoreAt(name, i,difficulty);
		}

		
	}
	private void reveal(int x, int y)
	{

		if(x>-1 && x<sqnbrx[difficulty] && y>-1 && y<sqnbry[difficulty])
			if(Visible[y][x] != Definitions.VISIBLE)
			{
				if(Visible[y][x] == Definitions.FLAG)
				{
					flagnbr--;
				}
				Visible[y][x] = Definitions.VISIBLE;
				if(Board[y][x]==0)
				{
					for(int i=-1;i<2;i++)
						for(int j=-1;j<2;j++)
						{
							reveal(x+i,y+j);
						}
				}
				else if(Board[y][x]==-1)
				{
					gameover=true;
					clock.Pause();
					for(int ny=0;ny<sqnbry[difficulty];ny++)
						for(int nx=0;nx<sqnbrx[difficulty];nx++)
							if(Board[ny][nx]==-1)
								Visible[ny][nx] = Definitions.VISIBLE;

				}
			}
	}
	@Override
	public void Draw() {
		data.screen.ClearScreen();
		data.screen.AddImage(data.assets.GetImage(MenuButton),MainMenuButton );
		if(Paused)
			data.screen.AddImage(data.assets.GetImage(PauseandResumeButton),ResumeButton);
		else
			data.screen.AddImage(data.assets.GetImage(PauseandResumeButton),PauseButton );
		data.screen.AddImage(data.assets.GetImage("Board"+difficulty),boardoriginx ,boardoriginy , Definitions.BOARD_WIDTH[difficulty], Definitions.BOARD_HEIGHT[difficulty]);

		for(int y=0 ; y<sqnbry[difficulty] ; y++)
			for(int x=0;x<sqnbrx[difficulty];x++)
			{
				if(Visible[y][x] == Definitions.VISIBLE)
				{
					if(Board[y][x]>-1)
						data.screen.AddImage(data.assets.GetImage(""+Board[y][x]),boardoriginx+Definitions.BOARD_SQUARE*x ,boardoriginy+Definitions.BOARD_SQUARE*y , Definitions.BOARD_SQUARE, Definitions.BOARD_SQUARE);
					else
						data.screen.AddImage(data.assets.GetImage("Mine"),boardoriginx+Definitions.BOARD_SQUARE*x ,boardoriginy+Definitions.BOARD_SQUARE*y , Definitions.BOARD_SQUARE, Definitions.BOARD_SQUARE);
				}
				else if(Visible[y][x]==Definitions.FLAG)
				{
					data.screen.AddImage(data.assets.GetImage("Flag"),boardoriginx+Definitions.BOARD_SQUARE*x ,boardoriginy+Definitions.BOARD_SQUARE*y , Definitions.BOARD_SQUARE, Definitions.BOARD_SQUARE);
				}
			}
		data.screen.GetGfx().setTextAlign(TextAlignment.RIGHT);
		data.screen.GetGfx().setFill(new Color(0,0,0,1));
		int x = (int) clock.ElapsedTimeinSeconds();
		data.screen.GetGfx().fillText(""+x,boardoriginx+Definitions.BOARD_WIDTH[difficulty],boardoriginy-10*Definitions.scale);
		data.screen.GetGfx().setTextAlign(TextAlignment.LEFT);
		data.screen.GetGfx().fillText("Mines : "+(Definitions.MINE_NUMBER[difficulty]-flagnbr),boardoriginx,boardoriginy-10*Definitions.scale);
		if(gameover)
		{
			if(opacity==0)
			{
				step=1;
			}else if(opacity == 150)
			{
				step = -1;
			}
			opacity+=step;
			data.screen.GetGfx().setTextAlign(TextAlignment.CENTER);
			if(won)
			{
				data.screen.GetGfx().fillText("YOU WON", Definitions.SCREEN_WIDTH[difficulty]/2, Definitions.SCREEN_HEIGHT[difficulty]-Definitions.BOARD_HEIGHT[difficulty]-50*Definitions.scale);
			}
			data.screen.GetGfx().setFill(new Color(0,0,0,(double)1/150*opacity));
			data.screen.GetGfx().fillText("LEFT CLICK TO RESTART", Definitions.SCREEN_WIDTH[difficulty]/2, Definitions.SCREEN_HEIGHT[difficulty]-Definitions.BOARD_HEIGHT[difficulty]-80*Definitions.scale);
		}

		data.screen.Show();
		
	}
	@Override
	public void Init() {
		if(resize)
		{
			data.screen.Resize(Definitions.SCREEN_WIDTH[difficulty], Definitions.SCREEN_HEIGHT[difficulty]);
		}
		else
		{
			resize=true;
		}
		data.screen.GetCanvas().addEventFilter(javafx.scene.input.MouseEvent.MOUSE_CLICKED,filter);
		data.screen.GetGfx().setFont(new Font(20*Definitions.scale));

	}
	

}
