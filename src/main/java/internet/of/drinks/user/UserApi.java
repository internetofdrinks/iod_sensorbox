package internet.of.drinks.user;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import internet.of.drinks.bac.rest.BacValue;

/**
 * Created by Martin on 05.11.2016.
 */
public interface UserApi {

    @Headers("Content-Type: application/json;charset=UTF-8")
    @RequestLine("GET /users/{id}")
    User getUser(@Param("id") String id);
}