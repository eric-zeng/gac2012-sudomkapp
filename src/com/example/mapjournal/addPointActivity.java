package com.example.mapjournal;
import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

/**
 * The activity handles new point creating and inserting it into the database
 * @author Leo Dihong Gao
 *
 */
public class addPointActivity extends Activity {
    public static final String PREFS_NAME = "PrefsFile";
	double longitude, latitude;
	public static final String POINT_ID = "com.example.mapjournal.addPointActivity.POINT_ID";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_point);
        Intent intent = getIntent();
        longitude = intent.getDoubleExtra(MapJournalMapActivity.LONGITUDE, Integer.MAX_VALUE);
        latitude = intent.getDoubleExtra(MapJournalMapActivity.LATITUDE, Integer.MAX_VALUE);        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_add_point, menu);
        return true;
    }
    /**
     * add the point into the database
     * @param view
     * @return
     */
    public int onAddClick(View view){
    	String name = ((EditText)findViewById(R.id.name)).getText().toString();
    	String note = ((EditText)findViewById(R.id.notes)).getText().toString();
		
	    //Retrieve current trip from preferences 
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, 0);
        String currentTrip = prefs.getString("current", null);
		DBOpenHelper db = new DBOpenHelper(this);
		long id = db.addPoint(new Point(name, currentTrip, (int)(latitude*1E6), (int)(longitude*1E6), Calendar.getInstance().getTimeInMillis(), note));
				
		Intent intent = new Intent(this, MapJournalMapActivity.class);
		intent.putExtra(POINT_ID, id);
		intent.putExtra(MapJournalMapActivity.LATITUDE, latitude);
		intent.putExtra(MapJournalMapActivity.LONGITUDE, longitude);				
		startActivity(intent);
    	return 0;
    }
    
}
