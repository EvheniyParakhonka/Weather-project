package piftik.github.com.weatherproject.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import piftik.github.com.weatherproject.utils.ContextHolder;

abstract class PojoExecutor<Model> extends AsyncTask<Request<Model>, Void, Result> {
    private final IOnTaskCompleted listener;
    @SuppressLint("StaticFieldLeak")
    private final Context context;

    PojoExecutor(final IOnTaskCompleted listener) {
        this.listener = listener;
        context = ContextHolder.getInstance().getContext();
    }

    @SafeVarargs
    @Override
    protected final Result doInBackground(final Request<Model>... requests) {
        Result result = null;
        switch (requests[0].getId()) {
            case KEY_ADD:
                result = addPojo(requests[0].getObject());
                break;
            case KEY_DELETE:
                result = deletePojo((Long) requests[0].getObject());
                break;
            case KEY_GET:
                result = getPojo((Long) requests[0].getObject());
                break;
        }
        return result;
    }


    protected abstract Result<Model> getPojo(long id);

    protected abstract Result<Long> addPojo(Model model);

    protected abstract Result<Integer> deletePojo(long id);


    @Override
    protected void onPostExecute(final Result result) {
        if (listener != null) {
            listener.onTaskCompleted(result);
        }
    }
}
