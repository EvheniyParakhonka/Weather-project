package piftik.github.com.weatherproject.request.parser.GsonParsing;

import java.util.List;

public class ResponsGson {
    private CityGson city;
    private List<DaysGson> list;

    public ResponsGson(CityGson pCity, List<DaysGson> pList) {
        city = pCity;
        list = pList;
    }

    public List<DaysGson> getList() {
        return list;
    }

    public CityGson getCity() {
        return city;
    }

}
