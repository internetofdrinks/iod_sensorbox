package internet.of.drinks.bac.rest;

import feign.Headers;
import feign.RequestLine;

/**
 * Created by Max Partenfelder on 05/11/2016.
 */
public interface IdApi
{
    @Headers("Content-Type: application/json;charset=UTF-8")
    @RequestLine("POST /ids")
    void post(IdValue id);
}
