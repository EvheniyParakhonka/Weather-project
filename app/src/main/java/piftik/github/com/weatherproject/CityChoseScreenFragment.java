package piftik.github.com.weatherproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class CityChoseScreenFragment extends VisibleFragment {

    private Spinner mSpinner;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.city_chose_screen, container, false);


        mSpinner = (Spinner) view.findViewById(R.id.spiner_city);
        final Button getWeatherButton = (Button) view.findViewById(R.id.button_get_wether);

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.city, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        getWeatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final String cityID = mSpinner.getSelectedItem().toString();
                final Fragment fragment = new WeatherListFragment();
                final Bundle bundle = new Bundle();
                bundle.putString("CITY_ID", cityID);
                fragment.setArguments(bundle);
                final FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
            }
        });

        return view;
    }
}
