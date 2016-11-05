package internet.of.drinks.bac.rest;

import feign.Headers;
import feign.RequestLine;

/**
 * Created by Martin on 05.11.2016.
 */
public interface BacApi {

    @Headers("Content-Type: application/json;charset=UTF-8")
    @RequestLine("POST /bac")
    void post(BacValue value);
}
