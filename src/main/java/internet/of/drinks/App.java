package internet.of.drinks;

import internet.of.drinks.bac.BacDriver;
import internet.of.drinks.rfid.RfidDriver;
import internet.of.drinks.rfid.RfidListener;

/**
 * Created by Max Partenfelder on 05/11/2016.
 */
public class App implements RfidListener, Runnable
{
    public static final String RFID_UID = "uqD";
    public static final String ACC_UID = "zWT";
    public static final String DISP_UID = "AAX";
    public static final String ADC_UID = "vhe";

    private String currentUserId = "";
    private BacDriver bacDriver;
    private RfidDriver rfidDriver;

    public App()
    {
        this.bacDriver = new BacDriver(ADC_UID);
        this.rfidDriver = new RfidDriver(RFID_UID);
        this.rfidDriver.addListener(this);
        this.run();
    }

    @Override
    public void rfidEvent(String tagId)
    {
        this.currentUserId = tagId;
    }

    @Override
    public void run()
    {
        while(true)
        {
            try
            {
                Thread.sleep(50);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args)
    {
        // Connect to brickd with standard host/port
        IpConnection.INSTANCE.connect();

        // Create a new instance of the App
        new App();
    }
}