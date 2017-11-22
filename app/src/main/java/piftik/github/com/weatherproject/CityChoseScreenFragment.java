package piftik.github.com.weatherproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class CityChoseScreenFragment extends VisibleFragment {


    @Nullable

    @Override
    public View onCreateView(final LayoutInflater pInflater, @Nullable final ViewGroup pContainer, @Nullable final Bundle pSavedInstanceState) {
        final View view = pInflater.inflate(R.layout.city_chose_screen, pContainer, false);

        final Spinner mSpinner = (Spinner) view.findViewById(R.id.spiner_city);
        final Button getWeatherButton = (Button) view.findViewById(R.id.button_get_wether);

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
            R.array.city, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        getWeatherButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                final String cityID = mSpinner.getSelectedItem().toString();
                CityChoseScreenFragment.super.replace(cityID);

            }
        });

        return view;
    }

}
