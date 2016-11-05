package internet.of.drinks.bac.rest;

import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.slf4j.Slf4jLogger;

/**
 * Created by Max Partenfelder on 05/11/2016.
 */
public class IdClient implements IdApi
{
    private final IdApi idApi;
    public IdClient() {
        idApi = Feign.builder()
                .decoder(new GsonDecoder())
                .encoder(new GsonEncoder())
                .logger(new Slf4jLogger())
                .logLevel(Logger.Level.FULL)
                .target(IdApi.class, "https://iodservice.herokuapp.com");
    }

    @Override
    public void post(IdValue value)
    {
        idApi.post(value);
    }
}
