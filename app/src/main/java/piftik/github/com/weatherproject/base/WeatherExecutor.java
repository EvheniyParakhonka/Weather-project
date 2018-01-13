package piftik.github.com.weatherproject.base;

import piftik.github.com.weatherproject.App;
import piftik.github.com.weatherproject.models.Weather;
import piftik.github.com.weatherproject.models.dbHelpers.DBHelpersWeather;
import piftik.github.com.weatherproject.utils.ContextHolder;

public class WeatherExecutor extends PojoExecutor<Weather> {
    private final DBHelpersWeather mHelpersWeather = (DBHelpersWeather) ((App) ContextHolder.getInstance().getContext()).getDbHelper(Weather.class);

    private static final int KEY_RESULT_ADD = 301;
    private static final int KEY_RESULT_DELETE = 303;
    private static final int KEY_RESULT_GET = 304;

    public WeatherExecutor(final IOnTaskCompleted listener) {
        super(listener);
    }

    @Override
    public Result<Weather> getPojo(final long pId) {
        return new Result<>(KEY_RESULT_GET, mHelpersWeather.get(pId));
    }

    @Override
    public Result<Long> addPojo(final Weather pWeather) {
        return new Result<>(KEY_RESULT_ADD, mHelpersWeather.add(pWeather));
    }

    @Override
    public Result<Integer> deletePojo(final long pId) {
        return new Result<>(KEY_RESULT_DELETE, mHelpersWeather.delete(pId));
    }
}
