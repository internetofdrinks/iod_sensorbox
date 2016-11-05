package internet.of.drinks;

import com.tinkerforge.AlreadyConnectedException;
import com.tinkerforge.IPConnection;
import com.tinkerforge.IPConnectionBase;
import com.tinkerforge.NotConnectedException;

import java.io.IOException;

/**
 * Created by Max Partenfelder on 05/11/2016.
 */
public enum IpConnection
{
    INSTANCE("localhost", 4223);

    private String host;
    private int port;

    private IPConnection ipConnection;

    IpConnection(String host, int port)
    {
        this.ipConnection = new IPConnection();
        this.host = host;
        this.port = port;
    }

    public IPConnection getConnection()
    {
        if(this.ipConnection.getConnectionState() == IPConnectionBase.CONNECTION_STATE_DISCONNECTED)
        {
            this.connect();
        }
        return this.ipConnection;
    }

    public void connect()
    {
        System.out.println("Connecting to brickd");
        try
        {
            ipConnection.connect(this.host, this.port);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (AlreadyConnectedException e)
        {
            e.printStackTrace();
        }
        System.out.println("Connected");
    }

    public void disconnect()
    {
        try
        {
            this.ipConnection.disconnect();
        }
        catch (NotConnectedException e)
        {
            e.printStackTrace();
        }
    }
}
