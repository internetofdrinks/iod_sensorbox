package internet.of.drinks.bac.rest;

import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;

/**
 * Created by Martin on 05.11.2016.
 */
public class BacClient {

    public static void main(String[] args) {
        BacApi bacApi = Feign.builder()
                .decoder(new GsonDecoder())
                .encoder(new GsonEncoder())
                .target(BacApi.class, "https://iodservice.herokuapp.com");
        bacApi.post(new BacValue("4712", 0.5D));
    }
}
