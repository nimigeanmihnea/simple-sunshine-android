package com.p3_group.sunshine;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.p3_group.sunshine.data.WeatherContract;
import com.p3_group.sunshine.data.WeatherDbHelper;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;
import java.util.Set;

import static com.p3_group.sunshine.DetailActivityFragment.LOG_TAG;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(AndroidJUnit4.class)
public class TestDb{

    private Context context = InstrumentationRegistry.getTargetContext();
    private String testName = "North Pole";

    @Test
    public void testCreateDb(){
        context.deleteDatabase(WeatherDbHelper.DB_NAME);
        SQLiteDatabase db = new WeatherDbHelper(context).getWritableDatabase();
        assertEquals (true, db.isOpen());
        db.close();
    }

    @Test
    public void testInsertReadDb(){
        WeatherDbHelper dbHelper = new WeatherDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = getLocationContentValues();

        long id = db.insert(WeatherContract.LocationEntry.TABLE_NAME, null, values);

        assertTrue(id != -1);
        Log.d(LOG_TAG, "New row id: " + id);

        Cursor cursor = db.query(
                WeatherContract.LocationEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null,
                null);

        if(cursor.moveToFirst()){
            validateCursor(values, cursor);
        }else {
            fail("No values returned");
        }
    }

    public ContentValues getLocationContentValues(){
        String testLocationSetting = "99705";
        double testLat = 64.772;
        double testLong = -147.335;

        ContentValues values = new ContentValues();
        values.put(WeatherContract.LocationEntry.COLUMN_CITY_NAME, testName);
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
