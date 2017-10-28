package piftik.github.com.weatherproject.backend;

//class AsyncEndpoint extends AsyncTask<String, Object, ArrayList<piftik.github.com.weatherproject.Weather>> {
//    private static final String TAG = AsyncEndpoint.class.getSimpleName();
//    private String createURLRequest(final String pCityId) {
//
//        return BuildConfig.BASE_URL + pCityId;
//    }
//
//    @Override
//    protected ArrayList<piftik.github.com.weatherproject.Weather> doInBackground(final String... pCityId) {
//        final IHttpClient iHttpClient = new HttpClient();
//        final IGsonParserFromMyBackend iGsonParser = new GsonParserFromMyBackend();
//        final String url = createURLRequest(pCityId[0]);
//        ArrayList<piftik.github.com.weatherproject.Weather> jsonResponse = null;
//        try {
//
//            final String inputStream = iHttpClient.makeHttpRequest(url);
//            if (inputStream == null) {
//                final RequestFromOpenWeather requestFromOpenWeather = new RequestFromOpenWeather();
//                requestFromOpenWeather.createAsync(pCityId[0]);
//            }else {
//                jsonResponse = iGsonParser.extractWeatherFromJsonMyBackend(inputStream);
//            }
//        } catch (final Exception pE) {
//            Log.e(TAG, pE.toString());
//        }
//        return jsonResponse;
//    }
//
//    @Override
//    protected void onPostExecute(ArrayList<Weather> pWeathers) {
//        super.onPostExecute(pWeathers);
//    }
//}

