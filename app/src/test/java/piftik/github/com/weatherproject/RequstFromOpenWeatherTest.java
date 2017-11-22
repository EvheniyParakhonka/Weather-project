package piftik.github.com.weatherproject;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.InputStream;

import piftik.github.com.weatherproject.mocks.Mocks;
import piftik.github.com.weatherproject.request.http.IHttpClient;

import static org.mockito.Mockito.mock;

@RunWith(RobolectricTestRunner.class)
@Config(
        constants = BuildConfig.class,
        sdk = Constant.SDK_VERSION)

public class RequstFromOpenWeatherTest {

    private IHttpClient mHttpClient;
    private InputStream mMockedInputStream;

    @Before
    public void setUp() {
        mMockedInputStream = Mocks.stream();
        mHttpClient = mock(IHttpClient.class);

    }

//    @Test
//    public void parseUserListFromResource() throws Exception {
//
//        final ArgumentCaptor<String> mCaptor = ArgumentCaptor.forClass(String.class);
//        final String readFromStream = new ReadFromStream().readFromStream(mMockedInputStream);
//        doReturn(readFromStream).when(mHttpClient).makeHttpRequest(anyString());
//        final String response = mHttpClient.makeHttpRequest("http://myBackend/getUserList");
//        verify(mHttpClient).makeHttpRequest(mCaptor.capture());
//        final IJsonParser iJsonParser = new JsonParser();
//
//        final List<Weather> weatherArrayList = iJsonParser.extractWeatherFromJson(response);
//
//        assertEquals(weatherArrayList.get(0).getCity(), "Prigorod Kolozha");
//        assertEquals(weatherArrayList.get(0).getTempMin(), "281");
//
//    }

}
