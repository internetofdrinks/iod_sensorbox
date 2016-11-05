package internet.of.drinks.rfid;

import com.tinkerforge.BrickletNFCRFID;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import internet.of.drinks.IpConnection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Max Partenfelder on 05/11/2016.
 */
public class RfidDriver implements BrickletNFCRFID.StateChangedListener, Runnable
{
    private String readerUID;

    private List<RfidListener> listeners = new ArrayList<>();
    private BrickletNFCRFID rfidBricklet;

    public RfidDriver(String readerUID)
    {
        this.readerUID = readerUID;
        rfidBricklet = new BrickletNFCRFID(readerUID, IpConnection.INSTANCE.getConnection());
        rfidBricklet.addStateChangedListener(this);
    }

    public void addListener(RfidListener listener)
    {
        this.listeners.add(listener);
    }

    @Override
    public void stateChanged(short state, boolean idle)
    {
        String rfid = "";
        if(state == BrickletNFCRFID.STATE_REQUEST_TAG_ID_READY)
        {
            BrickletNFCRFID.TagID tagID = null;
            try
            {
                tagID = rfidBricklet.getTagID();
            }
            catch (TimeoutException e)
            {
                e.printStackTrace();
            }
            catch (NotConnectedException e)
            {
                e.printStackTrace();
            }

            String s = "Found tag of type " + tagID.tagType +
                    " with ID [" + Integer.toHexString(tagID.tid[0]);
            rfid = Integer.toHexString(tagID.tid[0]);
            for(int i = 1; i < tagID.tidLength; i++) {
                s += " " + Integer.toHexString(tagID.tid[i]);
                rfid += Integer.toHexString(tagID.tid[i]);
            }

            s += "]";
            System.out.println(s);

            for(RfidListener listener : this.listeners)
            {
                listener.rfidEvent(rfid);
            }
        }
    }

    @Override
    public void run()
    {
        while(true)
        {
            try
            {
                rfidBricklet.requestTagID(BrickletNFCRFID.TAG_TYPE_TYPE2);
            }
            catch (TimeoutException e)
            {
                e.printStackTrace();
            }
            catch (NotConnectedException e)
            {
                e.printStackTrace();
            }
        }
    }
}