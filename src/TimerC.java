/**
 * Created by Alex on 10/22/2016.
 */
public class TimerC
{
    private double startTime = 0;

    public void start()
    {
        startTime = System.currentTimeMillis();
    }

    public double getElapsedTime()
    {
        return (System.currentTimeMillis() - startTime) / 1000.0; //returns in seconds
    }

    public void reset() {
        startTime = 0;
    }
}