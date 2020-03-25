package demineur.States;

import java.awt.MouseInfo;
import java.util.concurrent.CountDownLatch;

import demineur.definitions.Definitions;
import demineur.gamedata.GameData;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class MainMenuState extends State {
	private GameData data;
	private Rectangle PButton,SButton,QButton,HButton,MButton,EButton,BButton,SBButton;
	private int z=(int) (40* Definitions.scale),pz=(int) (40* Definitions.scale);
	private int sz=(int) (20* Definitions.scale),spz=(int) (30* Definitions.scale);
	private String pButton,sButton,qButton,hButton,mButton,eButton,bButton,sbButton;
	private int diff;
	private Boolean PlayClicked = false , ScoreandDiffClicked = false , ScoreClicked = false;
	private Boolean updating = false;
	private EventHandler<MouseEvent> filter;
	private int Mousex,Mousey;
	private final int sw = (int) (260 * Definitions.scale) , sh =  (z+pz)*2+Definitions.BUTTON_HEIGHT*3;

	public MainMenuState(GameData data)
	{
		this.data = data;
		filter = new EventHandler<javafx.scene.input.MouseEvent>() 
		{
		    public void handle(javafx.scene.input.MouseEvent e) 
		    {
				if(!updating && e.getButton()==MouseButton.PRIMARY)
				{
					updating=true;
					Mousex=(int) e.getX();
					Mousey=(int) e.getY();
				}
		
		    }
	    };
		PButton= new Rectangle((sw-Definitions.BUTTON_WIDTH)/2,pz,Definitions.BUTTON_WIDTH,Definitions.BUTTON_HEIGHT);
		SButton= new Rectangle((sw-Definitions.BUTTON_WIDTH*1.25)/2,z+pz+Definitions.BUTTON_HEIGHT,Definitions.BUTTON_WIDTH*1.25,Definitions.BUTTON_HEIGHT);
		QButton= new Rectangle((sw-Definitions.BUTTON_WIDTH)/2,z*2+pz+Definitions.BUTTON_HEIGHT*2,Definitions.BUTTON_WIDTH,Definitions.BUTTON_HEIGHT);
		EButton= new Rectangle((sw-Definitions.BUTTON_WIDTH)/2,spz,Definitions.BUTTON_WIDTH,Definitions.BUTTON_HEIGHT);
		MButton= new Rectangle((sw-Definitions.BUTTON_WIDTH*1.5)/2,sz+spz+Definitions.BUTTON_HEIGHT,Definitions.BUTTON_WIDTH*1.5,Definitions.BUTTON_HEIGHT);
		HButton= new Rectangle((sw-Definitions.BUTTON_WIDTH)/2,sz*2+spz+Definitions.BUTTON_HEIGHT*2,Definitions.BUTTON_WIDTH,Definitions.BUTTON_HEIGHT);
		BButton= new Rectangle((sw-Definitions.BUTTON_WIDTH)/2,sz*3+spz+Definitions.BUTTON_HEIGHT*3,Definitions.BUTTON_WIDTH,Definitions.BUTTON_HEIGHT);
		SBButton= new Rectangle((sw-Definitions.BUTTON_WIDTH)/2,10+sz*3+spz+Definitions.BUTTON_HEIGHT*3,Definitions.BUTTON_WIDTH,Definitions.BUTTON_HEIGHT);
	}

	@Override
	public void Update() {
		if(updating)
		{
			if(PlayClicked)
			{
				if(EButton.contains(Mousex, Mousey))
				{
					
					data.machine.AddState(new GameState(data,0));
				    data.screen.GetCanvas().removeEventFilter(javafx.scene.input.MouseEvent.MOUSE_CLICKED, filter);
				}
				else if(MButton.contains(Mousex, Mousey))
				{
					data.machine.AddState(new GameState(data,1));
				    data.screen.GetCanvas().removeEventFilter(javafx.scene.input.MouseEvent.MOUSE_CLICKED, filter);
				}
				else if(HButton.contains(Mousex, Mousey))
				{
					data.machine.AddState(new GameState(data,2));
				    data.screen.GetCanvas().removeEventFilter(javafx.scene.input.MouseEvent.MOUSE_CLICKED, filter);
				}
				else if(BButton.contains(Mousex, Mousey))
				{
					PlayClicked=false;
				}
			}
			else if(ScoreandDiffClicked)
			{
				if(SBButton.contains(Mousex, Mousey))
				{
					ScoreandDiffClicked=false;
				}
			}
			else if(ScoreClicked)
			{
				if(EButton.contains(Mousex, Mousey))
				{
					ScoreandDiffClicked =true;
					diff =0;
				}
				else if(MButton.contains(Mousex, Mousey))
				{
					ScoreandDiffClicked =true;
					diff =1;
				}
				else if(HButton.contains(Mousex, Mousey))
				{
					ScoreandDiffClicked =true;
					diff =2;
				}
				else if(SBButton.contains(Mousex, Mousey))
				{
					ScoreClicked=false;
				}
				
			}

			else
			{
				if(PButton.contains(Mousex, Mousey))
				{
					PlayClicked=true;
				}
				else if(SButton.contains(Mousex, Mousey))
				{
					ScoreClicked=true;
				}
				else if(QButton.contains(Mousex, Mousey))
				{
					data.file.SaveScores();
					CountDownLatch donesignal = new CountDownLatch (1);
					Platform.runLater(() -> {
							Definitions.stage.close();
							donesignal.countDown();
						
					});
					try {
						donesignal.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}
			updating=false;
		}
		double x=MouseInfo.getPointerInfo().getLocation().x-Definitions.stage.getX()-7;
		double y=MouseInfo.getPointerInfo().getLocation().y-Definitions.stage.getY()-30;
		if(PlayClicked)
		{
			if(HButton.contains(x, y))
			{
				hButton = "HoverHardButton";
				mButton = "MediumButton";
				eButton = "EasyButton";
				bButton = "BackButton";
			}
			else if(MButton.contains(x, y))
			{
				hButton = "HardButton";
				mButton = "HoverMediumButton";
				eButton = "EasyButton";
				bButton = "BackButton";
			}
			else if(EButton.contains(x, y))
			{
				hButton = "HardButton";
				mButton = "MediumButton";
				eButton = "HoverEasyButton";
				bButton = "BackButton";
			}
			else if(BButton.contains(x, y))
			{			
				hButton = "HardButton";
				mButton = "MediumButton";
				eButton = "EasyButton";
				bButton = "HoverBackButton";
			}
			else
			{			
				hButton = "HardButton";
				mButton = "MediumButton";
				eButton = "EasyButton";
				bButton = "BackButton";
			}
		}
		else if(ScoreandDiffClicked)
		{
			if(SBButton.contains(x, y))
			{
				sbButton = "HoverBackButton";
			}
			else
			{
				sbButton = "BackButton";
			}
		}
		else if(ScoreClicked)
			if(HButton.contains(x, y))
			{
				hButton = "HoverHardButton";
				mButton = "MediumButton";
				eButton = "EasyButton";
				sbButton = "BackButton";
			}
			else if(MButton.contains(x, y))
			{
				hButton = "HardButton";
				mButton = "HoverMediumButton";
				eButton = "EasyButton";
				sbButton = "BackButton";
			}
			else if(EButton.contains(x, y))
			{
				hButton = "HardButton";
				mButton = "MediumButton";
				eButton = "HoverEasyButton";
				sbButton = "BackButton";
			}
			else if(BButton.contains(x, y))
			{			
				hButton = "HardButton";
				mButton = "MediumButton";
				eButton = "EasyButton";
				sbButton = "HoverBackButton";
			}
			else
			{			
				hButton = "HardButton";
				mButton = "MediumButton";
				eButton = "EasyButton";
				sbButton = "BackButton";
			}

		else
		{
			if(PButton.contains(x, y))
			{
				pButton="HoverPlayButton";
				sButton = "ScoreButton";
				qButton = "QuitButton";
	
			}
			else if(SButton.contains(x, y))
			{
				pButton="PlayButton";
				sButton = "HoverScoreButton";
				qButton = "QuitButton";
			}
			else if(QButton.contains(x, y))
			{
				pButton="PlayButton";
				sButton = "ScoreButton";
				qButton = "HoverQuitButton";
			}
			else
			{			
				pButton="PlayButton";
				sButton = "ScoreButton";
				qButton = "QuitButton";
			}

		}
	}

	@Override
	public void Draw() {
		data.screen.ClearScreen();
		if(PlayClicked)
		{
			data.screen.AddImage(data.assets.GetImage(hButton), HButton);
			data.screen.AddImage(data.assets.GetImage(eButton), EButton);
			data.screen.AddImage(data.assets.GetImage(mButton), MButton);
			data.screen.AddImage(data.assets.GetImage(bButton), BButton);
		}
		else if(ScoreandDiffClicked)
		{
			data.screen.GetGfx().setTextAlign(TextAlignment.LEFT);
			data.screen.GetGfx().setFill(new Color(0,0,0,1));
			data.screen.GetGfx().setFont(new Font(12* Definitions.scale));
			for(int i=0;i<10;i++)
			{
				String[] t ={"",""};
				if(data.file.GetScores(diff)[i] !="")
					t = data.file.GetScores(diff)[i].split(Definitions.sep);
				data.screen.GetGfx().fillText(i+" - "+t[0], 7*Definitions.scale,(20+i*20)* Definitions.scale);
				data.screen.GetGfx().fillText(t[1],200* Definitions.scale,(20+i*20)* Definitions.scale);
			}
			data.screen.AddImage(data.assets.GetImage(sbButton), SBButton);
		}
		else if(ScoreClicked)
		{
			data.screen.AddImage(data.assets.GetImage(hButton), HButton);
			data.screen.AddImage(data.assets.GetImage(eButton), EButton);
			data.screen.AddImage(data.assets.GetImage(mButton), MButton);
			data.screen.AddImage(data.assets.GetImage(sbButton), SBButton);
		}
		else
		{
			data.screen.AddImage(data.assets.GetImage(pButton), PButton);
			data.screen.AddImage(data.assets.GetImage(sButton), SButton);
			data.screen.AddImage(data.assets.GetImage(qButton), QButton);
		}
	}

	@Override
	public void Init() {
	    data.screen.GetCanvas().addEventFilter(javafx.scene.input.MouseEvent.MOUSE_CLICKED, filter);
		data.screen.Resize(sw,sh);
		

	}

}
