package piftik.github.com.weatherproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import piftik.github.com.weatherproject.utils.Constants;

public class CityChoseScreenFragment extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_chose_screen);
        final Spinner mSpinner = (Spinner) findViewById(R.id.spiner_city);
        final Button getWeatherButton = (Button) findViewById(R.id.button_get_wether);

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
            R.array.city, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        getWeatherButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v){
                final String cityID = mSpinner.getSelectedItem().toString();
                Intent intent = new Intent();
                intent.putExtra(Constants.BUNDLE_CITY_ID,cityID);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

//    @Nullable
//
//    @Override
//    public View onCreateView(final LayoutInflater pInflater, @Nullable final ViewGroup pContainer, @Nullable final Bundle pSavedInstanceState) {
//        final View view = pInflater.inflate(R.layout.city_chose_screen, pContainer, false);
//
//        final Spinner mSpinner = (Spinner) view.findViewById(R.id.spiner_city);
//        final Button getWeatherButton = (Button) view.findViewById(R.id.button_get_wether);
//
//        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
//            R.array.city, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        mSpinner.setAdapter(adapter);
//
//        getWeatherButton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(final View v) {
//                final String cityID = mSpinner.getSelectedItem().toString();
//                final Fragment fragment = new WeatherListFragment();
//                final Bundle bundle = new Bundle();
//                bundle.putString("CITY_ID", cityID);
//                fragment.setArguments(bundle);
//                final FragmentManager fragmentManager = getFragmentManager();
//                fragmentManager.beginTransaction().replace(getView().getId(), fragment).addToBackStack(null).commit();
//            }
//        });
//
//        return view;
//    }
}
