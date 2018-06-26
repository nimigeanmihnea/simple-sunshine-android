package com.p3_group.sunshine;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.p3_group.sunshine.data.WeatherContract;
import com.p3_group.sunshine.data.WeatherDbHelper;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;
import java.util.Set;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TestProvider {

    private Context context = InstrumentationRegistry.getTargetContext();
    public static String TEST_CITY_NAME = "North Pole";
    public static String TEST_LOCATION = "99705";
    public static String TEST_DATE = "20140612";

    @Test
    public void testDeleteAllRecords(){
        context.getContentResolver().delete(
                WeatherContract.WeatherEntry.CONTENT_URI,
                null,
                null
        );

        context.getContentResolver().delete(
                WeatherContract.LocationEntry.CONTENT_URI,
                null,
                null
        );

        Cursor cursor = context.getContentResolver().query(
                WeatherContract.WeatherEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
        assertEquals(cursor.getCount(), 0);
        cursor.close();

        cursor = context.getContentResolver().query(
                WeatherContract.LocationEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
        assertEquals(cursor.getCount(), 0);
        cursor.close();
    }

    @Test
    public void testInsertReadProvider(){
        context.deleteDatabase(WeatherDbHelper.DB_NAME);
        ContentValues values = getLocationContentValues();

        Uri uri = context.getContentResolver().insert(WeatherContract.LocationEntry.CONTENT_URI, values);

        Cursor cursor = context.getContentResolver().query(
                WeatherContract.LocationEntry.CONTENT_URI,
                null,
                null,
                null,
                null);

        if(cursor.moveToFirst()){
            validateCursor(values, cursor);
            ContentValues weatherValues = getWeatherContentValues(ContentUris.parseId(uri));

            Uri insertedUri = context.getContentResolver().insert(WeatherContract.WeatherEntry.CONTENT_URI, weatherValues);

            Cursor weatherCursor = context.getContentResolver().query(WeatherContract.WeatherEntry.CONTENT_URI,
                    null,
                    null,
                    null,
                    null);
            if(weatherCursor.moveToFirst()){
                validateCursor(weatherValues, weatherCursor);
            }else {
                fail("No weather data returned!");
            }

            weatherCursor.close();

            weatherCursor = context.getContentResolver().query(WeatherContract.WeatherEntry.buildWeatherLocation(TEST_LOCATION),
                    null,
                    null,
                    null,
                    null);
            if(weatherCursor.moveToFirst()){
                validateCursor(weatherValues, weatherCursor);
            }else {
                fail("No weather data returned!");
            }

            weatherCursor.close();

            weatherCursor = context.getContentResolver().query(WeatherContract.WeatherEntry.buildWeatherLocationWithStartDate(TEST_LOCATION, TEST_DATE),
                    null,
                    null,
                    null,
                    null);
            if(weatherCursor.moveToFirst()){
                validateCursor(weatherValues, weatherCursor);
            }else {
                fail("No weather data returned!");
            }

            weatherCursor.close();

            weatherCursor = context.getContentResolver().query(WeatherContract.WeatherEntry.buildWeatherLocationWithDate(TEST_LOCATION, TEST_DATE),
                    null,
                    null,
                    null,
                    null);
            if(weatherCursor.moveToFirst()){
                validateCursor(weatherValues, weatherCursor);
            }else {
                fail("No weather data returned!");
            }

        }else {
            fail("No values returned");
        }
    }

    @Test
    public void testGetType(){
        String type = context.getContentResolver().getType(WeatherContract.WeatherEntry.CONTENT_URI);
        assertEquals(WeatherContract.WeatherEntry.CONTENT_TYPE, type);

        String testLocation = "94074";
        type = context.getContentResolver().getType(WeatherContract.WeatherEntry
                .buildWeatherLocation(testLocation));
        assertEquals(WeatherContract.WeatherEntry.CONTENT_TYPE, type);

        String testDate = "20140612";
        type = context.getContentResolver().getType(WeatherContract.WeatherEntry
                .buildWeatherLocationWithDate(testLocation, testDate));
        assertEquals(WeatherContract.WeatherEntry.CONTENT_ITEM_TYPE, type);

        type = context.getContentResolver().getType(WeatherContract.LocationEntry.CONTENT_URI);
        assertEquals(WeatherContract.LocationEntry.CONTENT_TYPE, type);

        type = context.getContentResolver().getType(WeatherContract.LocationEntry.buildLocationUri(1L));
        assertEquals(WeatherContract.LocationEntry.CONTENT_ITEM_TYPE, type);
    }

    @Test
    public void testUpdateLocation(){
        testDeleteAllRecords();

        ContentValues values = getLocationContentValues();

        Uri locationUri = context.getContentResolver()
                .insert(WeatherContract.LocationEntry.CONTENT_URI, values);
        long id = ContentUris.parseId(locationUri);

        assertTrue(id != -1);
        ContentValues values1 = new ContentValues(values);
        values1.put(WeatherContract.LocationEntry._ID, id);
        values1.put(WeatherContract.LocationEntry.COLUMN_CITY_NAME, "Santa's Village");

        int count = context.getContentResolver()
                .update(WeatherContract.LocationEntry.CONTENT_URI, values1, WeatherContract.LocationEntry._ID,
                        new String[] {Long.toString(id)});
        assertEquals(count, 1);

        Cursor cursor = context.getContentResolver().query(
                WeatherContract.LocationEntry.buildLocationUri(id),
                null,
                null,
                null,
                null);
        if(cursor.moveToFirst())
            validateCursor(values1, cursor);
        cursor.close();
    }

    public ContentValues getWeatherContentValues(long id){
        ContentValues values = new ContentValues();
        values.put(WeatherContract.WeatherEntry.COLUMN_LOC_KEY, id);
        values.put(WeatherContract.WeatherEntry.COLUMN_DATETEXT, TEST_DATE);
        values.put(WeatherContract.WeatherEntry.COLUMN_DEGREES, 1.1);
        values.put(WeatherContract.WeatherEntry.COLUMN_HUMIDITY, 1.2);
        values.put(WeatherContract.WeatherEntry.COLUMN_PRESSURE, 1.3);
        values.put(WeatherContract.WeatherEntry.COLUMN_MAX_TEMP, 75);
        values.put(WeatherContract.WeatherEntry.COLUMN_MIN_TEMP, 65);
        values.put(WeatherContract.WeatherEntry.COLUMN_SHORT_DESC, "Asteroids");
        values.put(WeatherContract.WeatherEntry.COLUMN_WIND_SPEED, 5.5);
        values.put(WeatherContract.WeatherEntry.COLUMN_WEATHER_ID, 321);

        return values;
    }

    public ContentValues getLocationContentValues(){
        String testLocationSetting = TEST_LOCATION;
        double testLat = 64.772;
        double testLong = -147.335;

        ContentValues values = new ContentValues();
        values.put(WeatherContract.LocationEntry.COLUMN_CITY_NAME, TEST_CITY_NAME);
        values.put(WeatherContract.LocationEntry.COLUMN_LOCATION_SETTING, testLocationSetting);
        values.put(WeatherContract.LocationEntry.COLUMN_COORD_LAT, testLat);
        values.put(WeatherContract.LocationEntry.COLUMN_COORD_LONG, testLong);

        return values;
    }

    public static void validateCursor(ContentValues expectedValues, Cursor values){
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();

        for(Map.Entry<String, Object> entry : valueSet){
            String columnName = entry.getKey();
            int id = values.getColumnIndex(columnName);
            assertFalse(-1 == id);

            String expectedValue = entry.getValue().toString();
            assertEquals(expectedValue, values.getString(id));
        }
    }
}
