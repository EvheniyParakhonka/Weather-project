package piftik.github.com.weatherproject.update;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import piftik.github.com.weatherproject.BuildConfig;
import piftik.github.com.weatherproject.R;

public class UpdateDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.titleToDialog)
                .setMessage(R.string.messageToUpdateDialog)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(final DialogInterface pDialogInterface, final int pI) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.UPDATE_URL)));
                    }
                })
                .create();
    }

}
