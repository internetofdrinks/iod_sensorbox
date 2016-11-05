package internet.of.drinks.rfid;

/**
 * Created by Max Partenfelder on 05/11/2016.
 */
public interface RfidListener
{
    public void rfidEvent(String tagId);
}