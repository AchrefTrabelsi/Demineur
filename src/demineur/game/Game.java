package demineur.game;

import java.util.concurrent.CountDownLatch;

import demineur.States.SplashState;
import demineur.definitions.Definitions;
import demineur.gamedata.GameData;
import javafx.application.Platform;

public class Game implements Runnable {
	private Thread thread;
	public  GameData data;
	public Game(int width,int height)
	{
		data = new GameData(width,height);
		data.assets.LoadImage("Icon", "Icon.png");
		Definitions.stage.getIcons().add(data.assets.GetImage("Icon"));
		data.machine.AddState(new SplashState(data));
		Definitions.stage.setOnCloseRequest(e -> { data.file.SaveScores(); });
	}
	 

	public void draw()
	{
		CountDownLatch donesignal = new CountDownLatch (1);
		Platform.runLater(new Runnable() {
			@Override
			public void run()
			{
				data.machine.CurrentState().Draw();
				donesignal.countDown();
			}
		});
		try {
			donesignal.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public synchronized void start()
	{
		thread = new Thread(this);
		thread.start();
	}
	public synchronized void stop()
	{
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void run()
	{
		long earlier = System.currentTimeMillis();
		long now =0;
		long duration=0;
		while(true)
		{
			now =System.currentTimeMillis();
			duration+=(now-earlier);
			earlier=now;
			if(data.machine.ProcessStateChanges())
			{
				data.machine.CurrentState().Init();
			}
			if(duration<1000/Definitions.FPS)
			{
				try {
					Thread.sleep((long) (1000/Definitions.FPS-duration));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			while(duration>1000/Definitions.FPS)
			{
				data.machine.CurrentState().Update();
				duration-=1000/Definitions.FPS;
			}
			draw();
		}

	}


}
