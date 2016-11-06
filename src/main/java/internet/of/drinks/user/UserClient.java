package internet.of.drinks.user;

import feign.Feign;
import feign.Logger;
import feign.Retryer;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.slf4j.Slf4jLogger;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by Martin on 05.11.2016.
 */
public class UserClient implements UserApi {

    private final UserApi api;

    public UserClient() {
        api = Feign.builder()
                .decoder(new GsonDecoder())
                .encoder(new GsonEncoder())
                .logger(new Slf4jLogger())
                .logLevel(Logger.Level.FULL)
                .retryer(new Retryer.Default(1000, SECONDS.toMillis(3), 2))
                .target(UserApi.class, "https://iodservice.herokuapp.com");
    }

    @Override
    public User getUser(String id) {
        try {
            return api.getUser(id);
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        UserClient userClient = new UserClient();
        User user = userClient.getUser("4386da2e74980");
        System.out.println(user);
    }
}
