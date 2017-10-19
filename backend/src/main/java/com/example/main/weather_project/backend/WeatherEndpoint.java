package com.example.main.weather_project.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "weatherApi",
        version = "v1",
        resource = "weather",
        namespace = @ApiNamespace(
                ownerDomain = "backend.weather_project.main.example.com",
                ownerName = "backend.weather_project.main.example.com",
                packagePath = ""
        )
)
public class WeatherEndpoint {

    private static final Logger logger = Logger.getLogger(WeatherEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Weather.class);
    }

    /**
     * Returns the {@link Weather} with the corresponding ID.
     *
     * @param mCity the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Weather} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "weather/{mCity}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Weather get(@Named("mCity") String mCity) throws NotFoundException {
        logger.info("Getting Weather with ID: " + mCity);
        Weather weather = ofy().load().type(Weather.class).id(mCity).now();
        if (weather == null) {
            throw new NotFoundException("Could not find Weather with ID: " + mCity);
        }
        return weather;
    }

    /**
     * Inserts a new {@code Weather}.
     */
    @ApiMethod(
            name = "insert",
            path = "weather",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Weather insert(Weather weather) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that weather.mCity has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(weather).now();
        logger.info("Created Weather.");

        return ofy().load().entity(weather).now();
    }

@ApiMethod(
        name = "insertToSite"
)
public void insertToSite(@Named("mDate") String mDate,@Named("mWeatherMain") String mWeatherMain,
        @Named("mTemp") double mTemp,@Named("mCity") String mCity,
       @Named("mCountry") String mCountry){
    List<Weather.Days> mList = new ArrayList<>();
    mList.add(new Weather.Days(mTemp, mDate,mWeatherMain));
    Weather weather = new Weather(mCity,mCountry, mList);
    ofy().save().entity(weather).now();
    logger.info("Created User.");
}
    /**
     * Updates an existing {@code Weather}.
     *
     * @param mCity   the ID of the entity to be updated
     * @param weather the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code mCity} does not correspond to an existing
     *                           {@code Weather}
     */
    @ApiMethod(
            name = "update",
            path = "weather/{mCity}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Weather update(@Named("mCity") String mCity, Weather weather) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(mCity);
        ofy().save().entity(weather).now();
        logger.info("Updated Weather: " + weather);
        return ofy().load().entity(weather).now();
    }

    /**
     * Deletes the specified {@code Weather}.
     *
     * @param mCity the ID of the entity to delete
     * @throws NotFoundException if the {@code mCity} does not correspond to an existing
     *                           {@code Weather}
     */
    @ApiMethod(
            name = "remove",
            path = "weather/{mCity}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("mCity") String mCity) throws NotFoundException {
        checkExists(mCity);
        ofy().delete().type(Weather.class).id(mCity).now();
        logger.info("Deleted Weather with ID: " + mCity);
    }

    /**
     * List all entities.
     *
     * @param cursor used for pagination to determine which page to return
     * @param limit  the maximum number of entries to return
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(
            name = "list",
            path = "weather",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Weather> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Weather> query = ofy().load().type(Weather.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Weather> queryIterator = query.iterator();
        List<Weather> weatherList = new ArrayList<Weather>(limit);
        while (queryIterator.hasNext()) {
            weatherList.add(queryIterator.next());
        }
        return CollectionResponse.<Weather>builder().setItems(weatherList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(String mCity) throws NotFoundException {
        try {
            ofy().load().type(Weather.class).id(mCity).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Weather with ID: " + mCity);
        }
    }
}