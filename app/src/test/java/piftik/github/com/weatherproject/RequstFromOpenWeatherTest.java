package piftik.github.com.weatherproject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.InputStream;
import java.util.ArrayList;

import piftik.github.com.weatherproject.mocks.Mocks;
import piftik.github.com.weatherproject.request.http.IHttpClient;
import piftik.github.com.weatherproject.request.parser.GsonParser;
import piftik.github.com.weatherproject.request.parser.IJsonParser;
import piftik.github.com.weatherproject.request.parser.JsonParser;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(
        constants = BuildConfig.class,
        sdk = Constant.SDK_VERSION)

public class RequstFromOpenWeatherTest {

    private IHttpClient mHttpClient;
    private InputStream mMockedInputStream;

    @Before
    public void setUp() {
        mMockedInputStream = Mocks.stream("Json.json");
        mHttpClient = mock(IHttpClient.class);
    }


    @Test
    public void parseUserListFromResource() throws Exception {

        when(mHttpClient.makeHttpRequst(Matchers.anyString())).thenReturn(mMockedInputStream);
        InputStream response = mHttpClient.makeHttpRequst("http://myBackend/getUserList");

        IJsonParser iJsonParser = new JsonParser();

        ArrayList<Weather> weatherArrayList = iJsonParser.extractWeatherFromJson(response);

        assertEquals(weatherArrayList.get(0).getCity(), "Prigorod Kolozha");
        assertEquals(weatherArrayList.get(0).getTemp(), 281.92);

    }

    @Test
    public void parseUserListGsonParser() throws Exception{
        when(mHttpClient.makeHttpRequst(Matchers.anyString())).thenReturn(mMockedInputStream);
        InputStream response = mHttpClient.makeHttpRequst("http://myBackend/getUserList");

        IJsonParser iGsonParser = new GsonParser();

       ArrayList<Weather> weatherArrayList = iGsonParser.extractWeatherFromJson(response);
        assertEquals(weatherArrayList.get(0).getDate(), "2017-10-10 15:00:00");
        assertEquals(weatherArrayList.get(0).getTemp(), 281.92);
    }
}
