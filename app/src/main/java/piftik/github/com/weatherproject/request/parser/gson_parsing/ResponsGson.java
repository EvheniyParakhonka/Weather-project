package piftik.github.com.weatherproject.request.parser.gson_parsing;

import java.util.List;

class ResponsGson {
    private final CityGson city;
    private final List<DaysGson> list;

    public ResponsGson(final CityGson pCity, final List<DaysGson> pList) {
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
