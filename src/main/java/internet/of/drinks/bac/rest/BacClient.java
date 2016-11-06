package internet.of.drinks.bac.rest;

import feign.Feign;
import feign.Logger;
import feign.Retryer;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.slf4j.Slf4jLogger;

import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by Martin on 05.11.2016.
 */
public class BacClient implements BacApi {

    private final BacApi bacApi;
    private ForkJoinPool pool;

    public BacClient() {
        pool = ForkJoinPool.commonPool();
        bacApi = Feign.builder()
                .decoder(new GsonDecoder())
                .encoder(new GsonEncoder())
                .logger(new Slf4jLogger())
                .logLevel(Logger.Level.FULL)
                .retryer(new Retryer.Default(1000, SECONDS.toMillis(3), 2))
                .target(BacApi.class, "https://iodservice.herokuapp.com");
    }

    @Override
    public void post(BacValue value) {
        pool.submit(() -> bacApi.post(value));
    }

    public static void main(String[] args) {
        BacClient bacClient = new BacClient();
        bacClient.post(new BacValue("4712", 0.5D));
    }
}
