package piftik.github.com.weatherproject.request.parser.gson_parsing;

public class TempGson {
    private final double temp;

    public double getTemp() {
        return temp;
    }

    public TempGson(final double pTemp) {
        temp = pTemp;
    }
}
