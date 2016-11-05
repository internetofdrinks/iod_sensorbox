package internet.of.drinks.bac.rest;

/**
 * Created by Max Partenfelder on 05/11/2016.
 */
public class IdValue
{
    private String userid;

    public IdValue(String id)
    {
        this.userid = id;
    }

    public String getUserid()
    {
        return userid;
    }

    public void setUserid(String userid)
    {
        this.userid = userid;
    }
}
