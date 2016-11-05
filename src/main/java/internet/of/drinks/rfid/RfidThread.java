package internet.of.drinks.rfid;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Max Partenfelder on 05/11/2016.
 */
public class RfidThread implements Runnable
{
    private String host;
    private int port;
    private String readerUID;

    private List<RfidListener> listeners = new ArrayList<>();

    public RfidThread(String host, int port, String readerUID)
    {
        this.host = host;
        this.port = port;
        this.readerUID = readerUID;
    }

    @Override
    public void run()
    {

    }
}