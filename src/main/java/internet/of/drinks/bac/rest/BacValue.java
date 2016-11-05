package internet.of.drinks.bac.rest;

/**
 * Created by Martin on 05.11.2016.
 */

public class BacValue {
    private String userid;
    private Double baclevel;

    public BacValue(String userid, Double baclevel) {
        this.userid = userid;
        this.baclevel = baclevel;
    }

    public String getUserid() {
        return userid;
    }

    public Double getBaclevel() {
        return baclevel;
    }
}
