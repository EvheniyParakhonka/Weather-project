package piftik.github.com.weatherproject.request.parser.gson_parsing;

public class CityGson {

    private String name;
    private String country;

    public String getName() {
        return name;
    }

    public void setName(String pName) {
        name = pName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String pCountry) {
        country = pCountry;
    }

    public CityGson(String pName, String pCountry) {
        name = pName;
        country = pCountry;
    }
}
