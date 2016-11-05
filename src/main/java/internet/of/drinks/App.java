package internet.of.drinks;

import internet.of.drinks.rfid.RfidListener;

/**
 * Created by Max Partenfelder on 05/11/2016.
 */
public class App implements RfidListener
{
    public static final String RFID_UID = "uqD";
    public static final String ACC_UID = "zWT";
    public static final String DIPS_UID = "AAX";
    public static final String ADC_UID = "vhe";

    public static void main(String[] args)
    {
        // Connect to brickd with standard host/port
        IpConnection.INSTANCE.connect();


    }

    @Override
    public void RfidEvent(String tagId)
    {

    }
}
