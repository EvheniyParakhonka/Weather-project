package piftik.github.com.weatherproject.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import piftik.github.com.weatherproject.R;
import piftik.github.com.weatherproject.activity.WeatherListMainActivity;
import piftik.github.com.weatherproject.adapters.WeatherAdapterRecyclerView;
import piftik.github.com.weatherproject.base.IOnTaskCompleted;
import piftik.github.com.weatherproject.base.RequestAdapter;
import piftik.github.com.weatherproject.base.Result;
import piftik.github.com.weatherproject.base.WeatherExecutor;
import piftik.github.com.weatherproject.models.Weather;
import piftik.github.com.weatherproject.request.IForecastLoader;
import piftik.github.com.weatherproject.utils.Constants;
import piftik.github.com.weatherproject.utils.GetSmallImage;

import static android.content.ContentValues.TAG;

//import piftik.github.com.weatherproject.base.WeatherCursorLoader;

public class WeatherListFragment extends BaseFragment implements IOnTaskCompleted {

    private RecyclerView mWeatherRecyclerView;
    private IForecastLoader mForecastLoader;
    private MyIForecastLOaderListener mListener;
    private WeatherListMainActivity mWeatherListMainActivity;
    private Handler mUiHandler;
    private View mMProgress;
    private TextView mTempToday;
    private TextView mDateToday;
    private TextView mWeatherMainToday;
    private ImageView mImageViewSmallToday;
    private double mLatitude;
    private double mLongitude;
    private String mCityName;
    private long mCityID;
    private RequestAdapter<Weather> mRequestAdapter;
    private ArrayList<Weather> mWeathers;


    public static WeatherListFragment newInstance(final String pCityName, final double pLatitude, final double pLongitude) {
        final Bundle bundle = new Bundle();
        bundle.putDouble(Constants.BUNDLE_LATITUDE_KEY, pLatitude);
        bundle.putDouble(Constants.BUNDLE_LONGITUDE_KEY, pLongitude);
        bundle.putString(Constants.BUNDLE_CITY_NAME_KEY, pCityName);
        final WeatherListFragment weatherList = new WeatherListFragment();
        weatherList.setArguments(bundle);
        return weatherList;
    }

    @Override
    public void onCreate(@Nullable final Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        setRetainInstance(true);

        mForecastLoader = IForecastLoader.Impl.getInstance();
        mListener = new MyIForecastLOaderListener();
        mUiHandler = new Handler(Looper.getMainLooper());

        final Bundle bundle = getArguments();

        if (bundle != null) {
            mLatitude = bundle.getDouble(Constants.BUNDLE_LATITUDE_KEY);
            mLongitude = bundle.getDouble(Constants.BUNDLE_LONGITUDE_KEY);
            mCityName = bundle.getString(Constants.BUNDLE_CITY_NAME_KEY);
            mCityID = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE).getLong(mCityName, 0);
        }

    }


    @Override
    public void onResume() {
        super.onResume();
        getFromDB();
        getWeatherAsync(mCityName, mLatitude, mLongitude);

    }

    private void getFromDB() {
        mWeathers = new ArrayList<>();
//        List wether have 10 fonts
        final int sizeFont = 9;
        for (int i = 0; i < sizeFont; i++) {
            final Result<Weather> weathers = new WeatherExecutor(this).getPojo(mCityID + i);
            mWeathers.add(weathers.getObject());

        }
        if (mWeathers.get(0) != null) {
            showWeather(mWeathers);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mForecastLoader.addListener(mListener);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater pInflater, @Nullable final ViewGroup pContainer, @Nullable final Bundle pSavedInstanceState) {
        final View view = pInflater.inflate(R.layout.weather_list_fragment, pContainer, false);

        mTempToday = (TextView) view.findViewById(R.id.temperature_text_view_today);
        mDateToday = (TextView) view.findViewById(R.id.date_time_text_view_today);
        mWeatherMainToday = (TextView) view.findViewById(R.id.weather_main_text_view_today);
        mImageViewSmallToday = (ImageView) view.findViewById(R.id.small_image_weather_today);
        mWeatherRecyclerView = (RecyclerView) view.findViewById(R.id.weather_recycler_view);
        mWeatherRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mWeatherRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL));

        mMProgress = view.findViewById(R.id.progress);
        mMProgress.setVisibility(View.VISIBLE);

        return view;
    }

    @Override
    public String getTitle() {
        return mCityName;
    }

    @Override
    public boolean whatAFragment() {
        return true;
    }

    @Override
    public double whatALat() {
        return mLatitude;
    }

    @Override
    public double whatALong() {
        return mLongitude;
    }


    @Override
    public void onTaskCompleted(final Result pResult) {

    }


    private class MyIForecastLOaderListener implements IForecastLoader.IForecastLOaderListener, IOnTaskCompleted {

        @SuppressLint("SetTextI18n")
        @Override
        public void onSuccess(final List<Weather> pWeathers) {
            mRequestAdapter = new RequestAdapter<>();


            if (pWeathers != null && !pWeathers.isEmpty()) {
                for (int i = 0; i < pWeathers.size(); i++) {
                    Log.d(TAG, "onSuccess: " + pWeathers.get(i).getWeatherMain());
                    mCityID = pWeathers.get(i).getId();
                    pWeathers.get(i).setId(mCityID + i);
                    new WeatherExecutor(this).execute(mRequestAdapter.add(pWeathers.get(i)));
                    mRequestAdapter.add(pWeathers.get(i));

                }
                getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE).
                    edit().putLong(mCityName, pWeathers.get(0).getId()).apply();
            }
        }

        @Override
        public void onError(final int pErrorCode) {
            mUiHandler.post(new Runnable() {

                @Override
                public void run() {
                    showEmptyView(pErrorCode);
                }
            });
        }

        @Override
        public void onTaskCompleted(final Result pResult) {
        getFromDB();
        mMProgress.setVisibility(View.GONE);
        }
    }

    private void getWeatherAsync(final String pCityId, final double pLatitude, final double pLongitude) {
        mForecastLoader.getForecast(pCityId, pLatitude, pLongitude);
    }

    @Override
    public void onStop() {
        super.onStop();

        mForecastLoader.removeListener(mListener);
    }

    private void showEmptyView(final int pErrorCode) {
        mMProgress.setVisibility(View.GONE);
        Toast.makeText(getContext(), "empty " + pErrorCode, Toast.LENGTH_LONG).show();
    }

    @SuppressLint("SetTextI18n")
    private void showWeather(final ArrayList<Weather> pWeathers) {

        if (pWeathers != null && !pWeathers.isEmpty()) {
            for (int i = 0; i < pWeathers.size(); i++) {
                Log.d(TAG, "onSuccess: " + pWeathers.get(i).getWeatherMain());
                final WeatherAdapterRecyclerView weatherAdapter = new WeatherAdapterRecyclerView(getContext(), pWeathers);
                mWeatherRecyclerView.setAdapter(weatherAdapter);

            }
            mDateToday.setText(pWeathers.get(0).getDate());
            mTempToday.setText(String.valueOf(pWeathers.get(0).getTempMin()) + " \u00B0" + "C");
            mWeatherMainToday.setText(pWeathers.get(0).getWeatherMain());
            mImageViewSmallToday.setImageResource(GetSmallImage.getResousrceIDForWeatherSmallImage(pWeathers.get(0).getWeatherMain()));
        }
    }
}
