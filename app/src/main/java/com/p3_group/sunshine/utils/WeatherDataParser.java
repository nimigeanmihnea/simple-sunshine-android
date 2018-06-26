package com.p3_group.sunshine.utils;

public class WeatherDataParser {

    public  WeatherDataParser(){}

//    private double getMaxTemoperatureForDay(String weatherJsonString, int dayIndex) throws JSONException{
//        JSONObject weather = new JSONObject(weatherJsonString);
//        JSONArray days = weather.getJSONArray("list");
//        JSONObject dayInfo = days.getJSONObject(dayIndex);
//        JSONObject temperatureInfo = dayInfo.getJSONObject("temp");
//
//        return temperatureInfo.getDouble("max");
//    }

//    private String getReadableDateString(long time){
//        Date date = new Date(time * 1000);
//        SimpleDateFormat format = new SimpleDateFormat("E, MMM, d");
//        return format.format(date).toString();
//    }
//
//    private String formatHighsLows(double high, double low){
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
//        return roundedLow + "/" + roundedHigh;
//    }
//
//    public String[] getWeatherDataFromJson(String forecastJsonString, int days, String LOG_TAG) throws JSONException{
//        final String OWM_LIST = "list";
//        final String OWM_WEATHER = "weather";
//        final String OWM_TEMPERATURE = "temp";
//        final String OWM_MAX = "max";
//        final String OWM_MIN = "min";
//        final String OWM_DATETIME = "dt";
//        final String OWM_DESCRIPTION = "main";
//
//        JSONObject forecastJson = new JSONObject(forecastJsonString);
//        JSONArray weatherArray = forecastJson.getJSONArray(OWM_LIST);
//
//        String[] result = new String[days];
//        for(int i = 0; i < weatherArray.length(); i++){
//            String day;
//            String description;
//            String highAndLow;
//
//            JSONObject dayForecast = weatherArray.getJSONObject(i);
//
//            long dateTime = dayForecast.getLong(OWM_DATETIME);
//            day = getReadableDateString(dateTime);
//
//            JSONObject weatherObject = dayForecast.getJSONArray(OWM_WEATHER).getJSONObject(0);
//            description = weatherObject.getString(OWM_DESCRIPTION);
//
//            JSONObject temperatureObject = dayForecast.getJSONObject(OWM_TEMPERATURE);
//            double high = temperatureObject.getDouble(OWM_MAX);
//            double low = temperatureObject.getDouble(OWM_MIN);
//            highAndLow = formatHighsLows(high, low);
//
//            result[i] = day + " - " + description + " - " + highAndLow;
//        }
//
//        for(String s : result){
//            Log.v(LOG_TAG, "Forecast entry: " + s);
//        }
//
//        return result;
//    }

}
