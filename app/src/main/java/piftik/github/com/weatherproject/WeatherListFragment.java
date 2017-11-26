package piftik.github.com.weatherproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import piftik.github.com.weatherproject.adapter.WeatherAdapterRecyclerView;
import piftik.github.com.weatherproject.models.Weather;
import piftik.github.com.weatherproject.request.IForecastLoader;
import piftik.github.com.weatherproject.utils.Constants;
import piftik.github.com.weatherproject.utils.GetSmallImage;

import static android.content.ContentValues.TAG;

public class WeatherListFragment extends Fragment {

    private RecyclerView mWeatherRecyclerView;
    private IForecastLoader mForecastLoader;
    private MyIForecastLOaderListener mListener;
    private WeatherListMainActivity mWeatherListMainActivity;
    private Handler mUiHandler;
    private View mMProgress;
    private String mCityID;
    private String mCityIDResult;
    private View mButton;
    private TextView mTempToday;
    private TextView mDateToday;
    private TextView mWeatherMainToday;
    private ImageView mImageViewSmallToday;
    private OnNewLocationSelectedListnener mOnNewLocationSelectedListnener;

    public void setOnNewLocationSelectedListnener(final OnNewLocationSelectedListnener pOnNewLocationSelectedListnener) {
        mOnNewLocationSelectedListnener = pOnNewLocationSelectedListnener;
    }

    public static WeatherListFragment newInstance(final String pCityID) {
        final Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_CITY_ID, pCityID);
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
            mCityID = bundle.getString(Constants.BUNDLE_CITY_ID);
        }

    }


    @Override
    public void onResume() {
        super.onResume();
        getWeatherAsync(mCityID);
    }

    @Override
    public void onStart() {
        super.onStart();
        mForecastLoader.addListener(mListener);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater pInflater, @Nullable final ViewGroup pContainer, @Nullable final Bundle pSavedInstanceState) {
        final View view = pInflater.inflate(R.layout.fragment_weather_list, pContainer, false);

        mTempToday = (TextView) view.findViewById(R.id.temperature_text_view_today);
        mDateToday = (TextView) view.findViewById(R.id.date_time_text_view_today);
        mWeatherMainToday = (TextView) view.findViewById(R.id.weather_main_text_view_today);
        mImageViewSmallToday = (ImageView) view.findViewById(R.id.small_image_weather_today);
        mWeatherRecyclerView = (RecyclerView) view.findViewById(R.id.weather_recycler_view);
        mWeatherRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mWeatherRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL));

        mMProgress = view.findViewById(R.id.progress);
        mButton = view.findViewById(R.id.added_new_fragment_button);
        mMProgress.setVisibility(View.VISIBLE);
        mButton.setOnClickListener(new OnAddNewLocationClickListnener());

        return view;
    }




    private class MyIForecastLOaderListener implements IForecastLoader.IForecastLOaderListener {

        @SuppressLint("SetTextI18n")
        @Override
        public void onSuccess(final ArrayList<Weather> pWeathers) {

            mMProgress.setVisibility(View.GONE);
            if (pWeathers != null && !pWeathers.isEmpty()) {
                for (final Weather weather : pWeathers) {
                    Log.d(TAG, "onSuccess: " + weather.getWeatherMain());
                    final WeatherAdapterRecyclerView weatherAdapter = new WeatherAdapterRecyclerView(getContext(), pWeathers);
                    mWeatherRecyclerView.setAdapter(weatherAdapter);

                }
                mDateToday.setText(pWeathers.get(0).getDate());
                mTempToday.setText(String.valueOf(pWeathers.get(0).getTempMin()) + " \u00B0" + "C");
                mWeatherMainToday.setText(pWeathers.get(0).getWeatherMain());
                mImageViewSmallToday.setImageResource(GetSmallImage.getResousrceIDForWeatherSmallImage(pWeathers.get(0).getWeatherMain()));
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

    }

    private void getWeatherAsync(final String pStingId) {
        mForecastLoader.getForecast(pStingId);
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

    public interface OnNewLocationSelectedListnener{
        void onNewLocationSelected(Context pContext);
    }

    private class OnAddNewLocationClickListnener implements View.OnClickListener {
        @Override
        public void onClick(final View pView) {
            if (mOnNewLocationSelectedListnener != null){
                mOnNewLocationSelectedListnener.onNewLocationSelected(getContext());
            }
        }
    }
}
