package piftik.github.com.backend_servlet;

import com.google.appengine.repackaged.com.google.gson.annotations.SerializedName;

import java.util.Random;

public class UrlSwitcher {
    private final int current_app_version = 1;
    private String[] rainList = {"http://www.weatherwizkids.com/wp-content/uploads/2015/04/rain1.jpg",
        "http://www.weatherwizkids.com/wp-content/uploads/2015/02/rain21.jpg",
        "http://ontariospca.ca/blog/wp-content/uploads/2016/04/Photo-by-Tomasz-Sienicki-.jpeg",
        "https://cdn.pixabay.com/photo/2017/11/01/08/16/rain-2907366_960_720.jpg",
        "https://cdn.pixabay.com/photo/2015/06/01/08/54/landscape-793026_960_720.jpg"};

    private String[] snowList = {"http://i.funny.pho.to/preview/snow_effect/falling_snow_effect.jpeg",
        "https://media.fox5dc.com/media.fox5dc.com/photo/2017/10/20/snowfall_generic_1508527212596_4392735_ver1.0_640_360.jpg",
        "https://resources.stuff.co.nz/content/dam/images/1/k/c/w/r/1/image.related.StuffLandscapeSixteenByNine.620x349.1kceu5.png/1499883552682.jpg",
        "https://static01.nyt.com/images/2017/01/12/world/12IstanbulSnow1/12IstanbulSnow1-superJumbo.jpg",
        "https://i.ytimg.com/vi/Mndn7ogRP6o/hqdefault.jpg"};

    private String[] clearList = {"http://pasadenanow.com/main/wp-content/uploads/2016/10/crop13.png",
        "https://weatherconversation.files.wordpress.com/2012/08/aug-22-oakville-burlington-sunny-2012-001.jpg",
        "http://www.wallpapermania.eu/download/2014-01/6713/Wonderful-nature-landscape-hot-spring-day_5120x3200.jpg",
        "http://www.weatherforecast.co.za/images/easyblog_articles/57/b2ap3_large_clear_d-1394274.jpg",
        "https://icons.wxug.com/data/wximagenew/k/Klockheed/2.jpg"};

    private String[] cloudsList = {"https://static.pexels.com/photos/417045/pexels-photo-417045.jpeg",
        "http://www.weatherwizkids.com/wp-content/uploads/2015/02/fractus-clouds.jpg",
        "https://c1.staticflickr.com/1/216/499142064_88f57ac54d_b.jpg",
        "https://goseedrcloud.com/wp-content/uploads/2015/05/Sky-Clouds-Background-11.jpg",
        "http://miriadna.com/desctopwalls/images/max/Big-cloud-formation.jpg"};

    @SerializedName("url")
    private String url;

    @SerializedName("eror")
    private String error;

    String getUrl(final String weatherMain) {

        switch (weatherMain) {
            case "Rain":
                url = rainList[new Random().nextInt(rainList.length)];
                break;

            case "Snow":
                url = snowList[new Random().nextInt(snowList.length)];
                break;

            case "Clear":
                url = clearList[new Random().nextInt(clearList.length)];
                break;

            case "Clouds":
                url = cloudsList[new Random().nextInt(cloudsList.length)];
                break;
        }
        return url;
    }

    public String getError() {
        return error;
    }

    public int getCurrent_app_version() {
        return current_app_version;
    }

}
