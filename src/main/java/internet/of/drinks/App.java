package internet.of.drinks;

import com.tinkerforge.BrickletLCD20x4;
import internet.of.drinks.bac.BacDriver;
import internet.of.drinks.bac.rest.BacClient;
import internet.of.drinks.bac.rest.BacValue;
import internet.of.drinks.display.DisplayDriver;
import internet.of.drinks.rfid.RfidDriver;
import internet.of.drinks.rfid.RfidListener;

/**
 * Created by Max Partenfelder on 05/11/2016.
 */
public class App implements RfidListener, Runnable, BrickletLCD20x4.ButtonPressedListener
{
    public static final String RFID_UID = "uqD";
    public static final String ACC_UID = "zWT";
    public static final String DISP_UID = "AAX";
    public static final String ADC_UID = "vhe";

    private String currentUserId = "";
    private BacDriver bacDriver;
    private RfidDriver rfidDriver;
    private DisplayDriver displayDriver;

    public App()
    {
        this.bacDriver = new BacDriver(ADC_UID);

        this.rfidDriver = new RfidDriver(RFID_UID);
        this.rfidDriver.addListener(this);

        this.displayDriver = new DisplayDriver(DISP_UID);
        this.displayDriver.addButtonPressedListener(this);

        new Thread(this).start();
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

        }
    }

    public static void main(String[] args)
    {
        // Connect to brickd with standard host/port
        IpConnection.INSTANCE.connect();

        // Create a new instance of the App
        new App();
    }

    @Override
    public void buttonPressed(short button)
    {
        System.out.println("Button pressed: " + button);
//        bacLevel = bacDriver.getBacLevel(1000);
        if(button == 3 && !currentUserId.equals(""))
        {
            System.out.println("Measuring BAC level");
            double bacLevel = 0;
            try
            {
                bacLevel = bacDriver.getBacLevel(3000);
                displayDriver.displayBac(bacLevel);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            BacValue bv = new BacValue(this.currentUserId, bacLevel);
            BacClient bc = new BacClient();
            bc.post(bv);
            System.out.println("Posting:\nUID: " + currentUserId + "\nBAC: " + bacLevel);
        }
    }
}