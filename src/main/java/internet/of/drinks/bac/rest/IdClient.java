package internet.of.drinks.bac.rest;

import feign.Feign;
import feign.Logger;
import feign.Retryer;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.slf4j.Slf4jLogger;

import java.util.concurrent.ForkJoinPool;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by Max Partenfelder on 05/11/2016.
 */
public class IdClient implements IdApi {
    private final IdApi idApi;
    private final ForkJoinPool pool;

    public IdClient() {
        pool = ForkJoinPool.commonPool();
        idApi = Feign.builder()
                .decoder(new GsonDecoder())
                .encoder(new GsonEncoder())
                .logger(new Slf4jLogger())
                .logLevel(Logger.Level.FULL)
                .retryer(new Retryer.Default(1000, SECONDS.toMillis(3), 2))
                .target(IdApi.class, "https://iodservice.herokuapp.com");
    }

    @Override
    public void post(IdValue value) {
        pool.submit(() -> idApi.post(value));
    }
}
