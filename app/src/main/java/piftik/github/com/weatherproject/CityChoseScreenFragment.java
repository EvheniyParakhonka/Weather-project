package piftik.github.com.weatherproject;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class CityChoseScreenFragment extends Fragment {

    private OnNewPageCityChosseAddListnener mOnNewLocationSelectedListnener;

    public static CityChoseScreenFragment newInstance() {
        return new CityChoseScreenFragment();
    }

    public void setOnNewPageCityChooseAdd(final OnNewPageCityChosseAddListnener pOnNewLocationSelectedListnener) {
        mOnNewLocationSelectedListnener = pOnNewLocationSelectedListnener;
    }

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
            public void onClick(final View pView) {
                if (mOnNewLocationSelectedListnener != null) {
                    mOnNewLocationSelectedListnener.onNewPageCityChosseAdd(mSpinner.getSelectedItem().toString());
                }
            }
        });
        return view;


    }


    public interface OnNewPageCityChosseAddListnener {
        void onNewPageCityChosseAdd(String pCityId);
    }
}
