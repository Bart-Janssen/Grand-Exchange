package sample.Gui;

import javafx.application.Platform;
import javafx.scene.control.Label;
import sample.Logic.Connector;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

public class ConnectionFailureController extends Controller implements IConnectionFailureController, Observer
{
    public Label labelTime;
    private int time = 6;
    private static final Timer timer = new Timer();

    public ConnectionFailureController()
    {
        Connector connector = Connector.getInstance();
        connector.addObserver(this);
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                time--;
                setLabelTime(time);
                System.out.println(time);
                if (time <= 0)
                {
                    connector.run();
                    time = 6;
                }
            }
        }, 1000, 1000);
    }

    private void setLabelTime(final int time)
    {
        Platform.runLater(() -> labelTime.setText("Next try in: " + time));
    }

    private void restartMain()
    {
        Platform.runLater(Main::restart);
    }

    @Override
    public void update(Observable o, Object arg)
    {
        if ((boolean)arg)
        {
            restartMain();
            timer.cancel();
        }
    }
}