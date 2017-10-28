package piftik.github.com.weatherproject.request.parser.gson_parsing;

class CityGson {

    private String name;
    private String country;

    public String getName() {
        return name;
    }

    public void setName(final String pName) {
        name = pName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final String pCountry) {
        country = pCountry;
    }

    public CityGson(final String pName, final String pCountry) {
        name = pName;
        country = pCountry;
    }
}
